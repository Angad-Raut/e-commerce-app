var REST_API="http://localhost:9091"

function getSuccessMsg(title,message) {
  Swal.fire({
      title: title,
      text: message,
      icon: "success"
  });
}

function getValidationMsg(title,message) {
  Swal.fire({
      title: title,
      text: message,
      icon: "warning"
  });
}

function getErrorMsg(message) {
  Swal.fire({
        title: "Error",
        text: message,
        icon: "error"
  });
}