const root = document.getElementById("app-root");
window.addEventListener("load", route);
window.addEventListener("hashchange", route);


// when #/all -> fetch "components/directory-component/directory.component.html"
const routes = [
    {path: "", componentFileName: "home"},
    {path: "#/all", componentFileName: "directory"},
    {path: "#/new", componentFileName: "new-track"},
    {path: "#/login", componentFileName: "login"},
]


function route(){
    const hashPath = location.hash;
    const currentRoute = routes.find(r=>r.path===hashPath);
    // for(let r of routes){
    //     if(r.path===hashPath){
    //         return r;
    //     }
    // }
    // return undefined;
    
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
