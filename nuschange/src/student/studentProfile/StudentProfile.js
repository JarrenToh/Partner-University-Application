import { createContext, useContext, useState, useEffect } from "react";
import { AuthContext } from '../login/AuthContext';

const StudentProfile = () => {
    const { loggedInStudent, logout } = useContext(AuthContext);
    if (!loggedInStudent) {
        return <div> Not Logged in</div>;
    }
    console.log(loggedInStudent);
    return (
        <div className="form" style={{ display: "flex", flexDirection: "column", alignItems: "center",  height: "100vh" }}>
            <h1>User Profile</h1>
            <p>First Name: {loggedInStudent.firstName}</p>
            <p>Last Name: {loggedInStudent.lastName}</p>
            <p>Email: {loggedInStudent.email}</p>
            <p>Phone Number: {loggedInStudent.phoneNumber}</p>
            <p>Faculty: {loggedInStudent.faculty}</p>
        </div>
    );
}

export default StudentProfile;
