# Framework Architecture

This document illustrates the architecture of the selenium-testng-cucumber automation framework.

```mermaid
flowchart TB
  subgraph Repo[AutomationFrameworkAssignment]
    direction TB
    subgraph Module[selenium-testng-cucumber]
      direction TB
      Runner[Runner - TestRunner and per feature runners]
      Features[Features - feature files]
      Steps[Step Definitions]
      Hooks[Hooks - before and after]
      Listeners[Listeners - FeaturePathListener and TestNG listeners]
      Utils[Utils - DriverFactory DriverManager JsonDataReader TestDataLoader]
      Reports[Reports - cucumber html and json and extent]
      TestNG[TestNG - testng.xml]
      Pom[pom.xml]
    end
    CI[CI - Maven] --> Module
    IDE[IntelliJ] --> Module
  end

  TestNG --> Listeners
  Listeners --> Runner
  Runner --> Features
  Runner --> Steps
  Runner --> Hooks
  Hooks --> Utils
  Steps --> Utils
  Hooks --> DriverMgr[DriverManager]
  DriverMgr --> WebDriver[WebDriver thread local]
  Utils --> TestData[Json test data]
  Runner --> Reports
  Reports --> CI
