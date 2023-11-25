# Recruit Matcher
This is a WIP of a Spring Boot API to help recruiters compare candidates using a scoring system.

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
