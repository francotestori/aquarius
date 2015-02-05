$("#register-button").click(function(){
    var loginForm = $("#login-form");
    loginForm.attr("action", "/register");
    loginForm.submit();
});
