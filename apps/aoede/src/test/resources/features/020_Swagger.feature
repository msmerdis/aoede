@Swagger @Regression
Feature: Verify swagger endpoints are up and running
### Verify the setup of swagger

@TC020001
@Positive
Scenario: access swagger ui
### verify swagger ui is present in the aoede application

When request "/swagger-ui.html" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "<!-- HTML for static distribution bundle build -->"
And the aoede response contains "<!DOCTYPE html>"
And the aoede response contains "<title>Swagger UI</title>"

@TC020002
@Positive
Scenario Outline: access swagger api docs
### verify swagger docs are present in the aoede application

When request "/v2/api-docs?group=<group>" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "\"termsOfService\":\"/static/tos.html\""
And the aoede response contains "\"license\":{\"name\":\"Apache 2.0\",\"url\":\"https://www.apache.org/licenses/LICENSE-2.0\"}"
And the aoede response matches
	| swagger | 2.0 |

Examples:
	| group      |
	| api-docs   |
	| admin-docs |
	| auth-docs  |

@TC020003
@Positive
Scenario: access swagger resources
### verify swagger docs are present in the aoede application

When request "/swagger-resources" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "/v2/api-docs?group=api-docs"
And the aoede response contains "/v2/api-docs?group=admin-docs"
And the aoede response contains "/v2/api-docs?group=auth-docs"

