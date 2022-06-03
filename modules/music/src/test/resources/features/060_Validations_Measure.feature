@Validation @Measure @Negative @Create
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

Background: Log user in

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |
And prepare "C" scale as "C scale"
And store "tracks" element from "C scale" object as "track array"
And store element 0 from "track array" array as "track"
And store "measures" element from "track" object as "measure array"
And store element 0 from "measure array" array as "measure"

@TC060001
Scenario: create a Measure with null notes field
### create a new sheet defining the measure's notes field as null
### operation should fail and a validation error should be returned

And set "measure" object's "notes" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field                       | value | error                     |
	| sheet | tracks[0].measures[0].notes |       | Measure must define notes |