import "../assets/base.scss";
import "bootstrap/dist/css/bootstrap.min.css";
import "./AccordionComp.css";
import React, { useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Link } from "react-router-dom";
import {
  faThumbsUp,
  faThumbsDown,
  faFlag,
} from "@fortawesome/free-regular-svg-icons";
import { Button, Tooltip } from "reactstrap";
import PerfectScrollbar from "react-perfect-scrollbar";
import { Card, ListGroup, ListGroupItem, CardHeader } from "reactstrap";
import defaultProfilePicture from "../images/housekeeper.png";


function ReviewComp({
  student,
  toggleLike,
  toggleDislike,
  handleFlagged,
  loggedInStudent,
  studentLikedPUReviews,
  studentDislikedPUReviews
}) {
  const [isHovered, setIsHovered] = useState({});
  const [tooltipOpenLike, setTooltipOpenLike] = useState(false);
  const [tooltipOpenDislike, setTooltipOpenDislike] = useState(false);
  const [tooltipOpenFlag, setTooltipOpenFlag] = useState(false);

  const toggleTooltipLike = () => {
    setTooltipOpenLike(!tooltipOpenLike);
  };
  const toggleTooltipDisike = () => {
    setTooltipOpenDislike(!tooltipOpenDislike);
  };
  const toggleTooltipFlag = () => {
    setTooltipOpenFlag(!tooltipOpenFlag);
  };

  return (
    <Card className="card-box mb-5">
      <CardHeader style={{ textAlign: "center" }}>
        <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
          Student Reviews
        </h4>
      </CardHeader>
      <ListGroup flush>
        <div
          className="scroll-area rounded bg-white shadow-overflow"
          style={{ width: "100%", overflowX: "auto" }}
        >
          {student.length === 0 ? (
            <div className="text-center py-5">
              <h4 className="text-muted" style={{ fontSize: '28px' }}>No Review Found</h4>
            </div>
          ) :
            <PerfectScrollbar>
              {student.map((s) => (
                <ListGroupItem className="py-3" key={s.studentId}>
                  <div className="d-flex align-items-center">
                    <Link
                      to={`/other-profile/${s.studentId}`}
                      style={{ color: "black", textDecoration: "none" }}
                    >
                      <div className="mr-4">
                        <img
                          src={defaultProfilePicture}
                          alt="Default Profile"
                          className="rounded-circle"
                          style={{ width: "80px", height: "80px" }}
                        />
                      </div>
                    </Link>
                    <div
                      className="d-flex flex-column justify-content-center flex-grow-1"
                      style={{ width: "60%" }}
                    >
                      <Link
                        to={`/other-profile/${s.studentId}`}
                        style={{ color: "black", textDecoration: "none" }}
                      >
                        <div className="font-weight-bold d-block opacity-8">
                          {s.firstName} {s.lastName}
                        </div>
                      </Link>
                      <div
                        className="text-dark opacity-5"
                        style={{
                          textAlign: "justify",
                          textJustify: "inter-word",
                        }}
                      >
                        {s.puReview.review}
                      </div>
                    </div>
                    <div
                      className="d-flex align-items-center "
                      style={{ width: "30%" }}
                    >
                      <div className="ml-auto">
                        <>
                          <span
                            className={`font-weight-bold mr-2 ${s.puReview.rating < 3
                              ? "text-danger"
                              : "text-success"
                              }`}
                            style={{ fontSize: "24px" }}
                          >
                            {s.puReview.rating}
                          </span>
                          /5
                        </>
                        <div className="font-weight-bold d-block opacity-20 text-success">
                          Likes: {s.puReview.noOfLikes}
                        </div>
                        <div className="font-weight-bold d-block opacity-20 text-danger">
                          Dislike {s.puReview.noOfDislikes}
                        </div>
                      </div>
                      <div className="ml-auto" id="ReviewLikedButton">
                        <Button
                          color="link"
                          style={{ background: "transparent" }}
                          onClick={() => toggleLike(s.studentId)}
                          disabled={loggedInStudent === null}
                        >
                          <FontAwesomeIcon
                            icon={faThumbsUp}
                            size="2x"
                            style={{
                              cursor: "pointer",
                              color:
                                (loggedInStudent &&
                                  studentLikedPUReviews?.some((likedPUReview) => {
                                    return likedPUReview.puReviewId === s.puReview.puReviewId;
                                  })) ? "green"
                                  : "#007bff",
                              marginRight: "10px",
                            }}

                          />
                        </Button>
                        {loggedInStudent === null ?
                          <Tooltip placement="top" isOpen={tooltipOpenLike} target="ReviewLikedButton" toggle={toggleTooltipLike} >Must be signed in to like review</Tooltip>
                          :
                          null
                        }
                      </div>
                      <div id="ReviewDislikedButton">
                        <Button
                          color="link"
                          style={{ background: "transparent" }}
                          onClick={() => toggleDislike(s.studentId)}
                          disabled={loggedInStudent === null}
                          // title={loggedInStudent === null ? "Must be signed in to dislike review" : ""}
                          // data-tip={loggedInStudent === null ? "Must be signed in to dislike review" : ""}
                        >
                          <FontAwesomeIcon
                            icon={faThumbsDown}
                            size="2x"
                            style={{
                              cursor: "pointer",
                              color:
                                (loggedInStudent &&
                                  studentDislikedPUReviews?.some((dislikedPUReview) => {
                                    return dislikedPUReview.puReviewId === s.puReview.puReviewId;
                                  })) ? "red"
                                  : "#007bff",
                              marginRight: "10px",
                            }}
                            title={loggedInStudent === null ? "Must be signed in to flag review" : ""}
                          />
                        </Button>
                        {loggedInStudent === null ?
                          <Tooltip placement="top" isOpen={tooltipOpenDislike} target="ReviewDislikedButton" toggle={toggleTooltipDisike} >Must be signed in to dislike review</Tooltip>
                          :
                          null
                        }
                      </div>
                      <div id="ReviewFlagButton">
                        <Button
                          color="link"
                          style={{
                            background: "transparent",
                          }}
                          onMouseEnter={() =>
                            setIsHovered((prevStates) => ({
                              ...prevStates,
                              [s.studentId]: true,
                            }))
                          }
                          onMouseLeave={() =>
                            setIsHovered((prevStates) => ({
                              ...prevStates,
                              [s.studentId]: false,
                            }))
                          }
                          onClick={() => handleFlagged(s.studentId)}
                          disabled={loggedInStudent === null}
                          data-tip={loggedInStudent === null ? "Must be signed in to flag review" : ""}
                        >
                          <FontAwesomeIcon
                            icon={faFlag}
                            size="2x"
                            style={{
                              cursor: "pointer",
                              color: isHovered[s.studentId] ? "red" : "#007bff",
                            }}
                          />
                        </Button>
                        {loggedInStudent === null ?
                          <Tooltip placement="top" isOpen={tooltipOpenFlag} target="ReviewFlagButton" toggle={toggleTooltipFlag} >Must be signed in to flag review</Tooltip>
                          :
                          null
                        }
                      </div>
                    </div>
                  </div>
                </ListGroupItem>
              ))}
            </PerfectScrollbar>
          }
        </div>
      </ListGroup>
    </Card>
  );
}

export default ReviewComp;
