@chrome @wip
Feature: Checkout Feature

  Scenario: Checkout Scenario Sauce Labs Backpack
    Given User is on "swag_labs" login page
    And User logs in with username "standard_user" and password "secret_sauce"
    And User adds "sauce-labs-backpack" to cart
    And User clicks on shopping cart
    And User clicks on checkout button
    And User gives firstname "ali"
    And User gives lastname "kerem"
    And user gives postcode "170123"
    And User clicks on continute button
    And User clicks on finish button
    Then User verifies successful message "checkout_expected_success_msg"
