# Twitter Clone API

This project is a simple Twitter-like full-stack application built with Spring Boot and React.
It includes authentication, tweet management, comments, likes/dislikes, retweets, and a small React frontend created to test API integration and CORS behavior.

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- React
- Vite

## Features

- User registration and login
- Role-based user model
- Create, update, delete, and list tweets
- Add, update, and delete comments
- Like and dislike tweets
- Retweet and delete retweets
- React frontend running on port `3200`
- CORS configuration for frontend-backend communication

## Backend Base URL

The backend runs with this base URL:

```text
http://localhost:8080/workintech
```

## Important Endpoints

### Auth

- `POST /auth/register`
- `POST /auth/login`

### Tweet

- `POST /tweet`
- `GET /tweet/findByUserId?userId=1`
- `GET /tweet/findById?id=1`
- `PUT /tweet/{id}`
- `DELETE /tweet/{id}`

### Comment

- `POST /comment`
- `PUT /comment/{id}`
- `DELETE /comment/{id}`

### Like

- `POST /like`
- `POST /dislike`

### Retweet

- `POST /retweet`
- `DELETE /retweet/{id}`

## Authentication Rules

- `POST /auth/register` and `POST /auth/login` are public
- `GET /tweet/findByUserId` is public
- Other protected endpoints require authentication

The project currently uses HTTP Basic authentication for secured routes.

## Database Configuration

Backend uses PostgreSQL with the following local settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=123321
```

If needed, update these values in:

`src/main/resources/application.properties`

## Running the Backend

From the project root:

```bash
./mvnw spring-boot:run
```

Or run tests:

```bash
./mvnw test
```

## Running the Frontend

Frontend files are inside the `frontend` folder.

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:

```text
http://localhost:3200
```

## Frontend Demo Purpose

The React frontend fetches tweets from:

```text
http://localhost:8080/workintech/tweet/findByUserId
```

and displays them on the screen.

This was mainly added to observe and solve CORS issues between a React frontend and a Spring Boot backend.

## Sample Request Bodies

### Register

```json
{
  "username": "baris123",
  "email": "baris@example.com",
  "password": "123456",
  "bio": "Backend developer"
}
```

### Login

```json
{
  "username": "baris123",
  "password": "123456"
}
```

### Create Tweet

```json
{
  "content": "My first tweet"
}
```

### Create Comment

```json
{
  "tweetId": 1,
  "content": "Nice tweet"
}
```

### Like Tweet

```json
{
  "tweetId": 1
}
```

### Retweet

```json
{
  "tweetId": 1,
  "comment": "Sharing this tweet"
}
```

## Notes

- The project is intentionally simple and educational
- The frontend is minimal and mainly used for API integration testing
- The application currently contains a basic test setup and can be extended with integration tests
