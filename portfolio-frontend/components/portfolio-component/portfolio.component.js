/*Production Line:
if (!userId){
    JWT => username(stored/deleted) => userId(stored)
}
getAllTransactions(userId) => stocksList{symbol, quantity, averagePrice}(stored)(tabled) => symbolsList
fetchAllStocks(symbolsList) => currentPrices(maybe stored)(tabled) => priceChanges(tabled)

maybe make a list of objects with the table variables for ease of use
then delete all sessionStorage except for userId

Table: Symbol, Quantity, CurrentPrices, ChangedPrices
Use Current Prices * Quantity for Portfolio Total
Use ChangedPrices for Portfolio Change

Rant before i figured it out:

id need an add/delete portfolio component
id also need a current portfolio to access from the portfolio component
both of which will take me days
STORE DATA IN SESSION STORAGE, FUCK BAD PRACTICE, I CAN'T NEST ASYNC
*/
if (!sessionStorage.getItem('userId')){
    getUserNameFromToken(sessionStorage.getItem('Authorization')).then(data => sessionStorage.setItem("username", data))
    getUserFromUsername(sessionStorage.getItem('username')).then(data => {
        sessionStorage.setItem("userId", data.userId)
    })
    sessionStorage.removeItem('username');
}
var userId = sessionStorage.getItem('userId');
var stocksNames = [];
var portfolioValue;
var portfolioChange; //need total money from transactions
                        //(if users had a total balance, it would simply be available balance against total balance)

console.log(sessionStorage.getItem('Authorization'));
console.log(sessionStorage.getItem('userId'));
getAllTransactions(sessionStorage.getItem('userId')).then(data => {
    console.log(data);
    var stocksList = [];
    if (data != [])
    {
        for(var i = 0; i < data.length; i++){
            var stock = {
                symbol: data[i].ticker,
                quantity: data[i].shareAmount,
                price: data[i].sharePrice
            }
            if (stocksList.find(s => s.symbol == stock.symbol)){
                let stockIndex = stocksList.findIndex(s => s.symbol == stock.symbol);
                stocksList[stockIndex].quantity += stock.quantity;
                stocksList[stockIndex].price += stock.price;
            }
            else{
                stocksList.push(stock);
            }
        }
        //
        console.log(stocksList);
        if (stocksList.find(s => s.quantity == 0)){
            stocksList = stocksList.filter(s => s.quantity != 0);
        }
        //
        console.log(stocksList);
        //sessionStorage.setItem("stocksList", stocksList);
    }
})



/*
var raw = getAllStocks(user.userId);

for (var i = 0; i < raw.length; i++){
    stocksNames.push(raw[i].stockSymbol);
}

var stocks = JSON.parse(fetchAllStocks(stocksNames));
//this needs some messing with for formatting

document.getElementById("portfolio-view").innerHTML = portfolioValue;
document.getElementById("portfolio-view").innerHTML = portfolioChange;

const stockTableBody = document.getElementById("stocks-body");
for(var i = 0; i < stocks.result.length; i++){
    let tableRow = document.createElement("tr");
    let stockName = document.createElement("td");
    let stockQuantity = document.createElement("td");
    let stockAveragePrice = document.createElement("td");
    let stockCurrentPrice = document.createElement("td");
    let stockChange = document.createElement("td");
    //transactions inputs
    stockName.innerText = raw[i].stock_symbol;
    stockQuantity.innerText = raw[i].stock_quantity;
    stockAveragePrice = null; //need averagePrice from transactions
    tableRow.appendChild(stockName);
    tableRow.appendChild(stockQuantity);
    tableRow.appendChild(stockAveragePrice);
    //current price needs to be stored in session storage for stockChange
    stockCurrentPrice = stocks.result[i].targetPriceMean;
    stockChange = null; //assumedly currentPrice/averagePrice
    tableRow.appendChild(stockCurrentPrice);
    tableRow.appendChild(stockChange);
    stockTableBody.appendChild(tableRow);
    //make sure to actually put portfolioValue down after it gets finished up
    portfolioValue += raw[i].stock_quantity * stocks.result[i].targetPriceMean;
}
*/

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
        return data;
    }).catch((error) => {
            networkError.hidden = false;
    });
}

function getUserNameFromToken(token){
    return fetch("http://localhost:8082/username?token=" + token, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
    }).then((response) => { 
        return response.text().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

function getUserFromUsername(username){
    return fetch("http://localhost:8082/register/" + username, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

function getAllTransactions(userId){
    return fetch("http://localhost:8082/transactions?userId=" + userId, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}