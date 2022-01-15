@SimpleKey
Feature: Basic CRUD functionality for hash map repository
### Verify the ability to create/read/update and delete entities in hash map repository
### SimpleKeyDomain has been created for that purpose

@TC040001
@Positive
Scenario: retrieve all available entities

Given a "SimpleKeyDomain" with
	| value | string | simply one |
And the request was successful
And the response has a status code of 201
And a "SimpleKeyDomain" with
	| value | string | simply two |
And the request was successful
And the response has a status code of 201
And a "SimpleKeyDomain" with
	| value | string | simply three |
And the request was successful
And the response has a status code of 201
When request all available "SimpleKeyDomain"
Then the request was successful
And the response has a status code of 200
And the response array contains
	| id | value        |
	|  1 | simply one   |
	|  2 | simply two   |
	|  3 | simply three |

@TC040002
@Negative
Scenario: access a single entity
### Retrieve an entity and verify its contents

When request a "SimpleKeyDomain" with id "1"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id    | integer |      1     |
	| value | string  | simply one |

@TC040003
@Negative
Scenario: access an entity that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "SimpleKeyDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC040004
@Positive @Create
Scenario: create a new entity
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "SimpleKeyDomain" with
	| value | string | simply four |
And the request was successful
And the response has a status code of 201
And the response matches
	| value | string | simply four |
When request previously created "SimpleKeyDomain"
And the request was successful
And the response has a status code of 200
And the response matches
	| value | string | simply four |
Then request all available "SimpleKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array contains
	| id | value       |
	|  4 | simply four |
And the response array contains latest "SimpleKeyDomain"

@TC040005
@Negative @Create
Scenario: create a dublicate entity
### create an entity that already exists
### this should generate an error

Given a "SimpleKeyDomain" with
	| value | string | simply one |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | integer | 409      |
	| text | string  | CONFLICT |

@TC040006
@Positive @Update
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "SimpleKeyDomain" with
	| value | string | nope |
And the request was successful
And the response has a status code of 201
And the response matches
	| value | string | nope |
When update previously created "SimpleKeyDomain"
	| value | string | simply five |
And the request was successful
And the response has a status code of 204
Then request all available "SimpleKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "id" with value "6"
And the response array contains "value" with value "simply five"

@TC040007
@Negative @Update
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When update "SimpleKeyDomain" with id "1000"
	| value | string | simply five |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC040008
@Positive @Delete
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "SimpleKeyDomain" with
	| value | string | simply six |
And the request was successful
And the response has a status code of 201
And the response matches
	| value | string | simply six |
When delete "SimpleKeyDomain" with id "7"
And the request was successful
And the response has a status code of 204
Then request a "SimpleKeyDomain" with id "7"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
And request all available "SimpleKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array does not contain "id" with value "7"
And the response array does not contain "value" with value "simply six"

@TC040009
@Negative @Delete
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "SimpleKeyDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
