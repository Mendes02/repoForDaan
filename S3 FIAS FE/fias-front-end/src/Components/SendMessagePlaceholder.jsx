import { useState, useEffect } from "react";
import getFriends from "../APILayer/getFriends";

const SendMessagePlaceholder = (props) => {
  const [message, setMessage] = useState('');
  const [friends, setFriends] = useState([]);
  const [selectedFriendId, setSelectedFriendId] = useState('');

  const fetchFriends = async () => {
    try {
      const friendsList = await getFriends(props.userId);
      setFriends(friendsList);
    } catch (error) {
      console.error('Failed to fetch friends:', error);
      // Handle errors or show an error message to the user
    }
  };

  useEffect(() => {
    // Assuming getFriends is an async function that fetches the friend list


    if (props.userId) {
      fetchFriends();
    }
  }, [props.userId]);

  const onMessageSend = () => {
    if (!message) {
      alert('Please type a message!');
      return; // Don't forget to return after alert
    }

    props.onMessageSend({ 'text': message, 'to': selectedFriendId });
    setMessage('');
  };

  const onSubmit = (event) => {
    event.preventDefault();
    onMessageSend(); // Trigger message send on form submit
  };

  return (
    <form onSubmit={onSubmit}>
    <label htmlFor='destUsername'>Destination:</label>
      <select
        id='destUsername'
        onChange={(event) => setSelectedFriendId(event.target.value)}
        value={selectedFriendId}
        className="dropDownMenu"
      >
        <option value="">Select a friend</option>
        {friends.map(friend => (
          <option key={friend.id} value={friend.id}>{friend.username}</option>
        ))}
      </select>
      <br/>
      <br/>
      <label htmlFor='message'>Message:</label>
      <input
        id='message'
        type='text'
        onChange={(event) => setMessage(event.target.value)}
        value={message}
      />
      
      <button type="submitChat" className="">Send</button>
    </form>
  );
};

export default SendMessagePlaceholder;