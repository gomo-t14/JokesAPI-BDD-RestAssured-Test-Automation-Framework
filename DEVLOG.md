# DEVLOG FOR TEST FRAMEWORK
## Setting up gitHub repository 
1. Setting connection to remote repository faced connectivity issues
2. GitHub issue fix now analyzing the Jokes API


## Jokes API endpoints and suggested testing techniques
BASE_URI = https://official-joke-api.appspot.com

| **USE CASE**                        | **ENDPOINT**                                              | **SUGGESTED TECHNIQUE**                                            | **TEST CASES (Bulletproof with 3 Examples)**                                                                                                                                                                                    | **REASON / LINE OF THINKING**                                                              |
| ----------------------------------- | --------------------------------------------------------- | ------------------------------------------------------------------ |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| ------------------------------------------------------------------------------------------ |
| **Grab a random joke**              | `/random_joke` or `/jokes/random`                         | Equivalence Partitioning, Positive               | - Verify 200 OK and JSON contains `id`, `type`, `setup`, `punchline`.<br> - Use invalid URL `/jokes/randomm` → Expect 404 Not Found.<br> - Validate schema fields and data types are correct.                                   | Ensure endpoint always returns a valid joke object and handles incorrect paths gracefully. |
| **Get joke types**                  | `/types`                                                  | Boundary Value & Data Validation                                   | - Verify response returns non-empty array (e.g., `["programming","general","knock-knock"]`).<br> - Send POST request → Expect 405 Method Not Allowed.<br> - Validate response content type = `application/json`.                | Confirm API correctly lists available categories for filtering jokes.                      |
| **Grab ten random jokes**           | `/random_ten` or `/jokes/ten`                             | Boundary Value Analysis, Functional Testing                        | - verify exactly 10 jokes are returned.<br> - Each joke has valid `id`, `type`, `setup`, `punchline` fields.<br> - Modify endpoint `/jokes/tenn` → Expect 404 Not Found.                                                        | Validate the endpoint returns the expected batch size and data consistency.                |
| **Grab any number of random jokes** | `/jokes/random/<any-number>`                              | Equivalence Partitioning, Boundary Value Analysis, Negative Testing | - `/jokes/random/5` → Returns 5 jokes.<br> - `/jokes/random/0` → Expect 400 or empty array.<br> - /jokes/random/10000` → Expect 400 or API limit error.                                                                         | Test dynamic response sizes, API handles invalid or extreme inputs gracefully.             |
| **Grab jokes by type (random)**     | `/jokes/:type/random` (e.g., `/jokes/programming/random`) | Decision Table, Error Guessing                                     | - `/jokes/programming/random` → Returns 1 programming joke.<br> - `/jokes/invalid/random` → Expect 404 or empty result.<br> - Case sensitivity test `/jokes/Programming/random` → Should still work or return meaningful error. | Verify filtering works by type, API handles invalid types and case sensitivity.            |
| **Grab jokes by type (ten)**        | `/jokes/:type/ten` (e.g., `/jokes/general/ten`)           | Decision Table, Boundary Testing                                   | - `/jokes/general/ten` → Returns 10 jokes of `type="general"`.<br> - Verify count = 10 and all match the specified type.<br> - `/jokes/unknown/ten` → Expect 404 or empty response.                                             | Ensure batch retrieval works per type, prevents invalid types from returning data.         |
| **Grab joke by ID**                 | `/jokes/:id`                                              | Boundary Value Analysis, Equivalence Partitioning, Error Guessing  | - `/jokes/1` → Returns a valid joke object.<br> - `/jokes/999999` → Expect 404 Not Found.<br> - `/jokes/abc` → Expect 400 Bad Request (invalid ID format).                                                                      | Confirm specific joke retrieval works, API handles invalid or non-existent IDs correctly.  |


### In this section of test case design AI was used to :
1. tabularize the data of the outcome of the API analysis 
2. Write the analysis in table and good md format for easier visualization 

## Test cases to be Implemented 
1. here in order to maximize choosing good test cases a combination of sets of these classes will be employed each  **technique** will be exercised at least once and each **endpoint will be tested**

#### Test Cases
**NB: Each endpoint has been set as a requirement and given corresponding REQ ID**

| REQ_ID | TEST_ID | Requirement (from Endpoint) | Sample Input Data | Expected Output |
|--------|---------|----------------------------|-------------------|-----------------|
| REQ-01 | TC-01 | Grab a single random joke | `/random_joke` or `/jokes/random` | HTTP 200, JSON object with `id`, `type`, `setup`, `punchline` |
| REQ-02 | TC-02 | Get all joke types | `/types` | HTTP 200, JSON array of categories (non-empty) |
| REQ-03 | TC-03 | Grab exactly ten random jokes | `/random_ten` or `/jokes/ten` | HTTP 200, JSON array of 10 jokes, each with `id`, `type`, `setup`, `punchline` |
| REQ-04 | TC-04 | Grab a specific number of random jokes (valid) | `/jokes/random/5` | HTTP 200, JSON array of 5 jokes |
| REQ-04 | TC-05 | Grab zero jokes (boundary case) | `/jokes/random/0` | HTTP 400 Bad Request or empty array |
| REQ-04 | TC-06 | Grab maximum allowed jokes (boundary case) | `/jokes/random/250` | HTTP 200, JSON array of 250 jokes |
| REQ-04 | TC-07 | Grab too many jokes (invalid input) | `/jokes/random/1000` | HTTP 400 Bad Request or error message |
| REQ-05 | TC-08 | Grab a joke by type (random, valid type) | `/jokes/programming/random` | HTTP 200, JSON object with `type="programming"` |
| REQ-05 | TC-09 | Grab a joke by type (ten, valid type) | `/jokes/programming/ten` | HTTP 200, JSON array of 10 jokes with `type="programming"` |
| REQ-05 | TC-10 | Grab a joke by type (invalid type) | `/jokes/invalid/random` | HTTP 404 Not Found or empty array |
| REQ-06 | TC-11 | Grab a joke by ID (valid ID) | `/jokes/1` | HTTP 200, JSON object with `id=1` |
| REQ-06 | TC-12 | Grab a joke by ID (non-existent ID) | `/jokes/999999` | HTTP 404 Not Found |
| REQ-06 | TC-13 | Grab a joke by ID (invalid ID format) | `/jokes/abc` | HTTP 400 Bad Request |

**Based on the requirements of 3 BDD test case each test case chosen will be based on the residual risk given that the api does not work**
The goal of the api is to provide jokes to users the biggest assumed risk is not to be able to provide users jokes when asked,
given this all negative edge cases will be disregarded 
so the user will either want 1 joke, many jokes , a specific joke , however I have thought to implement a 4th case for nominal data like category of joke for the 4th cases


Result **, TC01, TC04,TC08,TC11** will be implemented
### In this section of test case design AI was used to :
1. Tabulate the results of analysis
2. classify and group end point into requirements
3. write proper markdown table in good format