const path = 'http://localhost:8080/PU-war/webresources';

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,

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

};

export default apiPaths;