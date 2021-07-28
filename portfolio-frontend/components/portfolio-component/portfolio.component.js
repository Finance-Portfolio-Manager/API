const cats = [];
cats.push(new Cat("Fluffy",["orange", "white"],"short",false,1));
cats.push(new Cat("Jack", ["black", "white"], "long", true, 1));
cats.push(new Cat("Milo", ["black"], "medium", false, 1));
cats.push(new Cat("Mittens", ["white", "grey"], "short", false, 1));
cats.push(new Cat("Skittles", ["white"], "medium", true, 1));
cats.push(new Cat("Edamame", ["black", "white", "orange"], "long", true, 1));
cats.push(new Cat("Mr. Bigglesworth", ["orange"], "long", true, 1));

const catsUpForAdoption = cats.filter(cat=>!cat.isAdopted);

const catListTemplate = cats.map(cat=>`<li> ${cat.name} </li>`).join("");


const catList = document.getElementById("cat-list");
/*
// we can use a traditional for loop
for(let i=0; i<cats.length; i++){
    console.log(cats[i].name);
}

// for/in allows us to access the index directly (for arrays), or object keys (for objects)
for(let i in cats){
    console.log(cats[i].name);
}
*/

function renderCatList(catData){
    for(let cat of catData){
        let newListItem = document.createElement("li");
        newListItem.innerText = cat.name; //<script src="..."></script>
        newListItem.classList.add("list-group-item");
        if(cat.isAdopted){
            newListItem.classList.add("list-group-item-secondary");
        }
        catList.appendChild(newListItem);
    }
}

renderCatList(cats);


const catTableBody = document.getElementById("cat-table-body");
for(let cat of catsUpForAdoption){
    let tableRow = document.createElement("tr");
    let catNameData = document.createElement("td");
    let catColorData = document.createElement("td");
    catNameData.innerText = cat.name;
    catColorData.innerText = cat.colors.join(", ");
    tableRow.appendChild(catNameData);
    tableRow.appendChild(catColorData);
    catTableBody.appendChild(tableRow);
}

/*
const catNameSelect = document.getElementById("cat-name-select");
for(let cat of catsUpForAdoption){
    let catOption = document.createElement("option");
    // catOption.setAttribute("value", cat.name);
    catOption.value = cat.name;
    catOption.innerText = cat.name;
    catNameSelect.appendChild(catOption);
}
*/
// adding select options using innerHTML
const catNameSelect = document.getElementById("cat-name-select");
const optionHtml = catsUpForAdoption.map(cat=>`<option value=${cat.name}> ${cat.name} </option>`).join("");
catNameSelect.innerHTML = optionHtml;


// adding toggle functionality for showing adoptable cats vs all cats
// let showAdoptableIsToggled = false; // we use instead the checked value of the event target 
const showAdoptableToggle = document.getElementById("showAdoptableCats");
showAdoptableToggle.addEventListener("change", toggleAllCats);

// callback function provides behavior to respond when event occurs 
function toggleAllCats(event){
    // when toggle is "selected" - if cat is not adoptable, remove from the list
    /* // when we iterated, we were removing some elements and throwing off the iteration, led to inconsistent results
    const listItems = catList.children;
    for(let item of listItems){
        if(item.classList.contains("list-group-item-secondary")){
            catList.removeChild(item);
        }
    }
    */
    console.log(event.target.checked);
    catList.innerHTML ="";
    if(event.target.checked){
        renderCatList(catsUpForAdoption);
    } else {
        renderCatList(cats);
    }
}








//vantage fetch
/*fetch("https://alpha-vantage.p.rapidapi.com/query?function=TIME_SERIES_DAILY&symbol=MSFT&outputsize=compact&datatype=json", {
	"method": "GET",
	"headers": {
		"x-rapidapi-key": "2026ae733amsh2b3a3e7ba055725p1025d0jsn28ad9fad2454",
		"x-rapidapi-host": "alpha-vantage.p.rapidapi.com"
	}
})
.then(response => {
	console.log(response);
})
.catch(err => {
	console.error(err);
});
*/