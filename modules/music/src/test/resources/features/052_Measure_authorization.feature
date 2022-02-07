@Measure @Authorization
Feature: Verify tracks are not accessible cross user
### Verify the ability to create/read/update and delete Tracks

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
And the response matches
	| tempo         | integer  | 120 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 3/4 |
And a "measure" with
	| sectionId | key | section |
And the request was successful
And the response has a status code of 201
And a logged in user "otherModuleMusicTest"

@TC052001
@Negative @Create
Scenario: create measure under a sheet of a different user
### Create a measure under a sheet of a different user
### Measures cannot be created under sections of a different user

When a "measure" with
	| sectionId | key | section |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC052002
@Negative
Scenario: access measures as a different user
### Retrieve the list of all available measures
### Measures created by other users should not be available

When request all available "measure"
Then the request was successful
And the response has a status code of 200
And "measure" returned array of size 0
And the response array does not contain latest "measure"

@TC052003
@Negative
Scenario: access measure as a different user
### Retrieve a measure created by a different user
### Measures created by other users should not be available

When request previously created "measure"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC052004
@Negative @Update
Scenario: update measure as a different user
### Update a measure created by a different user
### Measures created by other users should not be updatable

When update previously created "measure"
	| name | string | value |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC052005
@Negative @Delete
Scenario: delete measure as a different user
### Delete a measure created by a different user
### Measures created by other users should not be able to be deleted

When delete previously created "measure"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
