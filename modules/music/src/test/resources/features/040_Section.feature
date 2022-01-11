@Section
Feature: Basic Section CRUD functionality
### Verify the ability to create/read/update and delete Sections

@TC0401
@Positive
Scenario: retrieve all available Sections
### Retrieve the list of all available sections
### Verify that all common sections are populated by default
### Ensure that the sections that are going to be used later in the test do not

When request all available "section"
Then the request was successful
And the response has a status code of 200
And "section" returned array of size 0

@TC0402
@Negative
Scenario: search for section is not available
### Attempt to search for a Section
### Verify that an error is generated

When search "section" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@TC0403
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
	| code | 404       |
	| text | NOT_FOUND |

@TC0404
@Positive
Scenario: create a new Section
### create a new section and verify the track is created with the same data as provided
### retrieve the section and verify the same data are returned

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a "section" with
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
And the request was successful
And the response has a status code of 201
And the response matches
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
When request previously created "section"
And the request was successful
And the response has a status code of 200
And "section" has "measures" array of size 0
And the response matches
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
Then request previously created "track"
And the request was successful
And "track" contains latest "section" in "sections"
And "track" has "sections" array of size 1

@TC0405
@Positive
Scenario: update a Section
### create a section and then update it
### verify that the section contents have been updated

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a "section" with
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
And the request was successful
And the response has a status code of 201
And the response matches
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
When update previously created "section"
	| tempo         | 160 |
	| keySignature  | ERM |
	| timeSignature | 4/4 |
And the request was successful
And the response has a status code of 204
Then request previously created "track"
And the request was successful
And "track" contains latest "section" in "sections"
And "track" has "sections" array of size 1

@TC0406
@Negative
Scenario: update a non existing Section
### attempt to update a section that does not exist
### this should generate an error

Given prepare composite id "nonExistingSectionId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "nonExistingSectionId"
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0407
@Positive
Scenario: delete a Section
### create a section and then delete it
### verify that the section is no longer accessible

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a "section" with
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
And the request was successful
And the response has a status code of 201
And the response matches
	| tempo         | 120 |
	| keySignature  | TBC |
	| timeSignature | 3/4 |
When delete previously created "section"
And the request was successful
And the response has a status code of 204
Then request previously created "section"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0408
@Negative
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
	| code | 404       |
	| text | NOT_FOUND |
