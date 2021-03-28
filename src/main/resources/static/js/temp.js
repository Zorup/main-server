function logout(){
    $.ajax({
        url: '/v1/logout',
        type: 'POST',
        xhrFields: {
            withCredentials: true
        }
    }).done(function(){
        location.href="/login";
    }).fail(function(){
        alert("실패하였습니다.");
    });

}