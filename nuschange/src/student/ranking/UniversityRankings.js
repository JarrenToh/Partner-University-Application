import React, { useState, useEffect, useCallback, useContext } from "react";
import { AuthContext } from "../../AuthContext";
import { useLocation, Link } from "react-router-dom";
import { Button } from "reactstrap";
import UniversityCard from "./UniversityCard";
import "bootstrap/dist/css/bootstrap.min.css";
import "./UniversityRanking.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHeart, faHouseChimneyUser } from "@fortawesome/free-solid-svg-icons";
import { faHeart as heartOutline } from "@fortawesome/free-regular-svg-icons";
import NavbarComp from "../../student/components/NavbarComp";
import { AuthProvider, useAuth } from "../../../src/AuthContext";
import { Modal } from "react-bootstrap";

const UniversityRankings = ({ universitiesData }) => {
  const { loggedInStudent } = useContext(AuthContext);
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";

  const [currentStudent, setCurrentStudent] = useState({ ...loggedInStudent });
  const [universities, setUniversities] = useState(
    universitiesData.map((university) => ({ ...university, isFavorite: false }))
  );
  const [searchTerm, setSearchTerm] = useState("");
  const [filter, setFilter] = useState("");
  const [sortBy, setSortBy] = useState("ranking");
  const [favoritesOnly, setFavoritesOnly] = useState(false);
  const [displayLimit, setDisplayLimit] = useState(10);
  const [ranking, setRanking] = useState(false);
  const [studentLikedPus, setStudentLikedPus] = useState([]);
  const [puEnrolled, setPuEnrolled] = useState({
    name: "Dummy Uni",
    puId: 0,
  });
  const [showLikedModal, setShowLikedModal] = useState(false);
  const handleClose = () => setShowLikedModal(false);
  const handleShow = () => setShowLikedModal(true);

  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const searchQuery = searchParams.get("search") || "";

  useEffect(() => {
    setFilter(searchQuery);
  }, [searchQuery]);

  useEffect(() => {
    console.log(universitiesData);
    setUniversities(
      universitiesData.map((university) => ({
        ...university,
        isFavorite: studentLikedPus.some((u) => u.puId === university.puId),
      }))
    );
  }, [universitiesData]);

  useEffect(() => {
    if (loggedInStudent) {
      fetchLikedPus(loggedInStudent.studentId);
      getEnrolledPuAPI(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);

  const fetchLikedPus = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    console.log(data);
    setCurrentStudent(data);
    setStudentLikedPus(data.likedPUs);
    setUniversities(
      universitiesData.map((uni) => ({
        ...uni,
        isFavorite: data.some((u) => u.puId === uni.puId),
      }))
    );
  };

  const getEnrolledPuAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}/puEnrolled`);
    const data = await response.json();
    console.log(data);
    setPuEnrolled(data);
  };

  useEffect(() => {
    const fetchUniversities = async () => {
      const response = await fetch(
        "http://localhost:8080/PU-war/webresources/pu"
      );
      const data = await response.json();
      setUniversities(
        data.map((uni) => ({
          ...uni,
          isFavorite: studentLikedPus.some((u) => u.puId === uni.puId),
        }))
      );
    };

    fetchUniversities();
  }, []);

  const handleFilterChange = (event) => {
    setFilter(event.target.value);
  };

  const handleSortByChange = (event) => {
    setSortBy(event.target.value);
  };

  const handleToggleFavorite = (likedUniversity) => {
    setUniversities(
      universities.map((university) =>
        university.puId === likedUniversity.puId
          ? { ...university, isFavorite: !university.isFavorite }
          : university
      )
    );

    const alreadyLiked = studentLikedPus.some(
      (u) => u.puId === likedUniversity.puId
    );

    if (alreadyLiked) {
      //remove from likes
      const tempArr = [...studentLikedPus];
      delete likedUniversity.isFavorite;
      const removedArr = tempArr.filter(
        (pu) => pu.puId !== likedUniversity.puId
      );
      console.log(removedArr);
      setStudentLikedPus(removedArr);
      setCurrentStudent({
        ...currentStudent,
        likedPUs: removedArr,
      });
      removeLikedPuAPI(loggedInStudent.studentId, likedUniversity.puId);
    } else {
      // add to likes
      const tempArr = [...studentLikedPus];
      delete likedUniversity.isFavorite;
      tempArr.push(likedUniversity);
      console.log(tempArr);
      setStudentLikedPus(tempArr);
      setCurrentStudent({
        ...currentStudent,
        likedPUs: tempArr,
      });
      addLikedPuAPI(loggedInStudent.studentId, likedUniversity);
    }
  };

  const addLikedPuAPI = async (studentId, data) => {
    return fetch(`${API_URL_STUDENT}/${studentId}/likedPUs`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  };

  const removeLikedPuAPI = async (studentId, puId) => {
    return fetch(`${API_URL_STUDENT}/${studentId}/likedPUs/${puId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
    });
  };

  const handleShowMore = useCallback(() => {
    setDisplayLimit(displayLimit + 10);
  }, [setDisplayLimit, displayLimit]);

  const filteredUniversities = universities.filter(
    (university) =>
      university.name.toLowerCase().includes(filter.toLowerCase()) ||
      university.regionName.toLowerCase().includes(filter.toLowerCase()) ||
      university.countryName.toLowerCase().includes(filter.toLowerCase())
  );

  useEffect(() => {
    if (sortBy === "ranking") {
      setRanking(true);
    } else {
      setRanking(false);
    }
  }, [sortBy]);

  const sortedUniversities = filteredUniversities.sort((a, b) => {
    if (sortBy === "ranking") {
      if (a.rating !== b.rating) {
        // Sort by rank first
        return b.rating - a.rating;
      } else {
        // If rank is the same, sort by university ID
        return a.puId - b.puId;
      }
    } else {
      // Sort by name
      return a.name.localeCompare(b.name);
    }
  });

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  const displayedUniversities = favoritesOnly
    ? sortedUniversities.filter((university) => university.isFavorite)
    : sortedUniversities.slice(0, displayLimit);

  const LikeButton = (props) => {
    const { unEnrolled, university } = props;
    console.log(unEnrolled);
    if (unEnrolled) {
      return (
        <button
          className={`university-card__favorite-button`}
          onClick={() => handleToggleFavorite(university)}
        >
          <FontAwesomeIcon
            icon={university.isFavorite ? faHeart : heartOutline}
            style={{ color: "#d01b1b" }}
          />{" "}
        </button>
      );
    } else {
      return (
        <button className={`university-card__favorite-button`}
        onClick={handleShow}
        >
          <FontAwesomeIcon
            icon={faHouseChimneyUser}
          />{" "}
        </button>
      );
    }
  };

  return (
    <div className="wrapper">
      <NavbarComp
        isLoggedIn={isLoggedIn}
        setIsLoggedIn={setIsLoggedIn}
        user={user}
      />
      <br />
      <div className="container" style={{ maxWidth: "1300px" }}>
        <div className="universityRankings">
          <div className="universityRankings_description">
            <h1 className="headerRanking">Partner University Rankings</h1>
            <br />
            <p>
              Welcome to our university ranking page, where you can find
              comprehensive information on various universities worldwide. We
              understand that selecting a university can be a challenging task,
              and we're here to help you make an informed decision.
            </p>

            <p>
              Our rankings are unique because they are based on student ratings.
              We believe that students are the best judges of the universities
              they attend, and they have the most up-to-date information about
              campus life, academics, and resources. Therefore, we provide an
              opportunity for students to come together and rate their partner
              universities.
            </p>
            <br />
          </div>
          <br />
          <div className="university-rankings__header">
            <div className="university-rankings__filter">
              <input
                type="text"
                placeholder="Search "
                value={filter}
                onChange={handleFilterChange}
              />
            </div>
            <div className="university-rankings__sort-by">
              Sort by:
              <select value={sortBy} onChange={handleSortByChange}>
                <option value="ranking">Ranking</option>
                <option value="name">Name</option>
              </select>
            </div>
            {/*
            <div className="university-rankings__favorites">
              <label>
                <input
                  type="checkbox"
                  checked={favoritesOnly}
                  onChange={(event) => setFavoritesOnly(event.target.checked)}
                />
                Favorites only
              </label>
            </div>
            */}
          </div>
        </div>
        {displayedUniversities.length > 0 ? (
          <div className="university-rankings__grid">
            <br />
            {displayedUniversities.map((university, index) => (
              <div className="university-card-wrapper" key={university.puId}>
                <Link
                  to={`/student/university-description-page/${university.name}`}
                  style={{ textDecoration: "none" }}
                >
                  <UniversityCard
                    university={university}
                    index={index + 1}
                    ranking={ranking}
                  />
                </Link>
                {loggedInStudent && (
                  <LikeButton
                    unEnrolled={
                      puEnrolled.puId !== university.puId ? true : false
                    }
                    university={university}
                  />
                )}
              </div>
            ))}
          </div>
        ) : (
          <div className="university-rankings__noResult">
            <div className="university-rankings__empty">
              <br />
              <h2>No Results</h2>
            </div>
          </div>
        )}
        {sortedUniversities.length > displayLimit && (
          <Button onClick={handleShowMore}>Show More</Button>
        )}
      </div>
      <Modal show={showLikedModal} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>You are Enrolled Here!</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          To easily access your university from your profile page, simply click on the Enrolled University field.
          </Modal.Body>
        <Modal.Footer>
          <Button color="outline-danger" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default UniversityRankings;
