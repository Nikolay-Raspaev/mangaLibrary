import { useState } from 'react';


export default function TableManga(props) {

    return (
            <tbody>
                {
                    props.items.map((item, index) =>
                        <tr key={item.id} >                        
                            <td>{item.id}</td>
                            <td>{item.mangaName}</td>
                            <td>{item.chapterCount}</td>
                            <td>{item.creatorId}</td>
                            <td>
                                <select className="form-select" aria-label="Default select example">{item.readers.map(reader =>                         
                                    <option key={reader.id}>{reader.readerName}</option>)}
                                </select>
                            </td>                          
                        </tr>
                    )
                }
            </tbody >
    );
}