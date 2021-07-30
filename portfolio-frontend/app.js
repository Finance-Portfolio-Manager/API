const root = document.getElementById("app-root");
window.addEventListener("load", route);
window.addEventListener("hashchange", route);
window.addEventListener("load", navToggle);
window.addEventListener("hashchange", navToggle);



function navToggle(){
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
    {path: "#/register", componentFileName: "register"}
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
    localScript.src = `components/${scriptName}-component/${scriptName}.component.js`
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
    const passError = document.getElementById("error-modal");
    passError.hidden = true;
    const password = document.getElementById("modalPassword").value
    console.log(password);
    if(password==null || password == undefined){
        passError.hidden = false;
    } else {
        removeUser(password);
    }
});

function removeUser(password){  
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
        console.log(response);
        // return response.json();
    // }).then((data) => {
    //     sessionStorage.setItem("Authorization", data.jwt)
    //     if(data.jwt==undefined){
    //         userError.hidden = false;
    //     } else if (data.jwt != null && data.jwt != undefined){
    //         document.getElementById("toggle-nav-1").hidden = true;
    //         document.getElementById("toggle-nav-2").hidden = true;
    //         window.location.href = "#/overview";
    //      }  
    }).catch((error) => {
            // networkError.hidden = false;
            console.error(error);
    })
};
