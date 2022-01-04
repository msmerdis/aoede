@Swagger
Feature: Verify swagger endpoints are up and running
### Verify the setup of swagger

@Positive
Scenario: access swagger ui
### verify swagger ui is present in the aoede application

When request "/swagger-ui.html" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "<!-- HTML for static distribution bundle build -->"
And the aoede response contains "<!DOCTYPE html>"
And the aoede response contains "<title>Swagger UI</title>"

@Positive
Scenario: access swagger docs
### verify swagger docs are present in the aoede application

When request "/v2/api-docs" from aoede
Then the aoede response has a status code of 200
And the aoede response matches
	| swagger | 2.0 |

