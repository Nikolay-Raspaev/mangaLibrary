import { useEffect, useState } from "react";
import TableCreator from '../components/Table/TableCreator';
import MangaDto from '../Dto/Manga-Dto';

export default function Creator() {

    const host = "http://localhost:8080";

    const [creatorId, setCreatorId] = useState(0);

    const [creatorName, setCreatorName] = useState("");

    const [password, setPassword] = useState("");

    const [mangaModel, setMangaModel] = useState(new MangaDto({}));

    const [data, setData] = useState([]);


    const table = document.getElementById("tbody");

    useEffect(() => {
        getData()
        .then(_data =>setData(_data)) ;
        console.log(2);
      },[]);


    const getData = async function () {
        const response = await fetch(host + "/creator");
        const _data = await response.json()
        console.log(data);
        return _data;

        //table.innerHTML = "";
        // data.forEach(Creator => {
        //     let temp = "<select>";
        //     Creator.mangas.forEach(Manga => {
        //         temp += `<option>${Manga.mangaName + " " + Manga.chapterCount}</option>>`
        //     })
        //     temp += "</select>"
        //     table.innerHTML +=
        //         `<tr>
        //                 <th scope="row">${Creator.id}</th>
        //                 <td>${Creator.creatorName}</td>
        //                 <td>${Creator.hashedPassword}</td>
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
        const response = await fetch(host + `/creator?creatorName=${creatorName}&password=${password}`, requestParams);
        getData();
    }

    const remove = async function (){
        console.info('Try to remove item');
        if (creatorId !== 0) {
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
        const response = await fetch(host + `/creator/` + creatorId, requestParams);
        console.log("REMOVE");
        getData();
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
        if (creatorId === 0 || creatorName == null || password === 0) {
            return;
        }
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/creator/${creatorId}?creatorName=${creatorName}&password=${password}`, requestParams);
        getData();
        return await response.json();
    }
    const createButton = (e) =>{
        e.preventDefault()
        create();
    }

    const removeButton = (e) =>{
        e.preventDefault()
        remove();
    }

    const updateButton = (e) =>{
        e.preventDefault()
        update();
    }

    return (
        <main>
        <div className="container" id="root-div">
            <div className="content">
                <h1>Creator</h1>
                <form id="form">
                    <div className="d-flex justify-content-evenly mt-3">
                        <div className="col-sm-2">
                            <label htmlFor="creatorId" className="form-label">creatorId</label>
                            <input type='number' value = {creatorId} onChange={event => setCreatorId(event.target.value)} className="form-control"/>
                        </div>
                        <div className="col-sm-2">
                            <label htmlFor="creatorName" className="form-label">creatorName</label>
                            <input type='text' value = {creatorName} onChange={event => setCreatorName(event.target.value)} className="form-control"/>
                        </div>
                        <div className="col-sm-2">
                            <label htmlFor="password" className="form-label">password</label>
                            <input type='text' value = {password} onChange={event => setPassword(event.target.value)} className="form-control"/>
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
                            <th scope="col">CreatorName</th>
                            <th scope="col">Password</th>
                            <th scope="col">Mangs</th>
                        </tr>
                        </thead>
                        <TableCreator items = {data}/>
                        {/* <tbody id="tbody">
                        </tbody> */}
                    </table>
                </div>
            </div>
        </div>
        </main>
    );
}
