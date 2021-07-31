export default function fetchAllStocks(symbols){
    var link = "http://localhost:8082/api/all?symbol=";
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

export default function getUserFromToken(token){
    return fetch("http://localhost:8082/username?token=" + token, {
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

export default function getAllTransactions(userId){
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

export default function addStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'post',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

export default function deleteStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'delete',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => { 
        return response.json()
        .catch((error) => {
            console.log(error);
        }) 
    });
}

export default function updateStock(stock){
    fetch("http://localhost:8082/stocks", {
        method: 'put',
        headers: new Headers({
            'Content-Type':'application/json'
        }),
        body: JSON.stringify(stock)
    }).then((response) => { 
        return response.json().then((data) => {
            return data;
        }).catch((error) => {
            console.log(error);
        }) 
    });
}

export default function getStock(stockId){
    return fetch("http://localhost:8082/stocks/" + stockId, {
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

export default function getAllStocks(userId){
    return fetch("http://localhost:8082/stocks/all/" + userId, {
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