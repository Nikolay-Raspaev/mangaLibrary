import { NavLink } from 'react-router-dom';

export default function Header(props) {
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
                        {props.links.map(route =>
                            <li key={route.path}
                                className="nav-item">
                                <NavLink className="nav-link" to={route.path}>
                                        {route.label}
                                </NavLink>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
        </nav >
    );
}