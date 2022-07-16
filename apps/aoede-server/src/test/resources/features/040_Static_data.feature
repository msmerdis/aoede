@StaticData @Regression
Feature: Verify required static data are present
### Verify the setup of static data

@TC040001
@Positive
Scenario Outline: Verify clefs are present
### verify clef images are present

When request "/static/clef/<clef>.svg" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "svg"

Examples:
	| clef |
	| C    |
	| G    |
	| F    |

@TC040002
@Positive
Scenario: Verify terms of service are present
### verify terms of service are present

When request "/static/tos.html" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "<!DOCTYPE html>"
And the aoede response contains "Terms of Service"

