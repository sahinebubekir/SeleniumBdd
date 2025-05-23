#2. Hatalı Login Senaryosu
#Kullanıcı "locked_out_user" ile giriş yapar.
#
#Hata mesajının ekranda çıktığını doğrular.
  @smoke @chrome @wip
  Feature: Invalid login

    Scenario: Invalid username login scenario
    Given User is on "swag_labs" login page
    And When user logs in with username "usernames" and password "secret_sauce"
    Then The user should see an error message
    Then User closes the driver

    Scenario: Invalid password login scenario
      Given User is on "swag_labs" login page
      And When user logs in with username "standard_user" and password "secret_sauce1"
      Then The user should see an error message
      Then User closes the driver

    Scenario: Invalid username and password login scenario
      Given User is on "swag_labs" login page
      And When user logs in with username "usernames" and password "secret_sauce2"
      Then The user should see an error message
      Then User closes the driver