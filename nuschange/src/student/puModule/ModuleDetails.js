import React, { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import ModReviewComp from "./ModReviewComp";
import "./PuModule.css";
import NavbarComp from '../../student/components/NavbarComp';

const ModuleDetail = (props) => {
  const { puName, modId } = useParams();
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const API_URL_MOD = "http://localhost:8080/PU-war/webresources/pumodule";
  const [module, setModule] = useState({
    code: "AB123",
    moduleId: 1,
    moduleReviews: [
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
    ],
    name: "Dummy Module Name",
    description:
      "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.",
    pu_puid: "1",
  });

  useEffect(() => {
    getPuModApi(modId);
  }, []);

  const getPuModApi = async (moduleId) => {
    const response = await fetch(`${API_URL_MOD}/${moduleId}`);
    const data = await response.json();
    //setCurrentStudent(data);
    setModule(data);
  };


  return (
    <div className="wrapper" >
      <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
      <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>

        <div >
          <h1 className="text-center mb-3">{module.code}</h1>
        </div>
        <h3>{module.name}</h3>
        <h4>{puName}</h4>
        <hr></hr>
        <p>{module.description}</p>

        <ModReviewComp reviews={module.moduleReviews} />
      </div>
    </div>
  );
};

export default ModuleDetail;
