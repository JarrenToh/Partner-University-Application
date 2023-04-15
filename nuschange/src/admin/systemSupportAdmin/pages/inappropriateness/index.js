import React, { useState } from "react";

import Header from "../../../components/dashboard/Header";
import Menu from "../../../components/dashboard/Menu";
import Footer from "../../../components/dashboard/Footer";

import InappropriatenessComponent from "./components";
import apiPaths from "../../../../util/apiPaths";

import { Helmet } from "react-helmet";

const Inappropriateness = () => {
  const [selectedButton, setSelectedButton] = useState("PUReview");

  const handleButtonClicks = (category) => {
    setSelectedButton(category);
  };

  const buttons = [
    { label: "PUReview", category: "PUReview" },
    { label: "ForumComment", category: "ForumComment" },
    { label: "ForumPost", category: "ForumPost" },
    { label: "ForumTopic", category: "ForumTopic" },
    { label: "PUModuleReview", category: "PUModuleReview" },
  ];

  const additionalProps = {};

  switch (selectedButton) {
    case "PUReview":
      additionalProps.type = "Review";
      additionalProps.typeOfComponent = "PUReview";
      additionalProps.apiPath = `${apiPaths.listOfPUReviews}/getAllReportedPUReviews`;
      break;
    case "ForumComment":
      additionalProps.type = "Message";
      additionalProps.typeOfComponent = "ForumComment";
      additionalProps.apiPath = `${apiPaths.listOfForumComments}`;
      break;
    case "ForumPost":
      additionalProps.type = "Message";
      additionalProps.typeOfComponent = "ForumPost";
      additionalProps.apiPath = `${apiPaths.listOfForumPosts}`;
      break;
    case "ForumTopic":
      additionalProps.type = "Topic Name";
      additionalProps.typeOfComponent = "ForumTopic";
      additionalProps.apiPath = `${apiPaths.listOfForumTopics}`;
      break;
    case "PUModuleReview":
      additionalProps.type = "Review";
      additionalProps.typeOfComponent = "PUModuleReview";
      additionalProps.apiPath = `${apiPaths.listOfPUModuleReview}`;
      break;
    default:
      additionalProps.type = "Review";
      additionalProps.typeOfComponent = "PUReview";
      additionalProps.apiPath = `${apiPaths.listOfPUReviews}/getAllReportedPUReviews`;
  }

  return (
    <div>
      <Helmet>
        <title>View Inappropriate Content</title>
      </Helmet>
      <Header />
      <Menu />
      <div className="content-wrapper">
        <div className="card">
          <div className="card-header d-flex justify-content-between align-items-center">
            <h3 className="card-title">Inappropriateness Content</h3>
            <div className="btn-group">
              {buttons.map(({ label, category }) => (
                <button
                  key={category}
                  type="button"
                  className={`btn ${category === selectedButton
                      ? "btn-primary"
                      : "btn-outline-dark"
                    }`}
                  onClick={() => handleButtonClicks(category)}
                >
                  {label}
                </button>
              ))}
            </div>
          </div>
          <div className="card-body">
            <InappropriatenessComponent
              {...additionalProps}
              selectedButton={selectedButton}
            />
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Inappropriateness;
