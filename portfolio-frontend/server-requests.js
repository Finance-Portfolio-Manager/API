function fetchStock(symbol){
    const fetchRequest = "https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + 
                            symbol + "&outputsize=compact&datatype=json"
    fetch(fetchRequest, {
        "method": "GET",
        "headers": {
            "x-rapidapi-key": "2026ae733amsh2b3a3e7ba055725p1025d0jsn28ad9fad2454",
            "x-rapidapi-host": "alpha-vantage.p.rapidapi.com"
        }
    })
    .then(response => {
        return response;
    })
    .catch(err => {
        console.error(err);
    });
}

//symbols should be something you obtain from getAllStocks
//%2C to split symbols
function fetchAllStocks(symbols){
    fetch("https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/get-quotes?region=US&symbols=" + symbols, {
	"method": "GET",
	"headers": {
		"x-rapidapi-key": "2026ae733amsh2b3a3e7ba055725p1025d0jsn28ad9fad2454",
		"x-rapidapi-host": "apidojo-yahoo-finance-v1.p.rapidapi.com"
	}
    })
    .then(response => {
	    return response;
    })
    .catch(err => {
	    console.error(err);
    });
}

/*
Stock{
    int userId
    int stockId
    String stockSymbol
    double stockQuantity
}
*/
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

function deleteStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'delete',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
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

function updateStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'put',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
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

function getStock(stockId){
    fetch("http://localhost:8082/stocks/" + stockId, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
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

function getAllStocks(userId){
    fetch("http://localhost:8082/stocks/all/" + userId, {
        method: 'get',
        headers: new Headers({
            'Content-Type':'application/json'
        })
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

function removeUser(password){  
    const credentials = {
        username:sessionStorage.getItem('Authorization'),
        password:password
    }
    fetch("http://localhost:8082/delete", {
        method: 'DELETE',
        headers: new Headers({
            'Content-Type':'application/json',
        }),
        body: JSON.stringify(credentials)
    }).then((response) => {
        console.log(response);
        // return response.json();
    // }).then((data) => {
    //     sessionStorage.setItem("Authorization", data.jwt)
    //     if(data.jwt==undefined){
    //         userError.hidden = false;
    //     } else if (data.jwt != null && data.jwt != undefined){
    //         document.getElementById("toggle-nav-1").hidden = true;
    //         document.getElementById("toggle-nav-2").hidden = true;
    //         window.location.href = "#/overview";
    //      }  
    }).catch((error) => {
            // networkError.hidden = false;
            console.error(error);
    })
};
