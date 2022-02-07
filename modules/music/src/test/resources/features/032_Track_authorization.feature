@Track @Authorization
Feature: Verify tracks are not accessible cross user
### Verify the ability to create/read/update and delete Tracks

Background: Create a track under a random sheet

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
And a logged in user "otherModuleMusicTest"

@TC032001
@Negative @Create
Scenario: create track under a sheet of a different user
### Create a track under a sheet of a different user
### Tracks cannot be created under sheets of a different user

When a "track" with
	| sheetId | key    | sheet  |
	| clef    | string | Treble |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC032002
@Negative
Scenario: access tracks as a different user
### Retrieve the list of all available tracks
### Tracks created by other users should not be available

When request all available "track"
Then the request was successful
And the response has a status code of 200
And "track" returned array of size 0
And the response array does not contain latest "track"

@TC032003
@Negative
Scenario: access track as a different user
### Retrieve a track created by a different user
### Tracks created by other users should not be available

When request previously created "track"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC032004
@Negative @Update
Scenario: update track as a different user
### Update a track created by a different user
### Tracks created by other users should not be updatable

When update previously created "track"
	| clef | string  | Bass |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC032005
@Negative @Delete
Scenario: delete track as a different user
### Delete a track created by a different user
### Tracks created by other users should not be able to be deleted

When delete previously created "track"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
