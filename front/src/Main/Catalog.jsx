import React, { useEffect, useState } from 'react'
import { NavLink } from 'react-router-dom';

export default function Catalog() {

    const host = "http://localhost:8080/api/1.0";

    const [mangs, setMangs] = useState([]);

    useEffect(() => {
        getMangs()
        .then(_data =>setMangs(_data));
        console.log(2);
        console.log(mangs);
        },[]);

    const getTokenForHeader = function () {
        return "Bearer " + localStorage.getItem("token");
    }

    const getMangs = async function () {
        const requestParams = {
            method: "GET",
            headers: {
                "Authorization": getTokenForHeader(),
            }
        };
        const response = await fetch(host + "/manga", requestParams);
        const _data = await response.json()
        console.log(_data);
        return _data;
        }

    return (
        <article className="p-2 catalog_article">
            <div className = "catalog_wrapper">
                <h1>Каталог</h1>
                <div className="p-2 d-flex flex-wrap">
                {mangs.map((manga, index) => (
                    <NavLink key={manga.id} to={`/mangapage?id=${manga.id}`}><img src={manga.image} alt={manga.mangaName} className="slideshow"/></NavLink>
                ))}
        
                </div>
            </div>
        </article>
    );
}