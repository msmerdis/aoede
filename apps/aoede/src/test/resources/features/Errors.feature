@Error
Feature: Verify handling of error cases
### Verify invalid requests are processed as expected

Scenario: access non existing url
When request "/this/path/does/not/exist" from aoede
Then the aoede response has a status code of 404
And the aoede response matches
	| code | 404       |
	| text | NOT_FOUND |
