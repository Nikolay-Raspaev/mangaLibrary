import { useEffect, useState } from "react";
import TableManga from '../components/Table/TableManga';

export default function Manga() {

    const host = "http://localhost:8080";

    const [creatorId, setCreatorId] = useState(0);

    const [mangaId, setMangaId] = useState(0);

    const [mangaName, setMangaName] = useState("");

    const [chapterCount, setChapterCount] = useState(0);


    const [data, setData] = useState([]);


    const table = document.getElementById("tbody");

    useEffect(() => {
        getData();
      },[]);


    const getData = async function () {
        const response = await fetch(host + "/manga");
        setData(await response.json())
        console.log(data);
        //table.innerHTML = "";
        // data.forEach(Manga => {
        //     let temp = "<select>";
        //     Manga.mangas.forEach(Manga => {
        //         temp += `<option>${Manga.mangaName + " " + Manga.chapterCount}</option>>`
        //     })
        //     temp += "</select>"
        //     table.innerHTML +=
        //         `<tr>
        //                 <th scope="row">${Manga.id}</th>
        //                 <td>${Manga.mangaName}</td>
        //                 <td>${Manga.hashedPassword}</td>
        //                 <td>${temp}</td>
        //             </tr>`;
        //     })
        }

    const create = async function (){
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/manga?creatorId=${creatorId}&chapterCount=${chapterCount}&mangaName=${mangaName}`, requestParams);
        return await response.json();
    }

    const remove = async function (){
        console.info('Try to remove item');
        if (mangaId !== 0) {
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
        console.log("REMOVE");
        getData();
    }

    const update = async function (){
        console.info('Try to update item');
        if (mangaId === 0 || mangaName == null || password === 0) {
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
    const createButton = (e) =>{
        e.preventDefault()
        create().then((result) => {
            getData();
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
        });
    }

    const removeButton = (e) =>{
        e.preventDefault()
        remove();
        getData();
    }

    const updateButton = (e) =>{
        e.preventDefault()
        update();
        getData();
    }



    return (
        <main>
        <div className="container" id="root-div">
            <div className="content">
                <h1>Manga</h1>
                <form id="form">
                    <div className="d-flex justify-content-evenly mt-3">
                        <div className="col-sm-2">
                            <label htmlFor="mangaId" className="form-label">creatorId</label>
                            <input type='number' value = {creatorId} onChange={event => setCreatorId(event.target.value)} className="form-control"/>
                        </div>
                        <div className="col-sm-2">
                            <label htmlFor="mangaId" className="form-label">mangaId</label>
                            <input type='number' value = {mangaId} onChange={event => setMangaId(event.target.value)} className="form-control"/>
                        </div>
                        <div className="col-sm-2">
                            <label htmlFor="mangaName" className="form-label">mangaName</label>
                            <input type='text' value = {mangaName} onChange={event => setMangaName(event.target.value)} className="form-control"/>
                        </div>
                        <div className="col-sm-2">
                            <label htmlFor="chapterCount" className="form-label">chapterCount</label>
                            <input type='number' value = {chapterCount} onChange={event => setChapterCount(event.target.value)} className="form-control"/>
                        </div>                           
                    </div>
                    <div className="row mt-3">
                        <div className="d-grid col-sm-3 mx-auto">
                            <button type="submit" onClick={createButton} className="btn btn-success">Добавить</button>
                        </div>
                        <div className="d-grid col-sm-3 mx-auto">
                            <button type="submit" onClick={updateButton} className="btn btn-success" id="btnUpdate" >Обновить</button>
                        </div>
                        <div className="d-grid col-sm-3 mx-auto">
                            <button id="btnRemove" onClick={removeButton} className="btn btn-success">Удалить</button>
                        </div>
                    </div>           
                </form>
                <div className="row table-responsive text-white">

                    <table className="table mt-3 text-white">
                        <thead>
                        <tr>
                            <th scope="col">#</th>
                            <th scope="col">mangaName</th>
                            <th scope="col">chapterCount</th>
                            <th scope="col">mangaId</th>
                            <th scope="col">readers</th>
                        </tr>
                        </thead>
                        <TableManga items = {data}/>
                        {/* <tbody id="tbody">
                        </tbody> */}
                    </table>
                </div>
            </div>
        </div>
        </main>
    );
}
