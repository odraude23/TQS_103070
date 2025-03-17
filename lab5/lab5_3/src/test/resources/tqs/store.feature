Feature: search a book

    Scenario: search a book by title
        Given I am on https://cover-bookstore.onrender.com home page
        When I search for "The Lightning Thief" in the search bar
        And I click on the book titled "The Lightning Thief"
        Then I should be in the book details page for "The Lightning Thief"

    Scenario: search a book by author 'J.K. Rowling'
        Given I am on https://cover-bookstore.onrender.com home page
        When I search for "J.K. Rowling" in the search bar
        Then I should get 1 book

    Scenario: search for an invalid book
        Given I am on https://cover-bookstore.onrender.com home page
        When I search for "Matrix" in the search bar
        Then I should get 0 book