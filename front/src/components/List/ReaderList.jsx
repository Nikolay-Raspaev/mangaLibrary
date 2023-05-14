import { useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';

export default function ReaderList(props) {


    return (
        <div className="row table-responsive text-white">
        {
            props.readers?.map((reader) =>         
                <div key={reader.id} className="d-flex flex-row flex-wrap  flex-grow-1 align-items-center mt-3"> 
                    <div>
                        <div className="text-white pt-3 description d-flex flex-column justify-content-start mb-3 fs-6 fw-bold">
                            Имя пользователя: {reader.readerName}
                        </div>
                    </div>                  
                </div>   
            )
        }   
        </div>
    );
}