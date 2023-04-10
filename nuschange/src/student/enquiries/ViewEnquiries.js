import React, { useState, useEffect, useContext } from 'react';
import { Table } from 'reactstrap';
import moment from 'moment'; // import moment.js
import { AuthContext } from '../login/AuthContext';

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
  

  return (
    <div>
      <h1>Enquiries</h1>
      <Table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Message</th>
            <th>Enquiry Date</th>
            <th>Response</th>
          </tr>
        </thead>
        <tbody>
          {enquiries.map(enquiry => (
            <tr key={enquiry.enquiryId}>
              <td>{enquiry.title}</td>
              <td>{enquiry.content}</td>
              <td>{moment(enquiry.enquirydate).format('MMMM Do YYYY, h:mm:ss a')}</td> {/* convert the date using moment.js */}
              <td>{enquiry.response || 'Please wait 2-3 working days from the Enquiry Date'}</td>
            </tr>
          ))}
        </tbody>
      </Table>
    </div>
  );
};

export default ViewEnquiries;
