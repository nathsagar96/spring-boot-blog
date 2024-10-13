# WordSmith

WordSmith is a powerful backend service for managing posts, comments, and categories for a blog site. This RESTful
API provides comprehensive functionality for user management, authentication, and content creation.

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [Getting Started](#getting-started)
- [Endpoints](#endpoints)
- [Error Handling](#error-handling)
- [Models](#models)
- [Contributing](#contributing)
- [License](#license)

## Features

- User management (registration, authentication, profile updates)
- Blog post creation, retrieval, update, and deletion
- Comment management
- Category organization
- Search functionality for posts
- Pagination and sorting options for list endpoints
- JWT-based authentication

## Technologies

WordSmith is built with the following technologies:

- Java 21
- Spring Boot 3
- Spring Security 6
- Maven
- PostgreSQL
- Docker Compose
- JWT (JSON Web Tokens) for authentication

## API Documentation

The complete API documentation is available in OpenAPI 3.0.1 format. You can view the full specification for detailed
information about each endpoint, request/response models, and available operations.

- To view the API documentation, navigate to `http://localhost:8080/v3/api-docs` in your browser.
- To view the Swagger UI, navigate to `http://localhost:8080/swagger-ui.html` in your browser.

## Security

The API uses JWT (JSON Web Token) for authentication. To access protected endpoints, you need to include the JWT token
in the Authorization header of your requests:

```text
Authorization: Bearer <your_jwt_token>
```

To obtain a token, use the `/api/auth/authenticate` endpoint with your username and password.

## Getting Started

1. Ensure you have Java 21 and Maven installed on your system.
2. Clone the repository:

   ```bash
   git clone https://github.com/nathsagar96/wordsmith.git
   cd wordsmith
   ```

3. Configure your jwt secret and expiration time in the `application.yml` file.
4. Build the project:

   ```bash
   mvn clean install
   ```

5. Run the application:

   ```bash
   mvn spring-boot:run
   ```

The API will be available at `http://localhost:8080` by default.

## Endpoints

Here's an overview of the main API endpoints:

### Authentication

- POST `/api/auth/register`: Register a new user
- POST `/api/auth/authenticate`: Authenticate and receive a JWT token

### Users

- GET `/api/users`: Get all users
- GET `/api/users/{userId}`: Get a specific user
- PUT `/api/users/{userId}`: Update a user
- DELETE `/api/users/{userId}`: Delete a user

### Posts

- GET `/api/posts`: Get all posts (with pagination)
- POST `/api/posts`: Create a new post
- GET `/api/posts/{postId}`: Get a specific post
- PUT `/api/posts/{postId}`: Update a post
- DELETE `/api/posts/{postId}`: Delete a post
- GET `/api/posts/user/{userId}`: Get posts by user
- GET `/api/posts/category/{categoryId}`: Get posts by category
- GET `/api/posts/search/{searchTerm}`: Search posts

### Comments

- GET `/api/comments`: Get all comments
- POST `/api/comments`: Create a new comment
- GET `/api/comments/{commentId}`: Get a specific comment
- PUT `/api/comments/{commentId}`: Update a comment
- DELETE `/api/comments/{commentId}`: Delete a comment
- GET `/api/comments/user/{userId}`: Get comments by user
- GET `/api/comments/post/{postId}`: Get comments for a post

### Categories

- GET `/api/categories`: Get all categories
- POST `/api/categories`: Create a new category
- GET `/api/categories/{categoryId}`: Get a specific category
- PUT `/api/categories/{categoryId}`: Update a category
- DELETE `/api/categories/{categoryId}`: Delete a category

## Error Handling

The API uses standard HTTP status codes and returns detailed error messages in the following format:

```json
{
  "timestamp": "2023-04-13T12:34:56.789Z",
  "status": 400,
  "error": "Bad Request",
  "errors": [
    "Error message 1",
    "Error message 2"
  ],
  "message": "Error summary",
  "path": "/api/endpoint"
}
```

## Models

The API uses the following main data models:

- User
- Post
- Comment
- Category

Refer to the API documentation for detailed information about the properties and constraints of each model.

## Contributing

We welcome contributions to the WordSmith API! Please follow these steps to contribute:

1. Fork the repository
2. Create a new branch for your feature or bug fix
3. Make your changes and commit them with clear, descriptive messages
4. Push your changes to your fork
5. Submit a pull request with a description of your changes

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for details.
