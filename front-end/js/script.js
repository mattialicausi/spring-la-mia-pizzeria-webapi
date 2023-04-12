// VARIABLES

const domContainerList = document.getElementById('dom-container-list');
const BASE_URL = 'http://localhost:8080/api/v1/pizza';


// METHODS

// get api

const getPizze = async () => {
    const response = await fetch(BASE_URL);
    console.log(response);
    return response;
};

// api into JSON

const loadAllPizza = async () => {
    const response = await getPizze();

    if (response.ok) {
        const allPizza = await response.json();
        console.log(allPizza);

        
        domContainerList.innerHTML = createPizzaList(allPizza);
   

    } else {
        console.log("ERROR");
    }
}


// create single item

const createSingleItem = (item) => {

    return `
    <div class="card h-100 col-4" style="width: 18rem;">
        <img class="h-50" src="${item.image}" class="card-img-top" alt="${item.name}">
        <div class="card-body">
            <div class="d-flex align-items-center justify-content-between">
                <h5 class="card-title">${item.name}</h5>
                <h5>€ ${item.price}</h5>
            </div>
            <p class="card-text">${item.description}.</p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
        </div>
    </div>
    
    `;

}

const createPizzaList = (data) => {

    let list = "";

    data.forEach((element) => {
        list += createSingleItem(element);
    })

    return list;

}


//STARTING METHODS



loadAllPizza();