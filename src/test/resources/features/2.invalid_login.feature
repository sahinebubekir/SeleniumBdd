@smoke
Feature: Invalid login

  Background: Going to the web page
    Given User is on "swag_labs" login page

  Scenario: Invalid username login scenario
    And User logs in with username "usernames" and password "secret_sauce"
    Then The user should see an error message
    Then User closes the driver

  Scenario: Invalid password login scenario
    And User logs in with username "standard_user" and password "secret_sauce1"
    Then The user should see an error message
    Then User closes the driver

  Scenario: Invalid username and password login scenario
    And User logs in with username "usernames" and password "secret_sauce2"
    Then The user should see an error message
    Then User closes the driver