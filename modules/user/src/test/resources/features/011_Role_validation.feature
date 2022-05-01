@Role @Validation @Negative
Feature: Basic Role field vaildation
### Verify input feelds for role actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |

@TC011001
@Create
Scenario: create role with empty name

When a "role" with
	| role | string |             |
	| desc | string | description |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                   |
	| createRole | role   |        | Role must define a name |

@TC011002
@Create
Scenario: create role with empty description

When a "role" with
	| role | string | name |
	| desc | string |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| createRole | desc   |        | Role must define a description |

@TC011003
@Create
Scenario: create role with empty name and description

When a "role" with
	| role | string | |
	| desc | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| createRole | role   |        | Role must define a name        |
	| createRole | desc   |        | Role must define a description |

@TC011004
@Create
Scenario: create role with null name and description

When a "role" with
	| role | null | |
	| desc | null | |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| createRole | role   |        | Role must define a name        |
	| createRole | desc   |        | Role must define a description |

@TC011005
@Create
Scenario: create role with empty name and description

When a "role" without data
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| createRole | role   |        | Role must define a name        |
	| createRole | desc   |        | Role must define a description |

@TC011006
@Update
Scenario: update a role with empty name

When update "role" with id "1000"
	| role | string |             |
	| desc | string | description |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                   |
	| updateRole | role   |        | Role must define a name |

@TC011007
@Update
Scenario: update a role with empty description

When update "role" with id "1000"
	| role | string | name |
	| desc | string |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| updateRole | desc   |        | Role must define a description |

@TC011008
@Update
Scenario: update a role with empty name and description

When update "role" with id "1000"
	| role | string | |
	| desc | string | |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| updateRole | role   |        | Role must define a name        |
	| updateRole | desc   |        | Role must define a description |

@TC011009
@Update
Scenario: update a role with null name and description

When update "role" with id "1000"
	| role | null | |
	| desc | null | |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| updateRole | role   |        | Role must define a name        |
	| updateRole | desc   |        | Role must define a description |

@TC011010
@Update
Scenario: update a role without description

When update "role" with id "1000"
	| role | string | name |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| updateRole | desc   |        | Role must define a description |

@TC011011
@Update
Scenario: update a role without description

When update "role" with id "1000"
	| desc | string | description |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| updateRole | role   |        | Role must define a name        |
