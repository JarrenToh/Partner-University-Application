import React, { Fragment, startTransition } from 'react';
import { useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { library } from '@fortawesome/fontawesome-svg-core';
import { far } from '@fortawesome/free-regular-svg-icons';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import './styles.css';
import SearchIcon from './homepage/search.svg';
import { Link } from 'react-router-dom';
import NavbarComp from './components/NavbarComp';
import { AuthProvider, useAuth, AuthContext } from '../../src/AuthContext';

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

const API_URL = 'http://localhost:8080/PU-war/webresources/forumPosts';

library.add(far, fas, faPlus);

export default function MyPosts() {
  const { loggedInStudent } = useContext(AuthContext);
  const [ studentId, setStudentId ] = useState(null);
  const { id, topicName } = useParams();
  const [forumPosts, setForumPosts] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [pageNumber, setPageNumber] = useState(0);
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [selectedDeletePostId, setSelectedDeletePostId] = useState(null);
  const [selectedDeletePostName, setSelectedDeletePostName] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');

  const itemsPerPage = 5; // Change this value to the number of items you want to display per page
  const pagesVisited = pageNumber * itemsPerPage;
  const pageCount = Math.ceil(forumPosts.length / itemsPerPage);

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  useEffect(() => {
        if (loggedInStudent) {
        setStudentId(loggedInStudent.studentId);
        }
    }, [loggedInStudent]);

  useEffect(() => {
    const fetchData = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/topic/${id}/student/${studentId}`);
            setForumPosts(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
  }, [studentId]);

  if (!loggedInStudent) {
    return <h1 style={{ textAlign: 'center', color: 'red', margin: '0 auto', width: '50%', fontWeight: 'bold', fontSize: '2em'}}>You are not logged in.</h1>;
  }

  const searchForumPost = async (searchQuery) => {
    const fetchData = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/query/topic/${id}/student/${studentId}?postTitle=${searchQuery}`);
            setForumPosts(response.data);
        } catch (error) {
            console.error(error);
        }
    };
    fetchData();
  }

  const handleDeleteButtonClick = (post) => {
    setSelectedDeletePostId(post.postId);
    setSelectedDeletePostName(post.title);
    setShowDeleteConfirmation(true);

  };

  const toggleModal = () => {
    setShowDeleteConfirmation(!showDeleteConfirmation);
  }

  const handleDeleteConfirmation = () => {
    // Perform the deletion here
    axios.delete(`http://localhost:8080/PU-war/webresources/forumPosts/${selectedDeletePostId}`)
    .then(response => {
      // Handle successful response here
      console.log('Delete successful');
      setAlertType('success');
      setAlertMessage('The post was deleted successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        window.location.href = window.location.href;
      }, 1000);
    })
    .catch(error => {
      // Handle error response here
      console.error('Delete failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error deleting the post. Please try again.');
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
    <Fragment>
      <div>
      <div style={{ display: "flex", justifyContent: "flex-end", alignItems: "center", marginRight: "2px", marginBottom: "3px" }}>
          <input
            placeholder="Search for Forum Post"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            style={{ borderRadius: "15px", padding: "3px", border: "0.5px solid grey", marginRight: "10px" }}
          />
          <img
            src={SearchIcon}
            alt="search"
            onClick={() => searchForumPost(searchQuery)}
            style={{ width: "20px", height: "20px", cursor: "pointer" }}
          />
        </div>
      </div>
        <Card className="card-box mb-5">
            <div className="card-header">
                <div className="card-header--title">
                    <small>Forum Topic: {topicName}</small>
                    <b>My Forum Posts</b>
                      <Modal isOpen={showDeleteConfirmation} toggle={toggleModal}>
                          <ModalHeader toggle={toggleModal}>Confirm deletion</ModalHeader>
                          <ModalBody>
                              Are you sure you want to delete post: {selectedDeletePostName}?
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
                                <th style={{ width: '40%' }}>Posts</th>
                                <th className="text-center">Posted</th>
                                <th className="text-center">Actions</th>
                            </tr>
                        </thead>
                        {forumPosts.length > 0 ? (
                            <tbody>
                                {forumPosts.slice(pagesVisited, pagesVisited + itemsPerPage).map((item) => (
                                    <tr key={item.postId}>
                                        <td>
                                            {item.isInappropriate ? (
                                                <a className="font-weight-bold text-black">
                                                    {item.title}
                                                    <FontAwesomeIcon
                                                        icon={['fa', 'circle-exclamation']}
                                                        className="font-size-lg ml-2 text-danger"
                                                        title="This forum post has been marked inappropriate by other users and will not be shown to others"
                                                    />
                                                </a>
                                            ) : (
                                                <a className="font-weight-bold text-black">
                                                    {item.title}
                                                </a>
                                            )}
                                            <Link to={`/other-profile/${item.studentId}`} className="text-black-50 d-block blue-link" style={{ textDecoration: 'none' }}>
                                                Author: {item.studentFirstName} {item.studentLastName}
                                            </Link>
                                            {item.isEdited && (
                                                <span className="edited-info">
                                                    Last Edited: {getTimeDifference(item.lastEdit)}
                                                </span>
                                            )}
                                        </td>
                                        <td style={{ textAlign: 'center', verticalAlign: 'middle' }}>
                                            <div>
                                                <a onClick={(e) => e.preventDefault()} className="font-weight-bold text-black">
                                                    {getTimeDifference(item.timeOfCreation)}
                                                </a>
                                            </div>
                                        </td>
                                        <td className="text-center">
                                            <Button
                                                tag={Link}
                                                to={`/view-post/${item.postId}/${topicName}/${id}`}
                                                size="sm"
                                                color="link"
                                                className="text-primary"
                                                title="View"
                                            >
                                                <FontAwesomeIcon icon={['fa', 'eye']} className="font-size-lg eye" />
                                            </Button>

                                            <Button
                                                tag={Link}
                                                to={`/forum-posts/edit/${id}/${item.postId}/${item.title}/${encodeURIComponent(item.message)}/${encodeURIComponent(topicName)}/${0}`}
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
                                    <td className="text-center" colSpan="3">
                                        No posts yet
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
