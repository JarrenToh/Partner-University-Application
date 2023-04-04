const path = 'http://localhost:8080/PU-war/webresources';

const apiPaths = {
    listOfFaqs: `${path}/admin/faqs`,
    listOfEnquiries: `${path}/admin/enquiries`,
    listOfPUs: `${path}/pu`,
    individualPU: `${path}/getPUById`,

    getPUbyName(puName) {
        return fetch('${path}/pugetPUByName/${puName}');
    }

    
};

export default apiPaths;