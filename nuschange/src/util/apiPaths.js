const path = 'http://localhost:8080/PU-war/webresources';

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,
    listOfCountries: `${path}/country`,

    getPUbyName(puName) {
        return fetch('${path}/pugetPUByName/${puName}');
    }

    
};

export default apiPaths;