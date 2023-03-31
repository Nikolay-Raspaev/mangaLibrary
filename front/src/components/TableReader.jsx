import { useState } from 'react';


export default function TableReader(props) {

    return (
            <tbody>
                {
                    props.items.map((item, index) =>
                        <tr key={item.id}>                                
                            <td>{item.id}</td>
                            <td>{item.readerName}</td>
                            <td>{item.hashedPassword}</td>
                            <td>
                                <select>{item.mangas.map(manga =>                         
                                    <option key={manga}>{manga}</option>)}
                                </select>
                            </td>                            
                        </tr>
                    )
                }
            </tbody >
    );
}