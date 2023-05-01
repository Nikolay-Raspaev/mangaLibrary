import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../components/Table/TableCreator';
import MangaDto from "../Dto/Manga-Dto";
import MangaCreatorList from "../components/List/MangaCreatorList";
import AddMangaModal from "../components/Modal/AddMangaModal";
import EditMangaModal from "../components/Modal/EditMangaModal";

export default function CreatorAction() {

    const host = "http://localhost:8080/api";

    const [creatorData, setCreatorData] = useState([]);

    const [creatorId, setCreatorId] = useState(0);

    const [creator, setCreator] = useState([]);

    const [mangaId, setMangaId] = useState(0);

    const [chapterCount, setChapterCount] = useState(0);

    const [mangaName, setMangaName] = useState("");

    const [mangaModel, setMangaModel] = useState(new MangaDto({}));

    useEffect(() => {
        getCreatorData()
        .then(_data =>setCreatorData(_data));
        },[]);

    const getCreatorData = async function () {
        const response = await fetch(host + "/creator");
        const _data = await response.json()
        return _data;
        }
    
    useEffect(() => {
        console.log(creatorId);
        if (creatorId != 0){
            console.log(creatorId);
            getCreator(creatorId)
            .then(_data =>setCreator(_data));
        }
        },[creatorId]);

    const getCreator = async function (id) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/creator/` + id, requestParams);
        const _data = await response.json()
        return _data;
        }

    const updateButton = (e) =>{
        e.preventDefault();
        update().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            getCreator(creatorId)
            .then(_data =>setCreator(_data));
        });
    }

    const update = async function (){
        console.info('Try to update item');
        if (mangaId === 0) {
            return;
        }
        mangaModel.id = mangaId;
        mangaModel.chapterCount = chapterCount;
        mangaModel.creatorId = creatorId;
        mangaModel.image = imageURL;
        mangaModel.mangaName = mangaName;
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(mangaModel),
        };
        const response = await fetch(host + `/manga/` + mangaModel.id, requestParams);
        return await response.json();
    }

    const setMangaIdButton = (id) =>{
        setMangaId(id);
    }

    const removeButton = (id) =>{
        remove(id).then(() => {
            getCreator(creatorId)
            .then(_data =>setCreator(_data));
        });
    }

    const remove = async function (id){
        console.info('Try to remove item');
        if (id !== 0) {
            if (!confirm('Do you really want to remove this item?')) {
                return;
            }
        }
        const requestParams = {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/manga/` + id, requestParams);
    }

    const createButton = (e) =>{
        e.preventDefault()
        create().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            getCreator(creatorId)
            .then(_data =>setCreator(_data));
        });
    }

    const create = async function (){
        mangaModel.chapterCount = chapterCount;
        mangaModel.creatorId = creatorId;
        mangaModel.image = imageURL;
        mangaModel.mangaName = mangaName;
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(mangaModel),
        };
        const response = await fetch(host + `/manga`, requestParams);
        return await response.json();
    }
    
        const [imageURL, setImageURL] = useState();
        const fileReader = new FileReader();
    
        fileReader.onloadend = () => {
          setImageURL(fileReader.result);
        };

        const handleOnChange = (event) => {
            event.preventDefault();
            if (event.target.files && event.target.files.length) {
              const file = event.target.files[0];
              fileReader.readAsDataURL(file);
            }
          };

    return (
        <main>
        <div className="container" id="root-div">
            <div className="content">
                <h1>Creator</h1>
                <form id="form">
                    <div className="d-flex mt-3">
                        <div className="col-sm-2 me-3">
                            <select className="form-select" value={creatorId} onChange={event => setCreatorId(event.target.value)} aria-label="Default select example">
                                <option value={0}>Creator</option>
                                    {
                                        creatorData?.map((creatorD) =>
                                            <option key={creatorD.id} value={creatorD.id}>{creatorD.creatorName}</option>
                                        )
                                    }
                            </select>
                        </div>
                        <div className="d-grid col-sm-2">
                            <button type="button" className="btn btn-success" data-bs-toggle="modal" data-bs-target="#exampleModal2">Добавить</button>
                        </div>
                    </div>
                </form>
                <MangaCreatorList
                    creator={creator}
                    setMangaIdButton={setMangaIdButton}
                    removeButton={removeButton}
                />
                <EditMangaModal
                    chapterCount={chapterCount}
                    setChapterCount={setChapterCount}
                    handleOnChange={handleOnChange}
                    updateButton={updateButton}
                />
                <AddMangaModal
                    chapterCount={chapterCount}
                    setChapterCount={setChapterCount}
                    mangaName={mangaName}
                    setMangaName={setMangaName}
                    handleOnChange={handleOnChange}
                    createButton={createButton}
                />
                      
            </div>
        </div>
        </main>
    );
}
