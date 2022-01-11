@Note
Feature: Basic Note CRUD functionality
### Verify the ability to create/read/update and delete Note

@TC0601
@Positive
Scenario: retrieve all available Notes
### Retrieve the list of all available note
### Verify that all common notes are populated by default
### Ensure that the notes that are going to be used later in the test do not

When request all available "note"
Then the request was successful
And the response has a status code of 200

@TC0602
@Negative
Scenario: search for note is not available
### Attempt to search for a Note
### Verify that an error is generated

When search "note" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@TC0603
@Negative
Scenario: access a Note that does not exist
### Retrieve a note that does not exist
### This should return with an error

When request a "note" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|   noteId  | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0604
@Positive
Scenario: create a new Note
### create a new note and verify the measure is created with the same data as provided
### retrieve the note and verify the same data are returned

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And a "note" with
	| note  |  64 |
	| value | 1/4 |
And the request was successful
And the response matches
	| note  |  64 |
	| value | 1/4 |
And the response has a status code of 201
When request previously created "note"
And the request was successful
And the response matches
	| note  |  64 |
	| value | 1/4 |
And the response has a status code of 200
Then request previously created "measure"
And the request was successful
And "measure" contains latest "note" in "notes"

@TC0605
@Positive
Scenario: update a Note
### create a note and then update it
### verify that the note contents have been updated

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And a "note" with
	| note  |  64 |
	| value | 1/4 |
And the request was successful
And the response has a status code of 201
And the response matches
	| note  |  64 |
	| value | 1/4 |
When update previously created "note"
	| note  |  61 |
	| value | 1/2 |
And the request was successful
And the response has a status code of 204
Then request all available "note" for latest "measure"
And the request was successful
And the response has a status code of 200
And the response array contains latest "note"

@TC0606
@Negative
Scenario: update a non existing note
### attempt to update a note that does not exist
### this should generate an error

Given prepare composite id "nonExistingNoteId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|   noteId  | integer | 1000 |
When update "note" with composite id "nonExistingNoteId"
	| note  |  64 |
	| value | 1/4 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0607
@Positive
Scenario: delete a Note
### create a note and then delete it
### verify that the note is no longer accessible

Given a randomized "sheet"
And the request was successful
And a randomized "track"
And the request was successful
And a randomized "section"
And the request was successful
And a randomized "measure"
And the request was successful
And a "note" with
	| note  |  64 |
	| value | 1/4 |
And the request was successful
And the response has a status code of 201
And the response matches
	| note  |  64 |
	| value | 1/4 |
When delete previously created "note"
And the request was successful
And the response has a status code of 204
Then request previously created "note"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0608
@Negative
Scenario: delete a non existing Note
### attempt to delete a note that does not exist
### this should generate an error

When delete "note" with composite id
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|   noteId  | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |