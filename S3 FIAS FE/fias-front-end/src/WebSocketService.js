import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

class WebSocketService {
    constructor() {
        this.client = null;
        this.connected = false;
    }

    connect(userId, onMessageReceived) {
        const socket = new SockJS('http://localhost:8080/chat');
        this.client = new Client({
            webSocketFactory: () => socket,
            onConnect: frame => {
                this.connected = true;
                console.log('Connected: ' + frame);

                this.client.subscribe(`/user/${userId}/queue/messages`, message => {
                    onMessageReceived(JSON.parse(message.body));
                });
            },
            onStompError: error => {
                console.error("Error in connection: " + error);
                this.connected = false;
            }
        });

        this.client.activate();
    }

    disconnect() {
        if (this.client) {
            this.client.deactivate();
        }
        this.connected = false;
        console.log("Disconnected");
    }

    sendMessage(receiverId, senderId, content)
    {
        if (this.client && this.connected) {
            const chatMessage = {
            sender: senderId,
            receiver: receiverId,
            content: content
            };
            this.client.publish({
                destination: "/app/chat.sendMessage",
                body: JSON.stringify(chatMessage)
            });
        }
    }
}
export default new WebSocketService();
