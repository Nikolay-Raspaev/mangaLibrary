import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../Table/TableCreator';
import MangaDto from "../../Dto/Manga-Dto";

export default function MangaReaderList(props) {


    return (
        <div className="row table-responsive text-white">
        {
            props.reader.mangas?.map((manga) =>       
                <div key={manga.id} className="d-flex flex-row flex-wrap  flex-grow-1 align-items-center mt-3"> 
                    <div className="me-3">
                        <NavLink to={`/mangapage?id=${manga.id}`} ><img src={manga.image} alt={manga.mangaName} className="slideshow" /></NavLink>
                    </div> 
                    <div>
                        <div className="pt-3 description d-flex flex-column justify-content-start mb-3 fs-6 fw-bold">
                            <NavLink className="text-white fs-5 unic_class fw-bold pt-3 mb-3"
                                to={`/mangapage?id=${manga.id}`}>Название: {manga.mangaName}
                            </NavLink>
                            <p>Глав: {manga.chapterCount}</p>
                        </div>
                        <div>
                            <button className="delete bg-danger p-2 px-2 mx-2 border border-0 rounded text-white fw-bold" onClick = {() => props.removeButton(manga.id)}  type="button">Удалить</button>                       
                        </div>
                    </div>                  
                </div>
            )
        }   
        </div>
    );
}