import React from 'react';

const EditReaderForm =(props) => {

    const types = ([
        {name:'Манга'},
        {name:'Манхва'},
        {name:'Маньхуа'},
    ])

    function done(e) {
        e.preventDefault();
        // if (formRef.current.checkValidity()) {
        //     props.onDone();
        //     props.setModalEdit();
        //     props.setData({type:'', name:'', description:'' })  
        // }else {
        //     formRef.current.reportValidity();
        //     props.setData({type:'', name:'', description:'' })  
        // }
    }

    const formRef = React.createRef();

    return (
        <form id="frm-items" ref={formRef} className="row g-3 text-dark">
        <div className="col-md-3 ">
        </div>
            <div className="col-sm-2">
                <label htmlFor="readerName" className="form-label">readerName</label>
                <input type='text' value = {props.readerName} onChange={event => props.setReaderName(event.target.value)} className="form-control"/>
            </div>
            <div className="col-sm-2">
                <label htmlFor="password" className="form-label">password</label>
                <input type='text' value = {props.password} onChange={event => props.setPassword(event.target.value)} className="form-control"/>
            </div>  
            <div className="col-md-3">
                <button onClick={() => props.update(item.id)} id="btn-add-item" className="btn btn-primary" type="submit">Изменить</button>
            </div>
        </form>
    );
}
export default EditReaderForm
