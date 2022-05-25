@Section
Feature: Basic Section CRUD functionality
### Verify the ability to create/read/update and delete Sections

Background: Log user in

Given a logged in user "moduleMusicTest"

@TC040001
@Positive
Scenario: retrieve all available Sections
### Retrieve the list of all available sections
### Verify that all common sections are populated by default
### Ensure that the sections that are going to be used later in the test do not

When request all available "section"
Then the request was successful
And the response has a status code of 200
And "section" returned array of size 0

@TC040002
@Negative
Scenario: search for section is not available
### Attempt to search for a Section
### Verify that an error is generated

When search "section" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC040003
@Negative
Scenario: access a Section that does not exist
### Retrieve a section that does not exist
### This should return with an error

When request a "section" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC040004
@Negative @Update
Scenario: update a non existing Section
### attempt to update a section that does not exist
### this should generate an error

Given prepare composite id "nonExistingSectionId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "nonExistingSectionId"
	| tempo         | integer  | 120 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 3/4 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC040005
@Negative @Delete
Scenario: delete a non existing Section
### attempt to delete a section that does not exist
### this should generate an error

When delete "section" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
