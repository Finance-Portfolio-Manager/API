
document.getElementById("login-form").addEventListener("submit", function(login){
    login.preventDefault();

    const username = document.getElementById("inputUsername").value;
    const password = document.getElementById("inputPassword").value;
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
            const errorDiv = document.getElementById("error-credentials");
            errorDiv.hidden = false;
        } else if (data.jwt != null && data.jwt != undefined){
            window.location.href = "./#/home";
        } else {
            const errorDiv = document.getElementById("error-network");
            errorDiv.hidden = false;
        }  
    }).catch((error) => {
        console.error(error);
    })
});