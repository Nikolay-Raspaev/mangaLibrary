import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../Table/TableCreator';
import MangaDto from "../../Dto/Manga-Dto";

export default function EditMangaModal(props) {

    return (
        <div className="modal fade text-black" id="exampleModal" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                        <h5 className="modal-title" id="exampleModalLabel">Изменение манги</h5>
                        <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                        <div>
                            <label htmlFor="chapterCount" className="form-label">chapterCount</label>
                            <input type='number' value={props.chapterCount} onChange={event => props.setChapterCount(event.target.value)} className="form-control"/>
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
                        <button type="button" onClick={props.updateButton} className="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
                    </div>
                </div>
            </div>
        </div> 
    );
}

