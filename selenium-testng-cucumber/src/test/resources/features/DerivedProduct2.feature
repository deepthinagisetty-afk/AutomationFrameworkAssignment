Feature: Derived product1 Automation

  Scenario: Navigate to Derived Product2 Page and Find duplicate links in Footer
    Given I navigate to Derived Product2 Page
    When Scroll to Footer of the Page
    When I capture all the Hyperlinks in Footer
    Then I find duplicate links in Footer

