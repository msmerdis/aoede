@KeySignature
Feature: Basic Key signature CRUD functionality
### Verify the ability to create/read/update and delete Key signatures

@TC0701
@Positive
Scenario: retrieve all available Key signatures
### Retrieve the list of all available key signatures
### Verify that all common key signatures are populated by default
### Ensure that the key signatures that are going to be used later in the test do not

When request all available "keySignature"
Then the request was successful
And the response has a status code of 200
And prepare data table "keySignatureObject"
	| id      | major  | minor  |
	| integer | string | string |
And the response array contains "keySignatureObject" objects
	| id | major | minor |
	| -7 |   C-  |   a-  |
	| -6 |   G-  |   e-  |
	| -5 |   D-  |   b-  |
	| -4 |   A-  |   f   |
	| -3 |   E-  |   c   |
	| -2 |   B-  |   g   |
	| -1 |   F   |   d   |
	|  0 |   C   |   a   |
	|  1 |   G   |   e   |
	|  2 |   D   |   b   |
	|  3 |   A   |   f+  |
	|  4 |   E   |   c+  |
	|  5 |   B   |   g+  |
	|  6 |   F+  |   d+  |
	|  7 |   C+  |   a+  |
And "keySignature" returned array of size 15

@TC0702
@Negative
Scenario: search for Key signature is not available
### Attempt to search for a key signature
### Verify that an error is generated

When search "keySignature" with keyword "D"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC0703
@Positive
Scenario: access a single Key signature by id
### Retrieve one of the common key signatures
### Verify its contents are retrieved correctly

When request a "keySignature" with id "0"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer | 0 |
	| major | string  | C |
	| minor | string  | a |

@TC0704
@Negative
Scenario: access a Key signature that does not exist
### Retrieve a key signature that does not exist
### This should return with an error

When request a "keySignature" with id "8"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC0705
@Negative @Create
Scenario: create a new Key signature
### create a new key signature is not allowed

Given a "keySignature" with
	|    id | integer | 8 |
	| major | string  | F |
	| minor | string  | d |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC0706
@Negative @Update
Scenario: update a Key signature
### updating key signatures is not allowed

Given update "keySignature" with id "-1"
	|    id | integer | 5 |
	| major | string  | F |
	| minor | string  | d |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "keySignature" with id "5"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer | 5  |
	| major | string  | B  |
	| minor | string  | g+ |

@TC0707
@Negative @Delete
Scenario: delete a Key signature
### deleting key signatures is not allowed

When delete "keySignature" with id "-2"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "keySignature" with id "-5"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer | -5 |
	| major | string  | D- |
	| minor | string  | b- |
