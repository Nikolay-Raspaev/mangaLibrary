import React, { useEffect, useState } from 'react'
import '../components/Banner/banner.css'
import Banner from '../components/Banner/Banner.jsx'
import { Link, NavLink } from 'react-router-dom';
import MangaDto from "../Dto/Manga-Dto";

export default function Catalog() {

    const host = "http://localhost:8080/api";

    const [mangs, setMangs] = useState([]);

    useEffect(() => {
        getMangs()
        .then(_data =>setMangs(_data));
        console.log(2);
        console.log(mangs);
        },[]);

    const getMangs = async function () {
        const response = await fetch(host + "/manga");
        const _data = await response.json()
        console.log(_data);
        return _data;
        }

    return (
        <article className="p-2 catalog_article">
        <Banner />
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