import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../Table/TableCreator';
import MangaDto from "../../Dto/Manga-Dto";

export default function AddMangaModal(props) {


    return (
        <div className="modal fade text-black" id="exampleModal2" tabIndex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel2">Создание манги</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div>
                            <label htmlFor="chapterCount" className="form-label">chapterCount</label>
                            <input type='number' value = {props.chapterCount} onChange={event => props.setChapterCount(event.target.value)} className="form-control"/>
                        </div>  
                        <div>
                            <label htmlFor="mangaName" className="form-label">mangaName</label>
                            <input type='text' value = {props.mangaName} onChange={event => props.setMangaName(event.target.value)} className="form-control"/>
                        </div>  
                        <div>
                            <label 
                                htmlFor="file-loader-button" 
                                className="form-label">
                                Загрузить файл
                            </label>
                            <input
                                id="file-loader-button"
                                type="file"
                                className="form-control text-white"
                                onChange={props.handleOnChange}
                            />
                        </div>
                    </div>

                    <div className="modal-footer">
                        <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" onClick={props.createButton} className="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
                    </div>
                </div>
            </div>
        </div>  
    );
}
