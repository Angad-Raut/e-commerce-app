$("#login-id").click(function(){
     var username = $("#yourUsername").val();
     var password = $("#yourPassword").val();
     var flag = false;
     if (username=="") {
        flag = true;
        getValidationMsg("Check Input!","Please enter username!");
        return true;
     }
     if (password=="") {
        flag = true;
        getValidationMsg("Check Input!","Please enter password!");
        return true;
     }
     if (flag==false) {
        var formData = {"username":username,"password":password};
        getLogin(formData);
     }
});

function getLogin(formData) {
  $.ajax({
     url: "http://localhost:9091/auth/login",
     type: "POST",
     dataType: "json",
     async:true,
     data: JSON.stringify(formData),
     success: function(data) {
         getSuccessMsg("Logged In!","Logged In Successfully!!");
         setLocalStorage(data);
         window.location.href = "/static/pages/dashboard.html";
     },
     error: function() {
         alert("Error occurred!!");
     },
     complete: function(response) {
          log.console("completed");
     }
  });
}

function setLocalStorage(data) {
   localStorage.setItem('userName', data.result.userName);
   localStorage.setItem('userMobile', data.result.userMobile);
   localStorage.setItem('userEmail', data.result.userEmail);
   localStorage.setItem('isAdmin', data.result.isAdmin);
}