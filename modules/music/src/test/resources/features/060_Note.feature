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
@Negative @Update
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
	| note  | integer  |  64 |
	| value | fraction | 1/4 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0605
@Negative @Delete
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
