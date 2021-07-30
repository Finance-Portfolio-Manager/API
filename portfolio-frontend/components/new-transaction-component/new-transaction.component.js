import getTickers from "./tickers.js";

const tickerList = getTickers();
    
var tick = document.getElementById("ticker");
var tickerError = document.getElementById("ticker-error");

tick.addEventListener('change', function(){
    tickerError.hidden = true;
    if(!tickerList.includes(tick.value.toUpperCase())){
        tickerError.hidden = false;
    }
})

var buy = document.getElementById("buy-btn");
var sell = document.getElementById("sell-btn");
var isABuy = true;

buy.addEventListener('click', function(){
    isABuy = true;
})

sell.addEventListener('click', function(){
    isABuy = false;
})

document.getElementById("new-txn-form").addEventListener("submit", function(t){
    t.preventDefault();

    var tickerSymobol = document.getElementById("ticker").value;
    var shareAmount = document.getElementById("share-amount").value;
    var sharePrice = document.getElementById("share-price").value;
    var note = document.getElementById("note").value;

    var newTransaction = {
        ticker:tickerSymobol,
        shareAmount:shareAmount,
        sharePrice:sharePrice,
        note:note,
        token:sessionStorage.getItem('Authorization'),
        isBuy:isABuy
    };
    fetch("http://localhost:8082/transactions/new", {
        method: 'post',
        headers: new Headers({
            'Content-Type':'application/json',
            'Authorization':sessionStorage.getItem('Authorization')
        }),
        body: JSON.stringify(newTransaction)
    }).then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error(error));
    // }
});


var validateTicker = function(tickerSymobol) {
    // check whether ticker is valid

    // var tickerSymobol = document.getElementById("ticker").value;

    fetch("tickers.json").then(response => {
        return response.json();
    }).then(data => {
        const tickerList = data;
    });

    if(Object.values(tickerList).includes(tickerSymobol)) {
        console.log("yay");
        return true;
    }

    // var stocks = require('stock-ticker-symbol');
    // if(stocks.lookup(ticker)===null) {
    //     tickerError.hidden = false;
    //     return false;
    // }else {
    //     return true;
    // }
}