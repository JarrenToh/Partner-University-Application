import React from 'react';
import "../../../student/assets/base.scss";
import './styles.css';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Row, Col, Card, Badge, ListGroup, ListGroupItem, CardBody, CardHeader } from 'reactstrap';

function UniversityDescriptionPage() {
    return (
        <div style={{ display: "flex", flexDirection: "column" }}>

            <div style={{ position: "relative", aspectRatio: "16/9", overflow: "visible", width: "50vw", margin: "0 auto" }}>
                <img src="https://media.timeout.com/images/105387553/750/422/image.jpg" alt="My Image" style={{ objectFit: "cover", width: "100%", height: "100%", position: "relative", zIndex: 1 }} />
            </div>
            <div style={{ display: "flex", flexDirection: "row", margin: "5vw" }}>
                <div style={{ textAlign: "left", flex: 1 }}>
                    <h2 style={{ marginTop: 0 }}>Description</h2>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sed bibendum ex. Aliquam sit amet commodo tortor. Integer sit amet purus et velit feugiat consectetur.</p>
                </div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", justifyContent: "space-between", flex: 1 }}>
                    <Button color="primary" className="m-4" size="lg">
                        Share
                    </Button>
                    <Button color="primary" className="m-4" size="lg">
                        Forum
                    </Button>
                    <Button color="primary" className="m-4" size="lg">
                        Mappable modules
                    </Button>
                </div>

            </div>
            <div style={{ display: "flex", flexDirection: "column", margin: "0 5vw 0 5vw" }}>
                <Row className="justify-content-between">
                    <Col xl="6">
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
                    </Col>


                    <Col xl="4">
                        <Card className="card-box mb-5">
                            <CardHeader style={{ textAlign: "center" }}>
                                <h4 className="font-size-lg mb-0 py-2 font-weight-bold">
                                    Alumnis
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
                    </Col>
                </Row>
            </div>
        </div>

    );
}

export default UniversityDescriptionPage;