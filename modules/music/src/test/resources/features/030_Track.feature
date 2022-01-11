@Track
Feature: Basic Track CRUD functionality
### Verify the ability to create/read/update and delete Tracks

@TC0301
@Positive
Scenario: retrieve all available Tracks
### Retrieve the list of all available tracks
### Verify that all common tracks are populated by default
### Ensure that the tracks that are going to be used later in the test do not

When request all available "track"
Then the request was successful
And the response has a status code of 200
And "track" returned array of size 0

@TC0302
@Negative
Scenario: search for track is not available
### Attempt to search for a track
### Verify that an error is generated

When search "track" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@TC0303
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
	| code | 404       |
	| text | NOT_FOUND |

@TC0304
@Positive
Scenario: create a new Track
### create a new track and verify the track is created with the same data as provided
### retrieve the track and verify the same data are returned

Given a randomized "sheet"
And the request was successful
And a "track" with
	| clef | Treble |
And the request was successful
And the response has a status code of 201
And the response matches
	| clef | Treble |
When request previously created "track"
And the request was successful
And the response has a status code of 200
And "track" has "sections" array of size 0
And the response matches
	| clef | Treble |
Then request previously created "sheet"
And the request was successful
And "sheet" contains latest "track" in "tracks"
And "sheet" has "tracks" array of size 1

@TC0305
@Positive
Scenario: update a Track
### create a track and then update it
### verify that the track contents have been updated

Given a randomized "sheet"
And the request was successful
And a "track" with
	| clef | Treble |
And the request was successful
And the response has a status code of 201
And the response matches
	| clef | Treble |
When update previously created "track"
	| clef | Bass |
And the request was successful
And the response has a status code of 204
Then request previously created "track"
And the request was successful
And the response has a status code of 200
And the response matches
	| clef | Bass |

@TC0306
@Negative
Scenario: update a non existing Track
### attempt to update a track that does not exist
### this should generate an error

Given prepare composite id "nonExistingTrackId"
	| sheetId | integer | 1000 |
	| trackId | integer | 1000 |
And update "track" with composite id "nonExistingTrackId"
	| clef | Subbass |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0307
@Positive
Scenario: delete a Track
### create a track and then delete it
### verify that the track is no longer accessible

Given a randomized "sheet"
And the request was successful
And a "track" with
	| clef | Alto |
And the request was successful
And the response has a status code of 201
And the response matches
	| clef | Alto |
When delete previously created "track"
And the request was successful
And the response has a status code of 204
Then request all available "track" for latest "sheet"
And the request was successful
And the response has a status code of 200
And the response array does not contain "clef" with value "Alto"
And the response array does not contain latest "track"

@TC0308
@Negative
Scenario: delete a non existing Track
### attempt to delete a track that does not exist
### this should generate an error

When delete "track" with composite id
	| sheetId | integer | 1000 |
	| trackId | integer | 1000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
