"use strict";

window.addEventListener('DOMContentLoaded', function () {
    const host = "http://localhost:8080";
    const table = document.getElementById("tbody");
    const form = document.getElementById("form");
    const readerIdInput = document.getElementById("readerId");
    const mangaIdInput = document.getElementById("mangaId");
    const readerNameInput = document.getElementById("readerName");
    const hashedPasswordInput = document.getElementById("hashedPassword");
    const buttonRemove = document.getElementById("btnRemove");
    const buttonUpdate = document.getElementById("btnUpdate");
    const buttonRemoveManga = document.getElementById("btnRemoveManga");
    const buttonAddManga = document.getElementById("btnAddManga");
    const getData = async function () {
        table.innerHTML = "";
        const response = await fetch(host + "/reader");
        const data = await response.json();
        console.log(data);
        data.forEach(Reader => {
            let temp = "<select>";
            Reader.mangas.forEach(String => {
                temp += `<option>${String}</option>>`
            })
            temp += "</select>"
            table.innerHTML +=
                `<tr>
                        <th scope="row" id="componentId">${Reader.id}</th>
                        <td>${Reader.readerName}</td>
                        <td>${Reader.hashedPassword}</td>
                        <td>${temp}</td>
                    </tr>`;
        })
    }

    const create = async function (readerName, hashedPassword) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        console.log(host + `/reader?readerName=${readerName}&password=${hashedPassword}`, requestParams);
        console.log(readerName);
        const response = await fetch(host + `/reader?readerName=${readerName}&password=${hashedPassword}`, requestParams);
        console.log(readerName);
        return await response.json();
    }

    const addManga = async function () {
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        console.log(host + `/reader/${readerIdInput.value}/addManga?mangaId=${mangaIdInput.value}`, requestParams);
        const response = await fetch(host + `/reader/${readerIdInput.value}/addManga?mangaId=${mangaIdInput.value}`, requestParams);
        return await response.json();
    }

    const remove = async function (){
        console.info('Try to remove item');
        if (readerIdInput.value !== 0) {
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
        const response = await fetch(host + `/reader/` + readerIdInput.value, requestParams);
        return await response.json();
    }

    const removeManga = async function (){
        console.info('Try to remove item');
        if (!confirm('Do you really want to remove this item?')) {
            console.info('Canceled');
            return;
        }       
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        console.log(host + `/reader/${readerIdInput.value}/removeManga?mangaId=${mangaIdInput.value}`, requestParams);
        const response = await fetch(host + `/reader/${readerIdInput.value}/removeManga?mangaId=${mangaIdInput.value}`, requestParams);
        return await response.json();
    }

    const update = async function (){
        console.info('Try to update item');
        if (readerIdInput.value === 0 || hashedPasswordInput.value === null) {
            return;
        }
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/reader/${readerIdInput.value}?readerName=${readerNameInput.value}&password=${hashedPasswordInput.value}`, requestParams);
        return await response.json();
    }

    buttonRemove.addEventListener('click', function (event){
        event.preventDefault();
        remove().then((result) => {
            getData()
            readerIdInput.value = "";
        });
    });

    buttonRemoveManga.addEventListener('click', function (event){
        event.preventDefault();
        removeManga().then((result) => {
            getData()
            mangaIdInput.value = "";
        });
    });

    buttonAddManga.addEventListener('click', function (event){
        event.preventDefault();
        addManga().then((result) => {
            getData()
            mangaIdInput.value = "";
        });
    });

    buttonUpdate.addEventListener('click', function (event){
        event.preventDefault();
        update().then((result) => {
            getData()
            readerIdInput.value = "";
            hashedPasswordInput.value = "";
        });
    });

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        console.log(readerNameInput.value);
        create(readerNameInput.value, hashedPasswordInput.value).then((result) => {
            console.log(readerNameInput.value);
            getData();
            hashedPasswordInput.value = "";
            readerIdInput.value = "";
            alert(`Reader[id=${result.id}, readerName=${result.readerName}, password=${result.hashedPassword}]`);
        });
    });

    getData();
});