var href = window.location.href;
var i = href.lastIndexOf("/");
var idNum = href.substring(i + 1);
var $unfollow = $("#unfollow-btn");
var $follow = $("#follow-btn");
var $followers = $('#followers');

if ($('#following').val() == "true") {
    $follow.addClass("hidden");
} else {
    $unfollow.addClass("hidden");
}

function follow() {
    $.post("/follow/user",
        {
            id: idNum
        }
    ).done(function () {
            $followers.html(parseInt($followers.html()) + 1);
            $follow.addClass("hidden");
            $unfollow.removeClass("hidden");
        });
}

function unfollow() {
    $.post("/unfollow/user",
        {
            id: idNum
        }
    ).done(function () {
            $followers.html(parseInt($followers.html()) - 1);
            $follow.removeClass("hidden");
            $unfollow.addClass("hidden");
        });

}