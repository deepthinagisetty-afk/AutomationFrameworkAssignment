# Framework Architecture

Below is the architecture of the **selenium-testng-cucumber** framework:

```mermaid
flowchart TB
  subgraph Repo[AutomationFrameworkAssignment]
    direction TB
    subgraph Module[selenium-testng-cucumber]
      direction TB
      Runner[runner/\nTestRunner & per-feature runners]
      Features[resources/features\n(.feature files)]
      Steps[steps/\nStep Definitions]
      Hooks[hooks/\n@Before/@After]
      Listeners[utils/\nFeaturePathListener, TestNG listeners]
      Utils[utils/\nDriverFactory, DriverManager,\nJsonDataReader, TestDataLoader]
      Reports[Reports\n(cucumber html/json, extent)]
      TestNG[testng.xml]
      Pom[pom.xml]
    end
    CI[CI / Maven\n(mvn test, surefire)] -->|runs| Module
    IDE[IntelliJ] -->|run/debug| Module
  end

  TestNG --> Listeners
  Listeners --> Runner
  Runner -->|boots Cucumber| Features
  Runner --> Steps
  Runner --> Hooks
  Hooks --> Utils
  Steps --> Utils
  Hooks --> DriverMgr[DriverManager]
  DriverMgr --> WebDriver[WebDriver (ThreadLocal)]
  Utils --> TestData[Json testdata]
  Runner --> Reports
  Reports --> CI
