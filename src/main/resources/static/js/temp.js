let deleteForumList = [];

$('input[name=fList]').click(function(){
    let ischecked = $(this).is(":checked");
    if(ischecked){
        deleteForumList.push($(this).val());
        //console.log($(this).val());
    }else{
        deleteForumList = deleteForumList.filter((element)=> element !== $(this).val());
       // console.log("배열 원소 삭제 "+$(this).val());
    }
});

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

function deleteForum(){
    alert("delete");

    $.ajax({
        url: '/v1/forum',
        type: 'DELETE',
        data: {
            forumId: deleteForumList,
        },
        xhrFields: {
            withCredentials: true
        }
    }).done(function(){
        location.href = "/template";
    }).fail(function(){
        alert("유효하지 않은 입력이거나, 네트워크 오류가 존재합니다. 다시 시도해주세요.");
    });
    deleteForumList = [];
}


function switchFeed(param){
    let url = '/template/' + param;
    location.href = url;
}

function changeLikes(postId){
    $.ajax({
        url: '/v1/like',
        type: 'POST',
        data: {
            postId: postId,
        },
        xhrFields: {
            withCredentials: true
        }
    }).done(function(data){
        $('.testLike').text(data['data']);
    }).fail(function(){
        alert("유효하지 않은 입력이거나, 네트워크 오류가 존재합니다. 다시 시도해주세요.");
    });
}

function ChangeName(){
    let forumName = $('#ChangeForumName').val();

    $.ajax({
        url: '/v1/forum',
        type: 'put',
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

$(document).ready(function(){

      var list = $(".list li");
      var numToShow = 3;
      var button = $("#next");
      var numInList = list.length;
      list.hide();
      if (numInList > numToShow) {
        button.show();
      }
      list.slice(0, numToShow).show();

      button.click(function(){
          var showing = list.filter(':visible').length;
          list.slice(showing - 1, showing + numToShow).fadeIn();
          var nowShowing = list.filter(':visible').length;
          if (nowShowing >= numInList) {
            button.hide();
          }
      });

});

$(".posts").slice(0,3).show();

$(".showMore").on("click",function(){
    $(".posts:hidden").slice(0, 5).show();

    if($(".posts:hidden").length == 0)
    {
        $(".showMore").fadeOut();
    }
})