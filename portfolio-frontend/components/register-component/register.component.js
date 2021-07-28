const form = document.getElementById("registration-form");

const firstName = document.getElementById("first-name").value;
const lastName = document.getElementById("last-name").value;
const email = document.getElementById("email").value;
const username = document.getElementById("username").value;
const password = document.getElementById("password").value;
const secondPassword = document.getElementById("repeat-password").value;

function validateInput(firstName, lastName, email, username, password, secondPassword){
    const nError = document.getElementById("name-error");
    const eError = document.getElementById("email-error");
    const unError = document.getElementById("username-error");
    const pError = document.getElementById("password-error");
    const rpError = document.getElementById("retype-password-error");
    const letters = /^[A-Za-z]+$/;
    
    if(!firstName.match(letters) || !lastName.match(letters)){
        nError.innerHTML = "<small style=\"color:Red\">First and last name may only contain letters<small>";
        return false;
    }
    
    if(lastName==="" || lastName==null){
        // messages.push("Last name is required")
    }
    
    if(email==="" || email==null){
        // messages.push("Email is required")
    }
    
    if(username==="" || username==null){
        // messages.push("Username is required")
    }
    
    if(password==="" || password==null){
        // messages.push("Password is required")
    }
}

form.addEventListener("submit", submitListener);

function submitListener(register){
    register.preventDefault(); 

    let isValidInput = validateInput(firstName, lastName, email, username, password, secondPassword);

    // while(!isValidInput){
    //     isValidInput = validateInput(firstName, lastName, email, username, password, secondPassword);
    // }
    if (isValidInput){
        submit();
    } else {
        form.addEventListener("submit", submitListener);
    }
}

function submit(){
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
}