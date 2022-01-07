@HashMapRepository
Feature: Basic CRUD functionality for hash map repository
### Verify the ability to create/read/update and delete entities in hash map repository
### TestHashMapDomain has been created for that purpose

@Positive
Scenario: retrieve all available entities

When request all available "TestHashMapDomain"
Then the request was successful
And the response has a status code of 200
And the response array contains
	| id | value |
	|  1 | one   |
	|  2 | two   |
	|  3 | three |

@Negative
Scenario: access a single entity
### Retrieve an entity and verify its contents

When request a "TestHashMapDomain" with id "1"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id    |  1  |
	| value | one |

@Negative
Scenario: access an entity that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "TestHashMapDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: create a new entity
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "TestHashMapDomain" with
	| id    |  4   |
	| value | Four |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    |  4   |
	| value | Four |
When request previously created "TestHashMapDomain"
And the request was successful
And the response has a status code of 200
And the response matches
	| id    |  4   |
	| value | Four |
Then request all available "TestHashMapDomain"
And the request was successful
And the response has a status code of 200
And the response array contains
	| id | value |
	|  4 | Four  |
And the response array contains latest "TestHashMapDomain"

@Negative
Scenario: create a dublicate entity
### create an entity that already exists
### this should generate an error

Given a "TestHashMapDomain" with
	| id    |  1        |
	| value | dublicate |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | 409      |
	| text | CONFLICT |

@Positive
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "TestHashMapDomain" with
	| id    |  5   |
	| value | nope |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    |  5   |
	| value | nope |
When update previously created "TestHashMapDomain"
	| id    |  5   |
	| value | five |
And the request was successful
And the response has a status code of 204
Then request all available "TestHashMapDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "id" with value "5"
And the response array contains "value" with value "five"

@Negative
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When update "TestHashMapDomain" with id "1000"
	| id    |  5   |
	| value | five |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "TestHashMapDomain" with
	| id    |  6  |
	| value | six |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    |  6  |
	| value | six |
When delete "TestHashMapDomain" with id "6"
And the request was successful
And the response has a status code of 204
Then request a "TestHashMapDomain" with id "6"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
And request all available "TestHashMapDomain"
And the request was successful
And the response has a status code of 200
And the response array does not contain "id" with value "6"
And the response array does not contain "value" with value "six"

@Negative
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "TestHashMapDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: update an entity's id
### create an entity and then update it's id
### verify that the entity contents have been updated

Given a "TestHashMapDomain" with
	| id    |  777  |
	| value | seven |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    |  777  |
	| value | seven |
When update previously created "TestHashMapDomain"
	| id    |   7   |
	| value | seven |
And the request was successful
And the response has a status code of 204
Then request all available "TestHashMapDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "id" with value "7"
And the response array contains "value" with value "seven"
And the response array does not contain "id" with value "777"
And request a "TestHashMapDomain" with id "7"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id    |   7   |
	| value | seven |
And request a "TestHashMapDomain" with id "777"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
