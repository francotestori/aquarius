var href = window.location.href;
var idNum = href.substring(href.lastIndexOf("/") + 1);
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


var lUpdate = new Date().getTime();
function submitComment() {
    $.post(
        "/project/comment",
        {
            userId: $("#userId").val(),
            projectId: $("#projectId").val(),
            comment: $("#comment").val()
        },
        function (data) {
            var $comment = $("#comment");
            newCommentRow($("#userId").val(), $comment.val());
            $comment.val("");
        }
    );
}

//Update comments automatically
window.setInterval(function () {
    $.post(
        "/project/update/comments",
        {
            projectId: $("#projectId").val(),
            lastUpdate: lUpdate
        },
        function (data) {
            lUpdate = new Date().getTime();
            data.forEach(function (entry) {
                newCommentRow(entry.t1, entry.t2)
            })
        }
    );
}, 5000);

function newCommentRow(username, comment) {
    $("#template-comment-row > h6").html(username);
    $("#template-comment-row > p").html(comment);
    var newRow = $("#template-comment-row").clone();
    $("#comment-wrapper").append(newRow);
    newRow.toggle(true);
}