import React, { useEffect, useState, useContext } from "react";
import { useLocation, useParams } from "react-router-dom";
import "./PuModule.css";
import NavbarComp from '../../student/components/NavbarComp';
import ModReviewComp from "./ModReviewComp";
import { AuthContext } from "../../AuthContext";

const ModuleDetail = (props) => {
  const { puName, modId } = useParams();
  const { loggedInStudent } = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const API_URL_MOD = "http://localhost:8080/PU-war/webresources/pumodule";
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const API_URL_MODREVIEW = "http://localhost:8080/PU-war/webresources/pumodulereview"
  const [module, setModule] = useState({
    code: "AB123",
    moduleId: 1,
    moduleReviews: [],
    name: "Dummy Module Name",
    description:
      "This module aims to train students to be conversant in front-end development for Enterprise Systems. It complements IS2103 which focuses on backend development aspects for Enterprise Systems. Topics covered include web development scripting languages, web templating design and component design, integrating with backend application, and basic mobile application development.",
    pu_puid: "1",
  });
  const [currentStudent, setCurrentStudent] = useState({});
  const [reviews, setReviews] = useState([]);
  const [studentLikedModReview, setStudentLikedModReview] = useState([])
  const [studentDislikedModReviews, setStudentDislikedModReview] = useState([])

  useEffect(() => {
    getPuModApi(modId);
    getPuModReviewsApi(modId);
  }, []);

  useEffect(() => {
    if (loggedInStudent) {
      getStudentAPI(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);

  const getStudentAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    setCurrentStudent(data);
    setStudentDislikedModReview(data.dislikedPUReviews);
    setStudentLikedModReview(data.likedPUReviews);
  };

  const getPuModApi = async (moduleId) => {
    const response = await fetch(`${API_URL_MOD}/${moduleId}`);
    const data = await response.json();
    //setCurrentStudent(data);
    setModule(data);
  };

  const getPuModReviewsApi = async (moduleId) => {
    const response = await fetch(`${API_URL_MODREVIEW}/from-module/${moduleId}`);
    const data = await response.json();
    setReviews(data);
    return data
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

        {/* <ModReviewComp reviews={module.moduleReviews} /> */}
        <ModReviewComp reviews={reviews} studentLikedModReviews={studentLikedModReview} studentDislikedModReviews={studentDislikedModReviews}/>
      </div>
    </div>
  );
};

export default ModuleDetail;
