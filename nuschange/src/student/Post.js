import { React, Fragment } from 'react';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComments } from '@fortawesome/free-solid-svg-icons'
import {
    Card,
    CardBody,
    CardHeader,
    FormText,
    Form,
    Col,
    Label,
    FormGroup,
    Input,
    Button,
    Row
} from 'reactstrap';
import './forum.css';
import { FaBold } from 'react-icons/fa';
import { Divider } from '@mui/material';
import NavbarComp from './components/NavbarComp';
import { AuthProvider, useAuth } from './login/AuthContext';

export default function ViewPost() {
    const { postId, studentId } = useParams();
    const [forumPost, setForumPost] = useState({});
    const [forumComments, setForumComments] = useState([]);
    const [showComments, setShowComments] = useState(false);
    const [likes, setLikes] = useState(0);
    const [dislikes, setDislikes] = useState(0);
    const [commentMessage, setCommentMessage] = useState("");
    const [replyMessages, setReplyMessages] = useState({});

    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [user, setUser] = useState(null);
    const { loggedInStudent, login, logout } = useAuth();

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/PU-war/webresources/forumPosts/${postId}`);
                setForumPost(response.data);
                setForumComments(response.data.forumComments);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [forumComments, forumPost]);

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
        setCommentMessage(e.target.value);
    }

    const handleAddComment = (e) => {
        e.preventDefault();

        //const formData = new FormData();

        //formData.append('title', title);
        //formData.append('content', content);

        const createdForumComment = {
            message: commentMessage
        };

        console.log(commentMessage)

        axios.post(`http://localhost:8080/PU-war/webresources/forumComments/user/forumPosts/${postId}/student/${studentId}`, createdForumComment)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });

        setCommentMessage("");
    }

    // const handleReplyMessageChange = (e) => {
    //     setReplyMessage(e.target.value);
    // }

    const handleReplyMessageChange = (commentId, message) => {
        setReplyMessages((prevReplyMessages) => ({
            ...prevReplyMessages,
            [commentId]: message
        }));
    };

    const handleAddReply = (e, commentId) => {
        e.preventDefault();

        const createdForumReply = {
            //message: replyMessage
            message: replyMessages[commentId]
        };

        console.log(createdForumReply);

        axios.post(`http://localhost:8080/PU-war/webresources/forumComments/user/comment/${commentId}/student/${studentId}`, createdForumReply)
            .then((response) => {
                console.log(response.data);
            })
            .catch((error) => {
                console.error(error);
            });

        setReplyMessages(prevState => ({
            ...prevState,
            [commentId]: ""
        }));

    }

    const handleLikeComment = () => {
        setLikes(likes + 1);
    };

    const handleDislikeComment = () => {
        setDislikes(dislikes + 1);
    };

    const handleLikePost = () => {
        setLikes(likes + 1);
    };

    const handleDislikePost = () => {
        setDislikes(dislikes + 1);
    };

    const handleReportPost = () => {

    }

    const handleReportComment = () => {

    }

    function countComments(forumComments) {
        let count = 0;
        forumComments.forEach(comment => {
            if (!comment.isAReply) {
                count++;
            }
        });
        return count;
    }

    return (
        //  <div className="container my-5">
        <>
            <div className="card">
            <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
                <div className="card-header">
                    <div className="d-flex justify-content-between align-items-center">
                        <h4>{forumPost.title}</h4>
                        <div className="text-muted" style={{ display: 'flex', flexWrap: 'wrap' }}>
                            <span style={{ marginLeft: "15px", marginRight: "5px", fontSize: "12px" }}>
                                Posted by {forumPost.studentFirstName} {forumPost.studentLastName}
                            </span>
                            <span style={{ fontSize: "12px" }}>
                                {getTimeDifference(forumPost.timeOfCreation)}
                                {forumPost.isEdited && (
                                    <span style={{ marginLeft: "15px" }}>
                                        Last Edited: {getTimeDifference(forumPost.lastEdit)}
                                    </span>
                                )}
                            </span>
                        </div>
                    </div>
                </div>
                <div className="card-body">
                    <div className="list-group-item">
                        <div className="d-flex w-100 justify-content-between">
                            <p style={{ fontSize: '15px' }}>{forumPost.message}</p>
                            <div className="ml-auto">
                                <div className="button-container">
                                    <button onClick={handleLikePost}>
                                        <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="mr-1" />
                                        {forumPost.noOfLikes}
                                    </button>
                                    <button onClick={handleDislikePost}>
                                        <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="mr-1" />
                                        {forumPost.noOfDislikes}
                                    </button>
                                    <button onClick={handleReportPost}>
                                        <FontAwesomeIcon icon="fa-regular fa-flag" className="mr-1" />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div >
            <div className="card">
                <div className="card-header">
                    <h4>Comments</h4>
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
                            <Col sm={4} className="d-flex align-items-end">
                                <Button className="btn-outline-primary" type="submit" id="addButton">
                                    Add comment
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                </div>
                <div className="card-body">
                    {forumComments.length > 0 ? (
                        <div className="list-group">
                            <span onClick={toggleComments} className="comments-toggle">
                                <FontAwesomeIcon icon={faComments} className="comments-icon" />
                                <span className="comments-text">
                                    {showComments ? 'Hide comments' : `Show ${countComments(forumComments)} comments`}
                                </span>
                            </span>
                            {showComments && (
                                forumComments.map(comment => (
                                    !comment.isAReply && (
                                        <div className="list-group-item" key={comment.commentId}>
                                            <div className="d-flex w-100 comment">
                                                <div className="d-flex comment-header align-items-center">
                                                    <h5 className="mb-1 author">{comment.studentFirstName} {comment.studentLastName}</h5>
                                                    <small className="time">{getTimeDifference(comment.timeOfCreation)}</small>
                                                </div>
                                                <div>
                                                    <p className="mb-1">{comment.message}</p>
                                                    <div className="button-container">
                                                        <button onClick={handleLikeComment}>
                                                            <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="mr-1" />
                                                            {comment.noOfLikes}
                                                        </button>
                                                        <button onClick={handleDislikeComment}>
                                                            <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="mr-1" />
                                                            {comment.noOfDislikes}
                                                        </button>
                                                        <button onClick={handleReportComment}>
                                                            <FontAwesomeIcon icon="fa-regular fa-flag" className="mr-1" />
                                                        </button>
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
                                                                        value={replyMessages[comment.commentId] || ''}
                                                                        onChange={(e) =>
                                                                            handleReplyMessageChange(comment.commentId, e.target.value)
                                                                        }
                                                                    />
                                                                </Col>
                                                                <Col sm={4} className="d-flex align-items-end">
                                                                    <Button className="btn-outline-primary" type="submit" id="addButton">
                                                                        Add reply
                                                                    </Button>
                                                                </Col>
                                                            </Row>
                                                        </Form>
                                                    </div>
                                                </div>
                                            </div>
                                            {comment.replies.length > 0 && (
                                                <div className="ml-5">
                                                    {/* <h6>{comment.replies.length} Replies</h6> */}
                                                    <span onClick={() => toggleReplies(comment.commentId)} className="comments-toggle">
                                                        <FontAwesomeIcon icon={faComments} className="comments-icon" />
                                                        <span className="comments-text">
                                                            {comment.showReplies ? 'Hide replies' : `Show ${comment.replies.length} replies`}
                                                        </span>
                                                    </span>
                                                    <div className="list-group">
                                                        {comment.showReplies && (
                                                            comment.replies.map(reply => (
                                                                <div className="list-group-item" key={reply.commentId}>
                                                                    <div className="d-flex w-100 justify-content-between">
                                                                        <div className="d-flex justify-content-between comment-header">
                                                                            <h6 className="mb-1 author">{reply.studentFirstName} {reply.studentLastName}</h6>
                                                                            <small className="time">{getTimeDifference(reply.timeOfCreation)}</small>
                                                                        </div>
                                                                    </div>
                                                                    <div className="comment-body">
                                                                        <p className="mb-1">{reply.message}</p>
                                                                        <div className="button-container">
                                                                            <button onClick={handleLikeComment}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="mr-1" />
                                                                                {reply.noOfLikes}
                                                                            </button>
                                                                            <button onClick={handleDislikeComment}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="mr-1" />
                                                                                {reply.noOfDislikes}
                                                                            </button>
                                                                            <button onClick={handleReportComment}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-flag" className="mr-1" />
                                                                            </button>
                                                                        </div>
                                                                    </div>
                                                                </div>
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
            </div>
        </>
    )
}