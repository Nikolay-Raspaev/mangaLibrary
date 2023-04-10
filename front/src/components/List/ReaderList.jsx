import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';

export default function ReaderList(props) {


    return (
        <div className="row table-responsive text-white">
        {
            props.readers?.map((reader) =>         
                <div key={reader.id} className="d-flex flex-row flex-wrap  flex-grow-1 align-items-center mt-3"> 
                    <div>
                        <div className="pt-3 description d-flex flex-column justify-content-start mb-3 fs-6 fw-bold">
                            <NavLink className="text-white fs-5  fw-bold pt-3 mb-3"
                                to={`/readeraction?id=${reader.id}`}>Имя пользователя: {reader.readerName}
                            </NavLink>
                        </div>
                    </div>                  
                </div>   
            )
        }   
        </div>
    );
}