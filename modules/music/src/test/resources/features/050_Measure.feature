@Measure
Feature: Basic Measure CRUD functionality
### Verify the ability to create/read/update and delete Measure

@Positive
Scenario: retrieve all available Measures
### Retrieve the list of all available measures
### Verify that all common measures are populated by default
### Ensure that the measures that are going to be used later in the test do not

When request all available "measure"
Then the request was successful
And the response has a status code of 200

@Negative
Scenario: search for measure is not available
### Attempt to search for a Measure
### Verify that an error is generated

When search "measure" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@Negative
Scenario: access a Measure that does not exist
### Retrieve a measure that does not exist
### This should return with an error

When request a "measure" with id "1"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: create a new Measure
### create a new measure and verify the measure is created with the same data as provided
### retrieve the measure and verify the same data are returned

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And the response has a status code of 201
When request previously created "measure"
And the request was successful
And the response has a status code of 200
Then request previously created "section"
And the request was successful
And "section" contains latest "measure" in "measures"

Scenario: update a Measure
### create a measure and then update it
### verify that the measure contents have been updated

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And the response has a status code of 201
When update previously created "measure"
	| name | value |
And the request was successful
And the response has a status code of 204
Then request all available "measure" for latest "section"
And the request was successful
And the response has a status code of 200
And the response array contains latest "measure"

@Negative
Scenario: update a non existing measure
### attempt to update a measure that does not exist
### this should generate an error

When update "measure" with id "100"
	| name | value |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

Scenario: delete a Measure
### create a measure and then delete it
### verify that the measure is no longer accessible

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And the response has a status code of 201
When delete previously created "measure"
And the request was successful
And the response has a status code of 204
Then request previously created "measure"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Negative
Scenario: delete a non existing Measure
### attempt to delete a measure that does not exist
### this should generate an error

When delete "measure" with id "101"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
