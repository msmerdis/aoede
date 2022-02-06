@Measure
Feature: Basic Measure CRUD functionality with section dependency
### Verify the ability to create/read/update and delete Measure

Background: Create a measure under a random section

Given a logged in user "moduleMusicTest"
And a "sheet" with
	| name | random string | sheet_{string:12} |
And the request was successful
And the response has a status code of 201
And a "track" with
	| sheetId | key    | sheet  |
	| clef    | string | Treble |
And the request was successful
And the response has a status code of 201
And prepare json "clefTreble"
	| id      | string | Treble |
And the response matches
	| clef    |  json  | clefTreble |
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  3/4  |
And the request was successful
And the response has a status code of 201
And a "measure" with
	| sectionId | key | section |
And the request was successful
And the response has a status code of 201

@TC0511
@Positive @Create
Scenario: create a new Measure
### create a new measure and verify the measure is created with the same data as provided
### retrieve the measure and verify the same data are returned

When request previously created "measure"
And the request was successful
And the response has a status code of 200
And "measure" has "notes" array of size 0
Then request previously created "section"
And the request was successful
And "section" contains latest "measure" in "measures"

@TC0512
@Positive @Update
Scenario: update a Measure
### create a measure and then update it
### verify that the measure contents have been updated

When update previously created "measure"
	| name | string | value |
And the request was successful
And the response has a status code of 204
Then request all available "measure" for latest "section"
And the request was successful
And the response has a status code of 200
And the response array contains latest "measure"

@TC0513
@Positive @Delete
Scenario: delete a Measure
### create a measure and then delete it
### verify that the measure is no longer accessible

When delete previously created "measure"
And the request was successful
And the response has a status code of 204
Then request previously created "measure"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
