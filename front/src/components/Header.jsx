import {NavLink, useNavigate} from 'react-router-dom';
import {useEffect, useState} from "react";

export default function Header(props) {
    const navigate = useNavigate();
    const logoutButtonOnClick = function () {
        localStorage.removeItem("token");
        localStorage.removeItem("user");
        localStorage.removeItem("role");
        window.dispatchEvent(new Event("storage"));
        navigate("/login");
    }

    const [userRole, setUserRole] = useState("NONE");

    useEffect(() => {
        window.addEventListener("storage", () => {
            getUserRole();
        });
        getUserRole();
    }, [])

    const getUserRole = function () {
        const role = localStorage.getItem("role") || "NONE";
        setUserRole(role);
    }

    const validate = function (userGroup) {
        return (userGroup === "AUTH" && userRole !== "NONE") || (userGroup === userRole);
    }
console.log(userRole);
    return (
        <nav className="navbar navbar-expand-lg navbar-dark">
            <div className="container-fluid">
                <button className="navbar-toggler" type="button"
                    data-bs-toggle="collapse" data-bs-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav"> 
                        {props.links.map(route =>{
                            if (validate(route.userGroup)) {
                            return (
                            <li key={route.path}
                                className="nav-item">
                                <NavLink className="nav-link" to={route.path}>
                                        {route.label}
                                </NavLink>
                            </li>
                            );}}
                        )}
                        {userRole === "NONE"?
                            null
                            :
                            <div className="border-bottom pb-3 mb-3">
                                <button className="btn btn-primary"
                                        onClick={logoutButtonOnClick}>
                                    Log Out
                                </button>
                            </div>
                        }
                    </ul>
                </div>
            </div>
        </nav >
    );
}