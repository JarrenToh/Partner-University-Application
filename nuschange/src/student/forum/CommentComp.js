import { React, Fragment } from 'react';
import { Link } from 'react-router-dom';
import './forum.css';

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

function CommentComp({comment, studentId}) {
  return (
   <>
      <div className="d-flex comment-header align-items-center">
        {studentId === comment.studentId ?
          (<Link to={`/student/profile`} style={{ textDecoration: "none" }}>
            <h5 className="mb-1 author" style={{ fontSize: "14px" }}>
              {comment.studentFirstName} {comment.studentLastName}
            </h5>
          </Link>) :
          (<Link to={`/student/other-profile/${comment.studentId}`} style={{ vtextDecoration: "none" }}>
            <h5 className="mb-1 author" style={{ fontSize: "14px" }}>
              {comment.studentFirstName} {comment.studentLastName}
            </h5>
          </Link>)}
        <small className="time">{getTimeDifference(comment.timeOfCreation)}</small>
      </div>
      </>
  )
}

export default CommentComp;