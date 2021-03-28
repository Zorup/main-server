function signIn(){
    let userId = $('#InputEmail').val();
    let userPwd = $('#InputPassword').val();

    $.ajax({
        url: '/v1/login',
        type: 'POST',
        data: {
            loginId: userId,
            password: userPwd
        },
        xhrFields: {
            withCredentials: true
        }
    }).done(function(){
        location.href = "/template";
    }).fail(function(){
        alert("유효하지 않은 접근입니다. 아이디나 비밀번호를 확인해주세요.");
    });

}

function getTemplate(){

}