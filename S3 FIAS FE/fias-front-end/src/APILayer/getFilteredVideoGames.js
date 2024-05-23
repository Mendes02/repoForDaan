async function getFilteredVideoGames(genre, name) {
    try {
        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
            throw new Error("No access token found");
        }

        // Replace null or undefined values with the string "null"
        const genreParam = genre || "null";
        const nameParam = name || "null";

        // Construct the URL with path parameters
        const url = `http://localhost:8080/videogame/searchFilter/${genreParam}/${nameParam}`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching filtered video games:', error);
        throw error;
    }
}

export default getFilteredVideoGames;