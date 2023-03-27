"use strict";

window.addEventListener('DOMContentLoaded', function () {
    const host = "http://localhost:8080";
    const table = document.getElementById("tbody");
    const form = document.getElementById("form");
    const creatorIdInput = document.getElementById("creatorId");
    const mangaIdInput = document.getElementById("mangaId");
    const creatorNameInput = document.getElementById("creatorName");
    const passwordInput = document.getElementById("password");
    const buttonRemove = document.getElementById("btnRemove");
    const buttonUpdate = document.getElementById("btnUpdate");btnRemoveAll
    const buttonRemoveAll = document.getElementById("btnRemoveAll");
    const getData = async function () {
        table.innerHTML = "";
        const response = await fetch(host + "/creator");
        const data = await response.json();
        console.log(data);
        data.forEach(Creator => {
            let temp = "<select>";
            Creator.mangas.forEach(Manga => {
                temp += `<option>${Manga.mangaName + " " + Manga.chapterCount}</option>>`
            })
            temp += "</select>"
            table.innerHTML +=
                `<tr>
                        <th scope="row">${Creator.id}</th>
                        <td>${Creator.creatorName}</td>
                        <td>${Creator.hashedPassword}</td>
                        <td>${temp}</td>
                    </tr>`;
        })
    }

    const create = async function (creatorName, password) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/creator?creatorName=${creatorName}&password=${password}`, requestParams);
        return await response.json();
    }

    const remove = async function (){
        console.info('Try to remove item');
        if (creatorIdInput.value !== 0) {
            if (!confirm('Do you really want to remove this item?')) {
                console.info('Canceled');
                return;
            }
        }
        const requestParams = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/creator/` + creatorIdInput.value, requestParams);
        return await response.json();
    }

    const removeAll = async function (){
        console.info('Try to remove item');
        if (!confirm('Do you really want to remove this item?')) {
            console.info('Canceled');
            return;
        }
        const requestParams = {
            method: "DELETE",
        };
        await fetch(host + `/creator/`, requestParams);
    }

    const update = async function (){
        console.info('Try to update item');
        if (creatorIdInput.value === 0 || creatorNameInput.value == null || passwordInput.value === 0) {
            return;
        }
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/creator/${creatorIdInput.value}?creatorName=${creatorNameInput.value}&password=${passwordInput.value}`, requestParams);
        return await response.json();
    }

    buttonRemove.addEventListener('click', function (event){
        event.preventDefault();
        remove().then((result) => {
            getData()
            creatorIdInput.value = "";
        });
    });
    buttonRemoveAll.addEventListener('click', function (event){
        event.preventDefault();
        removeAll().then(() => {
            getData()
        });
    });

    buttonUpdate.addEventListener('click', function (event){
        event.preventDefault();
        update().then((result) => {
            getData()
            creatorIdInput.value = "";
            passwordInput.value = "";
            creatorNameInput.value = "";
        });
    });

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        create(creatorNameInput.value, passwordInput.value).then((result) => {
            getData();
            creatorNameInput.value = "";
            passwordInput.value = "";
            console.log(result);
            alert(`Creator[id=${result.id}, creatorNameInput=${result.creatorName}, passwordInput=${result.hashedPassword}]`);
        });
    });

    getData();
});