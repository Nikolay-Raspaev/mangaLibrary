import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import TableCreator from '../Table/TableCreator';
import MangaDto from "../../Dto/Manga-Dto";

export default function AddMangaReaderModal(props) {


    return (
    <div className="modal fade text-black" id="exampleModal2" tabIndex="-1" aria-labelledby="exampleModalLabel2" aria-hidden="true">
        <div className="modal-dialog">
            <div className="modal-content">
                <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel2">Добавление манги к читателю</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div className="modal-body">
                    <div>
                        <select className="form-select" value={props.mangaId} onChange={event => props.setMangaId(event.target.value)} aria-label="Default select example">
                            <option value={0}>Manga</option>
                                {
                                    props.mangaData.map((mangaD) =>
                                        <option key={mangaD.id} value={mangaD.id}>{mangaD.mangaName}</option>
                                    )
                                }
                        </select>
                    </div>
                </div>
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" onClick={props.addMangaButton} className="btn btn-primary" data-bs-dismiss="modal">Save changes</button>
                </div>
            </div>
        </div>
    </div>  
    );
}
