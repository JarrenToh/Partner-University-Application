import React, { Fragment} from 'react';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from '../homepage/search.svg';
import { AuthProvider, useAuth, AuthContext } from '../../../src/AuthContext';
import NavbarComp from '../../student/components/NavbarComp';
import NotLoggedIn from '../../student/components/NotLoggedInPage';
import { Helmet } from "react-helmet";

import {
  Table,
  CardBody,
  Card,
  Button,
  Modal, 
  ModalHeader, 
  ModalBody, 
  ModalFooter,
  Alert
} from 'reactstrap';
import { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import ReactPaginate from 'react-paginate'

const API_URL = 'http://localhost:8080/PU-war/webresources/forumTopics/student';

library.add(far, fas, faPlus);

export default function MyTopics() {
  const { loggedInStudent } = useContext(AuthContext);
  const [ studentId, setStudentId ] = useState(null);
  const [forumTopics, setForumTopics] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [pus, setPus] = useState([]);
  const [selectedPuId, setSelectedPuId] = useState(0);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [selectedDeleteTopicId, setSelectedDeleteTopicId] = useState(null);
  const [selectedDeleteTopicName, setSelectedDeleteTopicName] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState("");
  const [alertType, setAlertType] = useState('danger');

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  const pagesVisited = pageNumber * itemsPerPage;
  const pageCount = Math.ceil(forumTopics.length / itemsPerPage);

  useEffect(() => {
      if (loggedInStudent) {
        setStudentId(loggedInStudent.studentId);
      }
  }, [loggedInStudent]);
  
  useEffect(() => {
    console.log("selectedPuId in useEffect:", selectedPuId);
    const fetchData = async () => {
        try {
          let response;
          if (selectedPuId == 0) {
            console.log("hello");
            console.log(studentId);
            response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/student/${studentId}`);
          } else {
            console.log("hi");
            response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/pu/${selectedPuId}/student/${studentId}`);
          }
          setForumTopics(response.data);
          const responsePu = await axios.get(`http://localhost:8080/PU-war/webresources/pu`);
          setPus(responsePu.data);
        } catch (error) {
          console.error(error);
        }
      };
      fetchData();
  }, [selectedPuId, studentId]);

  if (!loggedInStudent) {
    return NotLoggedIn();
  }

  const searchForumTopic = async (searchQuery) => {
    const fetchData = async () => {
      try {
        let response;
        if (selectedPuId == 0) {
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query/student/${studentId}?topicName=${searchQuery}`);
        } else {
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query/student/${studentId}/pu/${selectedPuId}?topicName=${searchQuery}`);
        }
        setForumTopics(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }

  const handleSortByChange = (event) => {
    console.log("selectedPuId before update:", selectedPuId);
    console.log("event target value:", event.target.value);
    setSelectedPuId(event.target.value);
    console.log("selectedPuId after update:", selectedPuId);
    setSearchQuery("");
  };

  const handleDeleteButtonClick = (topic) => {
    setSelectedDeleteTopicId(topic.topicId);
    setSelectedDeleteTopicName(topic.topicName);
    setShowDeleteConfirmation(true);

  };

  const toggleModal = () => {
    setShowDeleteConfirmation(!showDeleteConfirmation);
  }

  const handleDeleteConfirmation = () => {
    // Perform the deletion here
    axios.delete(`http://localhost:8080/PU-war/webresources/forumTopics/${selectedDeleteTopicId}`)
    .then(response => {
      // Handle successful response here
      console.log('Delete successful');
      setAlertType('success');
      setAlertMessage('The topic was deleted successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        window.location.href = window.location.href;
      }, 1000);
    })
    .catch(error => {
      // Handle error response here
      console.error('Delete failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error deleting the topic. Please try again.');
      setAlertVisible(true);
    });
    setShowDeleteConfirmation(false);
  };

  function getTimeDifference(timeOfCreation) {
    const now = new Date();
    const diff = (now.getTime() - new Date(timeOfCreation).getTime()) / 1000;

    if (diff < 60) {
        return `${Math.floor(diff)} seconds ago`;
    } else if (diff < 3600) {
        const minutes = Math.floor(diff / 60);
        const seconds = Math.floor(diff % 60);
        return `${minutes}min ${seconds} seconds ago`;
    } else if (diff < 86400) {
        const hours = Math.floor(diff / 3600);
        const minutes = Math.floor((diff % 3600) / 60);
        return `${hours} hours ${minutes} minutes ago`;
    } else {
        const days = Math.floor(diff / 86400);
        const hours = Math.floor((diff % 86400) / 3600);
        return `${days} days ${hours} hours ago`;
    }
  } 

  return (
    <div>
      <div>
        <Helmet>
          <title>My topics</title>
        </Helmet>
      </div>
      <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
    <Fragment>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "3px" }}>
          <div>
            <select value={selectedPuId} onChange={handleSortByChange}>
              <option value={0}>All</option>
              {pus.map(pu => (
                <option value={pu.puId} key={pu.puId}>{pu.name}</option>
              ))}
            </select>
          </div>
          <div style={{ display: "flex", justifyContent: "flex-end", alignItems: "center", marginRight: "2px" }}>
            <input
              placeholder="Search for Forum Topic"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              style={{borderRadius: "13px", padding: "3px", border: "0.2px solid grey", marginRight: "10px"}}
            />
            <img
              src={SearchIcon}
              alt="search"
              onClick={() => searchForumTopic(searchQuery)}
              style={{ width: "20px", height: "20px", cursor: "pointer", marginRight: "10px"}}
            />
          </div>
        </div>
      <Card className="card-box mb-5">
        <div className="card-header">
          <div className="card-header--title">
            <small>Forum</small>
            <b>My Forum Topics</b>
            <Modal isOpen={showDeleteConfirmation} toggle={toggleModal}>
              <ModalHeader toggle={toggleModal}>Confirm deletion</ModalHeader>
              <ModalBody>
                Are you sure you want to delete topic: {selectedDeleteTopicName}?
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={handleDeleteConfirmation}>Delete</Button>{' '}
                <Button color="secondary" onClick={toggleModal}>Cancel</Button>
              </ModalFooter>
            </Modal>
          </div>
        </div> 
        <CardBody className="p-0">
          <div className="table-responsive-md">
            <Table hover striped className="text-nowrap mb-0">
              <thead className="thead-light">
                <tr>
                  <th style={{ width: '40%' }}>Topics</th>
                  <th className="text-center">Partner University</th>
                  <th className="text-center">Number of Posts</th>
                  <th className="text-center">Actions</th>
                </tr>
              </thead>
              {forumTopics.length > 0 ? (
              <tbody>
                {forumTopics.slice(pagesVisited, pagesVisited + itemsPerPage).map((item) => (
                  <tr key={item.topicId}>
                    <td>
                      {item.isInappropriate ? (
                        <a className="font-weight-bold text-black">
                          {item.topicName}
                          <FontAwesomeIcon
                            icon={['fa', 'circle-exclamation']}
                            className="font-size-lg ml-2 text-danger"
                            title="This forum topic has been marked inappropriate by other users and will not be shown to others"
                          />
                        </a>
                      ) : (
                        <a className="font-weight-bold text-black">
                          {item.topicName}
                        </a>
                      )
                      }
                      <Link to={`/student/other-profile/${item.studentId}`} className="text-black-50 d-block blue-link" style={{textDecoration: 'none'}}>
                        Author: {item.studentFirstName} {item.studentLastName}
                      </Link>
                      {item.isEdited && (
                        <span className="edited-info">
                          Last Edited: {getTimeDifference(item.lastEdit)}
                        </span>
                      )}
                    </td>
                    <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                      <a className="font-weight-bold text-black">
                        {item.puName}
                      </a>
                    </td>
                        <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                            <a className="font-weight-bold text-black">
                                {item.forumPosts.length}
                            </a>
                        </td>
                        <td className="text-center">
                            <Button
                                tag={Link}
                                to={`/student/forum-topics/${item.topicId}/${encodeURIComponent(item.topicName)}`}
                                size="sm"
                                color="link"
                                className="text-primary"
                                title="View"
                            >
                                <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                            </Button>

                            <Button
                                tag={Link}
                                to={`/student/forum-topics/edit/${item.topicId}/${item.topicName}`}
                                size="sm"
                                color="link"
                                className="text-warning ml-2"
                                title="Edit"
                            >
                                <FontAwesomeIcon icon={['fa', 'edit']} className="font-size-lg edit" />
                            </Button>

                            <Button
                                onClick={() => handleDeleteButtonClick(item)}
                                size="sm"
                                color="link"
                                className="text-danger ml-2"
                                title="Delete"
                            >
                                <FontAwesomeIcon icon={['fa', 'trash']} className="font-size-lg delete" />
                            </Button>
                        </td>
                  </tr>
                ))}
              </tbody>
              ) : (
                <tbody>
                  <tr>
                    <td colSpan="4" className="text-center">
                      No topics yet
                    </td>
                  </tr>
                </tbody>
              )} 
            </Table>
          </div>
          <div className="divider" />
          <div className="divider" />
          <div className="p-3 d-flex justify-content-center">
            <ReactPaginate
              previousLabel={<FontAwesomeIcon icon={['fas', 'chevron-left']} />}
              nextLabel={<FontAwesomeIcon icon={['fas', 'chevron-right']} />}
              breakLabel={'...'}
              breakClassName={'page-item'}
              breakLinkClassName={'page-link'}
              pageCount={pageCount}
              marginPagesDisplayed={2}
              pageRangeDisplayed={5}
              onPageChange={({ selected }) => setPageNumber(selected)}
              containerClassName={'pagination pagination-primary'}
              activeClassName={'active'}
            />
          </div>
        </CardBody>
      </Card>
    </Fragment>
      {
        alertVisible && (
          <Alert color={alertType} toggle={() => setAlertVisible(false)}>
            {alertMessage}
          </Alert>
        )
      }
    </div >
  );
}