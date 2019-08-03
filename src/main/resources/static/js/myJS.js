function post() {
  var questionId=$("#question-id").val();
  var content=$("#comment_comment").val();
  $.ajax({
    type: "POST",
    url: "/comment",
    contentType:"application/json",
    data:JSON.stringify( {
      "parentId":questionId,
      "content":content,
      "type":1
    }),
    success: function (response) {
      if(response.code==200){
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