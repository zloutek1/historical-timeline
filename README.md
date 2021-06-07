# historical-timeline

[![CI](https://github.com/zloutek1/historical-timeline/actions/workflows/maven.yml/badge.svg)](https://github.com/zloutek1/historical-timeline/actions/workflows/maven.yml)
[![codecov](https://codecov.io/gh/zloutek1/historical-timeline/branch/main/graph/badge.svg?token=KX0SYDY65Z)](https://codecov.io/gh/zloutek1/historical-timeline)
[![java](https://img.shields.io/badge/java%20version-11%2B-green?logo=java)]()
[![maven](https://img.shields.io/badge/maven-3.6.3-ff69b4?logo=apache%20maven)]()

The web application allows to create timelines of historical events that teachers can show to students to better learn history. The main functionality for teachers is to add new historical events (e.g. Austerlitz battle, Dec 2, 1805). Each event is represented by a date, a location, a description and an image/placeholder. Teachers can create several timelines and add events to them. All events on a timeline will be shown graphically ordered in time (no need of something complex, also some simple HTML will do it). Teachers can create some study groups (e.g. Roman Empire, Second World War, etc...) and enroll students. Teachers can add one or more timelines to each group. Students can login to the system and take part to some groups created by teachers. They will be able to see all the timelines for the specific group and add a comment to them. Searching among all events is also possible.

# Credentials

| role    | email                   | password |
|---------|-------------------------|----------|
| student | jcaraher2@home.pl       | password |
| teacher | nkeelyp@vkontakte.ru    | password |
| admin   | droggerss@wikimedia.org | password |

# How to build and run spring-mvc
In the root directory of the project run:

```shell script
mvn clean install -DskipTests && cd spring-mvc && mvn cargo:run
```

which runs a spring mvc web server accessible on http://localhost:8080/pa165 


# How to build and run REST
In the root directory of the project run:

```shell script
mvn clean install -DskipTests && cd rest && mvn cargo:run
```

which runs a spring rest web server accessible on http://localhost:8080/pa165/rest

## How to test REST

Create a new timeline by POST method
```bash
 curl -X POST -i -H "Content-Type: application/json" --data
 '{"name":"test","fromDate":"1000-02-01","toDate":"1000-02-02", "studyGroup":"UNDEFINED"}'
 http://localhost:8080/pa165/rest/timelines/create
```

Update a timeline by PUT method
```bash
curl -X PUT -i -H "Content-Type: application/json" --data
'{"name":"test","fromDate":"1000-02-01","toDate":"1000-02-02"}'
http://localhost:8080/pa165/rest/timelines/update/1
```

Delete a timeline by DELETE method
```bash
curl -X DELETE
http://localhost:8080/pa165/rest/timelines/delete/1
```

Get list of all Timelines
```bash
curl -i -X GET
http://localhost:8080/pa165/rest/timelines
```

Get list of Timelines in given time frame
```bash
curl -i -X GET
http://localhost:8080/pa165/rest/timelines/between/1000-02-01/1000-02-02
```

Get Timeline by id
```bash
curl -i -X GET
http://localhost:8080/pa165/rest/timelines/1
```

Get Timeline by name
```
curl -i -X GET
http://localhost:8080/pa165/rest/timelines/testname
```
