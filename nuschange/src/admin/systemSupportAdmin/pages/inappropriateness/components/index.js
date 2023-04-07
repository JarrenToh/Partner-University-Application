import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../../../../util/API";

const InappropriatenessComponent = ({ type, typeOfComponent, apiPath, selectedButton }) => {

    const [data, setData] = useState([]);
    const navigate = useNavigate();

    const handleButtonClick = (enquiryId) => {
        navigate(`/enquiries/${enquiryId}`);
    };

    const getId = (item) => {
        switch (typeOfComponent) {
            case "ForumComment":
                return item.commentId;
            case "ForumPost":
                return item.postId;
            case "ForumTopic":
                return item.topicId;
            case "PUModuleReview":
                return item.moduleReviewId;
            case "PUReview":
                return item.PUReviewId;
            default:
                return -1;
        }
    };

    const showLikesDislikes = (item) => {
        return item === undefined ? 0 : item;
    };

    const showSpecificHeader = () => {
        return type === "Review" ? "Review" : (type === "Message" ? "Message" : "Topic Name");
    };

    const showSpecificResult = (item) => {
        return type === "Review" ? item.review : (type === "Message" ? item.message : item.topicName);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await API.get(apiPath);
                setData(response.data);
            } catch (error) {
                console.error(error);
            }
        };
        fetchData();
    }, [selectedButton]);

    return (
        <div>
            <div className="card-body">
                <table id="example1" className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>{showSpecificHeader()}</th>
                            <th>Number of Likes</th>
                            <th>Number of Dislikes</th>
                            <th>Created By</th>
                            <th>Reported By</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.map((item, index) => item.isInappropriate && (
                            <tr key={index}>
                                <td>{getId(item)}</td>
                                <td>{showSpecificResult(item)}s</td>
                                <td>{showLikesDislikes(item.noOfLikes)}</td>
                                <td>{showLikesDislikes(item.noOfDislikes)}</td>
                                <td>{item.studentFirstName} {item.studentLastName}</td>
                                <td>
                                    <button onClick={() => handleButtonClick(item.enquiryId)} type="button" className="btn btn-primary">View Details</button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                    <tfoot>
                        <tr>
                            <th>ID</th>
                            <th>{showSpecificHeader()}</th>
                            <th>Number of Likes</th>
                            <th>Number of Dislikes</th>
                            <th>Created By</th>
                            <th>Reported By</th>
                            <th>Actions</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    )
};

export default InappropriatenessComponent;