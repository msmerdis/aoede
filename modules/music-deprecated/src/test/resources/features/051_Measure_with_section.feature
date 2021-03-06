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

@TC051001
@Positive @Create
Scenario: create a new Measure
### create a new measure and verify the measure is created with the same data as provided
### retrieve the measure and verify the same data are returned

When request previously created "measure"
And the request was successful
And the response has a status code of 200
Then request all available "measure" for latest "section"
And the request was successful
And the response has a status code of 200
And "measure" returned array of size 1

@TC051002
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

@TC051003
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

@TC051004
@Positive @Create
Scenario: create multiple Measures
### create three new measures and verify the measures are created with the same data as provided
### retrieve the section and verify the same data are returned

When a "measure" with
	| sectionId | key | section |
And the request was successful
And the response has a status code of 201
And a "measure" with
	| sectionId | key | section |
And the request was successful
And the response has a status code of 201
Then request all available "measure" for latest "section"
And the request was successful
And the response has a status code of 200
And "measure" returned array of size 3
And prepare composite id "measureKey1"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 1     |
And prepare composite id "measureKey2"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 2     |
And prepare composite id "measureKey3"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 3     |
And prepare data table "measureObject"
	| id          |
	| compositeId |
And the response array contains "measureObject" objects
	| id          |
	| measureKey1 |
	| measureKey2 |
	| measureKey3 |
