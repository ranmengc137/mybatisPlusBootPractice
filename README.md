# MybatisPlusBootPractice

## Overview
MybatisPlusBootPractice is a sample project demonstrating the integration of MyBatis-Plus with Spring Boot. This project showcases how to efficiently use MyBatis-Plus to simplify CRUD operations, dynamic SQL, and other database interactions in a Spring Boot environment.

## Features
- Simplified CRUD operations using MyBatis-Plus
- Dynamic SQL query capabilities
- Integration with Spring Boot for rapid development
- Example API endpoints for blog and dynamic content management
- Database schema and example data setup

## Requirements
- Java 11 or higher
- Maven 3.6+
- MySQL 5.7+ or compatible database
- Spring Boot 2.5+

## Installation
1. Clone the repository:
   ```
   git clone https://github.com/yourusername/MybatisPlusBootPractice.git
   ```
2. Navigate to the project directory:
   ```
   cd MybatisPlusBootPractice
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```

## Database Setup
Create a MySQL database and run the following schema to set up the necessary tables:

```sql
-- schema.sql
CREATE DATABASE IF NOT EXISTS mybatis_plus_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mybatis_plus_db;

CREATE TABLE blog (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  content TEXT,
  author VARCHAR(100),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE dynamic (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Make sure to update the `application.properties` or `application.yml` with your database credentials.

## Running the Application
Run the Spring Boot application using:

```
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Tests

### Blog Endpoints

#### Get all blogs

```http
GET http://localhost:8080/blog/list
Accept: application/json
```

#### Get blog by ID

```http
GET http://localhost:8080/blog/{id}
Accept: application/json
```

#### Create a new blog

```http
POST http://localhost:8080/blog/save
Content-Type: application/json

{
  "title": "Sample Blog Title",
  "content": "This is the content of the sample blog.",
  "author": "Author Name"
}
```

#### Update a blog

```http
PUT http://localhost:8080/blog/update
Content-Type: application/json

{
  "id": 1,
  "title": "Updated Blog Title",
  "content": "Updated content.",
  "author": "Author Name"
}
```

#### Delete a blog

```http
DELETE http://localhost:8080/blog/delete/{id}
```

---

### Dynamic Endpoints

#### Get all dynamic entries

```http
GET http://localhost:8080/dynamic/list
Accept: application/json
```

#### Get dynamic entry by ID

```http
GET http://localhost:8080/dynamic/{id}
Accept: application/json
```

#### Create a new dynamic entry

```http
POST http://localhost:8080/dynamic/save
Content-Type: application/json

{
  "name": "Dynamic Name",
  "description": "Description of dynamic entry"
}
```

#### Update a dynamic entry

```http
PUT http://localhost:8080/dynamic/update
Content-Type: application/json

{
  "id": 1,
  "name": "Updated Dynamic Name",
  "description": "Updated description"
}
```

#### Delete a dynamic entry

```http
DELETE http://localhost:8080/dynamic/delete/{id}
```

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
