function initMentionList(target) {
    let userList = {};
    $.ajax({
        url: '/v1/group/users',    //static 이동시 해당 url 수정 필요
        type: 'GET',
        xhrFields:{
            withCredentials: true
        }
    }).done(function (responseJSON){
        const users = responseJSON.list;
        $.each(responseJSON.list, function (i, e){
            let label= e.userId;
            let value = e.name;
            userList[value] = label;
        });

        $(target).suggest('@', {
            data: users,
            map: function(user) {
                return {
                    value: user.name,
                    text: '<strong>'+user.name+'</strong> <small>'+user.loginId+'</small>'
                }
            },
        });
    }).fail(function (){
        alert("사용자 리스트 조회 실패");
    });
    return userList; //중복되는 이름 처리 논의 필요
}
//각 test Area별 @ 건거 파싱하는 코드 짜야됨
