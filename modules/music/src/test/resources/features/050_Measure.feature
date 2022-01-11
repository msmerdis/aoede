@Measure
Feature: Basic Measure CRUD functionality
### Verify the ability to create/read/update and delete Measure

@TC0501
@Positive
Scenario: retrieve all available Measures
### Retrieve the list of all available measures
### Verify that all common measures are populated by default
### Ensure that the measures that are going to be used later in the test do not

When request all available "measure"
Then the request was successful
And the response has a status code of 200

@TC0502
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

@TC0503
@Negative
Scenario: access a Measure that does not exist
### Retrieve a measure that does not exist
### This should return with an error

When request a "measure" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0504
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
And "measure" has "notes" array of size 0
Then request previously created "section"
And the request was successful
And "section" contains latest "measure" in "measures"

@TC0505
@Positive
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

@TC0506
@Negative
Scenario: update a non existing measure
### attempt to update a measure that does not exist
### this should generate an error

Given prepare composite id "nonExistingMeasureId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
When update "measure" with composite id "nonExistingMeasureId"
	| name | value |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0507
@Positive
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

@TC0508
@Negative
Scenario: delete a non existing Measure
### attempt to delete a measure that does not exist
### this should generate an error

When delete "measure" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
