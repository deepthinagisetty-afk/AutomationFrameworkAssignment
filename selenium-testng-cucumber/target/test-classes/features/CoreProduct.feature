Feature: Core Product Automation

  Scenario: Navigate to Core Product Page
    Given I navigate to Core Product Page
    When I click on Shop and then click Men's
    When I click on Jackets Menu from Sub Menu Of Men
    Then I will capture prices relates to all the jackets
