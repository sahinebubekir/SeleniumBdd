Feature: Sorting Products

  Background: Login steps
    Given User is on "swag_labs" login page
    And User logs in with username "standard_user" and password "secret_sauce"


  Scenario: Sorting products high to low
    And User sorts products "Price (high to low)"
    Then User verifies the products are sorted "high to low"


  Scenario: Sorting products low to high
    And User sorts products "Price (low to high)"
    Then User verifies the products are sorted "low to high"