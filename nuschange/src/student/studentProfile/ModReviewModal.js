import React, { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
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
    noOfLikes: 1,
    rating: 2,
    review: "Dummy Review",
  });
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);

  useEffect(() => {
    console.log(studentId);
    console.log(modId);
    if (modReview) {
      console.log(modReview);
      setStars(modReview.rating);
      setReview(modReview.review);
      setEditedModReview({ ...modReview});
    } else {
    }
  }, [modReview]);

  const puModReviewAPI = (data) => {
    if (editedModReview.moduleReviewId > 0) {
      //edit existing review
      updatePUModReviewAPI(editedModReview.moduleReviewId, data);
    } else {
      // add fresh review
      createPUModReviewAPI(data);
    }
  };

  const updatePUModReviewAPI = async (moduleReviewId, data) => {
    console.log(data);
    return await fetch(`${API_URL_PUMODREVIEW}/${moduleReviewId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  };

  const createPUModReviewAPI = async (data) => {
    console.log(data);
    return await fetch(`${API_URL_PUMODREVIEW}/${studentId}/${modId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "POST",
      body: JSON.stringify(data),
    });
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
          onClick={() => puModReviewAPI({
            ...editedModReview,
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

export default ModReviewModal;
