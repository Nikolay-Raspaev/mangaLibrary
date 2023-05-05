import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../components/Table/TableCreator';
import MangaDto from "../Dto/Manga-Dto";
import MangaCreatorList from "../components/List/MangaCreatorList";
import AddMangaModal from "../components/Modal/AddMangaModal";
import EditMangaModal from "../components/Modal/EditMangaModal";

export default function CreatorAction() {

    const host = "http://localhost:8080/api/1.0";

    const [creatorData, setCreatorData] = useState([]);

    const [creator, setCreator] = useState([]);

    const [mangaId, setMangaId] = useState(0);

    const [chapterCount, setChapterCount] = useState(0);

    const [mangaName, setMangaName] = useState("");

    const [mangaModel, setMangaModel] = useState(new MangaDto({}));



    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

/*    useEffect(() => {
        getCreatorData()
        .then(_data =>setCreatorData(_data));
        },[]);*/


    useEffect(() => {
        getCreator().then(_data => {
            setCreator(_data)
            console.log(_data);
        });
    }, []);

    const getCreator = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
            }
        };
        let login = localStorage.getItem("user");
        console.log(host + `/creator/` + login);
        const response = await fetch(host + `/creator/` + login, requestParams);
        const _data = await response.json()
        return _data;
    }

    const getCreatorData = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const response = await fetch(host + "/creator", requestParams);
        console.log(response);
        const _data = await response.json()
        return _data;
        }
    




    const updateButton = (e) =>{
        e.preventDefault();
        update().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            getCreator(creator.id)
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
        mangaModel.creatorId = creator.id;
        mangaModel.image = imageURL;
        mangaModel.mangaName = mangaName;
        const requestParams = {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
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
            getCreator(creator.id)
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
                "Authorization": getTokenForHeader(),
            }
        };
        const response = await fetch(host + `/manga/` + id, requestParams);
    }

    const createButton = (e) =>{
        e.preventDefault()
        create().then((result) => {
            alert(`Manga[id=${result.id}, mangaName=${result.mangaName}, chapterCount=${result.chapterCount}]`);
            getCreator(creator.id)
            .then(_data =>setCreator(_data));
        });
    }

    const create = async function (){
        mangaModel.chapterCount = chapterCount;
        mangaModel.creatorId = creator.id;
        mangaModel.image = imageURL;
        mangaModel.mangaName = mangaName;
        console.log(mangaModel);
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": getTokenForHeader(),
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
