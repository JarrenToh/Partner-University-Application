import React, { useContext, useState } from 'react';
import moment from 'moment';
import {
  Alert,
  Button,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Form,
  FormGroup,
  Input,
  Label,
} from 'reactstrap';
import { AuthContext } from '../login/AuthContext';
import { Enquiry } from './Enquiry';
import './ContactForm.css';

const ContactForm = () => {
  const { loggedInStudent } = useContext(AuthContext);
  const [currentStudent, setCurrentStudent] = useState({ ...loggedInStudent });
  const [formStatus, setFormStatus] = useState('Send');
  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');

  const handleSubmit = async (event) => {
    event.preventDefault();
    setFormStatus('Submitting...');

    const student_id = loggedInStudent.studentId;
    const name = loggedInStudent.firstName;
    console.log(name);

    const url = `http://localhost:8080/PU-war/webresources/student/enquiries?studentId=${student_id}`;
    const headers = {
      'Content-Type': 'application/json',
    };

    const title = document.getElementById('title').value;
    const content = document.getElementById('content').value;
    const enquirydate = moment().format();

    const body = JSON.stringify({
      title: title,
      content: content,
      student_id: student_id,
      enquirydate: enquirydate,
    });

    console.log(body);

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: body,
      });

      if (response.ok) {
        setFormStatus('Send');
        setAlertType('success');
        setAlertMessage('You have posted the enquiry successfully!');
        setAlertVisible(true);
        setResponse(await response.json());
        setError(null);
        document.getElementById('title').value = '';
        document.getElementById('content').value = '';
      } else {
        setFormStatus('Send');
        setResponse(null);
        setError('Error occurred while submitting the form');
      }
    } catch (error) {
      setFormStatus('Send');
      setResponse(null);
      setError(error.message);
    }
  };

  if (!loggedInStudent) {
    return <div>Not Logged in</div>;
  }

  return (
    <div className="form-container"
  style={{
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
    minHeight: '100vh',
    overflow: 'hidden',
  }}
>

  <div
    className="background-image"
    style={{
      backgroundImage: `url('https://sublimelistings.com/wp-content/uploads/2016/11/background-contact-form-02.jpg')`,
      backgroundSize: 'cover',
      minHeight: '100vh',
      backgroundPosition: 'left',
      width: '90%',
      height: '100%',
      marginRight : '1%',
    }}
  />

  <Card
    className="form"
    style={{
      ...Enquiry.formStyle,
      backgroundColor: 'rgba(255,255,255,0.7)',
      boxShadow: '0 0 10px rgba(0,0,0,0.3)',
      maxWidth: '1000px',
      width: '40%',
      borderRadius: '3%',
      marginRight : '1%',
    }}
  >
        <CardHeader style={{backgroundColor : 'blue', color : 'white'}}>Contact Us</CardHeader>
        <CardBody>
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Label for="title">Title</Label>
              <Input type="text" id="title" name="title" placeholder="Title" required />
            </FormGroup>
            <FormGroup>
              <Label for="content">Message</Label>
              <Input
                type="textarea"
                id="content"
                name="content"
                placeholder="Message"
                rows="5"
                required
              />
            </FormGroup>
            <Button className='btnColor' type="submit" disabled={formStatus === 'Submitting...'}>
              {formStatus}
            </Button>
          </Form>
        </CardBody>
      </Card>
      {alertVisible && (
        <Alert color={alertType} toggle={() => setAlertVisible(false)}>
          {alertMessage}
        </Alert>
      )}
    </div>
  );
};

export default ContactForm;
