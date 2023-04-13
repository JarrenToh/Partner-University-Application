import React, { useContext, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import UniversityCard from "../ranking/UniversityCard";
import { AuthContext } from "../../AuthContext";

const LikedPUs = () => {
  const { loggedInStudent } = useContext(AuthContext);
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const [likedPUs, setLikedPUs] = useState([]);
  const [currentStudent, setCurrentStudent] = useState({ ...loggedInStudent });

  useEffect(() => {
    if (loggedInStudent) {
      getStudentAPI(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);

  const getStudentAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    setCurrentStudent(data);
    setLikedPUs(data.likedPUs);
  };

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container" style={{ border: 0 }}>
        <h1 className="text-center mb-3">Your Liked Universities</h1>
      </div>

      <div style={{ marginTop: "2.5%" }}>
        {likedPUs.length > 0 &&
          likedPUs.map((pu) => (
            <Link
              to={`/university-description-page/${pu.name}`}
              style={{ textDecoration: "none" }}
            >
              <UniversityCard university={pu} />
            </Link>
          ))}

        {(likedPUs.length == 0 || likedPUs == null) && (
          <div style={{ textAlign: "center" }}>
            <h5>
              You have not liked any universities! Press the heart button in the
              university page to like them.
            </h5>
          </div>
        )}
      </div>
    </div>
  );
};

export default LikedPUs;
