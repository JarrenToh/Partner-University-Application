import React from 'react';
import "../assets/base.scss";
import './styles.css';
import { Button } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import PerfectScrollbar from 'react-perfect-scrollbar';
import { Row, Col, Card, ListGroup, ListGroupItem, CardHeader } from 'reactstrap';
import { Link } from 'react-router-dom';
import ListGroupComp from '../components/ListGroupComp';

function UniversityDescriptionPage() {
    return (
        <div style={{ display: "flex", flexDirection: "column" }}>
            <div style={{ position: "relative", aspectRatio: "16/9", overflow: "visible", width: "50vw", margin: "0 auto" }}>
                <img src="https://media.timeout.com/images/105387553/750/422/image.jpg" alt="My Image" style={{ objectFit: "cover", width: "100%", height: "100%", position: "relative", zIndex: 1 }} />
            </div>
            <div style={{ display: "flex", flexDirection: "row", margin: "5vw" }}>
                <div style={{ textAlign: "left", flex: 1 }}>
                    <h1 style={{ marginTop: 0 }}>Description</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sed bibendum ex. Aliquam sit amet commodo tortor. Integer sit amet purus et velit feugiat consectetur.</p>
                </div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", justifyContent: "space-between", flex: 1 }}>
                    <Button color="primary" className="m-4" size="lg">
                        Share
                    </Button>
                    <Button color="primary" className="m-4" size="lg">
                        Forum
                    </Button>
                    <Link to="/university-description-page/mappable-module">
                        <Button color="primary" className="m-4" size="lg">
                            Mappable modules
                        </Button>
                    </Link>
                </div>
            </div>
            <div style={{ display: "flex", flexDirection: "column", margin: "0 5vw 0 5vw" }}>
                <Row className="justify-content-between">
                    <Col xl="6">
                        <ListGroupComp />
                    </Col>

                    <Col xl="4">
                        <ListGroupComp />
                    </Col>
                </Row>
            </div>
        </div>

    );
}

export default UniversityDescriptionPage;