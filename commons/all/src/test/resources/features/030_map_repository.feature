@MapRepository
Feature: Basic CRUD functionality for map repository
### Verify the ability to create/read/update and delete entities in map repository
### TestMapDomain has been created for that purpose

@TC030001
@Positive
Scenario: retrieve all available entities

When request all available "TestMapDomain"
Then the request was successful
And the response has a status code of 200
And prepare data table "TestMapDomainObjectTemplate"
	| id      | value  |
	| integer | string |
And the response array contains "TestMapDomainObjectTemplate" objects
	| id | value |
	|  1 | one   |
	|  2 | two   |
	|  3 | three |

@TC030002
@Negative
Scenario: access a single entity
### Retrieve an entity and verify its contents

When request a "TestMapDomain" with id "1"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id    | integer |  1  |
	| value | string  | one |

@TC030003
@Negative
Scenario: access an entity that does not exist
### Retrieve an entity that does not exist
### This should return with an error

When request a "TestMapDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030004
@Positive
@Create
Scenario: create a new entity
### create a new entity and verify it is created with the same data as provided
### retrieve the entity and verify the same data are returned

Given a "TestMapDomain" with
	| id    | integer |  4   |
	| value | string  | Four |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    | integer |  4   |
	| value | string  | Four |
When request previously created "TestMapDomain"
And the request was successful
And the response has a status code of 200
And the response matches
	| id    | integer |  4   |
	| value | string  | Four |
Then request all available "TestMapDomain"
And the request was successful
And the response has a status code of 200
And prepare data table "TestMapDomainObjectTemplate"
	| id      | value  |
	| integer | string |
And the response array contains "TestMapDomainObjectTemplate" objects
	| id | value |
	|  4 | Four  |
And the response array contains latest "TestMapDomain"

@TC030005
@Negative @Create
Scenario: create a dublicate entity
### create an entity that already exists
### this should generate an error

Given a "TestMapDomain" with
	| id    | integer |  1        |
	| value | string  | dublicate |
And the request was not successful
And the response has a status code of 409
And the response matches
	| code | integer | 409      |
	| text | string  | CONFLICT |

@TC030006
@Positive @Update
Scenario: update an entity
### create an entity and then update it
### verify that the entity contents have been updated

Given a "TestMapDomain" with
	| id    | integer |  5   |
	| value | string  | nope |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    | integer |  5   |
	| value | string  | nope |
When update previously created "TestMapDomain"
	| id    | integer |  5   |
	| value | string  | five |
And the request was successful
And the response has a status code of 204
Then request all available "TestMapDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "id" with "integer" value "5"
And the response array contains "value" with "string" value "five"

@TC030007
@Negative @Update
Scenario: update a non existing entity
### attempt to update an entity that does not exist
### this should generate an error

When update "TestMapDomain" with id "1000"
	| id    | integer |  5   |
	| value | string  | five |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030008
@Positive @Delete
Scenario: delete an entity
### create an entity and then delete it
### verify that the entity is no longer accessible

Given a "TestMapDomain" with
	| id    | integer |  6  |
	| value | string  | six |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    | integer |  6  |
	| value | string  | six |
When delete "TestMapDomain" with id "6"
And the request was successful
And the response has a status code of 204
Then request a "TestMapDomain" with id "6"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
And request all available "TestMapDomain"
And the request was successful
And the response has a status code of 200
And the response array does not contain "id" with "integer" value "6"
And the response array does not contain "value" with "string" value "six"

@TC030009
@Negative @Delete
Scenario: delete a non existing entity
### attempt to delete an entity that does not exist
### this should generate an error

When delete "TestMapDomain" with id "1000"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC030010
@Positive @Update
Scenario: update an entity's id
### create an entity and then update it's id
### verify that the entity contents have been updated

Given a "TestMapDomain" with
	| id    | integer |  777  |
	| value | string  | seven |
And the request was successful
And the response has a status code of 201
And the response matches
	| id    | integer |  777  |
	| value | string  | seven |
When update previously created "TestMapDomain"
	| id    | integer |   7   |
	| value | string  | seven |
And the request was successful
And the response has a status code of 204
Then request all available "TestMapDomain"
And the request was successful
And the response has a status code of 200
And the response array contains "id" with "integer" value "7"
And the response array contains "value" with "string" value "seven"
And the response array does not contain "id" with "integer" value "777"
And request a "TestMapDomain" with id "7"
Then the request was successful
And the response has a status code of 200
And the response matches
	| id    | integer |   7   |
	| value | string  | seven |
And request a "TestMapDomain" with id "777"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |
