import React, { useEffect, useState } from "react";
import { Alert, Modal, Button, Form } from "react-bootstrap";
import { Rating } from "@mui/material";

const ModReviewModal = (props) => {
  const API_URL_PUMODREVIEW =
    "http://localhost:8080/PU-war/webresources/pumodulereview";
  const { modReview, studentId, modId } = props;
  const [stars, setStars] = useState(0);
  const [review, setReview] = useState("");
  const [editedModReview, setEditedModReview] = useState({
    isInappropriate: false,
    moduleReviewId: 0,
    noOfDislikes: 0,
    noOfLikes: 0,
    rating: 2,
    review: "Dummy Review",
  });
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
  const [formStatus, setFormStatus] = useState("Save Changes");
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("danger");

  useEffect(() => {
    console.log(studentId);
    console.log(modId);
    if (modReview) {
      console.log(modReview);
      setStars(modReview.rating);
      setReview(modReview.review);
      setEditedModReview({ ...modReview });
    } else {
    }
  }, [modReview]);

  const puModReviewAPI = (event, data) => {
    event.preventDefault();
    setFormStatus("Saving...");

    if (stars === 0 || stars === null) {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage(
        "Your Rating of a Module cannot be zero! Please rate at least 1 star."
      );
      setAlertVisible(true);
      setStars(modReview.rating);
      return;
    } else if (review.length === 0) {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage(
        "Your Review of a Module cannot be empty! Please fill in your review."
      );
      setAlertVisible(true);
      setReview(modReview.review);
      return;
    }

    if (editedModReview.moduleReviewId > 0) {
      //edit existing review
      updatePUModReviewAPI(editedModReview.moduleReviewId, data);
    } else {
      // add fresh review
      createPUModReviewAPI(data);
    }
  };

  const updatePUModReviewAPI = async (moduleReviewId, data) => {
    const response = await fetch(`${API_URL_PUMODREVIEW}/${moduleReviewId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });

    if (response.ok) {
      setFormStatus("Save Changes");
      setAlertType("success");
      setAlertMessage("You have edited your module review successfully!");
      setAlertVisible(true);
      setSaveButtonDisabled(true);
    } else {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage("An error occured!");
      setAlertVisible(true);
    }
  };

  const createPUModReviewAPI = async (data) => {
    const response = await fetch(
      `${API_URL_PUMODREVIEW}/${studentId}/${modId}`,
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "POST",
        body: JSON.stringify(data),
      }
    );

    if (response.ok) {
      setFormStatus("Save Changes");
      setAlertType("success");
      setAlertMessage("You have added your module review successfully!");
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
        onSubmit={(event) =>
          puModReviewAPI(event, {
            ...editedModReview,
            rating: stars,
            review: review,
          })
        }
      >
        <Modal.Header closeButton>
          <Modal.Title id="contained-modal-title-vcenter">
            Your Module Review
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
          <Button variant="success" disabled={saveButtonDisabled} type="submit">
            {formStatus}
          </Button>
        </Modal.Footer>
      </Form>
    </Modal>
  );
};

export default ModReviewModal;
