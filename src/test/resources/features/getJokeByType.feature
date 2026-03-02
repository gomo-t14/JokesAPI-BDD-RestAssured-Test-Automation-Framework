@api @TC08
Feature: TC08 - Get a random joke by type
  REQ-05: The API must return a joke whose type field matches
  the requested type, along with all required fields.

  Background:
    Given the base URL is "https://official-joke-api.appspot.com"

  Scenario Outline: Get a random joke by type and verify fields
    When I send a GET request to "/jokes/<jokeType>/random"
    Then the  response status code should be 200
    And  the response field "type" should equal "<jokeType>"
    And  the response should contain the following fields:
      | field     |
      | type      |
      | setup     |
      | punchline |
      | id        |
    And the  response field "setup" should not be empty
    And the  response field "punchline" should not be empty
    And the  response field "id" should be an integer

    Examples:
      | jokeType    |
      | programming |
      | general     |