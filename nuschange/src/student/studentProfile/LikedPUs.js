import React, { useContext, useState } from "react";
import UniversityCard from "../ranking/UniversityCard";
import { AuthContext } from "../login/AuthContext";

const LikedPUs = () => {
  const { loggedInStudent } = useContext(AuthContext);
  const [likedPUs, setLikedPUs] = useState([]);

  return (
    <div style={{ paddingLeft: "5%", paddingRight: "5%" }}>
      <div className="container">
        <h1>Your Liked Universities</h1>
      </div>

      {likedPUs.length > 0 &&
        likedPUs.map((pu) => (
          <UniversityCard university={pu} />
        ))}

      {(likedPUs.length == 0 ||
        likedPUs == null) && (
        <div style={{ textAlign: "center" }}>
          <h5>
            You have not liked any universities! Press the heart button in the
            university page to like them.
          </h5>
        </div>
      )}
    </div>
  );
};

export default LikedPUs;
