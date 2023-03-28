import React, { useState, useEffect, useCallback } from "react";
import { useLocation } from "react-router-dom";
import { Button } from "reactstrap";
import UniversityCard from "./UniversityCard";
import "bootstrap/dist/css/bootstrap.min.css";
import "./UniversityRanking.css";

const UniversityRankings = ({ universitiesData }) => {
  const [universities, setUniversities] = useState(
    universitiesData.map((university) => ({ ...university, isFavorite: false }))
  );
  const [filter, setFilter] = useState("");
  const [sortBy, setSortBy] = useState("ranking");
  const [favoritesOnly, setFavoritesOnly] = useState(false);
  const [displayLimit, setDisplayLimit] = useState(10);

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const searchQuery = searchParams.get("search") || "";

  useEffect(() => {
    setFilter(searchQuery);
  }, [searchQuery]);

  const handleFilterChange = (event) => {
    setFilter(event.target.value);
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

  const filteredUniversities = universities.filter(
    (university) =>
      university.name.toLowerCase().includes(filter.toLowerCase()) ||
      university.region.toLowerCase().includes(filter.toLowerCase()) ||
      university.country.toLowerCase().includes(filter.toLowerCase())
  );

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
    <div className="university-rankings">
      <div className="university-rankings__header">
        <div className="university-rankings__filter">
          <input
            type="text"
            placeholder="Search "
            value={filter}
            onChange={handleFilterChange}
          />
        </div>
        <div className="university-rankings__sort-by">
          Sort by:
          <select value={sortBy} onChange={handleSortByChange}>
            <option value="ranking">Ranking</option>
            <option value="name">Name</option>
          </select>
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
      {displayedUniversities.length > 0 ? (
        <div className="university-rankings__grid">
          {displayedUniversities.map((university) => (
            <div className="university-card-wrapper" key={university.puId}>
              <UniversityCard university={university} />
              <button
                className={`university-card__favorite-button ${
                  university.isFavorite
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
        <div className="university-rankings__empty">
          <h2>No Results</h2>
        </div>
      )}
      {sortedUniversities.length > displayLimit && (
        <Button onClick={handleShowMore}>Show More</Button>
      )}
    </div>
  );
};

export default UniversityRankings;

