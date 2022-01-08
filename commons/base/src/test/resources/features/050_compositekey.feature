@CompositeKey
Feature: Basic CRUD functionality for hash map repository
### Verify the ability to create/read/update and delete entities in hash map repository
### CompositeKeyDomain has been created for that purpose

@Positive
Scenario: retrieve all available entities

Given a "CompositeKeyDomain" with
	| parentId | 1             |
	| childId  | 1             |
	| value    | composite one |
And the request was successful
And the response has a status code of 201
And a "CompositeKeyDomain" with
	| parentId | 1             |
	| childId  | 2             |
	| value    | composite two |
And the request was successful
And the response has a status code of 201
And a "CompositeKeyDomain" with
	| parentId | 2               |
	| childId  | 1               |
	| value    | composite three |
And the request was successful
And the response has a status code of 201
When request all available "CompositeKeyDomain"
Then the request was successful
And the response has a status code of 200
And the response array contains
	| parentId | childId | value           |
	|     1    |    1    | composite one   |
	|     1    |    2    | composite two   |
	|     2    |    1    | composite three |

@Negative
Scenario: access a single entity
### Retrieve an entity and verify its contents

When request a "CompositeKeyDomain" with composite id
	| parentId | integer | 1 |
	| childId  | integer | 2 |
Then the request was successful
And the response has a status code of 200
And the response matches
	| parentId |      1        |
	| childId  |      2        |
	| value    | composite two |

@Negative
Scenario: access an entity that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "CompositeKeyDomain" with composite id
	| parentId | integer | 1000 |
	| childId  | integer | 2000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: create a new entity
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "CompositeKeyDomain" with
	| parentId | 4              |
	| childId  | 1              |
	| value    | composite four |
And the request was successful
And the response has a status code of 201
And the response matches
	| parentId | 4              |
	| childId  | 1              |
	| value    | composite four |
When request previously created "CompositeKeyDomain"
And the request was successful
And the response has a status code of 200
And the response matches
	| parentId | 4              |
	| childId  | 1              |
	| value    | composite four |
Then request all available "CompositeKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array contains
	| parentId | childId | value          |
	|     4    |    1    | composite four |
And the response array contains latest "CompositeKeyDomain"

@Negative
Scenario: create a dublicate entity
### create an entity that already exists
### this should generate an error

Given a "CompositeKeyDomain" with
	| parentId | 2             |
	| childId  | 1             |
	| value    | composite one |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | 409      |
	| text | CONFLICT |

@Positive
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "CompositeKeyDomain" with
	| parentId |  3   |
	| childId  |  1   |
	| value    | nope |
And the request was successful
And the response has a status code of 201
And the response matches
	| parentId |  3   |
	| childId  |  1   |
	| value    | nope |
When update previously created "CompositeKeyDomain"
	| value | composite five |
And the request was successful
And the response has a status code of 204
Then request all available "CompositeKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "parentId" with value "3"
And the response array contains "childId" with value "1"
And the response array contains "value" with value "composite five"

@Negative
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When prepare composite id "nonExistingCompositeId"
	| parentId | integer | 1000 |
	| childId  | integer | 2000 |
And update "CompositeKeyDomain" with composite id "nonExistingCompositeId"
	| value | anything |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "CompositeKeyDomain" with
	| parentId |       4       |
	| childId  |       1       |
	| value    | composite six |
And the request was successful
And the response has a status code of 201
And the response matches
	| parentId |       4       |
	| childId  |       1       |
	| value    | composite six |
When delete "CompositeKeyDomain" with composite id
	| parentId | integer | 4 |
	| childId  | integer | 1 |
And the request was successful
And the response has a status code of 204
Then request a "CompositeKeyDomain" with composite id
	| parentId | integer | 4 |
	| childId  | integer | 1 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
And request all available "CompositeKeyDomain"
And the request was successful
And the response has a status code of 200
And the response array does not contain "value" with value "composite six"
And the response array does not contain latest "CompositeKeyDomain"

@Negative
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "CompositeKeyDomain" with composite id
	| parentId | integer | 1000 |
	| childId  | integer | 2000 |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |
