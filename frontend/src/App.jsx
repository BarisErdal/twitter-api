import { useState } from "react";

const endpoint = "http://localhost:8080/workintech/tweet/findByUserId";

export default function App() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [userId, setUserId] = useState("1");
  const [tweets, setTweets] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function fetchTweets() {
    setLoading(true);
    setError("");

    try {
      const authHeader = `Basic ${btoa(`${username}:${password}`)}`;
      const requestUrl = `${endpoint}?userId=${encodeURIComponent(userId)}`;
      const response = await fetch(requestUrl, {
        method: "GET",
        headers: {
          Authorization: authHeader,
          Accept: "application/json"
        }
      });

      if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
      }

      

      const data = await response.json();
      setTweets(Array.isArray(data) ? data : []);
    } catch (err) {
      setTweets([]);
      setError(err.message || "Tweets could not be fetched.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="app-shell">
      <section className="hero-card">
        <p className="eyebrow">React + Spring Boot</p>
        <h1>Tweet API</h1>
      

        <div className="credentials-card">
          <label>
            Username
            <input
              type="text"
              value={username}
              onChange={(event) => setUsername(event.target.value)}
              placeholder="Enter your username"
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="Enter your password"
            />
          </label>

          <label>
            User ID
            <input
              type="number"
              min="1"
              value={userId}
              onChange={(event) => setUserId(event.target.value)}
              placeholder="Enter user id"
            />
          </label>

          <button type="button" onClick={fetchTweets} disabled={loading}>
            {loading ? "Loading tweets..." : "Fetch Tweets"}
          </button>
        </div>

        <p className="endpoint">
          Request URL: <span>{endpoint}?userId={userId || "..."}</span>
        </p>
      </section>

      <section className="feed-card">
        <div className="feed-header">
          <h2>User Tweets</h2>
          <span>{tweets.length} items</span>
        </div>

        {error ? <p className="status error">{error}</p> : null}
        {!error && !loading && tweets.length === 0 ? (
          <p className="status">No tweets loaded yet.</p>
        ) : null}

        <div className="tweet-list">
          {tweets.map((tweet) => (
            <article key={tweet.id} className="tweet-card">
              <div className="tweet-meta">
                <strong>@{tweet.user?.username ?? "unknown"}</strong>
                <span>Tweet #{tweet.id}</span>
              </div>

              <p className="tweet-content">{tweet.content}</p>

              <div className="tweet-stats">
                <span>{tweet.like_count} likes</span>
                <span>{tweet.comment_count} comments</span>
                <span>{tweet.retweet_count} retweets</span>
              </div>
            </article>
          ))}
        </div>
      </section>
    </main>
  );
}
