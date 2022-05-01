@Note
Feature: Basic Note CRUD functionality with measure dependency
### Verify the ability to create/read/update and delete Note

Background: Create a note under a random measure

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
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    64   |
	| value     | fraction |   1/4   |
And the request was successful
And the response matches
	| note  | integer  |  64 |
	| value | fraction | 1/4 |
And the response has a status code of 201

@TC061001
@Positive @Create
Scenario: create a new Note
### create a new note and verify the measure is created with the same data as provided
### retrieve the note and verify the same data are returned

When request previously created "note"
And the request was successful
And the response matches
	| note  | integer  |  64 |
	| value | fraction | 1/4 |
And the response has a status code of 200
Then request previously created "measure"
And the request was successful
And "measure" contains latest "note" in "notes"

@TC061002
@Positive @Update
Scenario: update a Note
### create a note and then update it
### verify that the note contents have been updated

When update previously created "note"
	| note  | integer  |  61 |
	| value | fraction | 1/2 |
And the request was successful
And the response has a status code of 204
Then request all available "note" for latest "measure"
And the request was successful
And the response has a status code of 200
And the response array contains latest "note"

@TC061003
@Positive @Delete
Scenario: delete a Note
### create a note and then delete it
### verify that the note is no longer accessible

When delete previously created "note"
And the request was successful
And the response has a status code of 204
Then request previously created "note"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC061004
@Positive @Create
Scenario: create multiple Notes
### create three new notes and verify the notes are created with the same data as provided
### retrieve the measure and verify the same data are returned

When a "note" with
	| measureId | key      | measure |
	| note      | integer  |    -1   |
	| value     | fraction |   1/8   |
And the request was successful
And the response matches
	| note  | integer  |  -1 |
	| value | fraction | 1/8 |
And the response has a status code of 201
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |   127   |
	| value     | fraction |   3/8   |
And the request was successful
And the response matches
	| note  | integer  | 127 |
	| value | fraction | 3/8 |
And the response has a status code of 201
Then request previously created "measure"
And the request was successful
And the response has a status code of 200
And "measure" has "notes" array of size 3
And prepare composite id "noteKey1"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 1     |
	|   noteId   | int | 1     |
And prepare composite id "noteKey2"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 1     |
	|   noteId   | int | 2     |
And prepare composite id "noteKey3"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
	|  measureId | int | 1     |
	|   noteId   | int | 3     |
And prepare data table "noteObject"
	| id          | note | value    |
	| compositeId | int  | fraction |
And the response contains "noteObject" objects in "notes"
	| id          | note | value    |
	| noteKey1    |  64  |  1/4     |
	| noteKey2    |  -1  |  1/8     |
	| noteKey3    | 127  |  3/8     |
