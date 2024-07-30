# DEI Management System - DMS

## Dependencies

- Require download
  - [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
  - [Maven](https://maven.apache.org/download.cgi)
  - [Node 14+](https://nodejs.org/en/) ([Node Version Manager](https://github.com/nvm-sh/nvm) recommended)
  - [Docker](https://www.docker.com/)
- No download required
  - [Spring-boot](https://spring.io/)
  - [Vue.js](https://vuejs.org/)


## Run Locally

Clone the project

```bash
git clone git@gitlab.rnl.tecnico.ulisboa.pt:<REPO>
```

Go to the project directory

```bash
cd DMS/
```

### Database

To run the database with Docker (recommended), run the following command:

```bash
docker compose up
```

Alternatively, you can create services that will be run in the background:

```bash
docker compose up -d
```

To stop the database, run the following command:

```bash
docker compose down
```

### Backend

Create a copy of the `application-local.properties` file.

```bash
cp ./backend/src/main/resources/application-local.properties.example ./backend/src/main/resources/application-local.properties
```

If you're running your database using Docker, the datasource variables should match the ones in `Docker-compose.yml`.

To build and run the backend, execute the following commands:

```bash
cd ./backend
mvn clean spring-boot:run
```

## Frontend

Create a copy of the `example.env` file named `.env`.

```bash
cp ./frontend/example.env ./frontend/.env
```

Now, you need to install the dependencies:

```bash
cd ./frontend
npm i
```

To run the frontend, run the following command:

```bash
npm run dev
```

Access http://localhost:8081

## Access the Database

In order to access the database, you can use the following command:

```bash
mysql -u root -p<password> -h 127.0.0.1 -P 7654 dmsdb
```