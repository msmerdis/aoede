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
	| passsalt | string | salt_1 |
And the request was successful
And the response has a status code of 201
And a "user" with
	| status   | string | ACTIVE |
	| username | string | user_2 |
	| password | string | pass_2 |
	| passsalt | string | salt_2 |
And the request was successful
And the response has a status code of 201
And a "user" with
	| status   | string | ACTIVE |
	| username | string | user_3 |
	| password | string | pass_3 |
	| passsalt | string | salt_3 |
And the request was successful
And the response has a status code of 201
When request all available "user"
Then the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	| id   | status | username | password | passsalt |
	| long | string | string   | string   | string   |
And the response array contains "userTemplate" objects
	| id | status | username | password | passsalt |
	|  1 | ACTIVE | user_1   | pass_1   | salt_1   |
	|  2 | ACTIVE | user_2   | pass_2   | salt_2   |
	|  3 | ACTIVE | user_3   | pass_3   | salt_3   |

@TC020002
@Negative
Scenario: access a single ROLE
### Retrieve an entity and verify its contents

Given a "user" with
	| status   | string | ACTIVE     |
	| username | string | accessName |
	| password | string | accessPass |
	| passsalt | string | accessSalt |
When request previously created "user"
Then the request was successful
And the response has a status code of 200
And the response matches
	| status   | string | ACTIVE     |
	| username | string | accessName |
	| password | string | accessPass |
	| passsalt | string | accessSalt |

@TC020003
@Negative
Scenario: access a ROLE that does not exist
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
	| passsalt | string | createSalt |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE     |
	| username | string | createName |
	| password | string | createPass |
	| passsalt | string | createSalt |
When request previously created "user"
And the request was successful
And the response has a status code of 200
And the response matches
	| id       | key    | user       |
	| status   | string | ACTIVE     |
	| username | string | createName |
	| password | string | createPass |
	| passsalt | string | createSalt |
Then request all available "user"
And the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	| id  | status | username | password | passsalt |
	| key | string | string   | string   | string   |
And the response array contains "userTemplate" objects
	| id   | status | username   | password   | passsalt   |
	| user | ACTIVE | createName | createPass | createSalt |
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
	| passsalt | string | createSalt |
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
	| passsalt | string | tempSalt |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | PENDING  |
	| username | string | tempName |
	| password | string | tempPass |
	| passsalt | string | tempSalt |
When update previously created "user"
	| status   | string | ACTIVE     |
	| username | string | updateName |
	| password | string | updatePass |
	| passsalt | string | updateSalt |
And the request was successful
And the response has a status code of 204
Then request previously created "user"
And the request was successful
And the response has a status code of 200
And the response matches
	| id       | key    | user       |
	| status   | string | ACTIVE     |
	| username | string | updateName |
	| password | string | updatePass |
	| passsalt | string | updateSalt |
And request all available "user"
And the request was successful
And the response has a status code of 200
And prepare data table "userTemplate"
	| id  | status | username | password | passsalt |
	| key | string | string   | string   | string   |
And the response array contains "userTemplate" objects
	| id   | status | username   | password   | passsalt   |
	| user | ACTIVE | updateName | updatePass | updateSalt |
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
	| passsalt | string | updateSalt |
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
	| passsalt | string | deleteSalt |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE     |
	| username | string | deleteName |
	| password | string | deletePass |
	| passsalt | string | deleteSalt |
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
