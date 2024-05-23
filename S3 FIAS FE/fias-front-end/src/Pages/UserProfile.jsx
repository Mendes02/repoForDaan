import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import getProfileData from '../APILayer/getProfileData';    
import Navbar from '../Components/Navbar/Navbar';
import profilePic from '../Pages/static/PFP.jpg';
import { jwtDecode } from 'jwt-decode';
import createFriendshipRequest from '../APILayer/sendFriendRequest';

const UserProfile = () => {
  const [profileData, setProfileData] = useState(null);
  const { userId } = useParams();
  const accessToken = jwtDecode(sessionStorage.getItem("accessToken"));
  const currentUserId = accessToken.userId;

  useEffect(() => {
    const fetchProfileData = async () => {
      try {
        const data = await getProfileData(userId);
        setProfileData(data);
      } catch (error) {
        console.error('Error fetching profile data:', error);
      }
    };

    if (userId) {
      fetchProfileData();
    }
  }, [userId]);

  if (!profileData) {
    return <div>Loading...</div>;
  }

  const sendFriendRequest = async (requesterId, requestedId) => {
    const responseStatus = await createFriendshipRequest(requesterId,requestedId);
    if (responseStatus === 'ok') {
      setProfileData({ ...profileData, friendRequestStatus: 'Pending' });
    }
    console.log('Friend request sent to user ID:', userId);
  };

  let friendRequestElement = null;
  switch (profileData.friendRequestStatus) {
    case 'S':
      friendRequestElement = <Link to="/incomingFriendRequests" className="friend-request-button">See friend requests</Link>
      break;
    case 'NA':
      friendRequestElement = <p>You are friends with this user</p>;
      break;
    case 'Pending':
      friendRequestElement = (
        <button className="friend-request-button pending">Pending friend request</button>
      );
      break;
    case 'Available':
      friendRequestElement = (
        <button onClick={() => sendFriendRequest(currentUserId,userId)} className="friend-request-button">
          Send Friend Request
        </button>
      );
      break;
    default:
      // Handle other potential statuses or leave as null if none.
  }

  return (
    <div>
      <Navbar />
      <div className='backgroundProfilePage'>
        <div className='profilePicBackground'>
          <div className="profilePic">
            <img src={profilePic} alt="Profile" />
          </div>
          <h1 className="profileHeader">{profileData.user.username}'s Profile</h1>
        </div>
        {friendRequestElement}
        <div className="profileContainer">
          <div className="profileRow">
            <span className="profileLabel">Total likes :</span>
            <span>{profileData.numberOfLikesFromOtherUsers}</span>
          </div>
          <div className="profileRow">
            <span className="profileLabel">Role:</span>
            <span>{profileData.user.role}</span>
          </div>
          <div className="profileRow">
            <span className="profileLabel">Friends:</span>
            <span>{profileData.friends}</span>
          </div>
          <div className="profileRow">
            <span className="profileLabel">Posts:</span>
            <span>{profileData.numberOfPosts}</span>
          </div>
          <div className="profileRow">
            <span className="profileLabel">Reviews:</span>
            <span>{profileData.numberOfReviews}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfile;