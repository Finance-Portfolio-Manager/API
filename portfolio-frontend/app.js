const root = document.getElementById("app-root");
window.addEventListener("load", route);
window.addEventListener("hashchange", route);
window.addEventListener("load", navToggle);
window.addEventListener("hashchange", navToggle);



function navToggle(){
    document.getElementById("wrong-password").hidden = true;
    document.getElementById("no-password").hidden = true;
    if(sessionStorage.getItem("Authorization")){
        document.getElementById("toggle-nav-1").hidden = true;
        document.getElementById("toggle-nav-2").hidden = true;
        document.getElementById("navbarDropdown").hidden = false;
    } else {
        document.getElementById("toggle-nav-1").hidden = false;
        document.getElementById("toggle-nav-2").hidden = false;
        document.getElementById("navbarDropdown").hidden = true;
    }
}


function hasToken(){
    if(sessionStorage.getItem("Authorization")){
        return "";
    } else {
        return "login";
    }
}

const routes = [
    {path: "", componentFileName: hasToken()},
    {path: "#/new", componentFileName: "new-transaction"},
    {path: "#/portfolio", componentFileName: "portfolio"},
    {path: "#/login", componentFileName: "login"},
    {path: "#/register", componentFileName: "register"},
    {path: "#/overview", componentFileName: "overview"}
]


function route(){
    const hashPath = location.hash;
    const currentRoute = routes.find(r=>r.path===hashPath);
    const viewName = currentRoute?currentRoute.componentFileName:"home";
    renderView(viewName);
}

function renderView(view){
    fetch(`components/${view}-component/${view}.component.html`)
        .then(response=>response.text())
        .then(htmlBody=>{
            root.innerHTML = htmlBody;
            loadScript(view);
        });
}

function loadScript(scriptName){
    let localScript = document.getElementById("dynamic-js");
    if (localScript){
        localScript.remove();
    }
    localScript = document.createElement("script");
    localScript.id = "dynamic-js";
    localScript.src = `components/${scriptName}-component/${scriptName}.component.js`;
    localScript.type = "module";
    document.body.appendChild(localScript);
}

document.getElementById("log-out").addEventListener("click", function(logout){
    logout.preventDefault();
    sessionStorage.removeItem("Authorization");
    window.location.href = "#/login";
});

document.getElementById("deleteButton").addEventListener("click", function(removeAccount){
    removeAccount.preventDefault();
    const passErrorWrong = document.getElementById("wrong-password");
    const passError = document.getElementById("no-password");
    const password = document.getElementById("modalPassword").value
    passErrorWrong.hidden = true;
    passError.hidden = true;
    console.log(password);
    if(password==null || password == undefined){
        passError.hidden = false;
    } else {
        removeUser(password);
    }
});

function removeUser(password){  
    const passError = document.getElementById("wrong-password");
    const credentials = {
        username:sessionStorage.getItem('Authorization'),
        password:password
    }
    fetch("http://localhost:8082/delete", {
        method: 'DELETE',
        headers: new Headers({
            'Content-Type':'application/json',
        }),
        body: JSON.stringify(credentials)
    }).then((response) => {
        if(response.status>=200 && response.status<=299){
            sessionStorage.removeItem("Authorization");
            $('#exampleModal').modal('hide');
            window.location.href = "#/login";
        } else {
            passError.hidden = false;
        }
    }).catch((error) => {
        passError.hidden = false;
    })
};
