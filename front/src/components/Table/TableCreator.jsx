import { useState } from 'react';


export default function TableCreator(props) {

    return (
            <tbody>
                {
                    props.items.map((item, index) =>
                        <tr key={item.id}>                                
                            <td>{item.id}</td>
                            <td>{item.creatorName}</td>
                            <td>{item.hashedPassword}</td>
                            <td>
                                <select className="form-select" aria-label="Default select example">{item.mangas.map(manga =>                         
                                    <option key={manga.mangaName } >{manga.mangaName + " " +manga.chapterCount}</option>)}
                                </select>
                            </td>

                            
                        </tr>
                    )
                }
            </tbody >
    );
}