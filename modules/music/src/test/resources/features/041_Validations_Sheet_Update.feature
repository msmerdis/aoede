@Validation @Sheet @Negative @Update
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |
And a logged in user "validatorModuleMusicTest"
And prepare "C" scale as "C scale"
And a "sheet" with "C scale" json element
And the request was successful
And the response has a status code of 201
And set "C scale" object's "id" element to "sheet" as "key"

@TC041001
Scenario: update a Sheet with empty name
### update a new sheet with an empty name
### operation should fail and a validation error should be returned

When set "C scale" object's "name" element to "" as "string"
And update previously created "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC041002
Scenario: update a Sheet with null as name
### update a new sheet with null as name
### operation should fail and a validation error should be returned

When set "C scale" object's "name" element to "" as "null"
And update previously created "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC041003
Scenario: update a Sheet without a name element
### update a new sheet without a name element
### operation should fail and a validation error should be returned

When remove "name" element from "C scale"
And update previously created "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC041004
Scenario: update a Sheet without an id
### update a new sheet without providing an id
### operation should fail and a validation error should be returned

When remove "id" element from "C scale"
And update previously created "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                     |
	| sheet | id     |        | Sheet's id cannot be null |

@TC041005
Scenario: update a Sheet with no tracks field
### create a new sheet without defining the tracks field
### operation should fail and a validation error should be returned

When remove "tracks" element from "C scale"
And a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | tracks |        | Sheet must define tracks |

@TC041006
Scenario: update a Sheet with null tracks field
### create a new sheet defining the tracks field as null
### operation should fail and a validation error should be returned

When set "C scale" object's "tracks" element to "" as "null"
And a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | tracks |        | Sheet must define tracks |
