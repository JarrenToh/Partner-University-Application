import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React, { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faThumbsDown, faFlag } from "@fortawesome/free-regular-svg-icons";
import { Button } from "reactstrap";
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';
import defaultProfilePicture from '../images/housekeeper.png';


function ReviewComp({student, toggleLike, toggleDislike, handleFlagged, likes, dislikes}) {
    const [isHovered, setIsHovered] = useState({});
    return (
        <Card className="card-box mb-5">
            <CardHeader style={{ textAlign: "center" }}>
                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                    Student Reviews
                </h4>
            </CardHeader>
            <ListGroup flush>
                <div className="scroll-area rounded bg-white shadow-overflow" style={{ width: "100%", overflowX: "auto" }}>
                    <PerfectScrollbar>
                        {student.map((s) => (
                            <ListGroupItem className="py-3" key={s.studentId}>
                                <div className="d-flex align-items-center">
                                    <div className="mr-4">
                                        <img src={defaultProfilePicture} alt="Default Profile" className="rounded-circle" style={{ width: "80px", height: "80px" }} />
                                    </div>
                                    <div className="d-flex flex-column justify-content-center flex-grow-1" style={{ width: "60%" }}>
                                        <div className="font-weight-bold d-block opacity-8">
                                            {s.firstName} {s.lastName}
                                        </div>
                                        <div className="text-dark opacity-5" style={{ textAlign: "justify", textJustify: "inter-word" }}>
                                            {s.puReview.review}
                                        </div>
                                    </div>
                                    <div className="d-flex align-items-center " style={{ width: "30%" }}>
                                        <div className="ml-auto">
                                            <>
                                                <span className={`font-weight-bold mr-2 ${s.puReview.rating < 3 ? 'text-danger' : 'text-success'}`} style={{ fontSize: '24px' }}>{s.puReview.rating}</span>
                                                /5
                                            </>
                                            <div className="font-weight-bold d-block opacity-20 text-success">
                                                Likes: {s.puReview.noOfLikes}
                                            </div>
                                            <div className="font-weight-bold d-block opacity-20 text-danger">
                                                Dislike {s.puReview.noOfDislikes}
                                            </div>
                                        </div>
                                        <div className="ml-auto">
                                            <Button color="link" style={{ background: "transparent" }} onClick={() => toggleLike(s.studentId)}>
                                                <FontAwesomeIcon
                                                    icon={faThumbsUp}
                                                    size="2x"
                                                    style={{
                                                        cursor: "pointer",
                                                        color: likes[s.studentId] === "like" ? "green" : "#007bff",
                                                        marginRight: "10px",
                                                    }}
                                                />
                                            </Button>
                                        </div>
                                        <div>
                                            <Button color="link" style={{ background: "transparent" }} onClick={() => toggleDislike(s.studentId)}>
                                                <FontAwesomeIcon
                                                    icon={faThumbsDown}
                                                    size="2x"
                                                    style={{
                                                        cursor: "pointer",
                                                        color: dislikes[s.studentId] === "dislike" ? "red" : "#007bff",
                                                        marginRight: "10px",
                                                    }}
                                                />
                                            </Button>
                                        </div>
                                        <div>
                                            <Button
                                                color="link"
                                                style={{
                                                    background: "transparent",
                                                }}
                                                onMouseEnter={() => setIsHovered((prevStates) => ({
                                                    ...prevStates,
                                                    [s.studentId]: true,
                                                }))}
                                                onMouseLeave={() => setIsHovered((prevStates) => ({
                                                    ...prevStates,
                                                    [s.studentId]: false,
                                                }))}
                                                onClick={() => handleFlagged(s.studentId)}
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
                                        </div>
                                    </div>
                                </div>
                            </ListGroupItem>
                        ))}
                    </PerfectScrollbar>
                </div>
            </ListGroup>

        </Card>
    );
}

export default ReviewComp;
