import {Navigate, Outlet, useNavigate} from 'react-router-dom';
import {useEffect} from "react";

const PrivateRoutes = (props) => {

    const navigate = useNavigate();

    useEffect(() => {
        window.addEventListener("storage", () => {
            let token = localStorage.getItem("token");
            if (token) {
                getRole(token).then((role) => {
                    if (localStorage.getItem("role") != role) {
                        localStorage.removeItem("token");
                        localStorage.removeItem("user");
                        localStorage.removeItem("role");
                        window.dispatchEvent(new Event("storage"));
                        navigate("/catalog");
                    }
                });
            }
        });
    }, [])

    const getRole = async function (token) {
        const requestParams = {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        };
        const requestUrl = `http://localhost:8080/who_am_i?token=${token}`;
        const response = await fetch(requestUrl, requestParams);
        return await response.text();
    }

    let isAllowed = false;
    let userRole = localStorage.getItem("role");
    if ((props.userGroup === "AUTH" && userRole) || (props.userGroup === userRole)) {
        isAllowed = true;
    }

    return isAllowed ? <Outlet /> : <Navigate to="/login" />;
}

export default PrivateRoutes;
