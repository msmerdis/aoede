@Swagger
Feature: Verify swagger endpoints are up and running
### Verify the setup of swagger

Scenario: access swagger ui
When request "/swagger-ui.html" from aoede
Then the aoede response has a status code of 200

Scenario: access swagger docs
When request "/v2/api-docs" from aoede
Then the aoede response has a status code of 200