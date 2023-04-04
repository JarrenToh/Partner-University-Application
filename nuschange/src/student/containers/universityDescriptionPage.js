import React, { useState, useEffect } from "react";
import "../assets/base.scss";
import './styles.css';
import { Button } from 'reactstrap';
import { Row, Col } from 'reactstrap';
import { Link } from 'react-router-dom';
import apiPaths from '../../util/apiPaths';
import AlumnusComp from "../components/AlumnusComp";
import ReviewComp from "../components/ReviewComp";
import { FaWhatsapp, FaFacebook, FaTelegram } from 'react-icons/fa';
import { FacebookShareButton, TelegramShareButton, WhatsappShareButton } from 'react-share';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Popover from 'react-bootstrap/Popover';

function UniversityDescriptionPage(props) {
    const { puName } = props;
    // const [pu, setPU] = useState({});
    // const [puReview, setPUReviews] = useState([]);
    // const [alumnus, setAlumnus] = useState([]);

    const popover = (
        <Popover id="popover-basic">
            <Popover.Header as="h3">Share</Popover.Header>
            <Popover.Body>
                <Button variant="outline-primary" href={`whatsapp://send?text=${encodeURIComponent('Check out this cool content!')}`} target="_blank">
                    <i className="fab fa-whatsapp"></i> WhatsApp
                </Button>
                {' '}
                <Button variant="outline-primary" href={`https://www.facebook.com/sharer/sharer.php?u=${encodeURIComponent(window.location.href)}`} target="_blank">
                    <i className="fab fa-facebook"></i> Facebook
                </Button>
                {' '}
                <Button variant="outline-primary" href={`https://t.me/share/url?url=${encodeURIComponent(window.location.href)}`} target="_blank">
                    <i className="fab fa-telegram"></i> Telegram
                </Button>
            </Popover.Body>
        </Popover>
    );

    const [puReview, setPUReviews] = useState([
        {
            "puReviewId": 123,
            "rating": 4,
            "review": "This product is great!",
            "noOfLikes": 10,
            "noOfDislikes": 2,
            "isInappropriate": false
        },
        {
            "puReviewId": 124,
            "rating": 3,
            "review": "This product is okay.",
            "noOfLikes": 5,
            "noOfDislikes": 3,
            "isInappropriate": false
        },
        {
            "puReviewId": 125,
            "rating": 5,
            "review": "This product exceeded my expectations!",
            "noOfLikes": 20,
            "noOfDislikes": 1,
            "isInappropriate": false
        },
        {
            "puReviewId": 126,
            "rating": 2,
            "review": "I don't like this product.",
            "noOfLikes": 1,
            "noOfDislikes": 10,
            "isInappropriate": true
        },
        {
            "puReviewId": 127,
            "rating": 4,
            "review": "This product is amazing!",
            "noOfLikes": 15,
            "noOfDislikes": 0,
            "isInappropriate": false
        }
    ]
    );
    const [alumnus, setAlumnus] = useState([
        {
            "comments": [],
            "email": "benleongrules@comp.nus.edu.sg",
            "enquiries": [],
            "faculty": "School Of Computing",
            "firstName": "Ben",
            "lastName": "Leong",
            "password": "password",
            "phoneNumber": "90002040",
            "posts": [],
            "studentId": 1,
            "topics": []
        },
        {
            "comments": [],
            "email": "immutablepain@comp.nus.edu.sg",
            "enquiries": [],
            "faculty": "School Of Computing",
            "firstName": "Henry",
            "lastName": "Chia",
            "password": "password",
            "phoneNumber": "90002040",
            "posts": [],
            "studentId": 2,
            "topics": []
        },
        {
            "comments": [],
            "email": "competitive@comp.nus.edu.sg",
            "enquiries": [],
            "faculty": "School Of Computing",
            "firstName": "Steven",
            "lastName": "Halim",
            "password": "password",
            "phoneNumber": "90002040",
            "posts": [],
            "studentId": 3,
            "topics": []
        }
    ]);

    const [pu, setPU] = useState({
        "countryName": "Zimbabwe",
        "description": "Africa University is a private, Pan-African and United Methodist-related institution. It has over 1,200 students from 36 African countries.[1] It is located 17 km northwest of Mutare, Zimbabwe. It grants bachelor's, master's and PhD degrees in various programs.",
        "images": "data:image/png;base64,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",
        "modules": [],
        "name": "Africa University",
        "puId": 1,
        "puReviews": [],
        "rating": 4.4,
        "regionName": "Africa"
    });

    // useEffect(() => {
    //     apiPaths.getPUbyName(puName)
    //         .then((res) => res.json())
    //         .then((pu) => {
    //             setPU(pu)
    //         });
    // }, []);



    return (
        <div style={{ display: "flex", flexDirection: "column" }}>
            <div style={{ position: "relative", aspectRatio: "16/9", overflow: "visible", width: "50vw", margin: "0 auto" }}>
                <img src={`${pu.images}`} alt="My Image" style={{ objectFit: "cover", width: "100%", height: "100%", position: "relative", zIndex: 1 }} />
            </div>

            <div style={{ display: "flex", flexDirection: "row", margin: "5vw" }}>
                <div style={{ textAlign: "left", flex: 1 }}>
                    <h1 style={{ marginTop: 0 }}>{pu.name}</h1>
                    <p>{pu.description}</p>
                </div>
                <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end", justifyContent: "space-between", flex: 1 }}>
                    <OverlayTrigger trigger="click" placement="left" overlay={popover}>
                        <Button color="primary" className="m-4" size="lg">
                            Share
                        </Button>
                    </OverlayTrigger>
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
                        <ReviewComp reviews={puReview} />
                    </Col>

                    <Col xl="4">
                        <AlumnusComp alumnus={alumnus} />
                    </Col>
                </Row>
            </div>
        </div>

    );
}

export default UniversityDescriptionPage;