import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React, { useState } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faThumbsDown, faFlag } from "@fortawesome/free-regular-svg-icons";
import { Button } from "reactstrap";
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';


function ReviewComp(props) {
    const [like, setLike] = useState(false);
    const [dislike, setDislike] = useState(false);
    const [flagged, setFlagged] = useState(false);
  
    const handleLike = () => {
      setLike(!like);
      setDislike(false);
    };
  
    const handleDislike = () => {
      setDislike(!dislike);
      setLike(false);
    };
    return (
        <Card className="card-box mb-5">
            <CardHeader style={{ textAlign: "center" }}>
                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                    Reviews
                </h4>
            </CardHeader>
            <ListGroup flush>
                <div className="scroll-area rounded bg-white shadow-overflow" style={{ width: "100%", overflowX: "auto" }}>
                    <PerfectScrollbar>
                        {props.reviews.map((r) => (
                            <ListGroupItem className="py-3 border-0" key={r.puReviewId}>
                                <div className="align-box-row w-100 justify-content-between align-items-center">
                                    <div className="mr-3">
                                        <div className="bg-neutral-dark text-primary text-center font-size-xxl d-80 rounded-sm">
                                            <FontAwesomeIcon icon={["far", "user-circle"]} />
                                        </div>
                                    </div>
                                    <div className="d-flex flex-column justify-content-center flex-grow-1" style={{ width: "60%" }}>
                                        <div className="font-weight-bold d-block opacity-8">
                                            student
                                        </div>
                                        <div className="text-dark opacity-5" style={{ overflowWrap: "break-word" }}>
                                            {r.review}
                                        </div>
                                    </div>
                                    <div className="d-flex align-items-center" style={{ width: "100%", justifyContent: "flex-end" }}>
                                        <div className="ml-auto mr-3">
                                            <Button color="link" style={{ background: "transparent" }} onClick={handleLike}>
                                                <FontAwesomeIcon
                                                    icon={faThumbsUp}
                                                    size="2x"
                                                    style={{
                                                        cursor: "pointer",
                                                        color: like ? "green" : "#007bff",
                                                        marginRight: "10px",
                                                    }}
                                                />
                                            </Button>
                                        </div>
                                        <div className="mr-3">
                                            <Button color="link" style={{ background: "transparent" }} onClick={handleDislike}>
                                                <FontAwesomeIcon
                                                    icon={faThumbsDown}
                                                    size="2x"
                                                    style={{
                                                        cursor: "pointer",
                                                        color: dislike ? "red" : "#007bff",
                                                        marginRight: "10px",
                                                    }}
                                                />
                                            </Button>
                                        </div>
                                        <div>
                                            <Button color="link" style={{ background: "transparent" }}>
                                                <FontAwesomeIcon icon={faFlag} size="2x" style={{ cursor: "pointer", color: "#007bff" }} />
                                            </Button>
                                        </div>
                                    </div>
                                </div>
                                <div className="divider" />
                            </ListGroupItem>
                        ))}
                    </PerfectScrollbar>
                </div>
            </ListGroup>

        </Card>
    );
}

export default ReviewComp;
