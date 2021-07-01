console.log("in script!!!");
document.addEventListener('DOMContentLoaded', function (event) {

    console.log("DOMContentLoaded");

    let state = "init"; // countries - countries, cities - cities, statistics - statistics
    let countriesBtn = document.querySelector(".countries-btn");
    let citiesBtn = document.querySelector(".cities-btn");
    let statBtn = document.querySelector(".stat-btn");
    let downloadBtn = document.querySelector(".download-btn");

    // update();
    let content = document.querySelector(".content");

    let edId = document.querySelector(".client_form > .client_id");
    let edName = document.querySelector(".client_form > .client_name");
    let edSurname = document.querySelector(".client_form > .client_surname");
    let edEmail = document.querySelector(".client_form > .client_email");
    let edPassw = document.querySelector(".client_form > .client_password");

    let root = document.querySelector('.root_path');
    let pathRoot = root.textContent + '/api/user/';
    console.log(pathRoot);

    // let pathRoot = "http://localhost:8080/api/user/";
    //let pathRoot = prompt('pathRoot?',"http://localhost/api/user/" );
    // Request URL: http://vovanik.ddns.net/journey/api/user/users


        let clientPatern = {
        id: -1,
        name: "John",
        surname: "Johnson",
        email: "johnson@mail.com",
        pwd: "123",

    }

    clientsShow();


    /* ************************* clientShow all *************************************/
    async function clientsShow() {
        let response = await fetch(pathRoot + 'users', {
            method: 'GET',
            headers: {
                'accept': 'application/json',
                'content-type': 'application/json',
            },
        });

        if (response.ok) {
            let json = await response.json();
            console.log(json);

            // let table = document.createElement('div');
            // content.append(table);
            // console.log(str);
            let row_container = document.querySelector('.row_container');

            for (let i = 0; i < json.length; i++) {
                str =
                    `
                    <div class='client_num'>${i}</div>
                    <div class='client_id'>${json[i].id}</div>
                    <div class='client_name'>${json[i].name}</div>
                    <div class='client_surname'> ${json[i].surname}</div>
                    <div class='client_email'> ${json[i].email}</div>
                    <div class='client_password'>${json[i].pwd}</div>

                 <div id='craeteButton_${json[i].id}' class='client_row_button craeteButton'>Create</div>
                 <div id='updateButton_${json[i].id}' class='client_row_button updateButton'>Update</div>
                 <div id='deleteButton_${json[i].id}' class='client_row_button deleteButton'>Delete</div>
                 `;

                table = document.createElement('div');
                table.className = "client_row client_row_data";
                if (i % 2 == 0) {
                    table.className = "client_row client_row_odd client_row_data";
                }
                table.innerHTML = str;
                table.id = 'rowID' + json[i].id;

                table.addEventListener("click", clientClicked);
                row_container.append(table);
            }


        } else {
            alert("HTTP error: " + response.status);
        }
    }

        /* ************************* redraw*************************************/
        async function redraw() {
            let row_container = document.querySelector('.row_container');
            rows = row_container.querySelectorAll('.client_row');
            rows.forEach(row=>row.remove());
            clientsShow();
        }


    /* ********************** getRowByChildId  **********************************/
    function getRowByChildId(idString) {
        let strs = idString.split("_");
        let rowId = "rowID" + strs[1];
        return document.getElementById(rowId);
    }

    function getIdFromRow(row) {
        if (row == null || row ==undefined)
            return -1;
        id = row.querySelector('.client_id').textContent;
        if (id == null || id == undefined)
            return -1;
        return id;
    }

    /* ********************** createClicked  **********************************/
    async function createClicked(event) {
        console.log("createClicked ", this.id);
        let row = getRowByChildId(this.id);
        let idDB = row.querySelector(".client_id").innerText;

        clientPatern.id = idDB;
        clientPatern.name = row.querySelector(".client_name").innerText;
        clientPatern.surname = row.querySelector(".client_surname").innerText;
        clientPatern.email = row.querySelector(".client_email").innerText;
        clientPatern.password = row.querySelector(".client_password").innerText;
        console.log("clientPatern", clientPatern);
        deselectRow();
        await clientCreate(clientPatern);
        redraw();
    }

    /* ********************** updateClicked  **********************************/
    async function updateClicked(event) {

        let row = getRowByChildId(this.id);
        let idDB = row.querySelector(".client_id").innerText;
        console.log("updateClicked id = ", idDB);

        clientPatern.id = idDB;
        clientPatern.name = row.querySelector(".client_name").innerText;
        clientPatern.surname = row.querySelector(".client_surname").innerText;
        clientPatern.email = row.querySelector(".client_email").innerText;
        clientPatern.pwd = row.querySelector(".client_password").innerText;
        deselectRow();

        await clientUdate(clientPatern);
        // redraw();

    }

    /* ********************** deleteClicked  **********************************/
    async function deleteClicked(event) {
        let row = getRowByChildId(this.id);
        let idDB = row.querySelector(".client_id").innerText;
        console.log("deleteClicked id = ", idDB);

        clientPatern.id = idDB;
        clientPatern.name = row.querySelector(".client_name").innerText;
        clientPatern.surname = row.querySelector(".client_surname").innerText;
        clientPatern.email = row.querySelector(".client_email").innerText;
        clientPatern.password = row.querySelector(".client_password").innerText;
        console.log("clientPatern", clientPatern);

        await  clientDelete(clientPatern);
        redraw();

    }

    /* ************************* clientClick ******************************/
    function clientClicked(event) {
        let clickedRow = document.getElementById(this.id);

        let selecteddRow = document.querySelector(".client_selected");
        console.log("clickedRow = ", getIdFromRow(clickedRow));
        console.log("selecteddRow = ", getIdFromRow(selecteddRow));

        if (getIdFromRow(clickedRow) == getIdFromRow(selecteddRow))
            return;

        let idDB = clickedRow.querySelector(".client_id").innerText;

        clickedRow.querySelector(`.craeteButton`).addEventListener("click", createClicked);
        clickedRow.querySelector(`.updateButton`).addEventListener("click", updateClicked);
        clickedRow.querySelector(`.deleteButton`).addEventListener("click", deleteClicked);

        if (deselectRow(idDB)) {
            clientGet(idDB);
            return;
        }
    }

    /****************************** selectRow *************************************** */
    function selectRow(clientFromDB) {
        clientElementId = 'rowID' + clientFromDB.id;

        let row = document.getElementById(clientElementId);
        row.className = ("client_selected client_row");
        let medNum = row.querySelector(".client_num");
        let medId = row.querySelector(".client_id");
        medId.innerText = clientFromDB.id;
        let medName = row.querySelector(".client_name");
        medName.contentEditable = 'true';
        medName.innerText = clientFromDB.name;
        let medSurname = row.querySelector(".client_surname");
        medSurname.contentEditable = 'true';
        medSurname.innerText = clientFromDB.surname;
        let medEmail = row.querySelector(".client_email");
        medEmail.contentEditable = 'true';
        medEmail.innerText = clientFromDB.email;
        let medPassw = row.querySelector(".client_password");
        medPassw.contentEditable = 'true';
        medPassw.innerText = clientFromDB.pwd;
    }

    /****************************** deselectRow *************************************** */
    function deselectRow(newSelectedId) {
        let rows = document.querySelectorAll(".client_selected");
        rows.forEach(row => {
            if (row == undefined) {
                // console.log("undefined rowId = " + "undefined", "newSelectedId = " + newSelectedId);
                return true;
            }
            let rowId = row.querySelector(".client_id").textContent;

            if (newSelectedId == rowId) {
                // console.log("equals rowId = " + rowId, "newSelectedId = " + newSelectedId);
                return false;
            }
            // console.log("different rowId = " + rowId, "newSelectedId = " + newSelectedId);
            row.className = ("client_row client_row_data");
            let rowNum = row.querySelector(".client_num")
            let ii = rowNum.textContent;
            if (ii % 2 == 0) {
                row.className = "client_row client_row_odd client_row_data";
            }
            row.querySelector(`.craeteButton`).removeEventListener('click', createClicked);
            row.querySelector(`.updateButton`).removeEventListener("click", updateClicked);
            row.querySelector(`.deleteButton`).removeEventListener("click", deleteClicked);
        });
        return true;
    }

    /****************************** clientGet(id) *************************************** */
    async function clientGet(id) {
        console.log("getting from db clientid id = ", id);
        if (id == undefined || id == null || id < 0)
            return;

        let param = new URLSearchParams({ id: id, });

        let response = await fetch(pathRoot + 'user/?' + param, {
            method: 'GET',
            headers: {
                'accept': 'application/json',
                'content-type': 'application/json',
            },
        });

        if (response.ok) {
            let clientFromDB = await response.json();
            selectRow(clientFromDB);
            console.log(clientFromDB);
            return clientFromDB;

        } else {
            alert("HTTP error: " + response.status);
            return;
        }
    }

    /* ************************* clientCreate *************************************/
    async function clientCreate(client) {
        // console.log("In client create. Client = ", client)
        let response = await fetch(pathRoot + 'create', {
            method: 'POST',
            headers: {
                'accept': 'application/json',
                'content-type': 'application/json',
            },
            body: JSON.stringify(client)
        });

        if (response.ok) {
            let json = await response.json();
            console.log("Created client is - ", json);
            return;
        }
        if (response.status == 409) {
            console.log("Conflict!!!!!! client  already exists");
            alert("Client with e-mail " + client.email + " already exists");
            return;
        }
        alert("HTTP error: " + response.status);
    }

    /* ************************* clientDelete *************************************/
    async function clientDelete(client) {
        // console.log("In client create. Client = ", client)
        let response = await fetch(pathRoot + 'delete', {
            method: 'DELETE',
            headers: {
                'accept': 'application/json',
                'content-type': 'application/json',
            },
            body: JSON.stringify(client)
        });

        if (response.ok) {
            let json = await response.json();
            console.log("Deleted client is - ", json);
            return;
            
        }
        if (response.status == 409) {
            console.log("Conflict!!!!!! client  already exists");
            alert("Unable to delete incorrect user passed");
            return;
        }
        alert("HTTP error: " + response.status);
    }

    /* ************************* clientUdate *************************************/
    async function clientUdate(client) {
        // console.log("In client create. Client = ", client)
        let response = await fetch(pathRoot + 'update', {
            method: 'POST',
            headers: {
                'accept': 'application/json',
                'content-type': 'application/json',
            },
            body: JSON.stringify(client)
        });

        if (response.ok) {
            let json = await response.json();
            console.log("Updated client is - ", json);
            selectRow(json);
            return;
        }
        if (response.status == 409) {
            console.log("Conflict!!!!!! client  already exists");
            alert("Client with e-mail " + client.email + " already exists");
            return;
        }
        alert("HTTP error: " + response.status);
    }



});

