
import React from 'react';

const PUCard = ({ pu: { puId , name, regionName, countryName, rating, images} }) => {
  return (
    <div className="pu" key={puId}>
      <div>
        <p>{countryName}</p>
      </div>

      <div>
        <img src={images !== "N/A" ? images : "https://via.placeholder.com/400"} alt={name} />
      </div>

      <div>
        <span>{regionName}</span>
        <h3>{name} <p>
            <br/><img src={images="https://cdn-icons-png.flaticon.com/512/2377/2377846.png"} alt={name} />
            {' '}{rating}/5.0
            </p> </h3>
      </div>
    </div>
  );
}

export default PUCard;