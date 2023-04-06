const path = 'http://localhost:8080/PU-war/webresources';

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,
    listOfCountries: `${path}/country`,
    listOfModules: `${path}/pumodule`,
    listOfPUReviews: `${path}/pureview`,
    listOfForumComments: `${path}/forumComments`,
    listOfForumPosts: `${path}/forumPosts`,
    listOfForumTopics: `${path}/forumTopics`,
    listOfPUModuleReview: `${path}/pumodulereview`,

    getPUbyName(puName) {
        return fetch('${path}/pugetPUByName/${puName}');
    }

    
};

export default apiPaths;