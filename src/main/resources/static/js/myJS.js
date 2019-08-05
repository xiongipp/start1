/*这个提交回复*/
function post() {
  var questionId=$("#question_id").val();
  var content=$("#comment_content").val();
  comment2target(questionId,1,content);
}
function comment(e) {
  var commentId=e.getAttribute("data-id");
  var content=$("#input-"+commentId).val();
  comment2target(commentId,2,content);
  
}
/*展开二级评论*/
function collapseComments(e) {
  var id=e.getAttribute("data-id");
  var comments = $("#comment-"+id);
  //获取评论展开状态
  var collapse=e.getAttribute("data-collapse");
  if(collapse){
    //折叠
    comments.removeClass("in");
    e.removeAttribute("data-collapse");
    //移除高亮标签
    e.classList.remove("active");
  }else{
    $.getJSON("/comment/"+id, function(data){
      console.log(data);
      var commentBody=$("comment-body-"+id)
      var items=[];
      commentBody.appendChild()
      $.each(data.data, function(comment) {
        $("<div/>",{
          "class:":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments," ,
          html: items.join('')
        })
        items.push('<li id="' + key + '">' + val + '</li>');
      });
      $("<div/>",{
        "class:":"col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments," ,
        "id":"comment-"+id,
        html: items.join('')
      }).appendTo(commentBody);

    //展开二级评论
    comments.addClass("in");
    //标记评论展开状态
    e.setAttribute("data-collapse","in");
    //高亮评标签
    e.classList.add("active");
    });
  }
  
}
function comment2target(targetId,type,content){
  if(!content){
    alert("回复不能为空！")
    return;
  }
  $.ajax({
    type: "POST",
    url: "/comment",
    contentType:"application/json",
    data:JSON.stringify({
      "parentId": targetId,
      "content": content,
      "type": type,
    }),
      success: function (response) {
        if(response.code==200){
          window.location.reload();
          $("#comment_section").hide();
        }else {
          if(response.code==2004){
            var isAccepted=confirm(response.message);
            if(isAccepted){
              window.open("https://github.com/login/oauth/authorize?client_id=ce98d1e93a586201af03&redirect_uri=http://localhost:8087/callback&scope=user&state=1")
              window.localStorage.setItem("closable",true);
            }
          }else{
            alert(response.message());
          }

        }
        console.log(response);
      },
      dataType:"json"
    });
}
