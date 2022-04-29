@User @Validation @Negative
Feature: Basic User field vaildation
### Verify input feelds for user actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| field  | value  | error  |
	| string | string | string |

@TC023001
@Create
Scenario: create user with empty status

When a "user" with
	| status   | string |          |
	| username | string | username |
	| password | string | password |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field  | value  | error                          |
	| status |        | User must define a user status |

@TC023002
@Create
Scenario: create user with empty username

When a "user" with
	| status   | string | status   |
	| username | string |          |
	| password | string | password |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field    | value  | error                       |
	| username |        | User must define a username |

@TC023003
@Create
Scenario: create user with empty password

When a "user" with
	| status   | string | status   |
	| username | string | username |
	| password | string |          |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field    | value  | error                       |
	| password |        | User must define a password |

@TC023004
@Create
Scenario: create user with incorrect status

When a "user" with
	| status   | string | status   |
	| username | string | username |
	| password | string | password |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field  | value  | error                |
	| status | status | Invalid status value |

@TC023005
@Create
Scenario: create user with empty status, username and password

When a "user" with
	| status   | string | |
	| username | string | |
	| password | string | |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field    | value  | error                          |
	| status   |        | User must define a user status |
	| username |        | User must define a username    |
	| password |        | User must define a password    |

@TC023006
@Update
Scenario: update user with incorrect status

When update "user" with id "1000"
	| status   | string | status   |
	| username | string | username |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field  | value  | error                |
	| status | status | Invalid status value |

@TC023007
@Update
Scenario: update users password with empty value

Given a "user" with
	| status   | string | ACTIVE                  |
	| username | string | updateEmptyPasswordName |
	| password | string | updateEmptyPasswordPass |
And the request was successful
When update latest users password to ""
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response array contains "validationInfo" objects in "validations"
	| field    | value  | error                    |
	| password |        | Password cannot be empty |

@TC023008
Scenario: log in with empty username

When "" attempts to login with password "password"
And login has failed
And login results match
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |

@TC023009
Scenario: log in with empty password

When "username" attempts to login with password ""
And login has failed
And login results match
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |

@TC023010
Scenario: log in with empty username and password

When "" attempts to login with password ""
And login has failed
And login results match
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
