import React, {useState} from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar as filledStar } from "@fortawesome/free-solid-svg-icons";
import { faStar as outlineStar } from "@fortawesome/free-regular-svg-icons";

const ClickableStar = () => {
  const [isIcon1, setIsIcon1] = useState(true);

  const handleClick = () => {
    setIsIcon1(!isIcon1);
  };

  return (
    <div onClick={handleClick}>
      {isIcon1 ? (
        <FontAwesomeIcon icon={outlineStar} size="2xl" />
      ) : (
        <FontAwesomeIcon icon={filledStar} size="2xl" />
      )}
    </div>
  );
};

export default ClickableStar;
