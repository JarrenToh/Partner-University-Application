import React from "react";

const University = ({ university, isFavorite, onToggleFavorite }) => {
  return (
    <div className="university">
      <div className="university__details">
        <div className="university__ranking">{university.puId}</div>
        <div className="university__name">{university.name}</div>
        <div className="university__location">{university.region}, {university.country}</div>
        <div className="university__rating">Rating: {university.rating}</div>
      </div>
      <div className="university__favorite">
        <button
          className={`university__favorite-button ${
            isFavorite ? "university__favorite-button--active" : ""
          }`}
          onClick={onToggleFavorite}
        >
          <i className="fas fa-heart"></i>
        </button>
      </div>
      <div className="university__image">
        <img src={university.url} alt={university.name} />
      </div>
    </div>
  );
};

export default University;
