@User @Validation @Negative
Feature: Basic User field vaildation
### Verify input feelds for user actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |

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
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                          |
	| createUser | status |        | User must define a user status |

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
And the response contains "validationInfo" objects in "validations"
	| name       | field    | value  | error                       |
	| createUser | username |        | User must define a username |

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
And the response contains "validationInfo" objects in "validations"
	| name       | field    | value  | error                       |
	| createUser | password |        | User must define a password |

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
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                |
	| UserStatus | status | status | Invalid status value |

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
And the response contains "validationInfo" objects in "validations"
	| name       | field    | value  | error                          |
	| createUser | status   |        | User must define a user status |
	| createUser | username |        | User must define a username    |
	| createUser | password |        | User must define a password    |

@TC023006
@Create
Scenario: create user with null status, username and password

When a "user" with
	| status   | null | |
	| username | null | |
	| password | null | |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field    | value  | error                          |
	| createUser | status   |        | User must define a user status |
	| createUser | username |        | User must define a username    |
	| createUser | password |        | User must define a password    |

@TC023007
@Create
Scenario: create user with null status, username and password

When a "user" without data
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field    | value  | error                          |
	| createUser | status   |        | User must define a user status |
	| createUser | username |        | User must define a username    |
	| createUser | password |        | User must define a password    |

@TC023009
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
And the response contains "validationInfo" objects in "validations"
	| name       | field  | value  | error                |
	| UserStatus | status | status | Invalid status value |

@TC023010
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
And the response contains "validationInfo" objects in "validations"
	| name           | field    | value  | error                    |
	| changePassword | password |        | Password cannot be empty |

@TC023011
Scenario: log in with empty username

When "" attempts to login with password "password"
And the "login" request was not successful
And the "login" response matches
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |

@TC023012
Scenario: log in with empty password

When "username" attempts to login with password ""
And the "login" request was not successful
And the "login" response matches
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |

@TC023013
Scenario: log in with empty username and password

When "" attempts to login with password ""
And the "login" request was not successful
And the "login" response matches
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
