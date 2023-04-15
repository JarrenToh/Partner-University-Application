import React, { useEffect, useState } from "react";
import { Alert, Modal, Button, Form } from "react-bootstrap";
import { Rating } from "@mui/material";

const ReviewModal = (props) => {
  const API_URL_PUREVIEW = "http://localhost:8080/PU-war/webresources/pureview";
  const { puReview, studentId } = props;
  const [stars, setStars] = useState(0);
  const [review, setReview] = useState("");
  const [editedPuReview, setEditedPuReview] = useState({});
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
  const [formStatus, setFormStatus] = useState("Save Changes");
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState('success');

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
    if (editedPuReview.puReviewId) {
      //edit existing review
    } else {
      // add fresh review
    }
  };

  const updatePUReviewAPI = async (event, reviewId, data) => {
    event.preventDefault();
    setFormStatus("Saving...");

    if (stars === 0 || stars === null) {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage("Your Rating of a University cannot be zero! Please rate at least 1 star.");
      setAlertVisible(true);
      setStars(puReview.rating);
      return;
    } else if (review.length === 0) {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage("Your Review of a University cannot be empty! Please fill in your review.");
      setAlertVisible(true);
      setReview(puReview.review);
      return;
    }

    const response = await fetch(`${API_URL_PUREVIEW}/${reviewId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });

    if (response.ok) {
      setFormStatus("Save Changes");
      setAlertType('success');
      setAlertMessage("You have edited your review successfully!");
      setAlertVisible(true);
      setSaveButtonDisabled(true);
    } else {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage("An error occured!");
      setAlertVisible(true);
    }
  };

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
      <Form
        noValidate
        onSubmit={(event) => updatePUReviewAPI(event, editedPuReview.puReviewId, {
          ...editedPuReview,
          rating: stars,
          review: review,
        })}
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
          <Form.Group
            className="mb-3"
            controlId="exampleForm.ControlTextarea1"
            hasValidation
          >
            <Form.Control
              as="textarea"
              rows={4}
              value={review}
              required
              onChange={handleReviewChange}
            />
            <Form.Control.Feedback type="invalid">
              Please fill in your review.
            </Form.Control.Feedback>
          </Form.Group>
          {alertVisible && (
            <Alert
              variant={alertType}
              toggle={() => setAlertVisible(false)}
              onClose={() => setAlertVisible(false)}
              dismissible
            >
              {alertMessage}
            </Alert>
          )}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-danger" onClick={props.onHide}>
            Close
          </Button>
          <Button type="submit" variant="success" disabled={saveButtonDisabled}>
            {formStatus}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ReviewModal;
