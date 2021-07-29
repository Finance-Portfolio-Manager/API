var userId = 1; //need to get userId from somewhere
var stocksNames = "";
var portfolioValue;
var portfolioChange; //need total money from transactions
                        //(if users had a total balance, it would simply be available balance against total balance)
var raw = JSON.parse(getAllStocks(userId));
for (var i = 0; i < raw.length; i++){
    stocksNames += raw[i].stockSymbol + "%2C";
}
stocksNames.slice(0, -3);
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
    stockName.innerText = raw[i].stock_symbol;
    stockQuantity.innerText = raw[i].stock_quantity;
    stockAveragePrice = null; //need averagePrice from transactions
    stockCurrentPrice = stocks.result[i].targetPriceMean;
    stockChange = null; //assumedly currentPrice/averagePrice
    tableRow.appendChild(stockName);
    tableRow.appendChild(stockQuantity);
    tableRow.appendChild(stockAveragePrice);
    tableRow.appendChild(stockCurrentPrice);
    tableRow.appendChild(stockChange);
    stockTableBody.appendChild(tableRow);
    //make sure to actually put portfolioValue down after it gets finished up
    portfolioValue += raw[i].stock_quantity * stocks.result[i].targetPriceMean;
}
