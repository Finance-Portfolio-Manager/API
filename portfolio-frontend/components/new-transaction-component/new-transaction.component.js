
document.getElementById("new-txn-form").addEventListener("submit", function(t){
    t.preventDefault();

    var tickerError = document.getElementById("ticker-error");
    tickerError.hidden = true;

    var tickerSymobol = document.getElementById("ticker").value;
    var shareAmount = document.getElementById("share-amount").value;
    var sharePrice = document.getElementById("share-price").value;
    var note = document.getElementById("note").value;

    // var isValidTicker = validateTicker(tickerSymobol);

    // if(isValidTicker) {
    var newTransaction = {
        ticker:tickerSymobol,
        shareAmount:shareAmount,
        sharePrice:sharePrice,
        note:note
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