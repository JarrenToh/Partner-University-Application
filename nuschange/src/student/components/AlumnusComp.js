import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';

function AlumnusComp(props) {
    return (
        <Card className="card-box mb-5">
            <CardHeader style={{ textAlign: "center" }}>
                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                    Alumnus
                </h4>
            </CardHeader>
            <ListGroup flush>
                <div
                    className="scroll-area rounded bg-white shadow-overflow"
                    style={{ width: "100%", overflowX: "auto" }}
                >
                    <PerfectScrollbar>
                        {props.alumnus.map((alumni) => (
                            <><ListGroupItem className="py-3 border-0" key={alumni.studentId}>
                                <div className="align-box-row w-100">
                                    <div className="mr-3">
                                        <div className="bg-neutral-dark text-primary text-center font-size-xl d-60 rounded-sm">
                                            <FontAwesomeIcon icon={["far", "user-circle"]} />
                                        </div>
                                    </div>
                                    <div>
                                        <div className="font-weight-bold d-block opacity-8">
                                            {alumni.firstName} {alumni.lastName}
                                        </div>
                                        <div className="text-dark opacity-5">
                                            {alumni.email}
                                        </div>
                                    </div>
                                </div>
                            </ListGroupItem><div className="divider" /></>
                        ))}
                    </PerfectScrollbar>
                </div>
            </ListGroup>
        </Card>
    );
}

export default AlumnusComp;