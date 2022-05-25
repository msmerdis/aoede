@Section @Authorization
Feature: Verify section are not accessible cross user
### Verify the ability to create/read/update and delete Sections

Background: Create a section under a random track

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
And a logged in user "otherModuleMusicTest"

@TC042001
@Negative @Create
Scenario: create section under a track of a different user
### Create a section under a track of a different user
### Sections cannot be created under tracks of a different user

When a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC042002
@Negative
Scenario: access sections as a different user
### Retrieve the list of all available sections
### Sections created by other users should not be available

When request all available "section"
Then the request was successful
And the response has a status code of 200
And "section" returned array of size 0
And the response array does not contain latest "section"

@TC042003
@Negative
Scenario: access section as a different user
### Retrieve a section created by a different user
### Sections created by other users should not be available

When request previously created "section"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC042004
@Negative @Update
Scenario: update section as a different user
### Update a section created by a different user
### Sections created by other users should not be updatable

When update previously created "section"
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC042005
@Negative @Delete
Scenario: delete section as a different user
### Delete a section created by a different user
### Sections created by other users should not be able to be deleted

When delete previously created "section"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
