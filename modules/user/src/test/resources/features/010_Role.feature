@Role
Feature: Basic Role CRUD functionality
### Verify the ability to create/read/update and delete Roles

@TC010001
@Positive
Scenario: retrieve all available roles

Given a "role" with
	| role | string | VIEW |
And the request was successful
And the response has a status code of 201
And a "role" with
	| role | string | EDIT |
And the request was successful
And the response has a status code of 201
And a "role" with
	| role | string | ADMIN |
And the request was successful
And the response has a status code of 201
When request all available "role"
Then the request was successful
And the response has a status code of 200
And prepare data table "roleTemplate"
	| id      | role |
	| integer | string    |
And the response array contains "roleTemplate" objects
	| id | role |
	|  1 | VIEW      |
	|  2 | EDIT      |
	|  3 | ADMIN     |

@TC010002
@Negative
Scenario: access a single role
### Retrieve an entity and verify its contents

Given a "role" with
	| role | string | CREATE |
When request previously created "role"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id        | key     | role   |
	| role | string  | CREATE |

@TC010003
@Negative
Scenario: access a role that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "role" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC010004
@Positive @Create
Scenario: create a new role
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "role" with
	| role | string | INVITE |
And the request was successful
And the response has a status code of 201
And the response matches
	| role | string | INVITE |
When request previously created "role"
And the request was successful
And the response has a status code of 200
And the response matches
	| id        | key    | role   |
	| role | string | INVITE |
Then request all available "role"
And the request was successful
And the response has a status code of 200
And prepare data table "roleTemplate"
	| id  | role  |
	| key | string     |
And the response array contains "roleTemplate" objects
	| id   | role |
	| role | INVITE    |
And the response array contains latest "role"

@TC010005
@Negative @Create
Scenario: create a dublicate role
### create an entity that already exists
### this should generate an error

Given a "role" with
	| role | string | VIEW |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | integer | 409      |
	| text | string  | CONFLICT |

@TC010006
@Positive @Update
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "role" with
	| role | string | nope |
	| desc | string | nope |
And the request was successful
And the response has a status code of 201
And the response matches
	| role | string | nope |
	| desc | string | nope |
When update previously created "role"
	| role | string | UPDATE |
	| desc | string | UPDATE |
And the request was successful
And the response has a status code of 204
Then request previously created "role"
And the request was successful
And the response has a status code of 200
And the response matches
	| id   | key    | role   |
	| role | string | UPDATE |
	| desc | string | UPDATE |
And request all available "role"
And the request was successful
And the response has a status code of 200
And prepare data table "roleTemplate"
	| id   | role   | desc   |
	| key  | string | string |
And the response array contains "roleTemplate" objects
	| id   | role   | desc   |
	| role | UPDATE | UPDATE |
And the response array contains latest "role"
And the response array contains "role" with "string" value "UPDATE"
And the response array does not contain "role" with "string" value "nope"

@TC010007
@Negative @Update
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When update "role" with id "1000"
	| role | string | UPDATE |
	| desc | string | UPDATE |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC010008
@Positive @Delete
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "role" with
	| role | string | DELETE |
	| desc | string | DELETE |
And the request was successful
And the response has a status code of 201
And the response matches
	| role | string | DELETE |
	| desc | string | DELETE |
When delete previously created "role"
And the request was successful
And the response has a status code of 204
Then request previously created "role"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
And request all available "role"
And the request was successful
And the response has a status code of 200
And the response array does not contain "id" with "key" value "role"
And the response array does not contain "role" with "string" value "DELETE"

@TC010009
@Negative @Delete
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "role" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
