import React, { useEffect, useContext, useState } from "react";
import { Modal, Button, Form, InputGroup, Col, Alert } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";

import { AuthContext } from "../login/AuthContext";

const SocialMediaModal = (props) => {
  const { loggedInStudent, logout } = useContext(AuthContext);
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const [socialMedia, setSocialMedia] = useState(props.socialMedia);
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
  const [formStatus, setFormStatus] = useState("Save Changes");
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState("danger");

  useEffect(() => {
    setSocialMedia(props.socialMedia);
  }, [props.socialMedia]);

  // API to update student social media links (working for now)
  const updateStudentAPI = async (studentId, data) => {
    setFormStatus("Saving...");
    const containsEmptyString = socialMedia.some((str) => str === "");

    if (containsEmptyString) {
      setFormStatus("Save Changes");
      setAlertMessage("Please fill in all fields!");
      setAlertType("danger");
      setAlertVisible(true);
    }

    try {
      const response = await fetch(`${API_URL_STUDENT}/${studentId}`, {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "PUT",
        body: JSON.stringify(data),
      });

      if (response.ok) {
        props.onSocialMediaChange(socialMedia);
        setFormStatus("Save Changes");
        setAlertType("success");
        setAlertMessage(
          "You have edited your social media links successfully!"
        );
        setAlertVisible(true);
        setSaveButtonDisabled(true);
      } else {
        setFormStatus("Save Changes");
        setAlertType("danger");
        setAlertMessage("An error occured!");
        setAlertVisible(true);
      }
    } catch (error) {
      setFormStatus("Save Changes");
      setAlertType("danger");
      setAlertMessage("An error occured!");
      setAlertVisible(true);
    }
  };

  function handleAdd() {
    const values = [...socialMedia];
    values.push("");
    setSocialMedia(values);
  }

  function handleChange(i, event) {
    const values = [...socialMedia];
    values[i] = event.target.value;
    setSocialMedia(values);
    setSaveButtonDisabled(false);
  }

  function handleRemove(i) {
    const values = [...socialMedia];
    values.splice(i, 1);
    setSocialMedia(values);
    setSaveButtonDisabled(false);
  }

  return (
    <Modal
      {...props}
      size="lg"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          Edit Social Media
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div>
          {socialMedia.length > 0 &&
            socialMedia.map((field, idx) => (
              <div style={{ paddingBottom: "2.5%" }} key={idx}>
                <Form.Label>{`Link ${idx + 1}`}</Form.Label>
                <InputGroup hasValidation>
                  <Form.Control
                    required
                    placeholder={`Social Media Link ${idx + 1}`}
                    value={field}
                    onChange={(e) => handleChange(idx, e)}
                  />
                  <Button variant="danger" onClick={() => handleRemove(idx)}>
                    <FontAwesomeIcon icon={faTrash} />
                  </Button>
                  <Form.Control.Feedback type="invalid">
                    Please type a relevant link!
                  </Form.Control.Feedback>
                </InputGroup>
              </div>
            ))}
          {socialMedia.length === 0 && (
            <div style={{ textAlign: "center", margin: "5%" }}>
              <h5>
                You have no social media links now. You can add them by clicking
                on the button below!
              </h5>
            </div>
          )}

          <div className="d-grid gap-2" style={{ paddingBottom: "2.5%" }}>
            <Button variant="primary" onClick={() => handleAdd()}>
              Add Social Media Link
            </Button>
          </div>
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
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="outline-danger" onClick={props.onHide}>
          Close
        </Button>
        <Button
          variant="success"
          disabled={saveButtonDisabled || formStatus === "Saving..."}
          onClick={() =>
            updateStudentAPI(loggedInStudent.studentId, {
              ...loggedInStudent,
              socialMedia: socialMedia,
            })
          }
        >
          {formStatus}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default SocialMediaModal;
