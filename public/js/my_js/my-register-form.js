$(document).ready(function () {
    $("#register-button").click(function () {
        var $password = $("#password");
        var $password_confirm = $("#confirm-password");
        if ($password.val() != $password_confirm.val()) passwordMismatch();
        else $("#register-form").submit();
    });
});

function passwordMismatch() {
    $("#password-mismatch").removeClass("hidden");
}