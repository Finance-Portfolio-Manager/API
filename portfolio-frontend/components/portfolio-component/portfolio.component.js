getUserFromToken(sessionStorage.getItem('Authorization')).then(data => {
    var userId = data.userId;
    getAllTransactions(userId).then(data => {
        var stocksList = [];
    
        for(var i = 0; i < data.length; i++){
            var stock = {
                symbol: data[i].ticker,
                quantity: data[i].shareAmount,
                price: data[i].sharePrice
            }
            if (stocksList.find(s => s.symbol == stock.symbol)){
                //into 0: new + old = 0(NaN)
                //out of 0: price = new price
                let sIndex = stocksList.findIndex(s => s.symbol == stock.symbol);
                if ((stocksList[sIndex].quantity + stock.quantity) != 0){
                    stocksList[sIndex].price *= 1+((stock.price - stocksList[sIndex].price)/(stocksList[sIndex].quantity + stock.quantity));
                }
                else{
                    stocksList[sIndex].price = 0;
                }
                if(stocksList[sIndex].quantity == 0){
                    stocksList[sIndex].price = stock.price;
                }
                stocksList[sIndex].quantity += stock.quantity;
            }
            else{
                stocksList.push(stock);
            }
        }
    
        if (stocksList.find(s => s.quantity == 0)){
            stocksList = stocksList.filter(s => s.quantity != 0);
        }
    
        var symbolsList = [];
        var portfolioValue = 0;
        for(var i = 0; i < stocksList.length; i++){
            symbolsList.push(stocksList[i].symbol);
            portfolioValue += stocksList[i].price * stocksList[i].quantity;
        }
    
        fetchAllStocks(symbolsList).then(data =>{
            var currentPrices = [];
            for(var i = 0; i < data.quoteResponse.result.length; i++){
                currentPrices.push(data.quoteResponse.result[i].regularMarketPrice);
            }
            
            var portfolioChange = 0;
            var portfolioAverage = 0;
            for(var i = 0; i <currentPrices.length; i++){
                portfolioChange += currentPrices[i];
                portfolioAverage += stocksList[i].price;
            }
            portfolioChange /= portfolioAverage * .01;
    
            portfolioValue = portfolioValue.toFixed(2);
            portfolioChange = portfolioChange.toFixed(2);
    
            document.getElementById("portfolio-total").innerHTML = "$" + portfolioValue;
            document.getElementById("portfolio-change").innerHTML = portfolioChange + "%";
            const stockTableBody = document.getElementById("stocks-view");
            for(var i = 0; i < stocksList.length; i++){
                let tableRow = document.createElement("tr");
                let stockName = document.createElement("td");
                let stockQuantity = document.createElement("td");
                let stockAveragePrice = document.createElement("td");
                let stockCurrentPrice = document.createElement("td");
                let stockChange = document.createElement("td");
                stockName.innerText = stocksList[i].symbol;
                stockQuantity.innerText = stocksList[i].quantity.toFixed(2);
                stockAveragePrice.innerText = "$" + stocksList[i].price.toFixed(2);
                stockCurrentPrice.innerText = "$" + currentPrices[i].toFixed(2);
                stockChange.innerText = ((currentPrices[i] * 100) / stocksList[i].price).toFixed(2) + "%";
                tableRow.appendChild(stockName);
                tableRow.appendChild(stockQuantity);
                tableRow.appendChild(stockAveragePrice);
                tableRow.appendChild(stockCurrentPrice);
                tableRow.appendChild(stockChange);
                stockTableBody.appendChild(tableRow);
            }
        })
    })
    
})



function fetchAllStocks(symbols){
    var link = "http://portfoliomanager-env.eba-49pyjjuv.us-east-2.elasticbeanstalk.com/api/all?symbol=";
    for (var i = 0; i < symbols.length; i++){
        link += symbols[i] + ",";
    }
    return fetch(link, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

function getUserFromToken(token){
    return fetch("http://portfoliomanager-env.eba-49pyjjuv.us-east-2.elasticbeanstalk.com/username?token=" + token, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

function getAllTransactions(userId){
    return fetch("http://portfoliomanager-env.eba-49pyjjuv.us-east-2.elasticbeanstalk.com/transactions?userId=" + userId, {
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