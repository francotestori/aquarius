$("#lastCommentsUpdate").val(new Date().getTime());

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
            lastUpdate: $("#lastCommentsUpdate").val()
        },
        function (data) {
            $("#lastCommentsUpdate").val(new Date().getTime());
            data.forEach(function(entry){
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