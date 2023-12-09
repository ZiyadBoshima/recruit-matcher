# Recruit Matcher
This is a WIP Spring Boot API to help recruiters compare candidates using a scoring system.

## AI-Powered Features
- Resume data extraction and processing
- Candidate ranking for jobs

## To Get Started
Clone the repository.
```git
git clone https://github.com/ZiyadBoshima/recruit-matcher.git
```
Change directory.
```bash
cd recruit-matcher
```
Build the project using Maven.
```bash
mvn clean install
```
This project uses MongoDB as the database, and ChatGPT for processing resumes. Add your local database credentials and ChatGPT API key to application.properties.
```env
openai.model=gpt-3.5-turbo
openai.api.url=https://api.openai.com/v1/chat/completions
openai.api.key={API_KEY}

spring.data.mongodb.database={MONGODB_DATABASE}
spring.data.mongodb.uri={MONGODB_URI}
```
Run the project.
```bash
mvn spring-boot:run
```

## API
#### Candidate Service

Method	      | Path	                    | Description
------------- | ------------------------- | ------------------------ |
GET           | /candidates/all	          | Get all candidates
GET           | /candidates/{id}          | Get specific candidate
POST          | /candidates/              | Add a single candidate manually
POST          | /candidates/submit-resume | Add a single candidate automatically using their resume

##### Job Service

Method	      | Path	                    | Description
------------- | ------------------------- | ------------------------ |
GET	          | /jobs/{id}	              | Get a specific job
POST          | /jobs/                    | Submit a job
PUT           | /jobs/                    | Update a job
DELETE        | /jobs/{id}	              | Delete a specific job
GET           | /jobs/{id}/ranked-candidates | Get all candidates ranked based on a specific job

