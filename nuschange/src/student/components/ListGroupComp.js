import "../assets/base.scss";
import 'bootstrap/dist/css/bootstrap.min.css';
import "./AccordionComp.css";
import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Row, Col, Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';

function ListGroupComp() {
    return (
        <Card className="card-box mb-5">
            <CardHeader style={{ textAlign: "center" }}>
                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                    Reviews
                </h4>
            </CardHeader>
            <ListGroup flush>
                <div className="scroll-area rounded bg-white shadow-overflow">
                    <PerfectScrollbar>
                        <ListGroupItem className="py-3 border-0">
                            <div className="align-box-row w-100">
                                <div className="mr-3">
                                    <div className="bg-neutral-dark text-primary text-center font-size-xl d-60 rounded-sm">
                                        <FontAwesomeIcon icon={['far', 'bell']} />
                                    </div>
                                </div>
                                <div>
                                    <div className="font-weight-bold d-block opacity-8">
                                        Customers
                                    </div>
                                    <div className="text-dark opacity-5">
                                        A wonderful serenity has taken possession of my entire
                                        soul.
                                    </div>
                                </div>
                            </div>
                        </ListGroupItem>
                        <div className="divider" />
                        <ListGroupItem className="py-3 border-0">
                            <div className="align-box-row w-100">
                                <div className="mr-3">
                                    <div className="bg-neutral-dark text-primary text-center font-size-xl d-60 rounded-sm">
                                        <FontAwesomeIcon icon={['far', 'object-group']} />
                                    </div>
                                </div>
                                <div>
                                    <div className="font-weight-bold d-block opacity-8">
                                        New articles
                                    </div>
                                    <div className="text-dark opacity-5">
                                        I am alone, and feel the charm of existence in this
                                        spot.
                                    </div>
                                </div>
                            </div>
                        </ListGroupItem>
                        <div className="divider" />
                        <ListGroupItem className="py-3 border-0">
                            <div className="align-box-row w-100">
                                <div className="mr-3">
                                    <div className="bg-neutral-dark text-primary text-center font-size-xl d-60 rounded-sm">
                                        <FontAwesomeIcon icon={['far', 'chart-bar']} />
                                    </div>
                                </div>
                                <div>
                                    <div className="font-weight-bold d-block opacity-8">
                                        Blog posts
                                    </div>
                                    <div className="text-dark opacity-5">
                                        When, while the lovely valley teems with vapour around
                                        me.
                                    </div>
                                </div>
                            </div>
                        </ListGroupItem>
                        <div className="divider" />
                        <ListGroupItem className="py-3 border-0">
                            <div className="align-box-row w-100">
                                <div className="mr-3">
                                    <div className="bg-neutral-dark text-primary text-center font-size-xl d-60 rounded-sm">
                                        <FontAwesomeIcon icon={['far', 'map']} />
                                    </div>
                                </div>
                                <div>
                                    <div className="font-weight-bold d-block opacity-8">
                                        Google maps
                                    </div>
                                    <div className="text-dark opacity-5">
                                        Lorem ipsum dolor sit amet, consectetur adipiscing
                                        elit.
                                    </div>
                                </div>
                            </div>
                        </ListGroupItem>
                    </PerfectScrollbar>
                </div>
            </ListGroup>
        </Card>
    );
}

export default ListGroupComp;