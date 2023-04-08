import { useState } from 'react';


export default function TableReader(props) {

    return (
            <tbody>
                {
                    props.items.map((item) =>
                        <tr key={item.id}>                                
                            <td>{item.id}</td>
                            <td>{item.readerName}</td>
                            <td>{item.hashedPassword}</td>
                            <td>
                                <select className="form-select" aria-label="Default select example">{item.mangas.map(manga =>                         
                                    <option key={manga.id}>{manga.mangaName}</option>)}
                                </select>
                            </td>
                            <td>
                                <button onClick={() => props.remove(item.id)} className="btn btn-success">Удалить</button>
                            </td>                            
                        </tr>
                    )
                }
            </tbody >
    );
}