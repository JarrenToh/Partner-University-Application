import React from "react";

const UniversityCard = ({ university }) => {
  return (
    <div className="university-card">
      <div className="university-card__image-container">
        <img src={university.images} alt={university.name} className="university-card__image" />
      </div>
      <div className="university-card__details">
        <div className="university-card__name">{university.name}</div>
        <div className="university-card__location">{university.regionName}, {university.countryName}</div>
        <div className="university-card__rating"><img src="https://cdn-icons-png.flaticon.com/512/2377/2377846.png" className="starImg"/> Rating: {university.rating} {" "}/ 5.0</div>
      </div>
    </div>
  );
};

export default UniversityCard;