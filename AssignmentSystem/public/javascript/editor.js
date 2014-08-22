$(document).ready(function () {
    ace.require("ace/ext/language_tools"); // Required for auto completion

    var editors = initEditors();
    var webSocket = openNewWebSocket();
    initCollapsibleHeaders();

    var completionCallback; // FIXME: Create a better solution

    webSocket.onopen = function() {
        console.log('ws connected ('+jsRoutes.controllers.AssignmentController.openEditorSocket(getCurrentProblemID()).webSocketURL()+')');
        sendMessage("notifyOnReady");
    };

    webSocket.onerror = function() {
        console.log('ws error');
    };

    webSocket.onclose = function() {
        console.log('ws closed');
    };

    webSocket.onmessage = function(msgevent) {

        var object = JSON.parse(msgevent.data);
        console.log(object);

        if (object.type === 'ready') {
            console.timeEnd("notifyOnReady");
            $('#editor-status').addClass('ready');
        }

        else if (object.type === 'runMainResult') {
            console.timeEnd("runMain");
            $("#editor-status").toggleClass("fa-circle fa-circle-o-notch running ready");
            $('.ace-editor-console').text(object.data.output);
        }

        else if (object.type === 'runTestsResult') {
            console.timeEnd("runTests");
            $("#editor-status").toggleClass("fa-circle fa-circle-o-notch running ready");
            $('#test-table-body').empty();
            $(object.data.testResults).each(function(){
                $('#test-table-body').append(buildTestRow(this));
            });
            $("#test-summary").text(getSummary(object.data.testResults))
            $('#test-modal').modal('show');
        }

        else if (object.type === 'codeCompletionResult') {
            console.timeEnd("codeCompletion");
            var proposals = object.data.proposals.map(function (proposal, index) {
               return {
                   value: proposal.completion,
                   score: -index
               }
            });
            completionCallback(null, proposals);
        }

        else if (object.type === 'errorCheckingResult') {
            console.timeEnd("updateSourceCode");
            object.data.files.forEach(function (file) {
                var foundEditors = editors.filter(function (editor) {
                    return (file.fileId === getFileId(editor));
                });
                if (foundEditors.length === 1) {
                    var annotations = file.problemMarkers.map(function(problem) {
                        return new Annotation(problem.lineNumber - 1, problem.description, convertType(problem.type));
                    });
                    var editor = foundEditors[0];
                    editor.getSession().setAnnotations(annotations);
                    setClassMarker("#tabForFileId"+file.fileId, file.problemMarkers);
                }
            });
        }

    };

    function setClassMarker(tabForFileId, problemMarkers) {
        $(tabForFileId).removeClass("warning error");
        var errors = problemMarkers.map(function (problemMarker) { return convertType( problemMarker.type ); })
        if(arrayContains(errors, "error")){$(tabForFileId).addClass("error")}
        if(arrayContains(errors, "warning")){$(tabForFileId).addClass("warning")}
    }

    $(editors).each(function() {
        var editor = this;
        this.on('change', function() {
            throttle(function(){//this code is called 300ms after the last change-event
                var sourceCode = editor.getSession().getValue();
                sendMessage("updateSourceCode", {
                    fileId: getFileId(editor),
                    sourceCode: sourceCode
                });
            }, 300);
        });
    });

    function sendMessage(type, message) {
        console.time(type);
        message = message || {};
        var data = JSON.stringify({type: type, data: message});
        webSocket.send(data);
        console.log("Sent: " + data);
    }

    /*
        Listeners go below here
        vvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    */

    jwerty.key('ctrl+enter', function () {
        $("#run-code-button").click()
    });

    jwerty.key('ctrl+shift+enter', function () {
        $("#run-tests-button").click()
    });

    $("#ae-toggle-fullscreen").click(function(){
        $(".hidden-when-editor-maximized").toggle();
        $("#ace-editor-wrapper").toggleClass("maximized");
        $("#ae-toggle-fullscreen").toggleClass("fa-expand fa-compress");
        $(editors).each(function() { this.resize(); this.scrollPageUp() });
    });

    $(".ae-theme-settings").click(function(){
        var theme = ("ace/theme/"+$(this).attr("id"));
        $(editors).each(function() { this.setTheme(theme); });
    });

    $(".collapsing-header").click(function(){
        $(this).find("i").toggleClass("fa-angle-down fa-angle-right"); //toggle icon on header-click
    });

    $("#run-code-button").click(function(){
        clearAndSend("runMain");
    });

    $("#run-tests-button").click(function(){
        clearAndSend("runTests");
    });

    $("#deliver-assignment-button").click(function(){
        clearAndSend("deliverAssignment");
    });

    /*
        Helper functions go below here
        vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
    */

    function clearAndSend(command){
        $("#editor-status").toggleClass("fa-circle fa-circle-o-notch running ready");
        $('.ace-editor-console').text("");
        sendSourceCode();
        sendMessage(command);
    }

    function sendSourceCode() {
        $(editors).each(function() {
            var editor = this;
            var sourceCode = editor.getSession().getValue();
            sendMessage("updateSourceCode", {
                fileId: getFileId(editor),
                sourceCode: sourceCode
            });
        });
    }

    function initCollapsibleHeaders() {
        $(".hidden-when-editor-maximized").collapsible(); //makes ever header in this div collapsible
    }

    function initEditors() {
        var editors = [];
        $(".ace-editor-instance").each(function () {
            editors.push(createEditor($(this).attr("id")));
        });
        return editors;
    }

    function createEditor(editorID){
        var editor = ace.edit(editorID);
        editor.setTheme("ace/theme/eclipse");
        editor.getSession().setMode("ace/mode/java");
        editor.setOptions({enableBasicAutocompletion: true});
        editor.completers = [createCompleter()];
        return editor;
    }

    function createCompleter(){
        return {
            getCompletions: function(editor, session, pos, prefix, callback) {
                var fileId = getFileId(editor);
                sendMessage("codeCompletion", {
                    fileId: fileId,
                    offset: calculateOffset(session.getValue(), pos)
                });
                completionCallback = callback;
            }
        };
    }

    function calculateOffset(code, position){
        var lines = code.split("\n");
        var offset = 0;
        for(var i = 0; i < position.row; i++){
            offset += lines[i].length + 1;
        }
        offset += position.column;
        return offset;
    }

    function openNewWebSocket() {
        return new WebSocket(jsRoutes.controllers.AssignmentController.openEditorSocket(getCurrentProblemID()).webSocketURL());
    }

    function getCurrentProblemID(){
        return $("#problem-id").data("problemid");
    }

    function getCurrentProblemScore(){
        return parseInt( $("#problem-score").data("problemscore") );
    }

    function getFileId(editor) {
        return $(editor.container).attr('data-file-id');
    }

    function convertType(type) {
        switch (type) {
            case 'Warning':
                return 'warning';
            case 'Information':
                return 'info';
            default:
                return 'error';
        }
    }

    function Annotation(lineNumber, message, type){
        this.row = lineNumber;
        this.text = message;
        this.type = type; // "error", "warning", "info"
    }

    var throttle = (function(){
        var timer = 0;
        return function(callback, ms){
            clearTimeout (timer);
            timer = setTimeout(callback, ms);
        };
    })();

    function buildTestRow(testResult){
        var mapping = {
            "OK": "success",
            "Failed": "danger",
            "Ignored": "warning"
        }
        return '<tr class="'+mapping[testResult.status]+'"><td>'+testResult.methodName+'</td><td>'+testResult.status+'</td>';
    }

    function getSummary(testResults){

        var testsPassed = $(testResults).filter(function(){
            return this.status === "OK";
        }).length;
        return testsPassed+' out of '+testResults.length+' passed, earning you a score of '+ Math.round( (testsPassed)/(testResults.length) * getCurrentProblemScore() )


    }

    function arrayContains(array, element){
        return $.inArray(element, array) > -1;
    }
});
