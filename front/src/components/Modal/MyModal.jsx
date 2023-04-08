import cl from './MyModal.module.css';
import React from 'react'
const MyModal = (props) => {

    const rootClasses = [cl.myModal]
    if(props.visible){
        rootClasses.push(cl.active);
    }

    const setVisibleFunc = () =>{
        props.setVisible(false)
    }
    
    return (
        <div className={rootClasses.join(' ')} onClick={() => setVisibleFunc()}>
            <div className={cl.MyModalContent} onClick={(e) => e.stopPropagation()}>
                {props.children}
            </div>
        </div>
    );
};

export default MyModal