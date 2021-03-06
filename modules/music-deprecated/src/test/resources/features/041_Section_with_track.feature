@Section
Feature: Basic Section CRUD functionality with track dependency
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
	| id | string | Treble |
And the response matches
	| clef | json | clefTreble |
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

@TC041001
@Positive @Create
Scenario: create a new Section
### create a new section and verify the track is created with the same data as provided
### retrieve the section and verify the same data are returned

When request previously created "section"
And the request was successful
And the response has a status code of 200
And the response matches
	| tempo         | integer  | 120 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 3/4 |
Then request all available "section" for latest "track"
And the request was successful
And the response has a status code of 200
And "section" returned array of size 1

@TC041002
@Positive @Update
Scenario: update a Section
### create a section and then update it
### verify that the section contents have been updated

When update previously created "section"
	| tempo         | integer  | 160 |
	| keySignature  | integer  |  1  |
	| timeSignature | fraction | 4/4 |
And the request was successful
And the response has a status code of 204
Then request previously created "section"
And the request was successful
And the response has a status code of 200
And the response matches
	| tempo         | integer  | 160 |
	| keySignature  | integer  |  1  |
	| timeSignature | fraction | 4/4 |

@TC041003
@Positive @Delete
Scenario: delete a Section
### create a section and then delete it
### verify that the section is no longer accessible

When delete previously created "section"
And the request was successful
And the response has a status code of 204
Then request previously created "section"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC041004
@Positive @Create
Scenario: create multiple Sections
### create three new sections and verify the sections are created with the same data as provided
### retrieve the track and verify the same data are returned

When a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |   32  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
And the request was successful
And the response has a status code of 201
And the response matches
	| tempo         | integer  |  32 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 4/4 |
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  512  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  2/4  |
And the request was successful
And the response has a status code of 201
And the response matches
	| tempo         | integer  | 512 |
	| keySignature  | integer  |  0  |
	| timeSignature | fraction | 2/4 |
Then request all available "section" for latest "track"
And the request was successful
And the response has a status code of 200
And "section" returned array of size 3
And prepare composite id "sectionKey1"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 1     |
And prepare composite id "sectionKey2"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 2     |
And prepare composite id "sectionKey3"
	|   sheetId  | key | sheet |
	|   trackId  | int | 1     |
	|  sectionId | int | 3     |
And prepare data table "sectionObject"
	| id          | tempo | timeSignature |
	| compositeId | int   | fraction      |
And the response array contains "sectionObject" objects
	| id          | tempo | timeSignature |
	| sectionKey1 |  120  |      3/4      |
	| sectionKey2 |   32  |      4/4      |
	| sectionKey3 |  512  |      2/4      |
