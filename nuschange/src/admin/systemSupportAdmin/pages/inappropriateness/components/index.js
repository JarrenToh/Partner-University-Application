import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../../../../util/API";
import { systemSupportAdminPaths } from "../../../../../util/adminRoutes";

const InappropriatenessComponent = ({ type, typeOfComponent, apiPath, selectedButton }) => {

    const [data, setData] = useState([]);
    const [selectedId, setSelectedId] = useState(null); // new state variable to keep track of selected id
    const navigate = useNavigate();

    const handleButtonClick = (id) => {
        setSelectedId(id); // update the selected id state variable
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
                return item.puReviewId;
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
    }, [selectedButton, apiPath]);

    useEffect(() => {
        if (selectedId !== null) {

            let component = "";

            switch (typeOfComponent) {
                case "ForumComment":
                    component = "forumComments";
                    break;
                case "ForumPost":
                    component = "forumPosts";
                    break;
                case "ForumTopic":
                    component = "forumTopics";
                    break;
                case "PUModuleReview":
                    component = "puModuleReviews";
                    break;
                case "PUReview":
                    component = "puReviews";
                    break;
                default:
                    return -1;
            }

            navigate(`/admin${systemSupportAdminPaths.viewInappropriatenessContent}/${component}/${selectedId}`);
        }
    }, [selectedId, typeOfComponent, navigate]);

    return (
        <div>
            <div className="card-body">
                <table id="example1" className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>{showSpecificHeader()}</th>
                            <th>Number of Likes</th>
                            <th>Number of Dislikes</th>
                            <th>Created By</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {data.length > 0 ? (
                            data.filter((item) => item.isInappropriate).length > 0 ? (
                                data.map((item, index) =>
                                    item.isInappropriate && (
                                        <tr key={index}>
                                            <td>{showSpecificResult(item)}s</td>
                                            <td>{showLikesDislikes(item.noOfLikes)}</td>
                                            <td>{showLikesDislikes(item.noOfDislikes)}</td>
                                            <td>{item.studentFirstName} {item.studentLastName}</td>
                                            <td>
                                                <button onClick={() => handleButtonClick(getId(item))} type="button" className="btn btn-primary">View Details</button>
                                            </td>
                                        </tr>
                                    )
                                )
                            ) : (
                                <tr>
                                    <td colSpan={7} style={{ textAlign: "center" }}>No inappropriate content displayed</td>
                                </tr>
                            )
                        ) : (
                            <tr>
                                <td colSpan={7} style={{ textAlign: "center" }}>No data available</td>
                            </tr>
                        )}
                    </tbody>
                    <tfoot>
                        <tr>
                            <th>{showSpecificHeader()}</th>
                            <th>Number of Likes</th>
                            <th>Number of Dislikes</th>
                            <th>Created By</th>
                            <th>Actions</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    )
};

export default InappropriatenessComponent;