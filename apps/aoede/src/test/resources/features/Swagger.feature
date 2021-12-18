@Swagger
Feature: Verify swagger endpoints are up and running
### Verify the setup of swagger

@Positive
Scenario: access swagger ui
### verify swagger ui is present in the aoede application

When request "/swagger-ui.html" from aoede
Then the aoede response has a status code of 200

@Positive
Scenario: access swagger docs
### verify swagger docs are present in the aoede application

When request "/v2/api-docs" from aoede
Then the aoede response has a status code of 200