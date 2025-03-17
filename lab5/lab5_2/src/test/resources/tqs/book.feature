Feature: Book search

  Background: A Library
    Given I have the following books in the Library
      | title                                              |  author       | published  |
      | Percy Jackson & The Olympians: The Lightning Thief | Rick Riordan  | 2005-06-28 |  
      | Percy Jackson & The Olympians: The Sea of Monsters | Rick Riordan  | 2006-04-01 |  
      | Harry Potter and the Sorcerer’s Stone              | J.K. Rowling  | 1997-06-26 |  
      | Harry Potter and the Chamber of Secrets            | J.K. Rowling  | 1998-07-02 | 

  Scenario: Search book by author
    When the costumer searches for books with the author 'Rick Riordan'
    Then 2 books should have been found
      | title                                              |  author       | published  |
      | Percy Jackson & The Olympians: The Lightning Thief | Rick Riordan  | 2005-06-28 |  
      | Percy Jackson & The Olympians: The Sea of Monsters | Rick Riordan  | 2006-04-01 | 

  Scenario: Search books by publication year
    When the customer searches for books published between 1996-01-01 and 1999-01-01
    Then 2 books should have been found
      | title                                              |  author       | published  |
      | Harry Potter and the Sorcerer’s Stone              | J.K. Rowling  | 1997-06-26 |  
      | Harry Potter and the Chamber of Secrets            | J.K. Rowling  | 1998-07-02 |

  Scenario: Search books by Title
    When the costumer searches for books with the title 'Harry Potter and the Sorcerer’s Stone'
    Then 1 books should have been found
      | title                                              |  author       | published  |
      | Harry Potter and the Sorcerer’s Stone              | J.K. Rowling  | 1997-06-26 |

  Scenario: No book found
    When the costumer searches for books with the author 'Fernando Pessoa'
    Then 0 books should have been found
      | title                                              |  author       | published  |