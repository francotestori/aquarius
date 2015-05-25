var href = window.location.href;
var idNum = href.substring(href.lastIndexOf("/") + 1);

//region Scrollbar
$(function () {
    $('#comments-wrapper').slimScroll({
        height: '400px'
    });
    $('#info-tab-wrapper').slimScroll({
        height: '400px'
    });
});
//endregion

//region Follow
var $unfollow = $("#unfollow-btn");
var $follow = $("#follow-btn");
var $followers = $('#followers-qty');

if ($('#following').val() == "true") {
    $follow.addClass("hidden");
} else {
    $unfollow.addClass("hidden");
}

function follow() {
    $.post("/follow/project",
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
    $.post("/unfollow/project",
        {
            id: idNum
        }
    ).done(function () {
            $followers.html(parseInt($followers.html()) - 1);
            $follow.removeClass("hidden");
            $unfollow.addClass("hidden");
        });
}
//endregion

//region Contribute
var $pledge = $('#pledge');
var $raised = $('#raised-qty');
function submitPledge() {
    $.post("/addPledge",
        {
            id: idNum,
            amount: $pledge.val()
        }
    ).done(function () {
            $raised.html(parseInt($raised.html()) + parseInt($pledge.val()));
            $('#donateModal').modal('toggle');
        });
}
//endregion


var lUpdate = new Date().getTime();
function submitComment() {
    $.post(
        "/project/comment",
        {
            userId: $("#userId").val(),
            projectId: $("#projectId").val(),
            comment: $("#comment").val()
        },
        function(){
            $('#comment').val("")
        }
    );
}

window.setInterval(function () {
    $.post(
        "/project/update/comments",
        {
            projectId: $("#projectId").val(),
            lastUpdate: lUpdate
        },
        function (data) {
            lUpdate = new Date().getTime();
            var comments = JSON.parse(data).comments;
            $.each(comments, function(i){
                newCommentRow(comments[i])
            })
        }
    );
}, 2000);

function newCommentRow(comment) {
    var username = comment.username;
    var date = comment.date;
    //noinspection JSUnresolvedVariable,SpellCheckingInspection
    var imgsrc = comment.imgsrc;
    var text = comment.comment;
    var href = comment.href;

    var $template = $('#template-comment');
    $template.find("h6").html(username);
    $template.find("p").html(text);
    $template.find("img").attr("src", imgsrc);
    $template.find("a").attr("href", href);
    var clone = $template.clone();
    $("#comments-wrapper").append(clone);
}