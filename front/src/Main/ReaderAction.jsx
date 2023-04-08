import { useEffect, useState } from "react";
import TableReader from '../components/Table/TableReader';
import { NavLink } from 'react-router-dom';
import MangaReaderList from "../components/List/MangaReaderList";
import AddMangaReaderModal from "../components/Modal/AddMangaReaderModal";

export default function ReaderAction() {

    const host = "http://localhost:8080";

    const [mangaData, setMangaData] = useState([]);

    const [readerData, setReaderData] = useState([]);

    const [readerId, setReaderId] = useState(0);

    const [reader, setReader] = useState([]);

    const [mangaId, setMangaId] = useState(0);

    const [chapterCount, setChapterCount] = useState(0);

    const [mangaName, setMangaName] = useState("");

    useEffect(() => {
        getReaderData()
        .then(_data =>setReaderData(_data));
        getMangaData()
        .then(_data =>setMangaData(_data));
        console.log(2);
        console.log(readerData);
        },[]);

    const getReaderData = async function () {
        const response = await fetch(host + "/reader");
        const _data = await response.json()
        console.log(_data);
        return _data;
        }

    const getMangaData = async function () {
        const response = await fetch(host + "/manga");
        const _data = await response.json()
        console.log(_data);
        return _data;
        }
    
    useEffect(() => {
        console.log(readerId);
        getReader(readerId)
        .then(_data =>setReader(_data));
        console.log(readerId);
        },[readerId]);

    const getReader = async function (id) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/reader/` + id, requestParams);
        const _data = await response.json()
        return _data;
        }

    const updateButton = (e) =>{
        e.preventDefault();
        update().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            getReader(readerId)
            .then(_data =>setReader(_data));
        });
        console.log(readerId);
        
        console.log(readerId);
        console.log(reader);
    }

    const update = async function (){
        console.info('Try to update item');
        if (mangaId === 0) {
            return;
        }
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/manga/${mangaId}?chapterCount=${chapterCount}`, requestParams);
        return await response.json();
    }

    const setMangaIdButton = (id) =>{
        setMangaId(id);
    }

    const removeButton = (id) =>{
        remove(id).then((result) => {
            getReader(readerId)
            .then(_data =>setReader(_data));
        });
    }

    const remove = async function (id){
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
        console.log(host + `/reader/${readerId}/removeManga?mangaId=${id}`, requestParams);
        const response = await fetch(host + `/reader/${readerId}/removeManga?mangaId=${id}`, requestParams);
        return await response.json();
    }


    const addMangaButton = (e) =>{
        e.preventDefault()
        addManga().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            console.log(result);
            getReader(readerId)
            .then(_data =>setReader(_data));
        });
    }

    const addManga = async function (){
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            }
        };
        console.log(host + `/reader/${readerId}/addManga?mangaId=${mangaId}`, requestParams);
        const response = await fetch(host + `/reader/${readerId}/addManga?mangaId=${mangaId}`, requestParams);
        return await response.json();
    }

    return (
        <main>
        <div className="container" id="root-div">
            <div className="content">
                <h1>Reader</h1>
                <form id="form">
                    <div className="d-flex mt-3">
                        <div className="col-sm-2 me-3">
                            <select className="form-select" value={readerId} onChange={event => setReaderId(event.target.value)} aria-label="Default select example">
                                <option value={0}>Reader</option>
                                    {
                                        readerData.map((readerD) =>
                                            <option key={readerD.id} value={readerD.id}>{readerD.readerName}</option>
                                        )
                                    }
                            </select>
                        </div>
                        <div className="d-grid col-sm-2">
                            <button type="button" className="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal2">Добавить</button>
                        </div>
                    </div>
                </form>
                <MangaReaderList
                    reader={reader}
                    removeButton={removeButton}
                />
                <AddMangaReaderModal
                    mangaId={mangaId}
                    setMangaId={setMangaId}
                    mangaData={mangaData}
                    addMangaButton={addMangaButton}
                />      
            </div>
        </div>
        </main>
    );
}
