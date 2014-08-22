$(document).ready(function () {

    $(".assignment-preview a").find(".progress-bar").each(function(){
        scoreAdjust( $(this), getPercentWidth($(this)), 30+Math.random()*70);
    });

    var alwaysMax = $(".assignment-preview a").first().find(".progress-bar");
    scoreAdjust( alwaysMax, getPercentWidth(alwaysMax), 100);

    setTimeout(function(){$( ".assignment-leaderboard").find(".bronze").addClass("star-earned") },1000);
    setTimeout(function(){$( ".assignment-leaderboard").find(".silver").addClass("star-earned") },1500);
    setTimeout(function(){$( ".assignment-leaderboard").find(".gold").addClass("star-earned") },2000);

});

function getPercentWidth(e){
    return Math.round( e.width()/e.parent().width() * 100 );
}

function scoreAdjust (progressBar, currentScore, targetScore) {
    setTimeout(function () {
        if(currentScore === 50){ progressBar.parent().parent().find(".fa-star.bronze, .glow.bronze").addClass("star-earned") }
        if(currentScore === 75){ progressBar.parent().parent().find(".fa-star.silver, .glow.silver").addClass("star-earned") }
        if(currentScore === 100){progressBar.parent().parent().find(".fa-star.gold,   .glow.gold"  ).addClass("star-earned") }
        progressBar.width(currentScore+"%");
        currentScore++;
        if (currentScore <= targetScore) {
            scoreAdjust(progressBar, currentScore, targetScore);
        }
    }, 20)
}

