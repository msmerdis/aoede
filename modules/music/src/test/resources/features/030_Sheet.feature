@Sheet
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

Background: Log user in

Given a logged in user "basicModuleMusicTest"

@TC030001
@Positive
Scenario: retrieve all available Sheets
### Retrieve the list of all available sheets
### Verify that all common sheets are populated by default
### Ensure that the sheets that are going to be used later in the test do not

When request all available "sheet"
Then the request was successful
And the response has a status code of 200
And "sheet" returned array of size 0

@TC030002
@Negative
Scenario: search for sheet is not available
### Attempt to search for a sheet
### Verify that an error is generated

When search "sheet" with keyword "hello"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC030003
@Negative
Scenario: access a Sheet that does not exist
### Retrieve a sheet that does not exist
### This should return with an error

When request a "sheet" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030004
@Positive @Create
Scenario: create a new Sheet
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given prepare "C" scale as "C scale"
And a "sheet" with "C scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | C scale |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And the response matches "C scale" json
And the response matches
	|  id  | key    | sheet   |
	| name | string | C scale |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And prepare data table "sheetObject"
	| id  | name   |
	| key | string |
And the response array contains "sheetObject" objects
	| id    | name    |
	| sheet | C scale |
And the response array contains latest "sheet"

@TC030005
@Positive @Update
Scenario: update a Sheet
### create a sheet and then update it
### verify that the sheet contents have been updated

Given prepare "G" scale as "G scale"
And a "sheet" with "G scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | G scale |
And prepare "D" scale as "D scale"
And set "D scale" object's "id" element to "sheet" as "key"
When update previously created "sheet" with "D scale" json element
And the request was successful
And the response has a status code of 204
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And the response array contains "name" with "string" value "D scale"
And the response array contains latest "sheet"
And the response array does not contain "name" with "string" value "G scale"

@TC030006
@Negative @Update
Scenario: update a non existing Sheet
### attempt to update a sheet that does not exist
### this should generate an error

Given prepare "A" scale as "A scale"
When update "sheet" with id "100" and "A scale" json element
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030007
@Positive @Delete
Scenario: delete a Sheet
### create a sheet and then delete it
### verify that the sheet is no longer accessible

Given prepare "E" scale as "E scale"
And a "sheet" with "E scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | E scale |
When delete previously created "sheet"
And the request was successful
And the response has a status code of 204
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And the response array does not contain "name" with "string" value "E scale"
And the response array does not contain latest "sheet"

@TC030008
@Negative @Delete
Scenario: delete a non existing Sheet
### attempt to delete a sheet that does not exist
### this should generate an error

When delete "sheet" with id "101"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030009
@Positive @Create
Scenario: create a new Sheet without any tracks
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given prepare "C" scale as "C scale"
And prepare empty json array "empty tracks"
And set "C scale" object's "tracks" element to "empty tracks" as "json"
And a "sheet" with "C scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | C scale |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And the response matches "C scale" json
And the response matches
	|  id  | key    | sheet   |
	| name | string | C scale |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And prepare data table "sheetObject"
	| id  | name   |
	| key | string |
And the response array contains "sheetObject" objects
	| id    | name    |
	| sheet | C scale |
And the response array contains latest "sheet"

@TC030010
@Positive @Create
Scenario: create a new Sheet overriting its signature at third measure
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given prepare "B" scale as "B scale"
And store "tracks" element from "B scale" object as "track array"
And store element 0 from "track array" array as "track"
And store "measures" element from "track" object as "measure array"
And store element 2 from "measure array" array as "measure"
And set "measure" object's "tempo" element to "160" as "number"
And set "measure" object's "keySignature" element to "7" as "number"
And set "measure" object's "timeSignature" element to "8/8" as "fraction"
And a "sheet" with "B scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | B scale |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And the response matches "B scale" json
And the response matches
	|  id  | key    | sheet   |
	| name | string | B scale |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And prepare data table "sheetObject"
	| id  | name   |
	| key | string |
And the response array contains "sheetObject" objects
	| id    | name    |
	| sheet | B scale |
And the response array contains latest "sheet"

@TC030011
@Positive @Create
Scenario: create a new Sheet with custom track time signature beats
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given prepare "F" scale as "F scale"
And store "tracks" element from "F scale" object as "track array"
And store element 0 from "track array" array as "track"
And store "timeSignature" element from "track" object as "time"
And set "time" object's "numerator" element to "7" as "number"
And set "time" object's "denominator" element to "8" as "number"
And prepare json array "beats"
	| integer | 1 |
	| integer | 3 |
	| integer | 6 |
And set "time" object's "beats" element to "beats" as "json"
And a "sheet" with "F scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | F scale |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And the response matches "F scale" json
And the response matches
	|  id  | key    | sheet   |
	| name | string | F scale |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And prepare data table "sheetObject"
	| id  | name   |
	| key | string |
And the response array contains "sheetObject" objects
	| id    | name    |
	| sheet | F scale |
And the response array contains latest "sheet"

@TC030012
@Positive @Create
Scenario: create a new Sheet with custom measure time signature beats
### create a new sheet and verify the sheet is created with the same data as provided
### retrieve the sheet and verify the same data are returned

Given prepare "F" scale as "F scale"
And store "tracks" element from "F scale" object as "track array"
And store element 0 from "track array" array as "track"
And store "measures" element from "track" object as "measure array"
And store element 2 from "measure array" array as "measure"
And prepare json array "beats"
	| integer | 1 |
	| integer | 3 |
	| integer | 6 |
And prepare json "time"
	| numerator   | integer | 7     |
	| denominator | integer | 8     |
	| beats       | json    | beats |
And set "measure" object's "timeSignature" element to "time" as "json"
And a "sheet" with "F scale" json element
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | key    | sheet   |
	| name | string | F scale |
When request previously created "sheet"
And the request was successful
And the response has a status code of 200
And the response matches "F scale" json
And the response matches
	|  id  | key    | sheet   |
	| name | string | F scale |
Then request all available "sheet"
And the request was successful
And the response has a status code of 200
And prepare data table "sheetObject"
	| id  | name   |
	| key | string |
And the response array contains "sheetObject" objects
	| id    | name    |
	| sheet | F scale |
And the response array contains latest "sheet"
