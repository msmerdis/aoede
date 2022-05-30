@Validation @Sheet @Negative @Create
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

@TC040001
Scenario: create a Sheet with empty name
### create a new sheet with an empty name
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And set "C scale" object's "name" element to "" as "string"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC040002
Scenario: create a Sheet with null as name
### create a new sheet with null as name
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And set "C scale" object's "name" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC040003
Scenario: create a Sheet without a name element
### create a new sheet without a name element
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And remove "name" element from "C scale"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | name   |        | Sheet must define a name |

@TC040004
Scenario: create a Sheet with an id
### create a new sheet and additionally provide an id
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And set "C scale" object's "id" element to "1" as "int"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field  | value  | error                                      |
	| sheet | id     | 1      | Sheet cannot provide an id during creation |

@TC040005
Scenario: create a Sheet with no tracks field
### create a new sheet without defining the tracks field
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And remove "tracks" element from "C scale"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | tracks |        | Sheet must define tracks |

@TC040006
Scenario: create a Sheet with null tracks field
### create a new sheet defining the tracks field as null
### operation should fail and a validation error should be returned

Given prepare "C" scale as "C scale"
And set "C scale" object's "tracks" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field  | value  | error                    |
	| sheet | tracks |        | Sheet must define tracks |
