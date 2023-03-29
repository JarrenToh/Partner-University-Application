import React from "react";
import { Modal, Button, Form } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar as filledStar } from "@fortawesome/free-solid-svg-icons";
import { faStar as outlineStar } from "@fortawesome/free-regular-svg-icons";
import ClickableStar from "./ClickableStar";

const ReviewModal = (props) => {
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
          <ClickableStar />
          <ClickableStar />
          <ClickableStar />
          <ClickableStar />
          <ClickableStar />
        </div>

        <h5>Review</h5>
        <Form.Group className="mb-3" controlId="exampleForm.ControlTextarea1">
     
          <Form.Control as="textarea" rows={4} />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="outline-danger" onClick={props.onHide}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ReviewModal;
