import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';

function ReviewComp(props) {
    return (
        <Card className="card-box mb-5">
            <CardHeader style={{ textAlign: "center" }}>
                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                    Reviews
                </h4>
            </CardHeader>
            <ListGroup flush>
                <div
                    className="scroll-area rounded bg-white shadow-overflow"
                    style={{ width: "100%", overflowX: "auto" }}
                >
                    <PerfectScrollbar>
                        {props.reviews.map((r) => (
                            <ListGroupItem className="py-3 border-0" key={r.puReviewId}>
                                <div className="align-box-row w-100 justify-content-between align-items-center">
                                    <div className="mr-3">
                                        <div className="bg-neutral-dark text-primary text-center font-size-xxl d-80 rounded-sm" >
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
                                            <button style={{ background: "transparent" }}>
                                                <FontAwesomeIcon
                                                    icon={["far", "thumbs-up"]}
                                                    size="2x"
                                                    style={{ cursor: "pointer", color: "#007bff", marginRight: "10px" }}
                                                />
                                            </button>
                                        </div>
                                        <div className="mr-3">
                                            <button style={{ background: "transparent" }}>
                                                <FontAwesomeIcon
                                                    icon={["far", "thumbs-down"]}
                                                    size="2x"
                                                    style={{ cursor: "pointer", color: "#007bff", marginRight: "10px" }}
                                                />
                                            </button>
                                        </div>
                                        <div>
                                            <button style={{ background: "transparent" }}>
                                                <FontAwesomeIcon
                                                    icon={["far", "flag"]}
                                                    size="2x"
                                                    style={{ cursor: "pointer", color: "#007bff" }}
                                                />
                                            </button>
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
