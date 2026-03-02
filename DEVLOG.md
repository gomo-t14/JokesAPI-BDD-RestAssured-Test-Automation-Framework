# DEVLOG FOR TEST FRAMEWORK
## Setting up github repository 
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
2. Write the analysis im tabular and good md format for easier visualization 



