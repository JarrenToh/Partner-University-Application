import { BrowserRouter as Router, Routes, Route, useLocation, Link, useRouteMatch } from 'react-router-dom';
import { createContext, useContext, useState, useEffect } from "react";
import { Button } from "reactstrap";
import './App.css';
import HomePage from './student/homepage/HomePage';
import UniversityRankings from './student/ranking/UniversityRankings';
import NavbarComp from './student/components/NavbarComp';
import StudentLogin from './student/login/StudentLogin';
import { AuthProvider, useAuth } from './student/login/AuthContext';
import StudentProfile from './student/studentProfile/StudentProfile';
import ForumTopics from './student/ForumTopics';
import TopicPosts from './student/TopicPosts';
import NewPost from './student/NewPost';
import MyTopics from './student/MyTopics';
import MyPosts from './student/MyPosts';
import EditPost from './student/EditPost';
import NewTopic from './student/NewTopic';
import EditTopic from './student/EditTopic';
//import EachPost from './student/EachPost';
import Post from './student/Post';
import FAQPage from './student/FAQpage';
import LikedPUs from './student/studentProfile/LikedPUs';
import UniversityRankingsCountry from './student/ranking/UniversityRankingsCountry';
import UniversityRankingsRegion from './student/ranking/UniversityRankingsRegion';
import MappableModule from './student/containers/mappableModules';
import UniversityDescriptionPage from './student/containers/universityDescriptionPage';
import ModulesTaken from './student/studentProfile/ModulesTaken';
import ModuleDetail from './student/puModule/ModuleDetails';
import StudentEnquiry from './student/enquiries/Enquiries';

// admin
import Enquiry from './admin/userSupportAdmin/pages/enquiry';
import EnquiryDetails from './admin/userSupportAdmin/pages/enquiry/view';
import FAQs from './admin/userSupportAdmin/pages/faq';
import CreateFAQ from './admin/userSupportAdmin/pages/faq/create';
import FAQDetails from './admin/userSupportAdmin/pages/faq/view';
import Login from './admin/Login';
import CreatePartnerUuniversity from './admin/systemSupportAdmin/pages/partnerUniversity/create';
import PartnerUuniversityDetails from './admin/systemSupportAdmin/pages/partnerUniversity/view';
import PartnerUniversityModules from './admin/systemSupportAdmin/pages/partnerUniversityModule';
import PartnerUniversityModuleDetails from './admin/systemSupportAdmin/pages/partnerUniversityModule/view';
import CreatePartnerUniversityModule from './admin/systemSupportAdmin/pages/partnerUniversityModule/create';
import Main from './admin/Main';
import PartnerUuniversity from './admin/systemSupportAdmin/pages/partnerUniversity';
import Inappropriateness from './admin/systemSupportAdmin/pages/inappropriateness';
// import InappropriatenessDetails from './admin/systemSupportAdmin/pages/inappropriateness/view';
import InappropriatePUReviewDetails from './admin/systemSupportAdmin/pages/inappropriateness/puReviewDetails';
import InappropriateForumCommentDetails from './admin/systemSupportAdmin/pages/inappropriateness/forumCommentDetails';
import InappropriateForumPostDetails from './admin/systemSupportAdmin/pages/inappropriateness/forumPostDetails';
import InappropriateForumTopicDetails from './admin/systemSupportAdmin/pages/inappropriateness/forumTopicDetails';
import InappropriatePUModuleDetails from './admin/systemSupportAdmin/pages/inappropriateness/puModuleReviewDetails';

import './student/assets/base.scss';
import 'bootstrap/dist/css/bootstrap.min.css';
import ViewEnquiries from './student/enquiries/ViewEnquiries';
import LandingPage from './LandingPage';
// import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

// import Login from './admin/Login';

