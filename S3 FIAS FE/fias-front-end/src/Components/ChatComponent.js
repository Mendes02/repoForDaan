import React, { useEffect, useState } from 'react';
import WebSocketService from '../WebSocketService';

const ChatComponent = ({ userId }) => {
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState('');
    const [receiverId, setReceiverId] = useState('');

    const sendMessage = () => {
        if (newMessage.trim() !== '' && receiverId.trim() !== '') {
            WebSocketService.sendMessage(receiverId, newMessage);
            setMessages(prevMessages => [...prevMessages, { sender: userId, content: newMessage }]);
            setNewMessage('');
        }
    };

    const handleNewMessageChange = (event) => {
        setNewMessage(event.target.value);
    };

    const handleReceiverIdChange = (event) => {
        setReceiverId(event.target.value);
    };

    return (
        <div>
            <div className="chat-messages">
                {messages.map((message, index) => (
                    <div key={index} className={`message ${message.sender === userId ? 'sent' : 'received'}`}>
                        {message.content}
                    </div>
                ))}
            </div>
            <div className="chat-input">
                <input
                    type="text"
                    value={receiverId}
                    onChange={handleReceiverIdChange}
                    placeholder="Enter receiver's ID"
                />
                <textarea
                    value={newMessage}
                    onChange={handleNewMessageChange}
                    placeholder="Type a message..."
                />
                <button onClick={sendMessage}>Send</button>
            </div>
        </div>
    );
};

export default ChatComponent;