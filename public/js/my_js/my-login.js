$("#register-button").click(function(){
    var loginForm = $("#login-form");
    loginForm.attr("action", "/register");
    loginForm.submit();
});

$("#resend-conf-btn").click(function(){
    var loginForm = $("#login-form");
    loginForm.attr("action", "/resend-conf");
    loginForm.submit();
});
