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
import ForumTopics from './student/forum/ForumTopics';
import TopicPosts from './student/forum/TopicPosts';
import NewPost from './student/forum/NewPost';
import MyTopics from './student/forum/MyTopics';
import MyPosts from './student/forum/MyPosts';
import EditPost from './student/forum/EditForumPost';
import NewTopic from './student/forum/NewTopic';
import EditTopic from './student/forum/EditTopic';
import Post from './student/forum/Post';
import Error from './student/forum/ErrorPage';
import EditComment from './student/forum/EditComment';
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
import NotFoundPage from './student/components/NotFoundPage';


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
// general admin
import AdminProfile from './admin/Profile';
import NotFound from './admin/NotFound';

import withAdminAuth from './withAdminAuth';

import './student/assets/base.scss';
import 'bootstrap/dist/css/bootstrap.min.css';
import ViewEnquiries from './student/enquiries/ViewEnquiries';
import LandingPage from './LandingPage';

const App = () => {
  const API_URL = "http://localhost:8080/PU-war/webresources/pu";
  const [pus, setPUs] = useState([]);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState(null);
  const { loggedInStudent, login, logout } = useAuth();

  const ProtectedFAQs = withAdminAuth('userSupportAdmin')(FAQs);
  const ProtectedFAQDetails = withAdminAuth('userSupportAdmin')(FAQDetails);
  const ProtectedCreateFAQ = withAdminAuth('userSupportAdmin')(CreateFAQ);
  const ProtectedEnquiry = withAdminAuth('userSupportAdmin')(Enquiry);
  const ProtectedEnquiryDetails = withAdminAuth('userSupportAdmin')(EnquiryDetails);

  const ProtectedMain = withAdminAuth('')(Main);
  const ProtectedNotFound = withAdminAuth('')(NotFound);
  const ProtectedPartnerUniversity = withAdminAuth('systemSupportAdmin')(PartnerUniversity);
  const ProtectedPartnerUniversityDetails = withAdminAuth('systemSupportAdmin')(PartnerUniversityDetails);
  const ProtectedCreatePartnerUniversity = withAdminAuth('systemSupportAdmin')(CreatePartnerUniversity);
  const ProtectedPartnerUniversityModules = withAdminAuth('systemSupportAdmin')(PartnerUniversityModules);
  const ProtectedPartnerUniversityModuleDetails = withAdminAuth('systemSupportAdmin')(PartnerUniversityModuleDetails);
  const ProtectedCreatePartnerUniversityModule = withAdminAuth('systemSupportAdmin')(CreatePartnerUniversityModule);
  const ProtectedInappropriateness = withAdminAuth('systemSupportAdmin')(Inappropriateness);
  const ProtectedInappropriatenessDetails = withAdminAuth('systemSupportAdmin')(InappropriatenessDetails);
  const ProtectedForumTopicsSystemSupportAdmin = withAdminAuth('systemSupportAdmin')(ForumTopicsSystemSupportAdmin);
  const ProtectedCreateForumTopicsSystemSupportAdmin = withAdminAuth('systemSupportAdmin')(CreateForumTopicsSystemSupportAdmin);
  const ProtectedForumTopicsDetailsSystemSupportAdmin = withAdminAuth('systemSupportAdmin')(ForumTopicsDetailsSystemSupportAdmin);
  const ProtectedAdminProfile = withAdminAuth('')(AdminProfile);

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
    logout(); 
  };

  return (
    <AuthProvider>

      <div className="App">

        <Router>

          {/* student */}
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/student/*" element={<NotFoundPage/>} />
            <Route path="/student/home-page" element={<HomePage />} />
            <Route path="/student/profile" element={<StudentProfile user={user} />} />
            <Route path="/student/forum-topics/:puId" element={<ForumTopics />} />
            <Route path="/student/forum-topics/:id/:topicName" element={<TopicPosts />} />
            <Route path="/student/forum-posts/:id/:topicName" element={<NewPost />} />
            <Route path="/student/my-topics" element={<MyTopics />} />
            <Route path="/student/my-posts/:id/:topicName" element={<MyPosts />} />
            <Route path="/student/forum-posts/edit/:topicId/:id/:oldTitle/:oldMessage/:topicName/:number" element={<EditPost />} />
            <Route path="/student/new-topic" element={<NewTopic />} />
            <Route path="/student/forum-topics/edit/:topicId/:oldTopicName" element={<EditTopic />} />
            <Route path="/student/error" element={<Error />} />
            <Route path="/student/view-post/:postId/:topicName/:topicId" element={<Post />} />
            <Route path="/student/edit-comment/:commentId/:oldCommentMessage/:postId/:topicName/:topicId" element={<EditComment />} />
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
            <Route path="/student/module-details/:puName/:modId" element={<ModuleDetail />} />
            <Route path="student/other-profile/:studentId" element={<OtherStudentProfile/>}/>

            {/* admin */}
            <Route path="/admin/login" element={<Login />} />
            <Route path="/admin/:typeOfAdmin/*" element={<ProtectedNotFound />} />
            <Route path="/admin/:typeOfAdmin/main" element={<ProtectedMain />} />
            <Route path="/admin/:typeOfAdmin/profile/:usernameFromUrl" element={<ProtectedAdminProfile />} />
            <Route path="/admin/userSupportAdmin/faqs" element={<ProtectedFAQs />} />
            <Route path="/admin/userSupportAdmin/faqs/:id" element={<ProtectedFAQDetails />} />
            <Route path="/admin/userSupportAdmin/faqs/create" element={<ProtectedCreateFAQ />} />
            <Route path="/admin/userSupportAdmin/enquiries" element={<ProtectedEnquiry />} />
            <Route path="/admin/userSupportAdmin/enquiries/assigned" element={<ProtectedEnquiry />} />
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
        
      </div>

    </AuthProvider>
  );
}

export default App;

