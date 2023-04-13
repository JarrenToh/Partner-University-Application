import { BrowserRouter as Router, Routes, Route, useLocation, Link, useRouteMatch } from 'react-router-dom';
import { createContext, useContext, useState, useEffect } from "react";
import { Button } from "reactstrap";
import './App.css';
import HomePage from './student/homepage/HomePage';
import UniversityRankings from './student/ranking/UniversityRankings';
import NavbarComp from './student/components/NavbarComp';
import StudentLogin from './student/login/StudentLogin';
import { AuthProvider, useAuth } from './AuthContext';
import StudentProfile from './student/studentProfile/StudentProfile';
import ForumTopics from './student/ForumTopics';
import TopicPosts from './student/TopicPosts';
import NewPost from './student/NewPost';
import MyTopics from './student/MyTopics';
import MyPosts from './student/MyPosts';
import EditPost from './student/EditForumPost';
import NewTopic from './student/NewTopic';
import EditTopic from './student/EditTopic';
import Post from './student/Post';
import Error from './student/ErrorPage';
import EditComment from './student/EditComment';
import FAQPage from './student/FAQpage';
import LikedPUs from './student/studentProfile/LikedPUs';
import UniversityRankingsCountry from './student/ranking/UniversityRankingsCountry';
import UniversityRankingsRegion from './student/ranking/UniversityRankingsRegion';
import MappableModule from './student/containers/mappableModules';
import UniversityDescriptionPage from './student/containers/universityDescriptionPage';
import ModulesTaken from './student/studentProfile/ModulesTaken';
import ModuleDetail from './student/puModule/ModuleDetails';
import OtherStudentProfile from './student/containers/OtherStudentProfile';
import StudentEnquiry from './student/enquiries/Enquiries';


// admin
import Login from './admin/Login';
// user support admin
import Enquiry from './admin/userSupportAdmin/pages/enquiry';
import EnquiryDetails from './admin/userSupportAdmin/pages/enquiry/view';
import FAQs from './admin/userSupportAdmin/pages/faq';
import CreateFAQ from './admin/userSupportAdmin/pages/faq/create';
import FAQDetails from './admin/userSupportAdmin/pages/faq/view';
// system support admin
import CreatePartnerUniversity from './admin/systemSupportAdmin/pages/partnerUniversity/create';
import PartnerUniversityDetails from './admin/systemSupportAdmin/pages/partnerUniversity/view';
import PartnerUniversityModules from './admin/systemSupportAdmin/pages/partnerUniversityModule';
import PartnerUniversityModuleDetails from './admin/systemSupportAdmin/pages/partnerUniversityModule/view';
import CreatePartnerUniversityModule from './admin/systemSupportAdmin/pages/partnerUniversityModule/create';
import Main from './admin/Main';
import PartnerUniversity from './admin/systemSupportAdmin/pages/partnerUniversity';
import Inappropriateness from './admin/systemSupportAdmin/pages/inappropriateness';
import InappropriatenessDetails from './admin/systemSupportAdmin/pages/inappropriateness/view';
import ForumTopicsSystemSupportAdmin from './admin/systemSupportAdmin/pages/forumTopic/index';
import CreateForumTopicsSystemSupportAdmin from './admin/systemSupportAdmin/pages/forumTopic/create';
import ForumTopicsDetailsSystemSupportAdmin from './admin/systemSupportAdmin/pages/forumTopic/view';
import AdminProfile from './admin/Profile';

import withAdminAuth from './withAdminAuth';

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

  const ProtectedMain = withAdminAuth(Main);
  const ProtectedFAQs = withAdminAuth(FAQs);
  const ProtectedFAQDetails = withAdminAuth(FAQDetails);
  const ProtectedCreateFAQ = withAdminAuth(CreateFAQ);
  const ProtectedEnquiry = withAdminAuth(Enquiry);
  const ProtectedEnquiryDetails = withAdminAuth(EnquiryDetails);
  const ProtectedPartnerUniversity = withAdminAuth(PartnerUniversity);
  const ProtectedPartnerUniversityDetails = withAdminAuth(PartnerUniversityDetails);
  const ProtectedCreatePartnerUniversity = withAdminAuth(CreatePartnerUniversity);
  const ProtectedPartnerUniversityModules = withAdminAuth(PartnerUniversityModules);
  const ProtectedPartnerUniversityModuleDetails = withAdminAuth(PartnerUniversityModuleDetails);
  const ProtectedCreatePartnerUniversityModule = withAdminAuth(CreatePartnerUniversityModule);
  const ProtectedInappropriateness = withAdminAuth(Inappropriateness);
  const ProtectedInappropriatenessDetails = withAdminAuth(InappropriatenessDetails);
  const ProtectedForumTopicsSystemSupportAdmin = withAdminAuth(ForumTopicsSystemSupportAdmin);
  const ProtectedCreateForumTopicsSystemSupportAdmin = withAdminAuth(CreateForumTopicsSystemSupportAdmin);
  const ProtectedForumTopicsDetailsSystemSupportAdmin = withAdminAuth(ForumTopicsDetailsSystemSupportAdmin);
  const ProtectedAdminProfile = withAdminAuth(AdminProfile);

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
            <Route path="/admin/:typeOfAdmin/main" element={<ProtectedMain />} />
            <Route path="/admin/:typeOfAdmin/profile/:usernameFromUrl" element={<ProtectedAdminProfile />} />
            <Route path="/admin/userSupportAdmin/faqs" element={<ProtectedFAQs />} />
            <Route path="/admin/userSupportAdmin/faqs/:id" element={<ProtectedFAQDetails />} />
            <Route path="/admin/userSupportAdmin/faqs/create" element={<ProtectedCreateFAQ />} />
            <Route path="/admin/userSupportAdmin/enquiries" element={<ProtectedEnquiry />} />
            <Route path="/admin/userSupportAdmin/enquiries/assigned" element={<ProtectedEnquiry adminId={1} />} />
            <Route path="/admin/userSupportAdmin/enquiries/:id" element={<ProtectedEnquiryDetails />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities" element={<ProtectedPartnerUniversity />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities/:nameFromUrl" element={<ProtectedPartnerUniversityDetails />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities/create" element={<ProtectedCreatePartnerUniversity />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities/:puName/modules" element={<ProtectedPartnerUniversityModules />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities/:puName/modules/:puModuleCode" element={<ProtectedPartnerUniversityModuleDetails />} />
            <Route path="/admin/systemSupportAdmin/partnerUniversities/:puName/modules/create" element={<ProtectedCreatePartnerUniversityModule />} />
            <Route path="/admin/systemSupportAdmin/inappropriatenessContent" element={<ProtectedInappropriateness />} />
            <Route path="/admin/systemSupportAdmin/inappropriatenessContent/:typeOfComponent/:id" element={<ProtectedInappropriatenessDetails />} />
            <Route path="/admin/systemSupportAdmin/forumTopics" element={<ProtectedForumTopicsSystemSupportAdmin />} />
            <Route path="/admin/systemSupportAdmin/forumTopics/create" element={<ProtectedCreateForumTopicsSystemSupportAdmin />} />
            <Route path="/admin/systemSupportAdmin/forumTopics/:id" element={<ProtectedForumTopicsDetailsSystemSupportAdmin />} />
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
        {/* </Router>/*} */}
        
    </AuthProvider>
  );
}

export default App;

