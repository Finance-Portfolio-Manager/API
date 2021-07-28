document.getElementById("registration-form").addEventListener("submit", function(register){

    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const secondPassword = document.getElementById("repeat-password").value;

    register.preventDefault(); 
    const credentials = {
        firstName:document.getElementById("first-name").value,
        lastName:document.getElementById("last-name").value,
        email:document.getElementById("email").value,
        username:document.getElementById("username").value,
        password:document.getElementById("password").value
        };
        fetch("http://localhost:8082/register", {
            method: 'post',
            headers: new Headers({
                'Content-Type':'application/json'
            }),
            body: JSON.stringify(credentials)
        }).then(response => response.json())
            .then(data => console.log(data))
            .catch(error => console.error(error)); 
});