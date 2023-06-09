import React, { useState, useEffect, useCallback } from "react";
import { useLocation, Link } from "react-router-dom";
import { Button, Input, FormGroup } from "reactstrap";
import UniversityCard from "./UniversityCard";
import "bootstrap/dist/css/bootstrap.min.css";
import "./UniversityRanking.css";
import NavbarComp from '../../student/components/NavbarComp';
import { AuthProvider, useAuth } from '../../../src/AuthContext';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { faHeart as heartOutline } from "@fortawesome/free-regular-svg-icons";
import { Modal } from "react-bootstrap";

const UniversityRankings = ({ universitiesData }) => {

  const [universities, setUniversities] = useState(
    universitiesData.map((university) => ({ ...university, isFavorite: false }))
  );
  const [searchTerm, setSearchTerm] = useState("");
  const [filter, setFilter] = useState("");
  const [sortBy, setSortBy] = useState("ranking");
  const [favoritesOnly, setFavoritesOnly] = useState(false);
  const [displayLimit, setDisplayLimit] = useState(10);
  const [selectedOption, setSelectedOption] = useState("");
  const [selectedCountry, setSelectedCountry] = useState('');
  const [ranking, setRanking] = useState(false);
  const [puEnrolled, setPuEnrolled] = useState({
    name: "Dummy Uni",
    puId: 0,
  });
  const [showLikedModal, setShowLikedModal] = useState(false);
  const handleClose = () => setShowLikedModal(false);
  const handleShow = () => setShowLikedModal(true);

  useEffect(() => {
    setFilter(searchTerm);
  }, [searchTerm]);

  useEffect(() => {
    setUniversities(
      universitiesData.map((university) => ({
        ...university,
        isFavorite: false,
      }))
    );
  }, [universitiesData]);

  useEffect(() => {
    const fetchUniversities = async () => {
      const response = await fetch("http://localhost:8080/PU-war/webresources/pu");
      const data = await response.json();
      setUniversities(data);
    };

    fetchUniversities();
  }, []);

  const handleFilterChange = (event) => {
    const value = event.target.value;
    console.log('Selected country:', value);
    if (value.startsWith("country:")) {
      setFilter("");
      setSelectedCountry(value.substring(8));
    } else {
      setFilter(value);
      setSelectedCountry(value);
    }
  };


  const handleSortByChange = (event) => {
    setSortBy(event.target.value);
  };

  const handleToggleFavorite = (id) => {
    setUniversities(
      universities.map((university) =>
        university.puId === id
          ? { ...university, isFavorite: !university.isFavorite }
          : university
      )
    );
  };

  const handleShowMore = useCallback(() => {
    setDisplayLimit(displayLimit + 10);
  }, [setDisplayLimit, displayLimit]);

  const getUniqueCountries = () => {
    const countries = universitiesData.map((university) => university.countryName);
    const uniqueCountries = Array.from(new Set(countries)).sort();
    return ["All Countries", ...uniqueCountries];
  };

  const filteredUniversities = universities.filter((university) => {
    if (selectedCountry === "All Countries") {
      return true;
    } else if (selectedCountry !== "") {
      return university.countryName.toLowerCase() === selectedCountry.toLowerCase();
    }
    return (
      university.name.toLowerCase().includes(filter.toLowerCase()) ||
      university.regionName.toLowerCase().includes(filter.toLowerCase()) ||
      university.countryName.toLowerCase().includes(filter.toLowerCase())
    );
  });

  useEffect(() => {
    if (sortBy === "ranking") {
      setRanking(true);
    } else {
      setRanking(false);
    }
  }, [sortBy]);


  const sortedUniversities = filteredUniversities.sort((a, b) => {
    if (sortBy === "ranking") {
      if (a.rating !== b.rating) {
        // Sort by rank first
        return b.rating - a.rating;
      } else {
        // If rank is the same, sort by university ID
        return a.puId - b.puId;
      }
    } else {
      // Sort by name
      return a.name.localeCompare(b.name);
    }
  });



  const displayedUniversities = favoritesOnly
    ? sortedUniversities.filter((university) => university.isFavorite)
    : sortedUniversities.slice(0, displayLimit);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const { loggedInStudent, login, logout } = useAuth();

  return (
    <div className="wrapper">
      <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
      <br />
      <div className="container" style={{ maxWidth: '1300px' }}>
        <div className="universityRankings">
          <div className="universityRankings_description">
            <h1 className="headerRanking">Partner University Rankings</h1>
            <br />
            <p>Welcome to our university ranking page, where you can find comprehensive information on various universities worldwide. We understand that selecting a university can be a challenging task, and we're here to help you make an informed decision.</p>

            <p>Our rankings are unique because they are based on student ratings. We believe that students are the best judges of the universities they attend, and they have the most up-to-date information about campus life, academics, and resources. Therefore, we provide an opportunity for students to come together and rate their partner universities.</p>
            <br />
          </div>
          <br />
          <div className="university-rankings__header">
            <div className="university-rankings__sort-by">
              Sort by:
              <select value={sortBy} onChange={handleSortByChange}>
                <option value="ranking">Ranking</option>
                <option value="name">Name</option>
              </select>
            </div>
            <div className="university-rankings__country-filter">
              <FormGroup>
                <Input type="select" value={selectedCountry} onChange={handleFilterChange}>
                  {getUniqueCountries().map((country, index) => (
                    <option key={index} value={country} selected={selectedCountry === country}>
                      {country}
                    </option>
                  ))}
                </Input>
              </FormGroup>
            </div>
            <div className="university-rankings__filter">
              <input
                type="text"
                placeholder="Search"
                value={filter}
                onChange={handleFilterChange}
              />
            </div>
            {/*
            <div className="university-rankings__favorites">
              <label>
                <input
                  type="checkbox"
                  checked={favoritesOnly}
                  onChange={(event) => setFavoritesOnly(event.target.checked)}
                />
                Favorites only
              </label>
            </div>
  */}
          </div>
        </div>
        {displayedUniversities.length > 0 ? (
          <div className="university-rankings__grid">
            <br />
            {displayedUniversities.map((university, index) => (
              <div className="university-card-wrapper" key={university.puId}>
                <Link
                  to={`/student/university-description-page/${university.name}`}
                  style={{ textDecoration: "none" }}
                >
                  <UniversityCard
                    university={university}
                    index={index + 1}
                    ranking={ranking}
                  />
                </Link>
                {(loggedInStudent && puEnrolled.puId !== university.puId) && (
                  <button
                    className={`university-card__favorite-button`}
                    onClick={() => handleToggleFavorite(university)}
                  >
                    <FontAwesomeIcon
                      icon={university.isFavorite ? faHeart : heartOutline}
                      style={{ color: "#d01b1b" }}
                    />{" "}
                  </button>
                )}
              </div>
            ))}
          </div>
        ) : (
          <div className="university-rankings__noResult">
            <div className="university-rankings__empty">
              <br />
              <h2>No Results</h2>
            </div>
          </div>
        )}
        {sortedUniversities.length > displayLimit && (
          <Button onClick={handleShowMore} style={{ backgroundColor: `#1E90FF`, fontSize: `20px`, color: `white` }}>Show More</Button>
        )}
        <br />
        <br />
        <br />
        <br />
      </div>
      <Modal show={showLikedModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>You are Enrolled Here!</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          To easily access your university from your profile page, simply click on the "View University Details" Link.
          </Modal.Body>
        <Modal.Footer>
          <Button color="outline-danger" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
      <br/>
      <br/>
    </div>

  );
};

export default UniversityRankings;

