import React, { Fragment} from 'react';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from "./login/AuthContext";

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from './homepage/search.svg';

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

const API_URL = 'http://localhost:8080/PU-war/webresources/forumTopics';

library.add(far, fas, faPlus);

export default function ForumTopics() {
  const { loggedInStudent } = useContext(AuthContext);
  const [studentId, setStudentId] = useState(0);
  const { puId } = useParams();
  const [pus, setPus] = useState([]);
  const [forumTopics, setForumTopics] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [selectedPuId, setSelectedPuId] = useState(puId);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [selectedDeleteTopicId, setSelectedDeleteTopicId] = useState(null);
  const [selectedDeleteTopicName, setSelectedDeleteTopicName] = useState(null);
  const [showReportConfirmation, setShowReportConfirmation] = useState(false);
  const [selectedReportTopicId, setSelectedReportTopicId] = useState(null);
  const [selectedReportTopicName, setSelectedReportTopicName] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');

  const [pageNumber, setPageNumber] = useState(0);
  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  //const [pageCount, setPageCount] = useState(Math.ceil(forumTopics.length / itemsPerPage));
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
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics`);
        } else {
          console.log("hi");
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/pu/${selectedPuId}`);
        }
        const filteredTopics = response.data.filter(topic => topic.isInappropriate === false);
        setForumTopics(filteredTopics);
        const responsePu = await axios.get(`http://localhost:8080/PU-war/webresources/pu`);
        setPus(responsePu.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, [selectedPuId]);


  if (!loggedInStudent) {
    return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
  }

  const searchForumTopic = async (searchQuery) => {
    const fetchData = async () => {
      try {
        let response;
        console.log(selectedPuId);
        if (selectedPuId == 0) {
          console.log("hi");
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query?topicName=${searchQuery}`);
        } else {
          console.log("hello");
          response = await axios.get(`http://localhost:8080/PU-war/webresources/forumTopics/query/pu/${selectedPuId}?topicName=${searchQuery}`);
        }
        const filteredTopics = response.data.filter(topic => topic.isInappropriate === false);
        setForumTopics(filteredTopics);
        console.log(response.data);
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

  const handleReportButtonClick = (topic) => {
    setSelectedReportTopicId(topic.topicId);
    setSelectedReportTopicName(topic.topicName);
    setShowReportConfirmation(true);
  }

  const toggleReportModal = () => {
    setShowReportConfirmation(!showReportConfirmation);
  }

  const handleReportConfirmation = () => {
    axios.put(`http://localhost:8080/PU-war/webresources/forumTopics/report/${selectedReportTopicId}`)
    .then(response => {
      // Handle successful response here
      console.log('Report successful');
      setAlertType('success');
      setAlertMessage('The topic was reported successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        window.location.href = window.location.href;
      }, 1000);
    })
    .catch(error => {
      // Handle error response here
      console.error('Report failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error reporting the topic. Please try again.');
      setAlertVisible(true);
    });
    setShowReportConfirmation(false);
  }

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

  function calculatePostNumber(forumPosts) {
    let count = 0;
    forumPosts.forEach((forumPost) => {
      if (!forumPost.isInappropriate) {
        count = count + 1;
      }
    })
    return count;
  }

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

  // function calculateTopics(forumTopics) {
  //   let count = 0;   
  //   forumTopics.forEach((topic) => {
  //     if (!topic.isInappropriate) {
  //       count = count + 1;
  //     }
  //   })
  //   return count;
  // }

  return (
    <div>
    <Fragment>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: "3px"}}>
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
              style={{ borderRadius: "15px", padding: "3px", border: "0.5px solid grey", marginRight: "10px" }}
            />
            <img
              src={SearchIcon}
              alt="search"
              onClick={() => searchForumTopic(searchQuery)}
              style={{ width: "20px", height: "20px", cursor: "pointer" }}
            />
          </div>
        </div>
      <Card className="card-box mb-5">
        <div className="card-header">
          <div className="card-header--title">
            <small>Forum</small>
            <b>Forum Topics</b>
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
            <Modal isOpen={showReportConfirmation} toggle={toggleReportModal}>
              <ModalHeader toggle={toggleReportModal}>Confirm report</ModalHeader>
              <ModalBody>
                Are you sure you want to report topic: {selectedReportTopicName}?
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={handleReportConfirmation}>Report</Button>{' '}
                <Button color="secondary" onClick={toggleReportModal}>Cancel</Button>
              </ModalFooter>
            </Modal>
          </div>
          <div className="card-header--actions">
            <Button
              tag={Link}
              to={`/new-topic`}
              color="outline-primary"
              title="View details"
              className="mr-2">
              <FontAwesomeIcon icon={['fa', 'plus']} className="mr-1" />
              Create new Topic
            </Button>
          </div>
          <div className="card-header--actions">
            <Button
              tag={Link}
              to={`/my-topics`}
              color="outline-primary"
              title="View My Topics"
              className="ml-2">
              <FontAwesomeIcon icon={['fa', 'eye']} className="mr-1" />
              View My Topics
            </Button>
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
                        <a className="font-weight-bold text-black">
                          {item.topicName}
                        </a>
                        {studentId === item.studentId ? 
                        (<Link to={`/profile`} className="text-black-50 d-block blue-link" style={{ textDecoration: 'none' }}>
                          Author: {item.studentFirstName} {item.studentLastName}
                        </Link>) : (<Link to={`/other-profile/${item.studentId}`} className="text-black-50 d-block blue-link" style={{ textDecoration: 'none' }}>
                          Author: {item.studentFirstName} {item.studentLastName}
                        </Link>)}
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
                          {calculatePostNumber(item.forumPosts)}
                        </a>
                      </td>
                      {item.studentId == studentId ? (
                        <td className="text-center">
                          <Button
                            tag={Link}
                            to={`/forum-topics/${item.topicId}/${encodeURIComponent(item.topicName)}`}
                            size="sm"
                            color="link"
                            className="text-primary"
                            title="View"
                          >
                            <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                          </Button>
              
                          <Button
                            tag={Link}
                            to={`/forum-topics/edit/${item.topicId}/${item.topicName}`}
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
                      ) :
                        <td className="text-center">
                          <Button
                            tag={Link}
                            to={`/forum-topics/${item.topicId}/${encodeURIComponent(item.topicName)}`}
                            size="sm"
                            color="link"
                            className="text-primary"
                            title="View"
                          >
                            <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                          </Button>
              
                          <Button
                            onClick={() => handleReportButtonClick(item)}
                            size="sm"
                            color="link"
                            className="text-secondary ml-2"
                            title="Report"
                          >
                            <FontAwesomeIcon icon={['fa', 'flag']} className="font-size-lg flag" />
                          </Button>
                        </td>
                      }
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
      {alertVisible && (
        <Alert color={alertType} toggle={() => setAlertVisible(false)}>
          {alertMessage}
        </Alert>
      )}
    </div>
  );
}
