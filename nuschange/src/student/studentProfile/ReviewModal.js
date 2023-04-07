import React, { useEffect, useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { Rating } from "@mui/material";

const ReviewModal = (props) => {
  const [stars, setStars] = useState(0);
  const [review, setReview] = useState("");
  const { puReview } = props;

  useEffect(() => {
    setStars(puReview.rating);
    setReview(puReview.review);
  }, []);

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
            }}
            size="large"
          />
        </div>

        <h5>Review</h5>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
          <Form.Control as="textarea" rows={4} defaultValue={review}/>
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="outline-danger" onClick={props.onHide}>
          Close
        </Button>
        <Button variant="success" onClick={props.onHide}>Save changes</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ReviewModal;
