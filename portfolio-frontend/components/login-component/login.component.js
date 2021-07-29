document.getElementById("login-form").addEventListener("submit", function(login){
    login.preventDefault();
    const userError = document.getElementById("error-credentials");
    const networkError = document.getElementById("error-network");
    userError.hidden = true;
    networkError.hidden = true;

    const credentials = {
    username:document.getElementById("inputUsername").value,
    password:document.getElementById("inputPassword").value
};
  
    fetch("http://localhost:8082/login", {
        method: 'post',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(credentials)
    }).then((response) => {
        return response.json();
    }).then((data) => {
        sessionStorage.setItem("Authorization", data.jwt)
        if(data.jwt==undefined){
            userError.hidden = false;
        } else if (data.jwt != null && data.jwt != undefined){
            // document.getElementById("nav-port").hidden = false;
            // document.getElementById("nav-txn").hidden = false;
            // document.getElementById("toggle-nav-1").hidden = true;
            window.location.href = "#/overview";
         }  
    }).catch((error) => {
            networkError.hidden = false;
    })
});
