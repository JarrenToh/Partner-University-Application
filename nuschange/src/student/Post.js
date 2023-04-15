import { React, Fragment } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComments } from '@fortawesome/free-solid-svg-icons'
import { AuthContext } from '../../src/AuthContext';
import CommentComp from './CommentComp';
import NavbarComp from '../student/components/NavbarComp';
import NotLoggedIn from './components/NotLoggedInPage';

import {
  Form,
  Col,
  Input,
  Button,
  Row,
  Modal, 
  ModalHeader, 
  ModalBody, 
  ModalFooter,
  Alert
} from 'reactstrap';
import './forum.css';
import SearchIcon from './homepage/search.svg';

export default function ViewPost() {
  const { loggedInStudent } = useContext(AuthContext);
  const [studentId, setStudentId] = useState(null);
  const { postId, topicId, topicName } = useParams();
  const [forumPost, setForumPost] = useState({});
  const [forumComments, setForumComments] = useState([]);
  const [showComments, setShowComments] = useState(false);
  const [commentMessage, setCommentMessage] = useState("");
  const [replyMessages, setReplyMessages] = useState({});
  const [saveButtonDisabled, setSaveButtonDisabled] = useState(true);
  const [replyButtonsDisabled, setReplyButtonsDisabled] = useState({});
  const [showPostDeleteConfirmation, setShowPostDeleteConfirmation] = useState(false);
  const [selectedDeletePostId, setSelectedDeletePostId] = useState(null);
  const [selectedDeletePostName, setSelectedDeletePostName] = useState(null);
  const [showCommentDeleteConfirmation, setShowCommentDeleteConfirmation] = useState(false);
  const [selectedDeleteCommentId, setSelectedDeleteCommentId] = useState(null);
  const [selectedDeleteCommentMessage, setSelectedDeleteCommentMessage] = useState(null);
  const [alertVisible, setAlertVisible] = useState(false);
  const [alertMessage, setAlertMessage] = useState('');
  const [alertType, setAlertType] = useState('danger');
  const [showReportPostConfirmation, setShowReportPostConfirmation] = useState(false);
  const [selectedReportPostId, setSelectedReportPostId] = useState(null);
  const [selectedReportPostName, setSelectedReportPostName] = useState(null);
  const [showReportCommentConfirmation, setShowReportCommentConfirmation] = useState(false);
  const [selectedReportCommentId, setSelectedReportCommentId] = useState(null);
  const [selectedReportCommentMessage, setSelectedReportCommentMessage] = useState(null);
  const [isInitialReplyButtonsDisabledInitialized, setIsInitialReplyButtonsDisabledInitialized] = useState(false);
  const [isInitialCommentsLikeInitialized, setIsInitialCommentsLikeInitialized] = useState(false);
  const [postLikedByUser, setPostLikedByUser] = useState(false);
  const [postDislikedByUser, setPostDislikedByUser] = useState(false);
  const [commentLikedByUser, setCommentLikedByUser] = useState({});
  const [commentDislikedByUser, setCommentDislikedByUser] = useState({});
  const [searchQuery, setSearchQuery] = useState("");

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    if (loggedInStudent) {
      setStudentId(loggedInStudent.studentId);
    }
  }, [loggedInStudent]);

  useEffect(() => {
    const fetchData = async () => {
      try {
          const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/${postId}`);
          setForumPost(response.data);
         
          const isLikedByUser = response.data.likedStudents.includes(studentId);
          console.log("Liked by user: " + isLikedByUser);
          const isDislikedByUser = response.data.dislikedStudents.includes(studentId);
          console.log("Disliked by user: " + isDislikedByUser);
      
          if (isLikedByUser) {
            setPostLikedByUser(true);
            setPostDislikedByUser(false);
          } else if (isDislikedByUser) {
            setPostDislikedByUser(true);
            setPostLikedByUser(false);
          }
        if (searchQuery == "") {
          setForumComments(response.data.forumComments);

          const initialCommentsLikeInitialized = {};
          const initialCommentsDislikeInitialized = {};
          response.data.forumComments.forEach(comment => {
            if (comment.likedStudents.includes(studentId)) {
              initialCommentsLikeInitialized[comment.commentId] = true;
            } else if (comment.dislikedStudents.includes(studentId)) {
              initialCommentsDislikeInitialized[comment.commentId] = true;
            }
          });
          setCommentLikedByUser(initialCommentsLikeInitialized);
          setCommentDislikedByUser(initialCommentsDislikeInitialized);
        } else {
          searchForumComment(searchQuery);
        }
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }, [forumComments, forumPost, studentId]);
  
  useEffect(() => {
    if (!isInitialReplyButtonsDisabledInitialized && forumComments.length > 0) {
      const initialReplyButtonsDisabled = {};
      forumComments.forEach(comment => {
        initialReplyButtonsDisabled[comment.commentId] = true;
      });
      setReplyButtonsDisabled(initialReplyButtonsDisabled);
      setIsInitialReplyButtonsDisabledInitialized(true);
    }

  }, [forumComments, isInitialReplyButtonsDisabledInitialized, isInitialCommentsLikeInitialized]);

  const searchForumComment = async (searchQuery) => {
    const fetchData = async () => {
      try {
        let response;
        response = await axios.get(`http://localhost:8080/PU-war/webresources/forumComments/query/${postId}/?searchQuery=${searchQuery}`);
        setForumComments(response.data);

         const initialCommentsLikeInitialized = {};
          const initialCommentsDislikeInitialized = {};
          response.data.forumComments.forEach(comment => {
            if (comment.likedStudents.includes(studentId)) {
              initialCommentsLikeInitialized[comment.commentId] = true;
            } else if (comment.dislikedStudents.includes(studentId)) {
              initialCommentsDislikeInitialized[comment.commentId] = true;
            }
          });
          setCommentLikedByUser(initialCommentsLikeInitialized);
          setCommentDislikedByUser(initialCommentsDislikeInitialized);
      } catch (error) {
        console.error(error);
      }
    };
    fetchData();
  }

  const handleCommentLike = async(commentId) => {
    if (commentLikedByUser[commentId]) {
      // The user already liked the comment, so we need to unlike it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumComments/unlike/${commentId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Unlike successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Unlike failed: ', error);
      });
      setCommentLikedByUser((prevLikedComments) => {
        const newLikedComments = { ...prevLikedComments };
        delete newLikedComments[commentId];
        return newLikedComments;
      });
    } else {
      // The user didn't like the comment yet, so we need to like it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumComments/like/${commentId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Like successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Like failed: ', error);
      });
      setCommentLikedByUser((prevLikedComments) => {
        return { ...prevLikedComments, [commentId]: true };
      });
      // Check if the user already disliked the comment, and remove the dislike if necessary
      if (commentDislikedByUser[commentId]) {
        setCommentDislikedByUser((prevDislikedComments) => {
          const newDislikedComments = { ...prevDislikedComments };
          delete newDislikedComments[commentId];
          return newDislikedComments;
        });
      }
    }
  };

  const handleCommentDislike = async(commentId) => {
    if (commentDislikedByUser[commentId]) {
      // The user already disliked the comment, so we need to undislike it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumComments/undislike/${commentId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Undislike successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Undislike failed: ', error);
      });
      setCommentDislikedByUser((prevDislikedComments) => {
        const newDislikedComments = { ...prevDislikedComments };
        delete newDislikedComments[commentId];
        return newDislikedComments;
      });
    } else {
      // The user didn't dislike the comment yet, so we need to dislike it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumComments/dislike/${commentId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Dislike successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Dislike failed: ', error);
      });
      setCommentDislikedByUser((prevDislikedComments) => {
        return { ...prevDislikedComments, [commentId]: true };
      }); 
      // Check if the user already liked the comment, and remove the like if necessary
      if (commentLikedByUser[commentId]) {
        setCommentLikedByUser((prevLikedComments) => {
          const newLikedComments = { ...prevLikedComments };
          delete newLikedComments[commentId];
          return newLikedComments;
        });
      }
    }
  };

  const handlePostLike = async() => {
    if (postLikedByUser) {
      // The user already liked the post, so we need to unlike it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/unlike/${postId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Unlike successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Unlike failed: ', error);
      });
      setPostLikedByUser(false);
    } else {
      // The user didn't like the post yet, so we need to like it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/like/${postId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Like successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Like failed: ', error);
      });
      setPostLikedByUser(true);
      if (postDislikedByUser) {
        setPostDislikedByUser(false);
      }
    }
  };
  
  const handlePostDislike = async() => {
    if (postDislikedByUser) {
      // The user already disliked the post, so we need to undislike it
      await axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/undislike/${postId}/${studentId}`)
      .then(response => {
        console.log('Undislike successful');
      })
      .catch(error => {
        console.error('Undislike failed: ', error);
      });
      setPostDislikedByUser(false);
    } else {
      // The user didn't dislike the post yet, so we need to dislike it
      axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/dislike/${postId}/${studentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Dislike successful');
      })
      .catch(error => {
        // Handle error response here
        console.error('Dislike failed: ', error);
      });
      setPostDislikedByUser(true);
      if (postLikedByUser) {
        setPostLikedByUser(false);
      }
    }
  };

  if (!loggedInStudent) {
    return NotLoggedIn();
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

  const handleReportPostButtonClick = (post) => {
    setSelectedReportPostId(post.postId);
    setSelectedReportPostName(post.title);
    setShowReportPostConfirmation(true);
  }

  const togglePostReportModal = () => {
    setShowReportPostConfirmation(!showReportPostConfirmation);
  }

  const handleReportPostConfirmation = () => {
    axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/report/${selectedReportPostId}`)
    .then(response => {
      // Handle successful response here
      console.log('Report successful');
      setAlertType('success');
      setAlertMessage('The post was reported successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        navigate(`/student/forum-topics/${topicId}/${topicName}`)
      }, 1000);
    })
    .catch(error => {
      // Handle error response here
      console.error('Report failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error reporting the post. Please try again.');
      setAlertVisible(true);
    });
    setShowReportPostConfirmation(false);
  }

  const handleReportCommentButtonClick = (comment) => {
    setSelectedReportCommentId(comment.commentId);
    setSelectedReportCommentMessage(comment.message);
    setShowReportCommentConfirmation(true);
  }

  const toggleCommentReportModal = () => {
    setShowReportCommentConfirmation(!showReportCommentConfirmation);
  }

  const handleReportCommentConfirmation = () => {
       axios.put(`http://localhost:8080/PU-war/webresources/forumComments/report/${selectedReportCommentId}`)
      .then(response => {
        // Handle successful response here
        console.log('Report successful');
        setAlertType('success');
        setAlertMessage('The comment was reported successfully!');
        setAlertVisible(true);
  
        setTimeout(() => {
         // window.location.href = window.location.href;
        }, 1000);
      })
      .catch(error => {
        // Handle error response here
        console.error('Report failed: ', error);
        setAlertType('danger');
        setAlertMessage('Error reporting the comment. Please try again.');
        setAlertVisible(true);
      });
      setShowReportCommentConfirmation(false);
  }

  const handlePostDeleteButtonClick = (post) => {
    setSelectedDeletePostId(post.postId);
    setSelectedDeletePostName(post.title);
    setShowPostDeleteConfirmation(true);

  };

  const togglePostDeleteModal = () => {
    setShowPostDeleteConfirmation(!showPostDeleteConfirmation);
  }

  const handleDeletePostConfirmation = () => {
    // Perform the deletion here
    axios.delete(`http://localhost:8080/PU-war/webresources/forumPosts/${selectedDeletePostId}`)
    .then(response => {
      // Handle successful response here
      console.log('Delete successful');
      setAlertType('success');
      setAlertMessage('The post was deleted successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        navigate(`/student/error`);
      }, 0);
    })
    .catch(error => {
      // Handle error response here
      console.error('Delete failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error deleting the post. Please try again.');
      setAlertVisible(true);
    });
    setShowPostDeleteConfirmation(false);
  };

  const handleCommentDeleteButtonClick = (comment) => {
    setSelectedDeleteCommentId(comment.commentId);
    setSelectedDeleteCommentMessage(comment.message);
    setShowCommentDeleteConfirmation(true);

  };

  const toggleCommentDeleteModal = () => {
    setShowCommentDeleteConfirmation(!showCommentDeleteConfirmation);
  }

  const handleDeleteCommentConfirmation = () => {
    // Perform the deletion here
    axios.delete(`http://localhost:8080/PU-war/webresources/forumComments/${selectedDeleteCommentId}`)
    .then(response => {
      // Handle successful response here
      console.log('Delete successful');
      setAlertType('success');
      setAlertMessage('The comment was deleted successfully!');
      setAlertVisible(true);

      setTimeout(() => {
        //window.location.href = window.location.href;
      }, 1000);
    })
    .catch(error => {
      // Handle error response here
      console.error('Delete failed: ', error);
      setAlertType('danger');
      setAlertMessage('Error deleting the comment. Please try again.');
      setAlertVisible(true);
    });
    setShowCommentDeleteConfirmation(false);
  };

  function toggleComments() {
    setShowComments(!showComments);
  }

  function toggleReplies(commentId) {
    axios.put(`http://localhost:8080/PU-war/webresources/forumComments/showReplies/${commentId}`)
      .then((response) => {
        console.log(response.data);
        setForumComments((prevComments) => {
          return prevComments.map((comment) => {
            if (comment.commentId === commentId) {
              return {
                ...comment,
                showReplies: !comment.showReplies
              };
            }
            return comment;
          });
        });
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const handleCommentMessageChange = (e) => {
    const newComment = (e.target.value);

    if (newComment.trim().length === 0) {
      setSaveButtonDisabled(true);
    } else {
      setSaveButtonDisabled(false);
    }

    setCommentMessage(newComment);

  }

  const handleAddComment = (e) => {
    e.preventDefault();

    const createdForumComment = {
      message: commentMessage
    };

    console.log(commentMessage);
    console.log(studentId)

    axios.post(`http://localhost:8080/PU-war/webresources/forumComments/user/forumPosts/${postId}/student/${studentId}`, createdForumComment)
      .then((response) => {
        console.log(response.data);
        setForumComments([...forumComments, response.data]);
        setIsInitialReplyButtonsDisabledInitialized(false);
        setIsInitialCommentsLikeInitialized(false);
        setCommentMessage("");
        setSaveButtonDisabled(true);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const handleReplyMessageChange = (commentId, message) => {
    setReplyMessages((prevReplyMessages) => ({
      ...prevReplyMessages,
      [commentId]: message
    }));

    if (message.trim().length === 0) {
      setReplyButtonsDisabled((prevReplyButton) => ({
        ...prevReplyButton,
        [commentId]: true
      }));
    } else {
      setReplyButtonsDisabled((prevReplyButton) => ({
        ...prevReplyButton,
        [commentId]: false
      }));
    }

  };

  const handleAddReply = (e, commentId) => {
    e.preventDefault();

    const createdForumReply = {
      //message: replyMessage
      message: replyMessages[commentId]
    };

    console.log(createdForumReply);
    console.log(studentId)

    axios.post(`http://localhost:8080/PU-war/webresources/forumComments/user/comment/${commentId}/student/${studentId}`, createdForumReply)
      .then((response) => {
        console.log(response.data);
        setIsInitialCommentsLikeInitialized(false);
        setReplyMessages(prevState => ({
          ...prevState,
          [commentId]: ""
        }));
    
        setReplyButtonsDisabled((prevReplyButton) => ({
          ...prevReplyButton,
          [commentId]: true
        }));
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const checkComments = (post) => {
    let count = 0;
    const comments = post.forumComments
    comments.map((comment) => {
      if (!comment.isInappropriate && !comment.isAReply) {
        count++
      }
    });
    return count > 0 ? true : false;
  }

  const checkReplies = (comment) => {
    let count = 0;
    const replies = comment.replies;
    replies.forEach((reply) => {
      if (!reply.isInappropriate) {
        count++;
      }
    });
    return count > 0 ? true : false;
  };

  function countComments(forumComments) {
    let count = 0;
    forumComments.forEach(comment => {
      if (!comment.isAReply && !comment.isInappropriate) {
        count++;
      }
    });
    return count;
  }

  function countReplies(forumComment) {
    let count = 0;
    forumComment.replies.forEach(reply => {
      if (!reply.isInappropriate) {
        count++;
      }
    });
    return count;
  }

  return (
    <>
     <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
      <div className="forum-card">
        <div className="forum-header">
          <div className="d-flex justify-content-between align-items-center" style={{flex: 1}}>
            <h4>{forumPost.title}</h4>
            <div className="text-muted">
              {studentId === forumPost.studentId ?
                (<Link to={`/student/profile`} style={{ textDecoration: "none" }}>
                  <h5 className="mb-1 author" style={{ marginLeft: "15px", fontSize: "14px" }}>
                    Posted by {forumPost.studentFirstName} {forumPost.studentLastName}
                  </h5>
                </Link>) :
                (<Link to={`/student/other-profile/${forumPost.studentId}`} style={{ textDecoration: "none" }}>
                  <h5 className="mb-1 author" style={{ marginLeft: "15px", fontSize: "14px" }}>
                    Posted by {forumPost.studentFirstName} {forumPost.studentLastName}
                  </h5>
                </Link>)}
              <small className="time" style={{ fontSize: "13px"}}>
                {getTimeDifference(forumPost.timeOfCreation)}
              </small>
              {forumPost.isEdited && (
                <small className="time" style={{ fontSize: "13px"}}>
                  Last Edited: {getTimeDifference(forumPost.lastEdit)}
                </small>
              )}
            </div>
          </div>
        </div>
        <div className="forum-body">
          <div className="forum-item">
            <div className="d-flex w-100 justify-content-between">
              <p style={{ fontSize: '15px' }}>{forumPost.message}</p>
              <Modal isOpen={showPostDeleteConfirmation} toggle={togglePostDeleteModal}>
              <ModalHeader toggle={togglePostDeleteModal}>Confirm post deletion</ModalHeader>
              <ModalBody>
                Are you sure you want to delete post: {selectedDeletePostName}?
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={handleDeletePostConfirmation}>Delete</Button>{' '}
                <Button color="secondary" onClick={togglePostDeleteModal}>Cancel</Button>
              </ModalFooter>
            </Modal>
            <Modal isOpen={showReportPostConfirmation} toggle={togglePostReportModal}>
              <ModalHeader toggle={togglePostReportModal}>Confirm report</ModalHeader>
              <ModalBody>
                Are you sure you want to report post: {selectedReportPostName}?
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={handleReportPostConfirmation}>Report</Button>{' '}
                <Button color="secondary" onClick={togglePostReportModal}>Cancel</Button>
              </ModalFooter>
            </Modal>
              <div className="ml-auto">
                <div className="button-container">
                  {studentId === forumPost.studentId ?
                    (
                      <>
                        <Button className="forumButton" tag={Link}
                          to={`/student/forum-posts/edit/${topicId}/${forumPost.postId}/${encodeURIComponent(forumPost.title)}/${encodeURIComponent(forumPost.message)}/${encodeURIComponent(topicName)}/${1}`} title="Edit">
                         <FontAwesomeIcon icon={['fa', 'edit']}  />
                        </Button>
                        <Button className="forumButton" onClick={() => handlePostDeleteButtonClick(forumPost)} title="Delete">
                         <FontAwesomeIcon icon={['fa', 'trash']} />
                        </Button>
                      </>
                    ) : (
                      <>
                        <Button className={`forumButton ${postLikedByUser ? 'liked' : ''}`} onClick={() => handlePostLike()}>
                          <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="font-size-sm" style={{marginRight: "5px"}} />
                          {forumPost.noOfLikes}
                        </Button>
                        <Button className={`forumButton ${postDislikedByUser ? 'disliked' : ''}`} onClick={() => handlePostDislike()}>
                          <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="font-size-sm" style={{marginRight: "5px"}}/>
                          {forumPost.noOfDislikes}
                        </Button>
                        <Button className="forumButton" onClick={() => handleReportPostButtonClick(forumPost)}>
                          <FontAwesomeIcon icon="fa-regular fa-flag"  className="font-size-sm" />
                        </Button>
                      </>
                     )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div >
      <div className="forum-card">
        <div className="forum-header">
          <h4>Comments</h4>
          <div>
            <input
              placeholder="Search for comments"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
            />
            <img
              src={SearchIcon}
              alt="search"
              onClick={() => searchForumComment(searchQuery)}
              style={{ width: "20px", height: "20px", cursor: "pointer" }}
            />
          </div>
          <Modal isOpen={showCommentDeleteConfirmation} toggle={toggleCommentDeleteModal}>
            <ModalHeader toggle={toggleCommentDeleteModal}>Confirm comment deletion</ModalHeader>
            <ModalBody>
              Are you sure you want to delete comment: {selectedDeleteCommentMessage}?
            </ModalBody>
            <ModalFooter>
              <Button color="primary" onClick={handleDeleteCommentConfirmation}>Delete</Button>{' '}
              <Button color="secondary" onClick={toggleCommentDeleteModal}>Cancel</Button>
            </ModalFooter>
          </Modal>
          <Modal isOpen={showReportCommentConfirmation} toggle={toggleCommentReportModal}>
              <ModalHeader toggle={toggleCommentReportModal}>Confirm report</ModalHeader>
              <ModalBody>
                Are you sure you want to report comment: {selectedReportCommentMessage}?
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={handleReportCommentConfirmation}>Report</Button>{' '}
                <Button color="secondary" onClick={toggleCommentReportModal}>Cancel</Button>
              </ModalFooter>
            </Modal>
        </div>
        <div className="comment-form">
          <Form onSubmit={handleAddComment}>
            <Row>
              <Col sm={8}>
                <Input
                  style={{ width: '100%' }}
                  type="text"
                  name="commentMessage"
                  id="commentMessage"
                  placeholder="Enter your comment"
                  value={commentMessage}
                  onChange={handleCommentMessageChange}
                />
              </Col>
              <Col sm={4} className="d-flex align-items-center">
                <Button className="addButton" variant="success"
                  disabled={saveButtonDisabled} type="submit">
                  Add comment
                </Button>
              </Col>
            </Row>
          </Form>
        </div>
        <div className="forum-body">
          {forumComments.length > 0 ? (
            <div className="list-group" key={forumComments.length}>
              {checkComments(forumPost) ? (
                <span onClick={toggleComments} className="comments-toggle">
                  <FontAwesomeIcon icon={faComments} className="comments-icon" />
                  <span className="comments-text">
                    {showComments ? 'Hide comments' : `Show ${countComments(forumComments)} comments`}
                  </span>
                </span>
              ) :
                <span className="comments-text">
                  There are no comments yet
                </span>
              }
              {showComments && (
                forumComments.map(comment => (
                  !comment.isInappropriate && !comment.isAReply && (
                    <div className="forum-item" key={comment.commentId}>
                      <div className="d-flex w-100 comment">
                      <CommentComp key={comment.commentId} comment={comment} studentId={studentId}/>
                        <div>
                          <p className="mb-1">{comment.message}</p>
                          <div className="button-container">
                            {studentId === comment.studentId ?
                              (
                                <>
                                  <Button className="forumButton" tag={Link} to={`/student/edit-comment/${comment.commentId}/${comment.message}/${forumPost.postId}/${encodeURIComponent(topicName)}/${topicId}`} title="Edit">
                                    <FontAwesomeIcon icon={['fa', 'edit']}  className="font-size-sm"/>
                                  </Button>
                                  <Button className="forumButton" onClick={() => handleCommentDeleteButtonClick(comment)} title="Delete">
                                    <FontAwesomeIcon icon={['fa', 'trash']} className="font-size-sm"  />
                                  </Button>
                                </>
                              ) : (
                                <>
                                  <Button onClick={() => handleCommentLike(comment.commentId)} className={`forumButton ${commentLikedByUser[comment.commentId] ? 'liked' : ''}`}>
                                    <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="font-size-sm" style={{marginRight: "5px"}}/>
                                    {comment.noOfLikes}
                                  </Button>
                                  <Button onClick={() => handleCommentDislike(comment.commentId)} className={`forumButton ${commentDislikedByUser[comment.commentId] ? 'disliked' : ''}`}>
                                    <FontAwesomeIcon icon="fa-regular fa-thumbs-down"  className="font-size-sm" style={{marginRight: "5px"}}/>
                                    {comment.noOfDislikes}
                                  </Button>
                                  <Button className="forumButton" onClick={() => handleReportCommentButtonClick(comment)}>
                                    <FontAwesomeIcon icon="fa-regular fa-flag" className="font-size-sm" />
                                  </Button>
                                </>
                              )}
                          </div>
                        </div>
                          <div className="reply">
                            <Form onSubmit={(e) => handleAddReply(e, comment.commentId)}>
                              <Row>
                                <Col sm={6}>
                                  <Input
                                    style={{ width: '100%' }}
                                    type="text"
                                    name="replyMessage"
                                    id="replyMessage"
                                    placeholder="Enter your reply"
                                    value={replyMessages[comment.commentId] || ""}
                                    onChange={(e) =>
                                      handleReplyMessageChange(comment.commentId, e.target.value)
                                    }
                                  />
                                </Col>
                                <Col sm={4} className="d-flex align-items-center">
                                  <Button className="addButton" variant="success"
                                    disabled={replyButtonsDisabled[comment.commentId]} type="submit">
                                    Add reply
                                  </Button>
                                </Col>
                              </Row>
                            </Form>
                          </div>
                        </div>
                      {comment.replies.length > 0 && (
                        <div className="ml-5">
                          {checkReplies(comment) && (
                            <span onClick={() => toggleReplies(comment.commentId)} className="comments-toggle">
                              <FontAwesomeIcon icon={faComments} className="comments-icon" />
                              <span className="comments-text">
                                {comment.showReplies ? 'Hide replies' : `Show ${countReplies(comment)} replies`}
                              </span>
                            </span>
                          )}
                          <div className="list-group">
                            {comment.showReplies && (
                              comment.replies.map(reply => (
                                !reply.isInappropriate && (
                                  <div className="forum-item" key={reply.commentId}>
                                    <div className="d-flex w-100 justify-content-between">
                                    <CommentComp key={reply.commentId} comment={reply} studentId={studentId}/>
                                    </div>
                                    <div className="comment-body">
                                      <p className="mb-1">{reply.message}</p>
                                      <div className="button-container">
                                        {studentId === reply.studentId ?
                                          (
                                            <>
                                              <Button className="forumButton" tag={Link} to={`/student/edit-comment/${reply.commentId}/${reply.message}/${forumPost.postId}/${encodeURIComponent(topicName)}/${topicId}`} title="Edit">
                                                <FontAwesomeIcon icon={['fa', 'edit']} className="font-size-sm"  />
                                              </Button>
                                              <Button className="forumButton" onClick={() => handleCommentDeleteButtonClick(reply)} title="Delete">
                                                <FontAwesomeIcon icon={['fa', 'trash']}  className="font-size-sm" />
                                              </Button>
                                            </>
                                          ) : (
                                            <>
                                              <Button className={`forumButton ${commentLikedByUser[reply.commentId] ? 'liked' : ''}`} onClick={() => handleCommentLike(reply.commentId)}>
                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="font-size-sm" style={{marginRight: "5px"}}/>
                                                {reply.noOfLikes}
                                              </Button>
                                              <Button className={`forumButton ${commentDislikedByUser[reply.commentId] ? 'disliked' : ''}`} onClick={() => handleCommentDislike(reply.commentId)}>
                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="font-size-sm" style={{marginRight: "5px"}}/>
                                                {reply.noOfDislikes}
                                              </Button>
                                              <Button className="forumButton" onClick={() => handleReportCommentButtonClick(reply)}>
                                                <FontAwesomeIcon icon="fa-regular fa-flag" className="font-size-sm" />
                                              </Button>
                                            </>
                                          )}
                                      </div>
                                    </div>
                                  </div>
                                )
                              ))
                            )}
                          </div>
                        </div>
                      )}
                    </div>
                  )
                ))
              )}
            </div>
          ) : (
            <p>No Comments Yet</p>
          )}
        </div>
        {alertVisible && (
        <Alert color={alertType} toggle={() => setAlertVisible(false)}>
          {alertMessage}
        </Alert>
      )}
      </div>
    </>
  )
}