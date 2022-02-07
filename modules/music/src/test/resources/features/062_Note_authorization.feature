@Note @Authorization
Feature: Verify tracks are not accessible cross user
### Verify the ability to create/read/update and delete Tracks

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
And the response matches
	| tempo         | integer  | 120 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 3/4 |
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
	| note      | integer  |    64   |
	| value     | fraction |   1/4   |
And a logged in user "otherModuleMusicTest"

@TC062001
@Negative @Create
Scenario: create note under a measure of a different user
### Create a note under a sheet of a different user
### Notess cannot be created under sheets of a different user

When a "note" with
	| measureId | key      | measure |
	| note      | integer  |    66   |
	| value     | fraction |   1/2   |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC062002
@Negative
Scenario: access notes as a different user
### Retrieve the list of all available notes
### Notes created by other users should not be available

When request all available "note"
Then the request was successful
And the response has a status code of 200
And "note" returned array of size 0
And the response array does not contain latest "note"

@TC062003
@Negative
Scenario: access note as a different user
### Retrieve a note created by a different user
### Notes created by other users should not be available

When request previously created "note"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC062004
@Negative @Update
Scenario: update note as a different user
### Update a note created by a different user
### Notes created by other users should not be updatable

When update previously created "note"
	| measureId | key      | measure |
	| note      | integer  |    66   |
	| value     | fraction |   1/2   |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC062005
@Negative @Delete
Scenario: delete note as a different user
### Delete a note created by a different user
### Notes created by other users should not be able to be deleted

When delete previously created "note"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
