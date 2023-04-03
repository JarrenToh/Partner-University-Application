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
    <div className="wrapper" >

      <div className="app" style={{

        backgroundImage: `url('https://gov-web.s3.ap-northeast-1.amazonaws.com/uploads/2018/04/NUS-ERC.jpg')`,
        backgroundSize: 'cover',
        backgroundPosition: 'center',
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        //borderRadius: '1%', // Set border radius to 50% to create curved edges
        //overflow: 'hidden', // Hide anything that overflows the container
      }}>
        <img
          alt=""
          src={logo}
          width="300"
          height="100"
          className="d-inline-block align-top"
        />{' '}
        <div className="searchBar" >
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
          <Link to={`/university-rankings-country`}>
            <Button className="searchbyButton">By Country</Button>
          </Link>
          <Link to={`/university-rankings-region`}>
            <Button className="searchbyButton">By Region</Button>
          </Link>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.6)',
          borderRadius: '0.5%',
        }}>
        
          <h2 className="headerDescription">Top 5 Ranking Universities</h2>
        
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
              ))
            }
          </div>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.6)',
          borderRadius: '0.5%',
        }}>
          <h2 className="headerDescription">Asia</h2>
          <div className="container">
          {pus
              .filter((university) => university.regionName === "Asia")
              .sort((a, b) => b.rating - a.rating)
              .slice(0, 5)
              .map((university) => (
                <div key={university.puId}>
                  <Link to={`/university-rankings?search=${university.name}`}>
                    <PUCard pu={university} />
                  </Link>
                </div>
              ))
            }
          </div>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.5)',
          borderRadius: '0.5%',
        }}>
          <h2 className="headerDescription">Africa</h2>
          <div className="container">
          {pus
              .filter((university) => university.regionName === "Africa")
              .sort((a, b) => b.rating - a.rating)
              .slice(0, 5)
              .map((university) => (
                <div key={university.puId}>
                  <Link to={`/university-rankings?search=${university.name}`}>
                    <PUCard pu={university} />
                  </Link>
                </div>
              ))
            }
          </div>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.5)',
          borderRadius: '0.5%',
        }}>
          <h2 className="headerDescription">Australia</h2>
          <div className="container">
            {pus
              .filter((university) => university.regionName === "Australia")
              .sort((a, b) => b.rating - a.rating)
              .slice(0, 5)
              .map((university) => (
                <div key={university.puId}>
                  <Link to={`/university-rankings?search=${university.name}`}>
                    <PUCard pu={university} />
                  </Link>
                </div>
              ))
            }
          </div>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.5)',
          borderRadius: '0.5%',
        }}>
          <h2 className="headerDescription">America</h2>
          <div className="container">
          {pus
              .filter((university) => university.regionName === "America")
              .sort((a, b) => b.rating - a.rating)
              .slice(0, 5)
              .map((university) => (
                <div key={university.puId}>
                  <Link to={`/university-rankings?search=${university.name}`}>
                    <PUCard pu={university} />
                  </Link>
                </div>
              ))
            }
          </div>
        </div>

        <div className="container" style={{
          backgroundColor: 'rgba(128, 128, 128,0.5)',
          borderRadius: '0.5%',
        }}>
          <h2 className="headerDescription">Europe</h2>
          <div className="container">
          {pus
              .filter((university) => university.regionName === "Europe")
              .sort((a, b) => b.rating - a.rating)
              .slice(0, 5)
              .map((university) => (
                <div key={university.puId}>
                  <Link to={`/university-rankings?search=${university.name}`}>
                    <PUCard pu={university} />
                  </Link>
                </div>
              ))
            }
          </div>
        </div>

      </div>


    </div>
  );
};

export default HomePage;