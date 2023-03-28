import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//import Enquiry from './admin/userSupportAdmin/pages/enquiry';
//import EnquiryDetails from './admin/userSupportAdmin/pages/enquiry/view';
//import FAQs from './admin/userSupportAdmin/pages/faq/index';
//import CreateFAQ from './admin/userSupportAdmin/pages/faq/create';
//import FAQDetails from './admin/userSupportAdmin/pages/faq/view';
import './App.css';
//import Login from './admin/Login';
//import './student/assets/base.scss';
//import 'bootstrap/dist/css/bootstrap.min.css';
//import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//import ForumTopics from './student/ForumTopics';
//import TopicPosts from './student/TopicPosts';
//import Login from './admin/Login';
//import UniversityDescriptionPage from './student/components/Index/universityDescriptionPage';
//import MappableModule from './student/components/Index/mappableModules';

//import Main from './admin/Main';
//import PartnerUuniversity from './admin/systemSupportAdmin/pages/partnerUniversity';
import HomePage from './student/HomePage';
import UniversityRankings from './student/UniversityRankings';
import NavbarComp from './student/components/NavbarComp';

const App = () => {
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);

  useEffect(() => {
    searchPUs("");
  }, []);

  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}`);
    const data = await response.json();
    console.log(data);
    setPUs(data);
  };

  return (
 
    /*<Router basename='/admin'>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/main" element={<Main />} />
        <Route path="/faqs/createFaq" element={<CreateFAQ />} />
        <Route path="/faqs" element={<FAQs />} />
        <Route path="/faqs/:id" element={<FAQDetails />} />
        <Route path="/enquiries" element={<Enquiry />} />
        <Route path="/enquiries/assigned" element={<Enquiry adminId={1} />} />
        <Route path="/enquiries/:id" element={<EnquiryDetails />} />
        <Route path="/partnerUniversities" element={<PartnerUuniversity />} />
      </Routes>*/

    <div className="App">
      <NavbarComp />
      <Router basename='/student'>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/university-rankings" element={<UniversityRankings universitiesData={pus}/>} />
        </Routes>
      </Router>
      {/* <ForumTopics /> */}
      {/* <TopicPosts /> */}
      {/* <Router>
        <Routes>
          <Route path="/" element={<ForumTopics />} />
          <Route path="/post/:id" element={<TopicPosts />} />
        </Routes>
      </Router> */}
      {/* <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header> */}
      {/* <UniversityDescriptionPage/> */}
      {/* <MappableModule /> */}
    </div>

  );
}

export default App;