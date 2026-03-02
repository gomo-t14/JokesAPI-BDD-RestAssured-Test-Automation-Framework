@api @TC04
Feature:Get a specific number of random jokes
  As a user
  I want to get a number of x jokes
  So that I can use them in my application


  Background:
    Given the base URL is "https://official-joke-api.appspot.com"

  Scenario Outline: Get a specific number of random jokes
    When I send a GET request to "/jokes/random/<count>"
    Then the response status code should be 200
    And  the response array length should be <count>

    Examples:
      | count |
      | 1     |
      | 5     |
      | 10    |