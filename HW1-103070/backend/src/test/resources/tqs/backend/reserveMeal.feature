Feature: reserve a meal

    Scenario: reserve a meal for lunch
        Given I am on the home page
        When I select the restaurant, clicking on the view menus button
        And I click on the reservation button
        Then I should confirm my reservation 