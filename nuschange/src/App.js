import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createContext, useContext, useState, useEffect } from "react";
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

  return (
    <AuthProvider> {/* Wrap the app in AuthProvider */}
      <div className="App">
        <NavbarComp isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} user={user} />
        <Router basename='/student'>
          <Routes>
            <Route path="/home-page" element={<HomePage />} />
            <Route path="/profile" element={<StudentProfile user={user} />} />
            <Route path="/forum-topics/:puId" element={<ForumTopics />} />
            <Route path="/forum-topics/:id/:topicName" element={<TopicPosts />} />
            <Route path="/forum-posts/:id/:topicName" element={<NewPost />} />
            <Route path="/my-topics" element={<MyTopics />} />
            <Route path="/my-posts/:id/:topicName" element={<MyPosts />} />
            <Route path="/forum-posts/edit/:topicId/:id/:oldTitle/:oldMessage/:topicName/:number" element={<EditPost />} />
            <Route path="/new-topic" element={<NewTopic />} />
            <Route path="/forum-topics/edit/:topicId/:oldTopicName" element={<EditTopic />} />
            <Route path="/error" element={<Error />} />
            <Route path="/view-post/:postId/:topicName/:topicId" element={<Post />} />
            <Route path="/edit-comment/:commentId/:oldCommentMessage/:postId/:topicName/:topicId" element={<EditComment />} />
            <Route path="/login" element={<StudentLogin onLogin={handleLogin} />} />
            <Route path="/university-rankings" element={<UniversityRankings universitiesData={pus} />} />
            <Route path="/university-rankings-country" element={<UniversityRankingsCountry universitiesData={pus} />} />
            <Route path="/university-rankings-region" element={<UniversityRankingsRegion universitiesData={pus} />} />
            <Route path="/faq" element={<FAQPage />} />
            <Route path="/enquiry" element={<StudentEnquiry/>}/>
            <Route path="/viewEnquiries" element={<ViewEnquiries/>}/>
            <Route path="/profile/likedPus" element={<LikedPUs/>}/>
            <Route path="/university-description-page/:puName" element={<UniversityDescriptionPage/>}/>
            <Route path="/university-description-page/:puName/mappable-module" element={<MappableModule/>}/>
            <Route path="/university-description-page" element={<UniversityDescriptionPage/>}/>
            <Route path="/university-description-page/mappable-module" element={<MappableModule/>}/>
            <Route path="/profile/modulesTaken" element={<ModulesTaken/>}/>
            <Route path="/module-details/:puName/:modId" element={<ModuleDetail/>}/>
            <Route path='/other-profile/:studentId' element={<OtherStudentProfile/>}/>
          </Routes>
        </Router>

       {/* <Router basename='/admin'>
          <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/:typeOfAdmin/main" element={<ProtectedMain />} />
            <Route path="/:typeOfAdmin/profile/:usernameFromUrl" element={<ProtectedAdminProfile />} />
            <Route path="/userSupportAdmin/faqs" element={<ProtectedFAQs />} />
            <Route path="/userSupportAdmin/faqs/:id" element={<ProtectedFAQDetails />} />
            <Route path="/userSupportAdmin/faqs/create" element={<ProtectedCreateFAQ />} />
            <Route path="/userSupportAdmin/enquiries" element={<ProtectedEnquiry />} />
            <Route path="/userSupportAdmin/enquiries/assigned" element={<ProtectedEnquiry adminId={1} />} />
            <Route path="/userSupportAdmin/enquiries/:id" element={<ProtectedEnquiryDetails />} />
            <Route path="/systemSupportAdmin/partnerUniversities" element={<ProtectedPartnerUniversity />} />
            <Route path="/systemSupportAdmin/partnerUniversities/:nameFromUrl" element={<ProtectedPartnerUniversityDetails />} />
            <Route path="/systemSupportAdmin/partnerUniversities/create" element={<ProtectedCreatePartnerUniversity />} />
            <Route path="/systemSupportAdmin/partnerUniversities/:puName/modules" element={<ProtectedPartnerUniversityModules />} />
            <Route path="/systemSupportAdmin/partnerUniversities/:puName/modules/:puModuleCode" element={<ProtectedPartnerUniversityModuleDetails />} />
            <Route path="/systemSupportAdmin/partnerUniversities/:puName/modules/create" element={<ProtectedCreatePartnerUniversityModule />} />
            <Route path="/systemSupportAdmin/inappropriatenessContent" element={<ProtectedInappropriateness />} />
            <Route path="/systemSupportAdmin/inappropriatenessContent/:typeOfComponent/:id" element={<ProtectedInappropriatenessDetails />} />
            <Route path="/systemSupportAdmin/forumTopics" element={<ProtectedForumTopicsSystemSupportAdmin />} />
            <Route path="/systemSupportAdmin/forumTopics/create" element={<ProtectedCreateForumTopicsSystemSupportAdmin />} />
            <Route path="/systemSupportAdmin/forumTopics/:id" element={<ProtectedForumTopicsDetailsSystemSupportAdmin />} />
          </Routes>
        </Router>
      </div>

        </Router> */}

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
