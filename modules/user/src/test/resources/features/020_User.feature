@User
Feature: Basic User CRUD functionality
### Verify the ability to create/read/update and delete Users

@TC020001
@Positive
Scenario: retrieve all available users

Given a "user" with
	| status   | string | ACTIVE |
	| username | string | user_1 |
	| password | string | pass_1 |
And the request was successful
And the response has a status code of 201
And a "user" with
	| status   | string | ACTIVE |
	| username | string | user_2 |
	| password | string | pass_2 |
And the request was successful
And the response has a status code of 201
And a "user" with
	| status   | string | ACTIVE |
	| username | string | user_3 |
	| password | string | pass_3 |
And the request was successful
And the response has a status code of 201
When request all available "user"
Then the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	|  id  | status | username |
	| long | string | string   |
And the response array contains "userTemplate" objects
	|  id  | status | username |
	|   1  | ACTIVE | user_1   |
	|   2  | ACTIVE | user_2   |
	|   3  | ACTIVE | user_3   |

@TC020002
@Negative
Scenario: access a single user
### Retrieve an entity and verify its contents

Given a "user" with
	| status   | string | ACTIVE     |
	| username | string | accessName |
	| password | string | accessPass |
When request previously created "user"
Then the request was successful
And the response has a status code of 200
And the response matches
	| status   | string | ACTIVE     |
	| username | string | accessName |

@TC020003
@Negative
Scenario: access a user that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "user" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC020004
@Positive @Create
Scenario: create a new user
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "user" with
	| status   | string | ACTIVE     |
	| username | string | createName |
	| password | string | createPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE     |
	| username | string | createName |
When request previously created "user"
And the request was successful
And the response has a status code of 200
And the response matches
	| id       | key    | user       |
	| status   | string | ACTIVE     |
	| username | string | createName |
Then request all available "user"
And the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	| id   | status | username   |
	| key  | string | string     |
And the response array contains "userTemplate" objects
	| id   | status | username   |
	| user | ACTIVE | createName |
And the response array contains latest "user"

@TC020005
@Negative @Create
Scenario: create a dublicate user
### create an entity that already exists
### this should generate an error

Given a "user" with
	| status   | string | ACTIVE     |
	| username | string | createName |
	| password | string | createPass |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | integer | 409      |
	| text | string  | CONFLICT |

@TC020006
@Positive @Update
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "user" with
	| status   | string | PENDING  |
	| username | string | tempName |
	| password | string | tempPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | PENDING  |
	| username | string | tempName |
When update previously created "user"
	| status   | string | ACTIVE     |
	| username | string | updateName |
And the request was successful
And the response has a status code of 204
Then request previously created "user"
And the request was successful
And the response has a status code of 200
And the response matches
	| id       | key    | user       |
	| status   | string | ACTIVE     |
	| username | string | updateName |
And request all available "user"
And the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	| id   | status | username   |
	| key  | string | string     |
And the response array contains "userTemplate" objects
	| id   | status | username   |
	| user | ACTIVE | updateName |
And the response array contains latest "user"
And the response array contains "username" with "string" value "updateName"
And the response array does not contain "username" with "string" value "tempName"

@TC020007
@Negative @Update
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When update "user" with id "1000"
	| status   | string | ACTIVE     |
	| username | string | updateName |
	| password | string | updatePass |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC020008
@Positive @Delete
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "user" with
	| status   | string | ACTIVE     |
	| username | string | deleteName |
	| password | string | deletePass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE     |
	| username | string | deleteName |
When delete previously created "user"
And the request was successful
And the response has a status code of 204
Then request previously created "user"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
And request all available "user"
And the request was successful
And the response has a status code of 200
And the response array does not contain "id" with "key" value "user"
And the response array does not contain "username" with "string" value "deleteName"

@TC020009
@Negative @Delete
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "user" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC020010
@Negative @Create
Scenario: create a new user with invalid status
### create a new entity with invalid values, this should not be successful

Given a "user" with
	| status   | string | DOESNOTEXIST |
	| username | string | invalidName  |
	| password | string | invalidPass  |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
