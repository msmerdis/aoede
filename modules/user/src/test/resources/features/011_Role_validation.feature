@Role @Validation @Negative
Feature: Basic Role field vaildation
### Verify input feelds for role actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| field  | value  | error  |
	| string | string | string |

@TC011001
@Create
Scenario: create role with empty name

When a "role" with
	| role | string |             |
	| desc | string | description |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                   |
	| role   |        | Role must define a name |

@TC011002
@Create
Scenario: create role with empty description

When a "role" with
	| role | string | name |
	| desc | string |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                          |
	| desc   |        | Role must define a description |

@TC011003
@Create
Scenario: create role with empty name and description

When a "role" with
	| role | string | |
	| desc | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                          |
	| role   |        | Role must define a name        |
	| desc   |        | Role must define a description |

@TC011004
@Update
Scenario: update an entity with empty name

When update "role" with id "1000"
	| role | string |             |
	| desc | string | description |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                   |
	| role   |        | Role must define a name |

@TC011005
@Update
Scenario: update an entity with empty description

When update "role" with id "1000"
	| role | string | name |
	| desc | string |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                          |
	| desc   |        | Role must define a description |

@TC011006
@Update
Scenario: update an entity with empty name and description

When update "role" with id "1000"
	| role | string | |
	| desc | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| field  | value  | error                          |
	| role   |        | Role must define a name        |
	| desc   |        | Role must define a description |
