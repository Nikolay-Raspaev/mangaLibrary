"use strict";

window.addEventListener('DOMContentLoaded', function () {
    const host = "http://localhost:8080";
    const table = document.getElementById("tbody");
    const form = document.getElementById("form");
    const creatorIdInput = document.getElementById("creatorId");
    const mangaIdInput = document.getElementById("mangaId");
    const mangaNameInput = document.getElementById("mangaName");
    const chapterCountInput = document.getElementById("chapterCount");
    const buttonRemove = document.getElementById("btnRemove");
    const buttonUpdate = document.getElementById("btnUpdate");
    const getData = async function () {
        table.innerHTML = "";
        const response = await fetch(host + "/manga");
        const data = await response.json();
        console.log(data);
        data.forEach(Manga => {
            let temp = "<select>";
            Manga.readers.forEach(String => {
                temp += `<option>${String}</option>>`
            })
            temp += "</select>"
            table.innerHTML +=
                `<tr>
                        <th scope="row" id="componentId">${Manga.id}</th>
                        <td>${Manga.mangaName}</td>
                        <td>${Manga.chapterCount}</td>
                        <td>${Manga.creatorId}</td>
                        <td>${temp}</td>
                    </tr>`;
        })
    }

    const create = async function (creatorId, chapterCount, mangaName) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        console.log(creatorId);
        console.log(chapterCount);
        console.log(mangaName);
        const response = await fetch(host + `/manga?creatorId=${creatorId}&chapterCount=${chapterCount}&mangaName=${mangaName}`, requestParams);
        return await response.json();
    }

    const remove = async function (){
        console.info('Try to remove item');
        if (mangaIdInput.value !== 0) {
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
        const response = await fetch(host + `/manga/` + mangaIdInput.value, requestParams);
        return await response.json();
    }

    const update = async function (){
        console.info('Try to update item');
        if (mangaIdInput.value === 0 || chapterCountInput.value === null) {
            return;
        }
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/manga/${mangaIdInput.value}?chapterCount=${chapterCountInput.value}`, requestParams);
        return await response.json();
    }

    buttonRemove.addEventListener('click', function (event){
        event.preventDefault();
        remove().then((result) => {
            getData()
            mangaIdInput.value = "";
        });
    });

    buttonUpdate.addEventListener('click', function (event){
        event.preventDefault();
        update().then((result) => {
            getData()
            mangaIdInput.value = "";
            chapterCountInput.value = "";
        });
    });

    form.addEventListener("submit", function (event) {
        event.preventDefault();
        create(creatorIdInput.value, chapterCountInput.value, mangaNameInput.value).then((result) => {
            getData();
            mangaIdInput.value = "";
            creatorIdInput.value = "";
            chapterCountInput.value = "";
            mangaNameInput.value = "";
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}, chapterCount=${result.chapterCount}]`);
        });
    });

    getData();
});