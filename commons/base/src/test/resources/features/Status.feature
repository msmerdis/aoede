@Status
Feature: Verify handling of different status cases
### Verify different response status are generated as expected

@Positive
Scenario: test accepted status
### force application to return an accepted status and verify the generated response

When testing "accepted" error
Then the response has a status code of 202 and matches
	| code | 202      |
	| text | ACCEPTED |

@Positive
Scenario: test created status
### force application to return a created status and verify the generated response

When testing "created" error
Then the response has a status code of 201 and matches
	| code | 201     |
	| text | CREATED |

@Positive
Scenario: test no content status
### force application to return a no content status and verify the generated response

When testing "noContent" error
Then the response has a status code of 204 and no body

@Positive
Scenario: test success status
### force application to return a success status and verify the generated response

When testing "success" error
Then the response has a status code of 200 and matches
	| code | 200 |
	| text | OK  |
