import { React, Fragment } from 'react';
import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faComments } from '@fortawesome/free-solid-svg-icons'
import {
    Form,
    Col,
    Input,
    Button,
    Row
} from 'reactstrap';
import './forum.css';

export default function ViewPost() {
    const { postId, studentId } = useParams();
    const [forumPost, setForumPost] = useState({});
    const [forumComments, setForumComments] = useState([]);
    const [showComments, setShowComments] = useState(false);
    const [commentMessage, setCommentMessage] = useState("");
    const [replyMessages, setReplyMessages] = useState({});

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

    const handleLikePost = () => {
    };

    const handleDislikePost = () => {
    };

    const handleLikeComment = () => {
    };
    

    const handleDislikeComment = () => {      
    };

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
    
    const handleReportPost = (forumPostId) => {
        axios.put(`http://localhost:8080/PU-war/webresources/forumPosts/report/${forumPostId}`)
        .then(response => {
          // Handle successful response here
          console.log('Report successful');
        })
        .catch(error => {
          // Handle error response here
          console.error('Report failed: ', error);
        });
    }

    const handleReportComment = (forumCommentId) => {
        axios.put(`http://localhost:8080/PU-war/webresources/forumComments/report/${forumCommentId}`)
        .then(response => {
          // Handle successful response here
          console.log('Report successful');
        })
        .catch(error => {
          // Handle error response here
          console.error('Report failed: ', error);
        });
    }

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
            <div className="forum-card">
                <div className="forum-header">
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
                <div className="forum-body">
                    <div className="forum-item">
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
                                    <button onClick={() => handleReportPost(forumPost.postId)}>
                                        <FontAwesomeIcon icon="fa-regular fa-flag" className="mr-1" />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div >
            <div className="forum-card">
                <div className="forum-header">
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
                            <Col sm={4} className="d-flex align-items-center">
                                <Button className="addButton" type="submit">
                                    Add comment
                                </Button>
                            </Col>
                        </Row>
                    </Form>
                </div>
                <div className="forum-body">
                    {forumComments.length > 0 ? (
                        <div className="list-group"  key={forumComments.length}>
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
                                                <div className="d-flex comment-header align-items-center">
                                                    <h5 className="mb-1 author">{comment.studentFirstName} {comment.studentLastName}</h5>
                                                    <small className="time">{getTimeDifference(comment.timeOfCreation)}</small>
                                                </div>
                                                <div>
                                                    <p className="mb-1">{comment.message}</p>
                                                    <div className="button-container">
                                                        <button onClick={() => handleLikeComment(comment.commentId)} className={true ? 'liked' : ''}>
                                                            <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="mr-1" />
                                                            {comment.noOfLikes}
                                                        </button>
                                                        <button onClick={() => handleDislikeComment(comment.commentId)} className={true ? 'disliked' : ''}>
                                                            <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="mr-1" />
                                                            {comment.noOfDislikes}
                                                        </button>
                                                        <button onClick={() => handleReportComment(comment.commentId)}>
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
                                                                <Col sm={4} className="d-flex align-items-center">
                                                                    <Button className="addButton" type="submit">
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
                                                                        <div className="d-flex justify-content-between comment-header">
                                                                            <h6 className="mb-1 author">{reply.studentFirstName} {reply.studentLastName}</h6>
                                                                            <small className="time">{getTimeDifference(reply.timeOfCreation)}</small>
                                                                        </div>
                                                                    </div>
                                                                    <div className="comment-body">
                                                                        <p className="mb-1">{reply.message}</p>
                                                                        <div className="button-container">
                                                                            <button onClick={() => handleLikeComment(reply.commentId)} className={true ? 'liked' : ''}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-up" className="mr-1" />
                                                                                {reply.noOfLikes}
                                                                            </button>
                                                                            <button onClick={() => handleDislikeComment(reply.commentId)} className={false ? 'disliked' : ''}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-thumbs-down" className="mr-1" />
                                                                                {reply.noOfDislikes}
                                                                            </button>
                                                                            <button onClick={() => handleReportComment(reply.commentId)}>
                                                                                <FontAwesomeIcon icon="fa-regular fa-flag" className="mr-1" />
                                                                            </button>
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
            </div>
        </>
    )
}