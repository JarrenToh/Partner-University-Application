const path = 'http://localhost:8080/PU-war/webresources';

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,

    getPUbyName(puName) {
        return fetch(`${path}/pu/getPUByName/${puName}`);
    }

};

export default apiPaths;