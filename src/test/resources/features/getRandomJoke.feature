Feature: Random Joke
  As a user
  I want to get a random joke
  So that I can display the joke on my application

  Background:
    Given the TC01 base URL is "https://official-joke-api.appspot.com"

  Scenario: Get a random joke and verify response structure
    When I send a TC01 GET request to "/random_joke"
    Then the TC01 response status code should be 200
    And  the TC01 response should contain the following fields:
      | field     |
      | type      |
      | setup     |
      | punchline |
      | id        |
    And the TC01 response field "type" should not be empty
    And the TC01 response field "setup" should not be empty
    And the TC01 response field "punchline" should not be empty
    And the TC01 response field "id" should be an integer