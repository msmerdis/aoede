@Measure
Feature: Basic Measure CRUD functionality
### Verify the ability to create/read/update and delete Measure

Background: Create a track under a random sheet

Given a logged in user "moduleMusicTest"

@TC050001
@Positive
Scenario: retrieve all available Measures
### Retrieve the list of all available measures
### Verify that all common measures are populated by default
### Ensure that the measures that are going to be used later in the test do not

When request all available "measure"
Then the request was successful
And the response has a status code of 200

@TC050002
@Negative
Scenario: search for measure is not available
### Attempt to search for a Measure
### Verify that an error is generated

When search "measure" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC050003
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
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC050004
@Negative @Update
Scenario: update a non existing measure
### attempt to update a measure that does not exist
### this should generate an error

Given prepare composite id "nonExistingMeasureId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
When update "measure" with composite id "nonExistingMeasureId"
	| name | string | value |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC050005
@Negative @Delete
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
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
