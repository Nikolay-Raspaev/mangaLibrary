import { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import { useRef } from "react";

const hostURL = "http://localhost:8080";

const SingupPage = function () {

    const loginInput = useRef();
    const emailInput = useRef();
    const passwordInput = useRef();
    const passwordConfirmInput = useRef();

    useEffect(() => {
    }, []);

    const singup = async function (userSinginDto) {
        const requestParams = {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userSinginDto),
        };
        console.log(hostURL + "/sing_up");
        console.log(userSinginDto);
        const response = await fetch(hostURL + "/sing_up", requestParams);
        const result = await response.text();
        alert(result);
    }

    const singupFormOnSubmit = function (event) {
        event.preventDefault();
        const userSinginDto = {
            login: loginInput.current.value,
            email: emailInput.current.value,
            password: passwordInput.current.value,
            passwordConfirm: passwordConfirmInput.current.value
        }
        singup(userSinginDto);
    };

    return (
        <>
            <div>
                <form onSubmit={(event) => singupFormOnSubmit(event)}>
                    <div className="mb-3">
                        <p className="mb-1">Login</p>
                        <input className="form-control"
                               type="text" required maxLength="64"
                               ref={loginInput} />
                    </div>
                    <div className="mb-3">
                        <p className="mb-1">Email</p>
                        <input className="form-control"
                               type="text" required
                               ref={emailInput} />
                    </div>
                    <div className="mb-3">
                        <p className="mb-1">Password</p>
                        <input className="form-control"
                               type="password" required minLength="3" maxLength="64"
                               ref={passwordInput} />
                    </div>
                    <div className="mb-3">
                        <p className="mb-1">Confirm Password</p>
                        <input className="form-control"
                               type="password" required minLength="3" maxLength="64"
                               ref={passwordConfirmInput} />
                    </div>
                    <div className="mb-3">
                        <button type="submit" className="btn btn-success">
                            Create account
                        </button>
                    </div>
                    <div>
                        <p>
                            <span>Already have an account?&nbsp;</span>
                            <Link to="/login">Sing In here</Link>
                        </p>
                    </div>
                </form>
            </div>
        </>
    )
}

export default SingupPage;