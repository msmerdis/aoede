@Octave
Feature: Basic Octave CRUD functionality
### Verify the ability to create/read/update and delete octave

@TC013001
@Positive
Scenario: retrieve all available octaves
### Retrieve the list of all available octaves
### Verify that all common octaves are populated by default
### Ensure that the octaves that are going to be used later in the test do not

When request all available "octave"
Then the request was successful
And the response has a status code of 200
And prepare data table "octaveObject"
	|   id   | name         | pitch  |
	| number | string       | number |
And the response array contains "octaveObject" objects
	|   id   |         name | pitch |
	|   -1   | subsubcontra |    0  |
	|    0   |   sub-contra |   12  |
	|    1   |       contra |   24  |
	|    2   |        great |   36  |
	|    3   |        small |   48  |
	|    4   |    one-lined |   60  |
	|    5   |    two-lined |   72  |
	|    6   |  three-lined |   84  |
	|    7   |   four-lined |   96  |
	|    8   |   five-lined |  108  |
	|    9   |    six-lined |  120  |
And "octave" returned array of size 11

@TC013002
@Negative
Scenario: search for octave is not available
### Attempt to search for a octave
### Verify that an error is generated

When search "octave" with keyword "great"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC013003
@Positive
Scenario Outline: access a single octave by id
### Retrieve one of the common octaves
### Verify its contents are retrieved correctly

When request a "octave" with id "<id>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |    <id> |
	|  name | string  |  <name> |
	| pitch | integer | <pitch> |

Examples:
	|   id   |         name | pitch |
	|   -1   | subsubcontra |    0  |
	|    0   |   sub-contra |   12  |
	|    1   |       contra |   24  |
	|    2   |        great |   36  |
	|    3   |        small |   48  |
	|    4   |    one-lined |   60  |
	|    5   |    two-lined |   72  |
	|    6   |  three-lined |   84  |
	|    7   |   four-lined |   96  |
	|    8   |   five-lined |  108  |
	|    9   |    six-lined |  120  |

@TC013004
@Negative
Scenario: access a octave that does not exist
### Retrieve a octave that does not exist
### This should return with an error

When request a "octave" with id "10"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC013005
@Negative @Create
Scenario: create a new octave
### create a new octave is not allowed

Given a "octave" with
	|    id | integer |          10 |
	|  name | string  | seven-lined |
	| pitch | integer |         112 |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC013006
@Negative @Update
Scenario: update a octave
### updating octaves is not allowed

Given update "octave" with id "0"
	|    id | integer |    10 |
	|  name | string  | other |
	| pitch | integer |   112 |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "octave" with id "0"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |          0 |
	|  name | string  | sub-contra |
	| pitch | integer |         12 |

@TC013007
@Negative @Delete
Scenario: delete a octave
### deleting octaves is not allowed

When delete "octave" with id "1"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "octave" with id "1"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |      1 |
	|  name | string  | contra |
	| pitch | integer |     24 |
