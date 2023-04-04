import React, { useEffect, useContext, useState } from "react";
import { Modal, Button, Form, InputGroup, Col } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";

import { AuthContext } from "../login/AuthContext";

import DynamicSocialMediaForm from "./DynamicSocialMediaForm";

const SocialMediaModal = (props) => {
  const { loggedInStudent, logout } = useContext(AuthContext);
  const API_URL_STUDENT = "http://localhost:8080/PU-war/webresources/student";
  const [socialMedia, setSocialMedia] = useState(props.socialMedia);
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);

  useEffect(() => {
    setSocialMedia(props.socialMedia);
  }, [props.socialMedia]);

  const getStudentAPI = async (studentId) => {
    const response = await fetch(`${API_URL_STUDENT}/${studentId}`);
    const data = await response.json();
    //setCurrentStudent(data);
  };

  // API to update student social media links (working for now)
  function updateStudentAPI(studentId, data) {
    console.log(data);
    console.log(data.socialMedia);
    props.onSocialMediaChange(socialMedia);
    return fetch(`${API_URL_STUDENT}/${studentId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  }

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
                <InputGroup>
                  <Form.Control
                    placeholder={`Social Media Link ${idx + 1}`}
                    value={field}
                    onChange={(e) => handleChange(idx, e)}
                  />
                  <Button variant="danger" onClick={() => handleRemove(idx)}>
                    <FontAwesomeIcon icon={faTrash} />
                  </Button>
                </InputGroup>
              </div>
            ))}
          {socialMedia.length == 0 && (
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
          <div className="d-grid gap-2">
            <Button
              variant="success"
              disabled={saveButtonDisabled}
              onClick={() =>
                updateStudentAPI(loggedInStudent.studentId, {
                  ...loggedInStudent,
                  socialMedia: socialMedia,
                })
              }
            >
              Save Changes
            </Button>
          </div>
        </div>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="outline-danger" onClick={props.onHide}>
          Close
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default SocialMediaModal;

/*
<div>
          {socialMedia.length > 0 &&
            socialMedia.map((field, idx) => (
              <div style={{ paddingBottom: "5%" }} key={idx}>
                <InputGroup>
                  <Form.Control
                    placeholder={`Social Media Link ${idx + 1}`}
                    value={field}
                    onChange={(e) => handleChange(idx, e)}
                  />
                  <Button variant="danger" onClick={() => handleRemove(idx)}>
                    <FontAwesomeIcon icon={faTrash} />
                  </Button>
                </InputGroup>
              </div>
            ))}
          {socialMedia.length == 0 && (
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
          <div className="d-grid gap-2">
            <Button
              variant="success"
              disabled={saveButtonDisabled}
              onClick={() =>
                updateStudentAPI(loggedInStudent.studentId, {
                  ...loggedInStudent,
                  socialMedia: socialMedia,
                })
              }
            >
              Save Changes
            </Button>
          </div>
        </div>
*/