const App = () => {
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const { loggedInStudent, login, logout } = useAuth();

  useEffect(() => {
    searchPUs("");
  }, []);

  const searchPUs = async (title) => {
    const response = await fetch(`${API_URL}`);
    const data = await response.json();
    setPUs(data);
  };

  const handleLogin = (student) => {
    setUser(student);
    setIsLoggedIn(true);
  };

  const handleLogout = () => {
    setUser(null);
    setIsLoggedIn(false);
    logout(); // Call logout function from AuthContext
  };

  // const { pathname } = useLocation();

  // const [url, setUrl] = useState(window.location.pathname);
  // const [showNavbar, setShowNavbar] = useState(false);
  
  // useEffect(() => {
  //   console.log(url);
  // }, [url]);
  
  // useEffect(() => {
  //   setShowNavbar(url.startsWith('/student'));
  // }, [url]);
  

  return (
    <AuthProvider> {/* Wrap the app in AuthProvider */}

      <div className="App">
        
        <Router>
          
          {/* {showNavbar && (
            <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
          )} */}

          {/* student */}
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/student/home-page" element={<HomePage />} />
            <Route path="/student/profile" element={<StudentProfile user={user} />} />
            <Route path="/student/forum-topics" element={<ForumTopics />} />
            <Route path="/student/forum-topics/:id/:topicName/:studentId" element={<TopicPosts />} />
            <Route path="/student/forum-posts/:id/:topicName" element={<NewPost />} />
            <Route path="/student/my-topics/:studentId" element={<MyTopics />} />
            <Route path="/student/my-posts/:id/:topicName/:studentId" element={<MyPosts />} />
            <Route path="/student/forum-posts/edit/:id/:oldTitle/:oldMessage/:topicName" element={<EditPost />} />
            <Route path="/student/new-topic/:studentId" element={<NewTopic />} />
            <Route path="/student/forum-topics/edit/:topicId/:oldTopicName" element={<EditTopic />} />
            <Route path="/student/view-post/:postId/:studentId" element={<Post />} />
            <Route path="/student/login" element={<StudentLogin onLogin={handleLogin} />} />
            <Route path="/student/university-rankings" element={<UniversityRankings universitiesData={pus} />} />
            <Route path="/student/university-rankings-country" element={<UniversityRankingsCountry universitiesData={pus} />} />
            <Route path="/student/university-rankings-region" element={<UniversityRankingsRegion universitiesData={pus} />} />
            <Route path="/student/faq" element={<FAQPage />} />
            <Route path="/student/enquiry" element={<StudentEnquiry />} />
            <Route path="/student/viewEnquiries" element={<ViewEnquiries />} />
            <Route path="/student/profile/likedPus" element={<LikedPUs />} />
            <Route path="/student/university-description-page/:puName" element={<UniversityDescriptionPage />} />
            <Route path="/student/university-description-page/:puName/mappable-module" element={<MappableModule />} />
            <Route path="/student/university-description-page" element={<UniversityDescriptionPage />} />
            <Route path="/student/university-description-page/mappable-module" element={<MappableModule />} />
            <Route path="/student/profile/modulesTaken" element={<ModulesTaken />} />
            <Route path="/student/module-reviews" element={<ModuleDetail />} />

            {/* admin */}
            <Route path="/admin/login" element={<Login />} />
            <Route path="/admin/main" element={<Main />} />
            <Route path="/admin/faqs" element={<FAQs />} />
            <Route path="/admin/faqs/:id" element={<FAQDetails />} />
            <Route path="/admin/faqs/createFaq" element={<CreateFAQ />} />
            <Route path="/admin/enquiries" element={<Enquiry />} />
            <Route path="/admin/enquiries/assigned" element={<Enquiry adminId={1} />} />
            <Route path="/admin/enquiries/:id" element={<EnquiryDetails />} />
            <Route path="/admin/partnerUniversities" element={<PartnerUuniversity />} />
            <Route path="/admin/partnerUniversities/:id" element={<PartnerUuniversityDetails />} />
            <Route path="/admin/partnerUniversities/createPartnerUniversity" element={<CreatePartnerUuniversity />} />
            <Route path="/admin/partnerUniversities/:puName/modules" element={<PartnerUniversityModules />} />
            <Route path="/admin/partnerUniversities/:puName/modules/:puModuleCode" element={<PartnerUniversityModuleDetails />} />
            <Route path="/admin/partnerUniversities/:puName/modules/createModule" element={<CreatePartnerUniversityModule />} />
            <Route path="/admin/inappropriatenessContent" element={<Inappropriateness />} />
            {/* { <Route path="/admin/inappropriatenessContent/:type/:id" element={<InappropriatenessDetails />} /> } */}
            <Route path="/admin/inappropriatenessContent/puReviews/:id" element={<InappropriatePUReviewDetails />} />
            <Route path="/admin/inappropriatenessContent/forumComments/:id" element={<InappropriateForumCommentDetails />} />
            <Route path="/admin/inappropriatenessContent/forumPosts/:id" element={<InappropriateForumPostDetails />} />
            <Route path="/admin/inappropriatenessContent/forumTopics/:id" element={<InappropriateForumTopicDetails />} />
            <Route path="/admin/inappropriatenessContent/puModuleReviews/:id" element={<InappropriatePUModuleDetails />} />
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
    </AuthProvider>
  );
}

export default App;

