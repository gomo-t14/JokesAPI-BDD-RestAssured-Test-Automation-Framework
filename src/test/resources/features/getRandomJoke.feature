Feature: Random Joke
  As a user
  I want to get a random joke
  So that I can display the joke on my application

  Scenario: Get a random joke and verify response structure
    Given the base URL is "https://official-joke-api.appspot.com"
    When I send a GET request to "/random_joke"
    Then the response status code should be 200