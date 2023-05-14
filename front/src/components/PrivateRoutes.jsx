import {Navigate, Outlet, useNavigate} from 'react-router-dom';
import {useEffect} from "react";

const PrivateRoutes = (props) => {



    let isAllowed = false;
    let userRole = localStorage.getItem("role");
    if ((props.userGroup === "AUTH" && userRole) || (props.userGroup === userRole)) {
        isAllowed = true;
    }

    return isAllowed ? <Outlet /> : <Navigate to="/login" />;
}

export default PrivateRoutes;
