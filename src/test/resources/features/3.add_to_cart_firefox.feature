#3. Ürün Sepete Ekleme
#Login olur.
#
#İlk ürünü sepete ekler.
#
#Sepette ürün sayısının 1 olduğunu doğrular.
@firefox
Feature: Product add
  Scenario: Adding Sauce Labs Backpack to cart
    Given User is on "swag_labs" login page
    And User logs in with username "standard_user" and password "secret_sauce"
    And User adds "sauce-labs-backpack" to cart
    Then User verifies cart count is 1