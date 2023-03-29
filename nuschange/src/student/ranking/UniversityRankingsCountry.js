import React, { useState, useEffect, useCallback } from "react";
import { useLocation } from "react-router-dom";
import { Button, Input, FormGroup } from "reactstrap";
import UniversityCard from "./UniversityCard";
import "bootstrap/dist/css/bootstrap.min.css";
import "./UniversityRanking.css";

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
    const uniqueCountries = Array.from(new Set(countries));
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

  return (
    <div className="wrapper">
      <div className="container">
        <div className="universityRankings">
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
          </div>
        </div>
        {displayedUniversities.length > 0 ? (
          <div className="university-rankings__grid">
            <br />
            {displayedUniversities.map((university) => (
              <div className="university-card-wrapper" key={university.puId}>
                <UniversityCard university={university} />
                <button
                  className={`university-card__favorite-button ${university.isFavorite
                    ? "university-card__favorite-button--active"
                    : ""
                    }`}
                  onClick={() => handleToggleFavorite(university.puId)}
                >
                  <i className="fas fa-heart"></i>
                </button>
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
          <Button onClick={handleShowMore}>Show More</Button>
        )}
      </div>

    </div>
  );
};

export default UniversityRankings;

