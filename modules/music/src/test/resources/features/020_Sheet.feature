@Sheet
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

@TC0201
@Positive
Scenario: retrieve all available Sheets
### Retrieve the list of all available sheets
### Verify that all common sheets are populated by default
### Ensure that the sheets that are going to be used later in the test do not

When request all available "sheet"
Then the request was successful
And the response has a status code of 200
And "sheet" returned array of size 0

@TC0202
@Negative
Scenario: search for sheet is not available
### Attempt to search for a sheet
### Verify that an error is generated

When search "sheet" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@TC0203
@Negative
Scenario: access a Sheet that does not exist
### Retrieve a sheet that does not exist
### This should return with an error

When request a "sheet" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0204
@Positive @Create
Scenario: create a new Sheet
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given a "sheet" with
	| name | string | New Sheet |
And the request was successful
And the response has a status code of 201
And the response matches
	| name | New Sheet |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And "sheet" has "tracks" array of size 0
And the response matches
	| name | New Sheet |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And the response array contains
	| name      |
	| New Sheet |
And the response array contains latest "sheet"
And "sheet" returned array of size 1

@TC0205
@Positive @Update
Scenario: update a Sheet
### create a sheet and then update it
### verify that the sheet contents have been updated

Given a "sheet" with
	| name | string | Sheet 2 Update  |
And the request was successful
And the response has a status code of 201
And the response matches
	| name | Sheet 2 Update  |
When update previously created "sheet"
	| name | string | Sheet 2 Updated |
And the request was successful
And the response has a status code of 204
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And the response array contains "name" with value "Sheet 2 Updated"
And the response array contains latest "sheet"
And the response array does not contain "name" with value "Sheet 2 Update"

@TC0206
@Negative @Update
Scenario: update a non existing Sheet
### attempt to update a sheet that does not exist
### this should generate an error

When update "sheet" with id "100"
	| name | string | Sheet 2 Updated |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@TC0207
@Positive @Delete
Scenario: delete a Sheet
### create a sheet and then delete it
### verify that the sheet is no longer accessible

Given a "sheet" with
	| name | string | Sheet 2 Delete |
And the request was successful
And the response has a status code of 201
And the response matches
	| name | Sheet 2 Delete |
When delete previously created "sheet"
And the request was successful
And the response has a status code of 204
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And the response array does not contain "name" with value "Sheet 2 Delete"
And the response array does not contain latest "sheet"

@TC0208
@Negative @Delete
Scenario: delete a non existing Sheet
### attempt to delete a sheet that does not exist
### this should generate an error

When delete "sheet" with id "101"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
