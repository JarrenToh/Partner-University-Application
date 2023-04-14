import React, { useState, useEffect, useContext } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Header from "./components/dashboard/Header";
import Menu from "./components/dashboard/Menu";
import Footer from "./components/dashboard/Footer";

import API from "../util/API";
import apiPaths from "../util/apiPaths";
import { AuthContext } from "../AuthContext";

const Profile = () => {
    
    const navigate = useNavigate();
    const { usernameFromUrl } = useParams();
    const { loggedInAdmin } = useContext(AuthContext);

    const [name, setName] = useState("");
    const [username, setUsername] = useState("");
    const [currentPassword, setCurrentPassowrd] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [modalText, setModalText] = useState("");
    const [showUpdateSuccessModal, setShowUpdateSuccessModal] = useState(false);
    const [showCheckPasswordButton, setShowCheckPasswordButton] = useState(true);
    const [showPasswordEdit, setShowPasswordEdit] = useState(false);
    const [isCurrentPasswordReadOnly, setIsCurrentPasswordReadOnly] = useState(false);

    const [nameError, setNameError] = useState("");
    const [usernameError, setUsernameError] = useState("");
    const [currentPasswordError, setCurrentPasswordError] = useState("");
    const [passwordError, setPasswordError] = useState("");
    const [confirmPasswordError, setConfirmPasswordError] = useState("");

    const handleEditName = async () => {
        if (validateName()) {
            try {
                const updateString = name;

                const updatedProfile = {
                    updateString,
                };

                const apiPath = `${apiPaths.admin}/editAdminByName/${usernameFromUrl}`;
                await API.put(apiPath, updatedProfile);

                setModalText("Name");
                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleEditUsername = async () => {
        if (validateUsername()) {
            try {
                const updateString = username;

                const updatedProfile = {
                    updateString,
                };

                const apiPath = `${apiPaths.admin}/editAdminByUsername/${usernameFromUrl}`;
                await API.put(apiPath, updatedProfile);

                setModalText("Username");
                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCheckPassword = async () => {
        try {
            const apiPath = `${apiPaths.admin}/searchAdminByPassword/${usernameFromUrl}`;
            const response = await API.post(apiPath, { updateString: currentPassword });

            if (response.data.password === undefined) {
                setCurrentPasswordError("Password doesn't match with current user's password");
                setShowPasswordEdit(false);
                setShowCheckPasswordButton(true);
                setIsCurrentPasswordReadOnly(false);
            } else {
                setCurrentPasswordError("");
                setShowPasswordEdit(true);
                setShowCheckPasswordButton(false);
                setIsCurrentPasswordReadOnly(true);
            }

        } catch (error) {
            console.error(error);
        }
    };

    const handleEditPassword = async () => {
        if (await validatePassword()) {
            try {
                const updateString = password;

                const updatedProfile = {
                    updateString
                };

                const apiPath = `${apiPaths.admin}/editAdminByPassword/${usernameFromUrl}`;
                await API.put(apiPath, updatedProfile);

                setModalText("Password");
                setShowUpdateSuccessModal(true);
            } catch (error) {
                console.error(error);
            }
        }
    };

    const handleCancelUpdateSuccessModal = () => {
        setShowUpdateSuccessModal(false);
        console.log("username " + username);
        window.location.reload(`../profile/${username}`);
    };

    const validateName = () => {
        let isValid = true;
        if (name.trim() === "") {
            setNameError("Please enter a name");
            isValid = false;
        } else {
            setNameError("");
        }

        return isValid;
    };

    const validateUsername = () => {
        let isValid = true;
        if (username.trim() === "") {
            setUsernameError("Please enter a username");
            isValid = false;
        } else {
            setUsernameError("");
        }

        return isValid;
    };

    const validatePassword = async () => {
        let isValid = true;
        if (password.trim() === "") {
            setPasswordError("Please enter a password");
            isValid = false;
        } else {
            setPasswordError("");
        }
        if (confirmPassword.trim() === "") {
            setConfirmPasswordError("Please enter a confirm password");
            isValid = false;
        } else {
            setConfirmPasswordError("");
        }
        if (password.trim() !== "" && confirmPassword.trim() !== "") {
            if (password.trim() !== confirmPassword.trim()) {
                setConfirmPasswordError("Passwords do not match");
                isValid = false;
            } else {
                setConfirmPasswordError("");
            }
        }

        return isValid;
    }

    useEffect(() => {
        if (loggedInAdmin === null) return;

        const fetchData = async () => {
            try {
                const apiPath = `${apiPaths.admin}/searchAdminByUsername/${usernameFromUrl}`;
                const response = await API.get(apiPath);
                const data = response.data;

                const name = data.name;
                const username = data.username;

                setName(name);
                setUsername(username);
            } catch (error) {
                console.error(error);
            }
        };

        if (loggedInAdmin.username !== usernameFromUrl) {
            navigate(`/admin/${loggedInAdmin.userGroupEnum === "USER_SUPPORT" ? "userSupportAdmin" : "systemSupportAdmin"}/profile/${loggedInAdmin.username}`);
        } else {
            fetchData();
        }
    }, [loggedInAdmin, usernameFromUrl, navigate]);

    return (
        <div>
            <Header />
            <Menu />
            <div className="content-wrapper">
                <div className="card">
                    <div className="card card-primary">
                        <div className="card-header">
                            <h3 className="card-title">User Profile</h3>
                        </div>
                        <div className="card-body">
                            <div className="form-group">
                                <label htmlFor="inputName">Name</label>
                                <input type="text" id="inputName" className={`form-control ${nameError ? "is-invalid" : ""}`} placeholder="Input a name" onChange={(e) => setName(e.target.value)} value={name} />
                                {nameError && <div className="invalid-feedback">{nameError}</div>}
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary" onClick={handleEditName}>Update Name</button>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputUsername">Username</label>
                                <input id="inputUsername" className={`form-control ${usernameError ? "is-invalid" : ""}`} placeholder="Input a username" onChange={(e) => setUsername(e.target.value)} value={username} />
                                {usernameError && <div className="invalid-feedback">{usernameError}</div>}
                            </div>
                            <div className="form-group">
                                <button className="btn btn-primary" onClick={handleEditUsername}>Update Username</button>
                            </div>
                            <div className="form-group">
                                <label htmlFor="inputCurrentPassword">Current Password</label>
                                <input type="password" id="inputCurrentPassword" className={`form-control ${currentPasswordError ? "is-invalid" : ""}`} placeholder="Input a current password" onChange={(e) => setCurrentPassowrd(e.target.value)} value={currentPassword}  readOnly={isCurrentPasswordReadOnly}/>
                                {currentPasswordError && <div className="invalid-feedback">{currentPasswordError}</div>}
                            </div>
                            {showCheckPasswordButton && (
                                <div className="form-group">
                                    <button className="btn btn-primary" onClick={handleCheckPassword}>Check Password</button>
                                </div>
                            )}
                            {showPasswordEdit && (
                                <>
                                    <div className="form-group">
                                        <label htmlFor="inputPassword">Password</label>
                                        <input type="password" id="inputPassword" className={`form-control ${passwordError ? "is-invalid" : ""}`} placeholder="Input a password" onChange={(e) => setPassword(e.target.value)} value={password}/>
                                        {passwordError && <div className="invalid-feedback">{passwordError}</div>}
                                    </div>
                                    <div className="form-group">
                                        <label htmlFor="inputConfirmPassword">Confirm Password</label>
                                        <input type="password" id="inputConfirmPassword" className={`form-control ${confirmPasswordError ? "is-invalid" : ""}`} placeholder="Confirm password" onChange={(e) => setConfirmPassword(e.target.value)} value={confirmPassword} />
                                        {confirmPasswordError && <div className="invalid-feedback">{confirmPasswordError}</div>}
                                    </div>
                                    <div className="form-group">
                                        <button className="btn btn-primary" onClick={handleEditPassword}>Update Password</button>
                                    </div>
                                </>
                            )}
                            {showUpdateSuccessModal && (
                                <div className="modal fade show" id="modal-default" style={{ display: "block" }}>
                                    <div className="modal-dialog">
                                        <div className="modal-content">
                                            <div className="modal-header">
                                                <h4 className="modal-title">Successful Update of {modalText}</h4>
                                                <button
                                                    type="button"
                                                    className="close"
                                                    data-dismiss="modal"
                                                    aria-label="Close"
                                                    onClick={() => handleCancelUpdateSuccessModal()}>
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div className="modal-body">
                                                <p>You have successfully updated the {modalText.toLowerCase()}!</p>
                                            </div>
                                            <div className="modal-footer justify-content-between">
                                                <button
                                                    type="button"
                                                    className="btn btn-default"
                                                    onClick={() => handleCancelUpdateSuccessModal()}>
                                                    Close
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            </div>
            <Footer />
        </div>
    );
};

export default Profile;