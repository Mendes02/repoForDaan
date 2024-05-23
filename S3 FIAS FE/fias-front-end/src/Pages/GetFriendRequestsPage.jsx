import React, { useState, useEffect } from 'react';
import Navbar from '../Components/Navbar/Navbar';
import getFriendRequests from '../APILayer/getFriendRequests';
import { jwtDecode } from 'jwt-decode';
import { Link } from 'react-router-dom';
import { replyToFriendshipRequest } from '../APILayer/replyToFriendshipRequest';


const GetFriendRequests = () => {
    const [requests, setRequests] = useState([]);
    const accessToken = jwtDecode(sessionStorage.getItem("accessToken"));
    const userId = accessToken.userId;

    useEffect(() => {
        const fetchRequests = async () => {
            const token = sessionStorage.getItem("accessToken");
            const data = await getFriendRequests(userId, token);
            setRequests(data);
        };

        fetchRequests();
    }, [userId]);

    const handleResponse = async (requestId, value) => {
        const result = await replyToFriendshipRequest(requestId, value, sessionStorage.getItem("accessToken"));
    
        if (result.status === 'ok') {
            // Update the UI to reflect the change or refresh the requests
            // For example, remove the handled request from the list
            setRequests(requests.filter(request => request.id !== requestId));
        } else {
            // Handle errors
            console.error(result.error);
        }
    };

    return (
        <div className='requests-page-container'>
            <Navbar />
            <div className='requests-page'>
                <h1>Friend Requests</h1>
                {requests.length > 0 ? (
                    <ul>
                        {requests.map((request) => (
                            <li key={request.id}>
                                <Link to={`/userProfile/${request.requester.id}`}>
                                    {request.requester.username}
                                </Link> - 
                                <button onClick={() => handleResponse(request.id, true)}>Accept</button>
                                <button onClick={() => handleResponse(request.id, false)}>Decline</button>
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>You have no friend requests.</p>
                )}
            </div>
        </div>
    );
};

export default GetFriendRequests;