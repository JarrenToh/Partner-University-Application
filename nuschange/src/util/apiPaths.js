const path = "http://localhost:8080/PU-war/webresources";

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,
    listOfCountries: `${path}/country`,
    listOfPUModules: `${path}/pumodule`,
    listOfPUReviews: `${path}/pureview`,
    listOfForumComments: `${path}/forumComments`,
    listOfForumPosts: `${path}/forumPosts`,
    listOfForumTopics: `${path}/forumTopics`,
    listOfPUModuleReview: `${path}/pumodulereview`,
    listOfAdminForumTopics: `${path}/admin/forumTopics`,
    admin: `${path}/admin`,
    listOfLikedPUs: `${path}/admin/likedPUs`,

  getPUbyName(puName) {
    return fetch(`${path}/pu/getPUByName/${puName}`);
  },

  getStudentsWithReviewByPU(puName) {
    return fetch(`${path}/student/pu/puname/withreview/${puName}`);
  },

  getStudentsByPU(puName) {
    return fetch(`${path}/student/pu/puname/${puName}`);
  },

  getMappableModulesByPU(puName) {
    return fetch(`${path}/pu/mappableModule/${puName}`);
  },

  updateStudentReview(rId, data) {
    return fetch(`${path}/pureview/${rId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  updateStudentLikedReview(sId, rId, choice) {
    return fetch(
      `${path}/pureview/like/?studentId=${sId}&puReviewId=${rId}&choice=${choice}`,
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "PUT",
      }
    );
  },

  updateStudentDislikedReview(sId, rId, choice) {
    return fetch(
      `${path}/pureview/dislike/?studentId=${sId}&puReviewId=${rId}&choice=${choice}`,
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "PUT",
      }
    );
  },

  updateModReview(rId, data) {
    return fetch(`${path}/pumodulereview/${rId}`, {
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  updateLikedModReview(sId, rId, choice) {
    return fetch(
      `${path}/pumodulereview/like/?studentId=${sId}&modReviewId=${rId}&choice=${choice}`,
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "PUT",
      }
    );
  },

  updateDislikedModReview(sId, rId, choice) {
    return fetch(
      `${path}/pumodulereview/dislike/?studentId=${sId}&modReviewId=${rId}&choice=${choice}`,
      {
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
        },
        method: "PUT",
      }
    );
  },

  getStudentsById(id) {
    return fetch(`${path}/student/${id}`);
  },
};

export default apiPaths;
