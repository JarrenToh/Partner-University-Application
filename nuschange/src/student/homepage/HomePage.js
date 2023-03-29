import React, { useState, useEffect } from "react";
import { Routes, Route, Link, useLocation } from "react-router-dom";
import PUCard from "./PUCard";
import SearchIcon from "./search.svg";
import "./HomePage.css";
import { Button } from 'reactstrap';
import "bootstrap/dist/css/bootstrap.min.css"
import UniversityRankings from "../ranking/UniversityRankings";
import universities from "../universitiesData";
import logo from '../../NUSChange-logoV3.png';

const HomePage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const searchQuery = searchParams.get("search") || "";
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);

  const handleSearch = (event) => {
    event.preventDefault();
    if (event.key === "Enter") {
      setSearchTerm(event.target.value);
    }
  };

  useEffect(() => {
    searchPUs("");
  }, []);


  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}`);
    const data = await response.json();
    console.log(data);
    setPUs(data);
  };

  useEffect(() => {
    setSearchTerm(searchQuery);
  }, [searchQuery]);

  return (
    <div className="wrapper">
      
          <div className="app">
          <img
              alt=""
              src={logo}
              width="300"
              height="100"
              className="d-inline-block align-top"
            />{' '}
            <div className="search">
              <input
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="Search for Partner Universities"
              />
              <Link to={`/university-rankings?search=${searchTerm}`}>
                <img
                  src={SearchIcon}
                  alt="search"
                  onClick={() => searchPUs(searchTerm)}
                />
              </Link>
            </div>

            <div className="container">
              <button className="searchButton">By Country</button>
              <button className="searchButton">By Region</button>
            </div>

            <div className="container">
              <h2>Top 5 Ranking Universities</h2>
              <div className="container">
                {pus
                  .sort((a, b) => b.rating - a.rating)
                  .slice(0, 5)
                  .map((university) => (
                    <div key={university.puId}>
                      <Link to={`/university-rankings?search=${university.name}`}>
                        <PUCard pu={university} />
                      </Link>
                    </div>
                  ))}
              </div>
            </div>

            <div className="container">
              <h2>Asia</h2>
              <div className="container">
                {pus
                  .filter((university) => university.regionName === "Asia")
                  .map((university) => (
                    <div key={university.puId}>
                      <Link to={`/university-rankings?search=${university.name}`}>
                        <PUCard pu={university} />
                      </Link>
                    </div>
                  ))}
              </div>
            </div>

            <div className="container">
              <h2>Europe</h2>
              <div className="container">
                {pus
                  .filter((university) => university.regionName === "Europe")
                  .map((university) => (
                    <div key={university.puId}>
                      <Link to={`/university-rankings?search=${university.name}`}>
                        <PUCard pu={university} />
                      </Link>
                    </div>
                  ))}
              </div>
            </div>

          </div>
 
    </div>
  );
};

export default HomePage;