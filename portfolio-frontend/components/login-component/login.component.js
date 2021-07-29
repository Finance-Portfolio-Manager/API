
document.getElementById("login-form").addEventListener("submit", function(login){
    login.preventDefault();

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
            const userError = document.getElementById("error-credentials");
            userError.hidden = false;
        } else if (data.jwt != null && data.jwt != undefined){
            window.location.href = "./#/home";
         }  
    }).catch((error) => {
        if(TypeError){
            const networkError = document.getElementById("error-network");
            networkError.hidden = false;
        }
        
    })
});