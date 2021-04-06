function addUser(){
    let loginId = $('#InputLoginId').val();
    let password = $('#InputPassword').val();
    let name = $('#InputName').val();
    let email = $('#InputEmail').val();
    let position = $('#position').val();
    let department = $('#department').val();

    $.ajax({
        url: '/v1/signin',
        type: 'POST',
        data: {
            loginId: loginId,
            password: password,
            name: name,
            email: email,
            position: position,
            department: department,
        },
        xhrFields: {
            withCredentials: true
        }
    }).done(function(){
        alert("회원가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
        movLogin();
    }).fail(function(){
        alert("실패하였습니다."); //차후 상수값 return하도록 하여, 실패 원인 알리도록 수정
    });
}

function movLogin(){
    location.href = "/login"
}

function movForgot(){
    location.href="/forgot"
}