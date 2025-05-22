@smoke @chrome
Feature: Login Feature Chrome

  Scenario Outline: Successful login scenario

    Given User is on "swag_labs" login page
    And When user logs in with username "<usernames>" and password "secret_sauce"
    Then Then the user should see a success message
    Then User closes the driver
    Examples: usernames
      | usernames               |
      | standard_user           |
#      | locked_out_user         |
#      | problem_user            |
#      | performance_glitch_user |
#      | error_user              |
#      | visual_user             |