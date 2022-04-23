@ErrorInfo
Feature: Verify handling of error cases with info
### Verify info on errors is retured as expected

@TC011001
@Negative
Scenario: test fatal info
### force application to throw a bad request exception with fatal info and verify the generated response

When testing "fatal" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | fatal       |
And contains info array with severity "fatal" and text "fatal error"

@TC011002
@Negative
Scenario: test error info
### force application to throw a bad request exception with error info and verify the generated response

When testing "error" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | error       |
And contains info array with severity "error" and text "error error"

@TC011003
@Negative
Scenario: test warning info
### force application to throw a bad request exception with warning info and verify the generated response

When testing "warning" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | warning     |
And contains info array with severity "warning" and text "warning error"

@TC011004
@Negative
Scenario: test info info
### force application to throw a bad request exception with info info and verify the generated response

When testing "info" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | info        |
And contains info array with severity "info" and text "info error"

@TC011005
@Negative
Scenario: test debug info
### force application to throw a bad request exception with debug info and verify the generated response

When testing "debug" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | debug       |
And contains info array with severity "debug" and text "debug error"

@TC011006
@Negative
Scenario: test trace info
### force application to throw a bad request exception with trace info and verify the generated response

When testing "trace" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | trace       |
And contains info array with severity "trace" and text "trace error"

@TC011007
@Negative
Scenario: test trace info
### force application to throw a bad request exception with all info and verify the generated response

When testing "all" error info
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |
	| desc | all         |
And contains info array with severity "fatal" and text "fatal error"
And contains info array with severity "error" and text "error error"
And contains info array with severity "warning" and text "warning error"
And contains info array with severity "info" and text "info error"
And contains info array with severity "debug" and text "debug error"
And contains info array with severity "trace" and text "trace error"
