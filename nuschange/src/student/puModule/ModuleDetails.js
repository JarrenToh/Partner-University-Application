import React, { useState } from "react";
import ModReviewComp from "./ModReviewComp";
import "./PuModule.css";

const ModuleDetail = () => {
  const dummyModule = {
    code: "IS3106",
    name: "Enterprise Systems Interface Design and Development",
    description:
      "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.",
    pu_puid: "1",
  };

  const [moduleReview, setModuleReviews] = useState([
    {
      moduleReviewId: 123,
      rating: 4,
      review: "This module is great!",
      noOfLikes: 10,
      noOfDislikes: 2,
      isInappropriate: false,
    },
    {
      moduleReviewId: 124,
      rating: 3,
      review: "This module is okay.",
      noOfLikes: 5,
      noOfDislikes: 3,
      isInappropriate: false,
    },
    {
      moduleReviewId: 125,
      rating: 5,
      review: "This module exceeded my expectations!",
      noOfLikes: 20,
      noOfDislikes: 1,
      isInappropriate: false,
    },
    {
      moduleReviewId: 126,
      rating: 2,
      review: "I don't like this module.",
      noOfLikes: 1,
      noOfDislikes: 10,
      isInappropriate: true,
    },
    {
      moduleReviewId: 127,
      rating: 4,
      review: "This module is amazing!",
      noOfLikes: 15,
      noOfDislikes: 0,
      isInappropriate: false,
    },
  ]);

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{border: 0}}>
        <h1>{dummyModule.code}</h1> pu name here
      </div>
      <h3>{dummyModule.name}</h3>
      <p>{dummyModule.description}</p>

      <ModReviewComp reviews={moduleReview} />
    </div>
  );
};

export default ModuleDetail;
