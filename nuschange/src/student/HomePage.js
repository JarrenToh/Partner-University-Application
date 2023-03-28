import React, { useState, useEffect } from "react";
import { Routes, Route, Link, useLocation } from "react-router-dom";
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import PUCard from "./PUCard";
import SearchIcon from "./search.svg";
import "./App.css";
import { Button } from 'reactstrap';
import "bootstrap/dist/css/bootstrap.min.css"
import UniversityRankings from "./UniversityRankings";
import universities from "./universitiesData";

const HomePage = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const searchQuery = searchParams.get("search") || "";
  const API_URL = "http://www.omdbapi.com?apikey=b6003d8a";
  const [pus, setPUs] = useState([]);

  const handleSearch = (event) => {
    event.preventDefault();
    if (event.key === "Enter") {
      setSearchTerm(event.target.value);
    }
  };

  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}&s=${title}`);
    const data = await response.json();

    //setPUs(data.Search);
    setPUs(universities);
  };

  useEffect(() => {
    setSearchTerm(searchQuery);
  }, [searchQuery]);

  return (
    <div className="wrapper">
      <Routes>
        <Route path="/university-rankings" element={<UniversityRankings universitiesData={universities} />} />
        <Route path="/" element={
          <div className="app">
            <h1>NUSChange</h1>
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
                {universities
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
                {universities
                  .filter((university) => university.region === "Asia")
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
                {universities
                  .filter((university) => university.region === "Europe")
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
        } />
      </Routes>
    </div>
  );
};

export default HomePage;
