import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createContext, useContext, useState, useEffect } from "react";
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
import FAQPage from './student/FAQpage';
import LikedPUs from './student/LikedPUs';
import UniversityRankingsCountry from './student/ranking/UniversityRankingsCountry';
import UniversityRankingsRegion from './student/ranking/UniversityRankingsRegion';
import MappableModule from './student/containers/mappableModules';
import UniversityDescriptionPage from './student/containers/universityDescriptionPage';
import ModulesTaken from './student/studentProfile/ModulesTaken';


//import Enquiry from './admin/userSupportAdmin/pages/enquiry';
//import EnquiryDetails from './admin/userSupportAdmin/pages/enquiry/view';
//import FAQs from './admin/userSupportAdmin/pages/faq/index';
//import CreateFAQ from './admin/userSupportAdmin/pages/faq/create';
//import FAQDetails from './admin/userSupportAdmin/pages/faq/view';
//import Login from './admin/Login';
//import './student/assets/base.scss';
//import 'bootstrap/dist/css/bootstrap.min.css';
//import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

//import Login from './admin/Login';
//import UniversityDescriptionPage from './student/components/Index/universityDescriptionPage';
//import MappableModule from './student/components/Index/mappableModules';
//import Main from './admin/Main';
//import PartnerUuniversity from './admin/systemSupportAdmin/pages/partnerUniversity';

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
    console.log(data);
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
            <Route path="/university-rankings-country" element={<UniversityRankingsCountry universitiesData={pus} />} />
            <Route path="/university-rankings-region" element={<UniversityRankingsRegion universitiesData={pus} />} />
            <Route path="/profile" element={<StudentProfile user={user}/>} />
            <Route path="/forum-topics" element={<ForumTopics />} />
            <Route path="/forum-topics/:id/:topicName/:studentId" element={<TopicPosts />} />
            <Route path="/forum-posts/:id/:topicName" element={<NewPost />} />
            <Route path="/my-topics/:studentId" element={<MyTopics />} />
            <Route path="/my-posts/:id/:topicName/:studentId" element={<MyPosts />} />
            <Route path="/forum-posts/edit/:id/:oldTitle/:oldMessage/:topicName" element={<EditPost />} />
            <Route path="/new-topic/:studentId" element={<NewTopic />} />
            <Route path="/forum-topics/edit/:topicId/:oldTopicName" element={<EditTopic />} />
            <Route path="/login" element={<StudentLogin onLogin={handleLogin} />} />
            <Route path="/university-rankings" element={<UniversityRankings universitiesData={pus} />} />
            <Route path="/faq" element={<FAQPage/>}/>
            <Route path="/profile/likedPus" element={<LikedPUs/>}/>
            <Route path="/university-description-page" element={<UniversityDescriptionPage/>}/>
            <Route path="/university-description-page/mappable-module" element={<MappableModule/>}/>
            <Route path="/profile/modulesTaken" element={<ModulesTaken/>}/>
          </Routes>
        </Router>

        {/* <Router basename='/admin'>
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
      </Routes>*/}

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
