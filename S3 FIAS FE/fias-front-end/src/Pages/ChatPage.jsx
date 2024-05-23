import React, { useState, useEffect } from 'react';
import { Client } from '@stomp/stompjs';
import { v4 as uuidv4 } from 'uuid';
import ChatMessagesPlaceholder from '../Components/ChatMessagesPlaceholder';
import SendMessagePlaceholder from '../Components/SendMessagePlaceholder';
import { jwtDecode } from 'jwt-decode';
import Navbar from '../Components/Navbar/Navbar';
import getUser from '../APILayer/getUser';



const ChatPage = () => {
    const [stompClient, setStompClient] = useState(null);
    const [userId, setUserId] = useState(null);
    const [messagesReceived, setMessagesReceived] = useState([]);
    const [user,setUser] = useState();

    useEffect(() => {
        const encodedAccessToken = sessionStorage.getItem("accessToken");
        if (encodedAccessToken) {
            const decodedToken = jwtDecode(encodedAccessToken);
            setUserId(decodedToken.userId);
            setupStompClient(decodedToken.userId);
            fetchUserData(decodedToken.userId);
            console.log(user);
        }
        return () => {
          if (stompClient) {
              stompClient.unsubscribe('/topic/publicmessages');
              stompClient.unsubscribe(`/user/${userId}/queue/inboxmessages`);
              stompClient.deactivate(); // This should disconnect and clean up subscriptions
          }
      };
    }, []);

    const fetchUserData = async (userId) => {
        if (userId) {
            try {
                const userData = await getUser(userId);
                setUser(userData);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        }
    };

    const setupStompClient = (userId) => {
        const client = new Client({
            brokerURL: 'ws://localhost:8080/ws',
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        client.onConnect = () => {
            client.subscribe('/topic/publicmessages', (message) => {
                onMessageReceived(message);
            });

            client.subscribe(`/user/${userId}/queue/inboxmessages`, (message) => {
                onMessageReceived(message);
            });
        };

        client.activate();
        setStompClient(client);
    };

    const sendMessage = (newMessage) => {
        const payload = {
            'id': uuidv4(), 
            'from': userId, 
            'to': newMessage.to, 
            'text': newMessage.text,
            'fromName': user ? user.username : 'Me'
        };

        if (stompClient && stompClient.connected) {
            if (payload.to) {
                stompClient.publish({ destination: `/user/${payload.to}/queue/inboxmessages`, body: JSON.stringify(payload) });
            } else {
                stompClient.publish({ destination: '/topic/publicmessages', body: JSON.stringify(payload) });
            }
        }
    };

    const onMessageReceived = (message) => {
      const parsedMessage = JSON.parse(message.body);
      setMessagesReceived((messagesReceived) => {
          // Check for existing message to prevent duplicates
          if (!messagesReceived.some(msg => msg.id === parsedMessage.id)) {
              return [...messagesReceived, parsedMessage];
          } else {
              return messagesReceived;
          }
      });
  };

    return (
        <div className="App">
        <Navbar/>
            <div className='chatBackground'>
            <SendMessagePlaceholder userId={userId} onMessageSend={sendMessage} />
            <ChatMessagesPlaceholder username={userId} messagesReceived={messagesReceived} />
            </div>
        </div>
    );
}

export default ChatPage;

