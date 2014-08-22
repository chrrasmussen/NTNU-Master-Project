$(document).ready(function () {
    var path = location.pathname;
    $(".navbar li").each(function(){
        if( path.indexOf($(this).find("a").attr("href")) > -1 ){
            $(this).addClass("active");
        }
    });
});