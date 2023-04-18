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
                    {/* <h2>
                        <select>
                        <option value="1">По рейтингу</option>
                        <option value="2">По лайкам</option>
                        <option value="3">По просмотрам</option>
                        <option value="4">По кол-ву глав</option>
                        <option value="5">По новизне</option>
                        <option value="6">По последним обновлениям</option>
                        <option value="7">Рандом</option>
                        </select>
                        <button type="button" className="btn btn-dark">&#8593;&#8595;</button>        
                    </h2> */}
                <div className="p-2 d-flex flex-wrap">
                {mangs.map((manga, index) => (
                    <NavLink key={manga.id} to={`/mangapage?id=${manga.id}`}><img src={manga.image} alt={manga.mangaName} className="slideshow"/></NavLink>
                ))}
        
                </div>
            </div>
        </article>
    );
}