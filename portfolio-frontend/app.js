const root = document.getElementById("app-root");
window.addEventListener("load", route);
window.addEventListener("hashchange", route);
window.addEventListener("load", navToggle);
window.addEventListener("hashchange", navToggle);

function navToggle(){
    if(sessionStorage.getItem("Authorization")){
        document.getElementById("toggle-nav-1").hidden = true;
    } else {
        document.getElementById("toggle-nav-1").hidden = false;
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


