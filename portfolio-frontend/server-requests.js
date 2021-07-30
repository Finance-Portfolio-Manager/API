/*function fetchStock(symbol){
    fetch("http://localhost:8082/api?symbol=" + symbol, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}

//symbols should be something you obtain from getAllStocks
//%2C to split symbols
function fetchAllStocks(symbols){
    fetch("http://localhost:8082/api", {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(symbols)
    }).then((response) => {
        return response.json();
    }).then((data) => {
        sessionStorage.setItem("Authorization", data.jwt)
        if(data.jwt==undefined){
            userError.hidden = false;
        } else if (data.jwt != null && data.jwt != undefined){
            document.getElementById("toggle-nav-1").hidden = true;
            window.location.href = "./";
         }  
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function getUserNameFromToken(token){
    fetch("http://localhost:8082/username?token=" + token, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function getUserFromUsername(username){
    fetch("http://localhost:8082/register/" + username, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}
Stock{
    int userId
    int stockId
    String stockSymbol
    double stockQuantity
}
//all of the crud operations, i havent tested them here
function addStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'post',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function deleteStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'delete',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function updateStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'put',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function getStock(stockId){
    fetch("http://localhost:8082/stocks/" + stockId, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => {
        console.log(response);
        return response.json();
    }).catch((error) => {
            console.log("error");
            networkError.hidden = false;
    });
}

function getAllStocks(userId){
    fetch("http://localhost:8082/stocks/all/" + userId, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => {
        return response.json();
    }).catch((error) => {
            networkError.hidden = false;
    });
}
*/