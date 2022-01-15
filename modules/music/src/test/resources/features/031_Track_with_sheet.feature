@Track
Feature: Basic Track CRUD functionality with sheet dependency
### Verify the ability to create/read/update and delete Tracks

Background: Create a track under a random sheet

Given a "sheet" with
	| name | random string | sheet_{string:12} |
And the request was successful
And the response has a status code of 201
And a "track" with
	| sheetId | key    | sheet  |
	| clef    | string | Treble |
And the request was successful
And the response has a status code of 201
And the response matches
	| clef | Treble |

@TC0311
@Positive @Create
Scenario: create a new Track
### create a new track and verify the track is created with the same data as provided
### retrieve the track and verify the same data are returned

When request previously created "track"
And the request was successful
And the response has a status code of 200
And "track" has "sections" array of size 0
And the response matches
	| clef | Treble |
Then request previously created "sheet"
And the request was successful
And the response has a status code of 200
And "sheet" contains latest "track" in "tracks"
And "sheet" has "tracks" array of size 1

@TC0312
@Positive @Update
Scenario: update a Track
### create a track and then update it
### verify that the track contents have been updated

When update previously created "track"
	| clef | string | Bass |
And the request was successful
And the response has a status code of 204
Then request previously created "track"
And the request was successful
And the response has a status code of 200
And the response matches
	| clef | Bass |

@TC0313
@Positive @Delete
Scenario: delete a Track
### create a track and then delete it
### verify that the track is no longer accessible

When delete previously created "track"
And the request was successful
And the response has a status code of 204
Then request all available "track" for latest "sheet"
And the request was successful
And the response has a status code of 200
And the response array does not contain "clef" with value "Alto"
And the response array does not contain latest "track"
