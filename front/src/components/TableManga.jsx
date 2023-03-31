import { useState } from 'react';


export default function TableCreator(props) {

    return (
            <tbody>
                {
                    props.items.map((item, index) =>
                        <tr key={item.id}>                        
                            <td>{item.id}</td>
                            <td>{item.mangaName}</td>
                            <td>{item.chapterCount}</td>
                            <td>{item.creatorId}</td>
                            <td>
                                <select>{item.readers.map(reader =>                         
                                    <option key={reader}>{reader}</option>)}
                                </select>
                            </td>                          
                        </tr>
                    )
                }
            </tbody >
    );
}