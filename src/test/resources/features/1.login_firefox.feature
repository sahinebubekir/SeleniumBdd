@smoke @firefox
Feature: Login Feature Chrome

  Scenario Outline: Successful login scenario

    Given User is on "swag_labs" login page
    And User logs in with username "<usernames>" and password "secret_sauce"
    Then Then the user should see a success message
    Then User closes the driver
    Examples: usernames
      | usernames               |
      | standard_user           |
      | problem_user            |
      | performance_glitch_user |
      | error_user              |
      | visual_user             |