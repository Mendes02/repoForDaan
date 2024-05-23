export async function replyToFriendshipRequest(friendshipRequestId, replyValue) {
    const token = sessionStorage.getItem("accessToken");
    const url = `http://localhost:8080/friendships`;
    const body = {
        friendshipRequestId,
        replyValue
    };

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(body)
    };

    try {
        const response = await fetch(url, requestOptions);

        // Check if the response is successful
        if (response.ok) {
            return { status: 'ok' };
        } else if (response.status === 401) {
            // Handle unauthorized error
            return { status: 'unauthorized', error: 'You are not authorized to perform this action.' };
        } else if (response.status === 404) {
            // Handle not found error
            return { status: 'not_found', error: 'Friendship request does not exist.' };
        } else if (response.status === 400) {
            // Handle bad request error
            return { status: 'bad_request', error: 'Friendship already exists or the request is invalid.' };
        } else {
            // Handle other statuses
            throw new Error(`HTTP error! status: ${response.status}`);
        }
    } catch (error) {
        console.error("There was an error!", error);
        return { status: 'error', error: error.message };
    }
}
