# Jokes API Test Automation Framework

A BDD API test automation framework built in Java, using Cucumber, TestNG, and REST Assured
to validate the [Official Joke API](https://official-joke-api.appspot.com).

---

## Table of Contents

- [Project Overview](#project-overview)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Setup & Installation](#setup--installation)
- [Running the Tests](#running-the-tests)
- [Test Case Design](#test-case-design)
- [API Endpoints Under Test](#api-endpoints-under-test)
- [Cucumber Techniques Used](#cucumber-techniques-used)
- [Test Results & Reporting](#test-results--reporting)
- [AI Usage Disclosure](#ai-usage-disclosure)

---

## Project Overview

This framework tests the [Official Joke API](https://official-joke-api.appspot.com) using a
Behaviour Driven Development (BDD) approach with Gherkin feature files.

The goal of the API is to deliver jokes to users on demand. Test case selection was driven
by **residual risk** — the biggest assumed risk is that users cannot retrieve jokes when
requested. With this in mind, the following use cases were prioritised for implementation:

| Test Case | Description                        |
|-----------|------------------------------------|
| TC-01     | Get a single random joke           |
| TC-04     | Get a specific number of jokes     |
| TC-08     | Get a joke by type                 |
| TC-11     | Get a specific joke by ID          |

Negative and boundary edge cases were intentionally deprioritised in favour of covering
the core user journeys across all available endpoints.

---

## Tech Stack

| Tool / Library       | Version  | Purpose                                      |
|----------------------|----------|----------------------------------------------|
| Java                 | 17       | Core language                                |
| Maven                | 3.x      | Build and dependency management              |
| Cucumber             | 7.34.2   | BDD framework — Gherkin feature files        |
| TestNG               | 7.10.2   | Test runner and assertion framework          |
| REST Assured         | 5.5.0    | HTTP client for API calls and assertions     |
| Log4j                | 2.24.3   | Logging to console and rolling file          |
| Cucumber Reporting   | 5.8.4    | HTML test report generation                  |
| Picocontainer        | 7.34.2   | Dependency injection for shared test state   |

---

## Project Structure
```
Jokes_API_Test_Automation_Framework/
├── pom.xml
├── testng.xml
└── src/
    └── test/
        ├── java/
        │   └── com/example/
        │       ├── runner/
        │       │   └── TestRunner.java
        │       ├── hooks/
        │       │   └── Hooks.java
        │       └── steps/
        │           ├── SharedContext.java
        │           ├── TC01_RandomJokeSteps.java
        │           ├── TC04_RandomCountSteps.java
        │           ├── TC08_JokeByTypeSteps.java
        │           └── TC11_JokeByIdSteps.java
        └── resources/
            ├── log4j2.xml
            └── features/
                ├── TC01_random_joke.feature
                ├── TC04_random_count.feature
                ├── TC08_joke_by_type.feature
                └── TC11_joke_by_id.feature
```

---

## Setup & Installation

### Prerequisites

- Java 17 installed — verify with `java -version`
- Maven installed — verify with `mvn -version`
- IntelliJ IDEA (recommended) or any Java IDE
- Internet access to reach `https://official-joke-api.appspot.com`

### Clone the Repository
```bash
git clone https://github.com/<your-username>/Jokes_API_Test_Automation_Framework.git
cd Jokes_API_Test_Automation_Framework
```

### Install Dependencies
```bash
mvn clean install -DskipTests
```

---

## Running the Tests

### Run All Implemented Test Cases
```bash
mvn test
```

### Run a Specific Test Case by Tag
```bash
mvn test -Dcucumber.filter.tags="@TC01"
mvn test -Dcucumber.filter.tags="@TC04"
mvn test -Dcucumber.filter.tags="@TC08"
mvn test -Dcucumber.filter.tags="@TC11"
```

### Run Multiple Tags
```bash
mvn test -Dcucumber.filter.tags="@TC01 or @TC11"
```

### Test Reports

After running, reports are generated at:
```
target/cucumber-reports/report.html   ← HTML report
target/cucumber-reports/cucumber.json ← JSON report (for CI integration)
logs/test.log                         ← Rolling log file
```

Open `target/cucumber-reports/report.html` in a browser to view the full test report.

---

## Test Case Design

### API Under Test
```
BASE URI: https://official-joke-api.appspot.com
```

### Requirements & Test Case Mapping

| REQ_ID | TEST_ID | Requirement                            | Endpoint                  | Expected Output                                          |
|--------|---------|----------------------------------------|---------------------------|----------------------------------------------------------|
| REQ-01 | TC-01   | Grab a single random joke              | `/random_joke`            | HTTP 200, JSON with `type`, `setup`, `punchline`, `id`   |
| REQ-04 | TC-04   | Grab a specific number of random jokes | `/jokes/random/5`         | HTTP 200, JSON array of exactly 5 jokes                  |
| REQ-05 | TC-08   | Grab a joke by type                    | `/jokes/programming/random` | HTTP 200, JSON object with `type="programming"`        |
| REQ-06 | TC-11   | Grab a joke by ID                      | `/jokes/1`                | HTTP 200, JSON object with `id=1`                        |

### Test Case Selection Rationale

Test case selection was based on **residual risk analysis**. The primary purpose of the API
is to deliver jokes to users. The highest-risk failure scenarios are therefore:

- A user requests a single joke and receives nothing — **TC-01**
- A user requests multiple jokes and receives the wrong count — **TC-04**
- A user requests jokes of a specific category and receives the wrong type — **TC-08**
- A user requests a specific joke by ID and receives nothing — **TC-11**

Negative and boundary cases (TC-05, TC-06, TC-07, TC-10, TC-12, TC-13) were assessed as
lower residual risk and were excluded from the initial implementation scope.

### Response Structure

All joke endpoints return JSON in the following structure:
```json
{
  "type": "general",
  "setup": "Why did the cookie cry?",
  "punchline": "It was feeling crumby.",
  "id": 321
}
```

---

## Cucumber Techniques Used

Each feature file demonstrates a different Cucumber technique as required:

| Feature File                    | TC    | Techniques Applied                                      |
|---------------------------------|-------|---------------------------------------------------------|
| `TC01_random_joke.feature`      | TC-01 | `Scenario`, `Background`, `Data Table`, `Tags`          |
| `TC04_random_count.feature`     | TC-04 | `Scenario Outline`, `Examples`, `Background`, `Tags`    |
| `TC08_joke_by_type.feature`     | TC-08 | `Scenario Outline`, `Examples`, `Data Table`, `Tags`    |
| `TC11_joke_by_id.feature`       | TC-11 | `Scenario Outline`, `Examples`, `Data Table`, `Tags`    |

### Shared Context & Dependency Injection

A `SharedContext` class is used alongside **Picocontainer** to share the `baseUrl` and
`response` state between step definition classes without using static variables. Each step
class receives `SharedContext` via constructor injection, keeping the code modular and
each step file fully self-contained.

---

## Test Results & Reporting

Logs are written to both the console and a rolling file at `logs/test.log`.
Each scenario is wrapped with Before/After hooks that log the scenario name and
pass/fail status for easy traceability.

Example log output:
```
12:01:05.123 [main] INFO  com.example.hooks.Hooks - ================================================
12:01:05.124 [main] INFO  com.example.hooks.Hooks - START: Get a random joke and verify response structure | Tags: [@api, @TC01]
12:01:05.125 [main] INFO  com.example.steps.TC01_RandomJokeSteps - [TC01] GET https://official-joke-api.appspot.com/random_joke
12:01:05.891 [main] INFO  com.example.steps.TC01_RandomJokeSteps - [TC01] Status code — expected: 200 | actual: 200
12:01:05.892 [main] INFO  com.example.hooks.Hooks - END: Get a random joke and verify response structure | Status: PASSED
12:01:05.893 [main] INFO  com.example.hooks.Hooks - ================================================
```

---

## AI Usage Disclosure

AI assistance was used transparently throughout this project. The following areas involved
AI tooling:

| Area                          | AI Involvement                                                                 |
|-------------------------------|--------------------------------------------------------------------------------|
| Dependency setup              | `pom.xml` dependencies suggested and configured with AI assistance             |
| `SharedContext` design        | Concept of Picocontainer DI injection introduced by AI — fully understood and implemented by developer |
| `responseContainsFields()`    | Logic was too complex to implement independently — AI used to implement the method |
| `verifyArrayLength()`         | AI used to implement array length assertion                                    |
| Hooks & config XML files      | AI used to format log output pattern and set up `testng.xml` / `log4j2.xml`   |
| API analysis table            | AI used to tabularise endpoint analysis into markdown table                    |
| Test case grouping            | AI used to classify endpoints into REQ IDs and format the test case table      |
| README                        | AI used to format and write the README to the developer's specification        |

> All AI-generated code was reviewed, understood, and validated by the developer before
> inclusion in the project.