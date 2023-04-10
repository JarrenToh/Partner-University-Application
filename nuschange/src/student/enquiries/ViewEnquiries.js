import React, { useState, useEffect, useContext } from 'react';
import { Table } from 'reactstrap';
import moment from 'moment'; // import moment.js
import { AuthContext } from '../login/AuthContext';
import './ContactForm.css';

const ViewEnquiries = () => {
  const [enquiries, setEnquiries] = useState([]);
  const { loggedInStudent } = useContext(AuthContext);

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
    return <div>Not Logged in</div>;
  }

  return (
    <div>
      <h1>Enquiries</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th style={{fontSize : '22px', padding: '20px'}}>No.</th>
            <th style={{fontSize : '22px', padding: '20px'}}>Title</th>
            <th style={{fontSize : '22px', padding: '20px'}}>Message</th>
            <th style={{fontSize : '22px', padding: '20px'}}>Enquiry Date</th>
            <th style={{fontSize : '22px', padding: '20px'}}>Response</th>
          </tr>
        </thead>
        <tbody>
          {enquiries.map((enquiry, index) => (
            <tr key={enquiry.enquiryId}>
              <td>{index + 1}</td>
              <td>{enquiry.title}</td>
              <td>{enquiry.content}</td>
              <td>{moment(enquiry.enquirydate).format('MMMM Do YYYY, h:mm:ss a')}</td> {/* convert the date using moment.js */}
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
