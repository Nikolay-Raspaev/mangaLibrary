import React, {useState, useEffect} from 'react';
import MangaDto from '../Dto/Manga-Dto';
import ReaderList from "../components/List/ReaderList";

export default function MangaPage() {

  const [mangaModel, setMangaModel] = useState(new MangaDto({}));

  const [readerData, setReaderData] = useState([]);

  const host = "http://localhost:8080";

  useEffect(() => {
    const quryString = window.location.search;
    const urlParams = new URLSearchParams(quryString);
    const id = urlParams.get('id');
    getCreator(id)
    .then(_data =>setMangaModel(_data));
    getReaderData(id)
    .then(_data =>setReaderData(_data));
    console.log(readerData);
  }, []);

  const transformer = (mangaModel) => new MangaDto(mangaModel);
  const url = "manga/";

  const getReaderData = async function (id) {
      const requestParams = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        }
    };
    const response = await fetch(host + `/manga/` + id + `/readers`, requestParams);
    const _data = await response.json()
    console.log(_data);
    return _data;
    }

  useEffect(() => {
    console.log(mangaModel);
    console.log(readerData);
  }, [mangaModel]);

  useEffect(() => {
    console.log(readerData);
  });


    const getCreator = async function (id) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
            }
        };
        const response = await fetch(host + `/manga/` + id, requestParams);
        const _data = await response.json()
        return _data;
        }


    const addMangaButton = (e) =>{
      e.preventDefault()
      getReaderData(253)
      .then(_data =>setReaderData(_data));
      console.log(readerData);
   }

  return (
    <main className="p-3">
      <div className="container d-flex" >
        <div className="d-flex flex-column">
          <img className="img_style01" style={{borderRadius: "3%"}}src={mangaModel.image} alt={mangaModel.mangaName}/>
          <button type="button" onClick={addMangaButton} className="btn btn-primary mt-3">Добавить в избранное</button>
        </div>
        <div className="container table text-white fs-4 ms-4">
          <div className="row text-white fw-bold fs-3">О манге</div>
          <div className="row">
              <div className="col-xs-6 col-sm-3">Год производства</div>
              <div className="col-xs-6 col-sm-3">1000</div>
          </div>
          <div className="row">
              <div className="col-xs-6 col-sm-3">Страна</div>
              <div className="col-xs-6 col-sm-3">Россия</div>
          </div>
          <div className="row">
              <div className="col-xs-6 col-sm-3">Жанр</div>
              <div className="col-xs-6 col-sm-3">Драма</div>
          </div>
          <div className="row">
              <div className="col-xs-6 col-sm-3">Количество глав</div>
              <div className="col-xs-6 col-sm-3">{mangaModel.chapterCount}</div>
          </div>
          <div className="row">
              <div className="col-xs-6 col-sm-3">Возраст</div>
              <div className="col-xs-6 col-sm-3">16+</div>
          </div>
          <div className="row text-white fw-bold fs-3">Описание</div>
          <div className="row">
              <div className="col-xs-6 col-sm-12">
                <p>Ким Кон Чжа спокойно живет в своей халупе, завидуя всем популярным охотникам. Однажды его желание быть лучше всех сбывается и он получает легендарный навык “Копирование способностей”... ценой своей жизни.</p>
                <p>Прежде чем он успевает понять это, его убивает охотник №1, Летний дух! Но это активирует его навык, и теперь он скопировал новый, “Путешествие во времени после смерти”.</p>
                <p>Как Ким Кон Чжа же будет использовать эти навыки, чтобы победить конкурентов и подняться на вершину?</p>
              </div>
          </div>
          <ReaderList
            readers={mangaModel.readers}
          />
        </div>
        </div>
    </main>
  );
}
