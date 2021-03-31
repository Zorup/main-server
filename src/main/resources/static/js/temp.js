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

function addForum(){
    let forumName = $('#InputForumName').val();

    $.ajax({
        url: '/v1/forum',
        type: 'POST',
        data: {
            forumName: forumName,
        },
        xhrFields: {
            withCredentials: true
        }
    }).done(function(){
        location.href = "/template";
    }).fail(function(){
        alert("유효하지 않은 입력이거나, 네트워크 오류가 존재합니다. 다시 시도해주세요.");
    });

}

function switchFeed(){
    alert("게시판 바뀜")
}