@Track
Feature: Basic Track CRUD functionality
### Verify the ability to create/read/update and delete Tracks

Background: Log user in

Given a logged in user "moduleMusicTest"

@TC030001
@Positive
Scenario: retrieve all available Tracks
### Retrieve the list of all available tracks
### Verify that all common tracks are populated by default
### Ensure that the tracks that are going to be used later in the test do not

When request all available "track"
Then the request was successful
And the response has a status code of 200
And "track" returned array of size 0

@TC030002
@Negative
Scenario: search for track is not available
### Attempt to search for a track
### Verify that an error is generated

When search "track" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC030003
@Negative
Scenario: access a Track that does not exist
### Retrieve a track that does not exist
### This should return with an error

When request a "track" with composite id
	| sheetId | integer | 1 |
	| trackId | integer | 1 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030004
@Negative @Update
Scenario: update a non existing Track
### attempt to update a track that does not exist
### this should generate an error

Given prepare composite id "nonExistingTrackId"
	| sheetId | integer | 1000 |
	| trackId | integer | 1000 |
And update "track" with composite id "nonExistingTrackId"
	| clef | string | Subbass |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030005
@Negative @Delete
Scenario: delete a non existing Track
### attempt to delete a track that does not exist
### this should generate an error

When delete "track" with composite id
	| sheetId | integer | 1000 |
	| trackId | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
