import React, { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { Rating } from "@mui/material";

const ReviewModal = (props) => {
  const API_URL_PUREVIEW = "http://localhost:8080/PU-war/webresources/pureview";
  const { puReview, studentId } = props;
  const [stars, setStars] = useState(0);
  const [review, setReview] = useState("");
  const [editedPuReview, setEditedPuReview] = useState({});
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);

  useEffect(() => {
    if (puReview) {
      setStars(puReview.rating);
      setReview(puReview.review);
      setEditedPuReview({ ...puReview });
    } else {
      
    }
  }, [puReview]);

  // Method to detect if edit or add review
  const puReviewAPI = () => {
    if (editedPuReview.puReviewId) { //edit existing review 

    } else { // add fresh review

    }
  }
  
  function updatePUReviewAPI(reviewId, data) {
    console.log(data);
    return fetch(`${API_URL_PUREVIEW}/${reviewId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

  const handleReviewChange = (event) => {
    setReview(event.target.value);
    setSaveButtonDisabled(false);
  };

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Your Partner University Review
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <h5>Rating</h5>
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            alignItems: "center",
            margin: "2.5%",
          }}
        >
          <Rating
            name="simple-controlled"
            value={stars}
            onChange={(event, newValue) => {
              setStars(newValue);
              setSaveButtonDisabled(false);
            }}
            size="large"
          />
        </div>

        <h5>Review</h5>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Control
            as="textarea"
            rows={4}
            value={review}
            onChange={handleReviewChange}
          />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="outline-danger" onClick={props.onHide}>
          Close
        </Button>
        <Button
          variant="success"
          disabled={saveButtonDisabled}
          onClick={() =>
            updatePUReviewAPI(editedPuReview.puReviewId, {
              ...editedPuReview,
              rating: stars,
              review: review,
            })
          }
        >
          Save changes
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ReviewModal;
