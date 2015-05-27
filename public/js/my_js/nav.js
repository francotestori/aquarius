//Update notifications
var lUpdate = new Date().getTime();
window.setInterval(function () {
    getNotifications();
}, 1000);

//Mark notifications as read
var $toggler = $('#my-task-list');
var $list = $('#notification-list');

$toggler.click(function () {
    var inputList = $list.find("input");
    console.log(inputList);
    var ids = [];
    $.each(inputList, function (i) {
        ids.push($(inputList[i]).val());
    });
    $.post(
        "/readNotifications",
        {
            "idList": ids
        });
    $('.notification-badge').addClass("hidden");
});

function getNotifications() {
    $.post(
        "/getNotifications",
        {
            lastUpdate: lUpdate
        },
        function (data) {
            lUpdate = new Date().getTime();
            var obj = JSON.parse(data).notifications;
            $.each(obj, function (i) {
                var notification = obj[i];
                createNewNotification(notification);
            });
            var $badges = $('.notification-badge');
            $badges.each(function (i, badge) {
                if(obj.length > 0) {
                    $(badge).html(obj.length);
                    $(badge).removeClass("hidden");
                }
            })
        }
    );
}

function createNewNotification(notification) {
    var $template = $('#notification-template');
    $($template.find("input")[0]).val(notification.id);
    $($template.find(".heading")[0]).html(notification.message);
    $($template.find(".date")[0]).html(notification.date);
    var clone = $template.clone();
    clone.removeClass("hidden");
    clone.removeAttr("id");
    $('#notifications-wrapper').prepend(clone);
}