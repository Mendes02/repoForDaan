import './Pages/static/App.css';
import React from 'react';
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import CreateAccount from './Pages/CreateAccount';
import Login from './Pages/Login';
import Test from './Pages/TestPage';
import HomePage from './Pages/HomePage';
import Navbar from './Components/Navbar/Navbar';
import VideoGamePage from './Pages/VideoGames';
import VideoGame from './Pages/VideoGame';
import VideoGameReviews from './Pages/VideoGameReviews';
import Logout from './Pages/Logout';
import CreateReview from './Pages/CreateReview';
import ForumsPage from './Pages/ForumsPage';
import ForumPage from './Pages/ForumPage';
import CreateForumPost from './Pages/CreateForumPost';
import UserProfile from './Pages/UserProfile';
import GetFriendRequests from './Pages/GetFriendRequestsPage';
import ChatPage from './Pages/ChatPage';
import ForumPostPage from './Pages/ForumPostPage';

function App() {
  return (
    <BrowserRouter>
        <div className="App">
          <Routes>
           <Route path="/" element={<Login />} />
           <Route path="/create-account" element={<CreateAccount />} />
           <Route path="/test" element={<Test />} />
           <Route path="/home-page" element={<HomePage />} />
           <Route path="/videogames" element={<VideoGamePage/>}/>
           <Route path="/videogame/:id" element={<VideoGame/>} />
           <Route path="/logout" element={<Logout/>}/>
           <Route path="/videogameReviews/:videoGameId" element={<VideoGameReviews />} />
           <Route path="/videogameReviews/create/:videoGameId" element={<CreateReview/>} />
           <Route path="/forums" element={<ForumsPage />} />
           <Route path="/forums/:forumId" element={<ForumPage/>} />
           <Route path="/createPost/:forumId" element={<CreateForumPost/>} />
           <Route path="/userProfile/:userId" element={<UserProfile/>} />
           <Route path="/incomingFriendRequests" element={<GetFriendRequests/>} />
           <Route path="/chats" element={<ChatPage/>} />
           <Route path="/forumPost/:forumPostId" element={<ForumPostPage/>}/>
          </Routes>
       </div>
    </BrowserRouter>
  );
}

export default App;