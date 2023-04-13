import React, { useState, useEffect, useContext } from 'react';
import { Table } from 'reactstrap';
import moment from 'moment'; // import moment.js
import { AuthContext } from '../../AuthContext';
import './ContactForm.css';
import NotLoggedIn from '../components/NotLoggedInPage';
import NavbarComp from '../../student/components/NavbarComp';

const ViewEnquiries = () => {
  const [enquiries, setEnquiries] = useState([]);
  const { loggedInStudent } = useContext(AuthContext);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (!loggedInStudent) {
      return;
    }

    // fetch enquiries for the current student using their ID
    const student_id = loggedInStudent.studentId;
    const url = `http://localhost:8080/PU-war/webresources/student/enquiries?studentId=${student_id}`;
    fetch(url)
      .then(response => response.json())
      .then(data => setEnquiries(data))
      .catch(error => console.error(error));
  }, [loggedInStudent]);

  if (!loggedInStudent) {
    return NotLoggedIn();
  }

  return (
    <div className="wrapper" >
    <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
      <h1 className="headerEnquiries m-auto">Enquiries</h1>
      <br/>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th style={{fontSize : '21px', padding: '20px'}}>Ticket Number</th>
            <th style={{fontSize : '21px', padding: '20px'}}>Title</th>
            <th style={{fontSize : '21px', padding: '20px'}}>Message</th>
            <th style={{fontSize : '21px', padding: '20px'}}>Enquiry Date</th>
            <th style={{fontSize : '21px', padding: '20px'}}>Response</th>
          </tr>
        </thead>
        <tbody>
          {enquiries.map((enquiry, index) => (
            <tr key={enquiry.enquiryId}>
              <td>{enquiry.enquiryId}</td>
              <td>{enquiry.title}</td>
              <td>{enquiry.content}</td>
              <td>{moment(enquiry.enquiryDate).format('MMMM Do YYYY, h:mm:ss a')}</td> {/* convert the date using moment.js */}
              <td>{enquiry.response || 'Please wait 2-3 working days from the Enquiry Date'}</td>
            </tr>
          ))}
        </tbody>
      </Table>
      <style>{`
        table td {
          padding: 8px;
        }
        thead tr {
          background-color: #f2f2f2;
        }
        table {
          border-collapse: collapse;
          border-radius: 8px;
          font-size: 14px;
          font-family: Arial, sans-serif;
        }
      `}</style>
    </div>
  );
};

export default ViewEnquiries;
