import React from "react";

const UniversityCard = ({ university, index, ranking }) => {
  
  const rankingInfo = ranking ? (
    <div className="university-card__number">
      <img src="https://cdn-icons-png.flaticon.com/512/5987/5987898.png" className="rankImg" />
      {" "}
      Ranked #{index}
    </div>
  ) : null;

  return (
    <div className="university-number">
      <div className="university-card">
        <div className="university-card__image-container">
          <img src={university.images} alt={university.name} className="university-card__image" />
        </div>
        <div className="university-card__details">
          <div className="university-card__name">{university.name}</div>
          <div className="university-card__location">{university.regionName}, {university.countryName}</div>
          {rankingInfo}
        </div>
        <div className="university-card__ranking-container">
          <div className="university-card__rating"> <div className="ratingTop">Rating:</div>   <img src="https://cdn-icons-png.flaticon.com/512/2377/2377846.png" className="starImg" /> {university.rating} {" "}/ 5.0</div>
        </div>
      </div>
    </div>
  );
};

export default UniversityCard;