import { useEffect, useState } from "react";
import React from 'react'
import { useNavigate } from "react-router-dom";
import banner1 from "../../../img/popular_1.jpg";
import banner2 from "../../../img/popular_2.jpg";
import banner3 from "../../../img/popular_3.jpg"

export default function Banner() {
  const length = 3;
  var old = length - 1;
  var current = 0;
  const navigate = useNavigate();
  const [bannerState, setBannerState] = useState(["show", "hide", "hide"]);

  useEffect(() => {
    const timer = window.setInterval(() => {
      setBannerState([
        ...bannerState,
        (bannerState[current] = "show"),
        (bannerState[old] = "hide"),
      ]);
      //setBannerState([...bannerState, ]);

      console.info("Banner changed");

      old = current;
      current++;

      if (current === length) {
        current = 0;
      }
    }, 2000);

    return () => {
      window.clearInterval(timer);
    };
  }, []);

  return (
    <div className="d-flex align-items-center flex-column" id="banner">
        <a className={bannerState[0]} style={{ cursor: "pointer" }}><img src={banner1}/></a>
        <a className={bannerState[1]} style={{ cursor: "pointer" }}><img src={banner2}/></a>
        <a className={bannerState[2]} style={{ cursor: "pointer" }}><img src={banner3}/></a>
    </div>
  );
}