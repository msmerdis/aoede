@Sheet @Validation @Negative
Feature: Basic Sheet field vaildation
### Verify input feelds for sheet actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| field  | value  | error  |
	| string | string | string |
And prepare data table "nullValidationInfo"
	| field  | value | error  |
	| string | null  | string |

@TC022001
@Create
Scenario: create sheet with empty name

When a "sheet" with
	| name | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |

@TC022002
@Create
Scenario: create sheet with null name

When a "sheet" with
	| name | null | |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |

@TC022003
@Create
Scenario: create sheet without a name

When a "sheet" without data
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |

@TC022004
@Update
Scenario: update sheet with empty name

When update "sheet" with id "1000"
	| name | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |

@TC022005
@Update
Scenario: update sheet with null name

When update "sheet" with id "1000"
	| name | null | |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |

@TC022006
@Update
Scenario: update sheet without a name

When update "sheet" with id "1000" without data
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| field  | value  | error                    |
	| name   |        | Sheet must define a name |
