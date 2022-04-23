@ErrorValidation
Feature: Verify handling of error cases with validations
### Verify validations on errors is retured as expected

@TC012001
@Negative
Scenario: test validation info
### force application to throw a bad request exception with validation info and verify the generated response

When testing "username" validation info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | username    |
And contains validation array with field "username", value "" and error "cannot be empty"

@TC012002
@Negative
Scenario: test validation info
### force application to throw a bad request exception with validation info and verify the generated response

When testing "password" validation info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | password    |
And contains validation array with field "password", value "abcde" and error "try harder"

@TC012003
@Negative
Scenario: test validation info
### force application to throw a bad request exception with validation info and verify the generated response

When testing "all" validation info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | all         |
And contains validation array with field "username", value "" and error "cannot be empty"
And contains validation array with field "password", value "abcde" and error "try harder"