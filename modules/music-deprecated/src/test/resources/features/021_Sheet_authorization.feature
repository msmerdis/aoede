@Sheet @Authorization
Feature: Verify sheets are not accessible cross user
### Verify the ability to create/read/update and delete Sheets

Background: Create a random sheet and log as a different user

Given a logged in user "moduleMusicTest"
And a "sheet" with
	| name | random string | sheet_{string:16} |
And the request was successful
And the response has a status code of 201
And a logged in user "otherModuleMusicTest"

@TC021001
@Negative
Scenario: access sheets as a different user
### Retrieve the list of all available sheets
### Sheets created by other users should not be available

When request all available "sheet"
Then the request was successful
And the response has a status code of 200
And "sheet" returned array of size 0
And the response array does not contain latest "sheet"

@TC021002
@Negative
Scenario: access sheet as a different user
### Retrieve a sheet created by a different user
### Sheets created by other users should not be available

When request previously created "sheet"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC021003
@Negative @Update
Scenario: update sheet as a different user
### Update a sheet created by a different user
### Sheets created by other users should not be updatable

When update previously created "sheet"
	| name | string | Sheet 2 Updated |
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC021004
@Negative @Delete
Scenario: delete sheet as a different user
### Delete a sheet created by a different user
### Sheets created by other users should not be able to be deleted

When delete previously created "sheet"
Then the request was not successful
And the response has a status code of 401
And the response matches
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
