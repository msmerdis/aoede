@TimeSignature
Feature: Basic Time signature CRUD functionality
### Verify the ability to create/read/update and delete Time signatures

Background: Setup beats

Given prepare json array "beats01"
	| integer | 0 |
	| integer | 1 |
And prepare json array "beats012"
	| integer | 0 |
	| integer | 1 |
	| integer | 2 |
And prepare json array "beats02"
	| integer | 0 |
	| integer | 2 |
And prepare json array "beats03"
	| integer | 0 |
	| integer | 3 |
And prepare json array "beats036"
	| integer | 0 |
	| integer | 3 |
	| integer | 6 |
And prepare json array "beats0369"
	| integer | 0 |
	| integer | 3 |
	| integer | 6 |
	| integer | 9 |

@TC014001
@Positive
Scenario: retrieve all available Time signatures
### Retrieve the list of all available time signatures
### Verify that all common time signatures are populated by default
### Ensure that the time signatures that are going to be used later in the test do not

When request all available "timeSignature"
Then the request was successful
And the response has a status code of 200
And prepare data table "timeSignatureObject"
	| numerator | denominator | beats     |
	| integer   | integer     | json      |
And the response array contains "timeSignatureObject" objects
	| numerator | denominator | beats     |
	|     2     |      4      | beats01   |
	|     2     |      2      | beats01   |
	|     3     |      8      | beats012  |
	|     3     |      4      | beats012  |
	|     3     |      2      | beats012  |
	|     4     |      8      | beats02   |
	|     4     |      4      | beats02   |
	|     4     |      2      | beats02   |
	|     6     |      8      | beats03   |
	|     6     |      4      | beats03   |
	|     9     |      8      | beats036  |
	|     9     |      4      | beats036  |
	|    12     |      8      | beats0369 |
	|    12     |      4      | beats0369 |
And "timeSignature" returned array of size 14

@TC014002
@Negative
Scenario: search for Time signature is not available
### Attempt to search for a time signature
### Verify that an error is generated

When search "timeSignature" with keyword "2"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC014003
@Positive
Scenario Outline: access a single Time signature by id
### Retrieve one of the common time signatures
### Verify its contents are retrieved correctly

When request a "timeSignature" with id "<numerator>-<denominator>"
Then the request was successful
And the response has a status code of 200
And the response matches
	| numerator   | integer | <numerator>   |
	| denominator | integer | <denominator> |
	| beats       | json    | <beats>       |

Examples:
	| numerator | denominator | beats     |
	|     2     |      4      | beats01   |
	|     2     |      2      | beats01   |
	|     3     |      8      | beats012  |
	|     3     |      4      | beats012  |
	|     3     |      2      | beats012  |
	|     4     |      8      | beats02   |
	|     4     |      4      | beats02   |
	|     4     |      2      | beats02   |
	|     6     |      8      | beats03   |
	|     6     |      4      | beats03   |
	|     9     |      8      | beats036  |
	|     9     |      4      | beats036  |
	|    12     |      8      | beats0369 |
	|    12     |      4      | beats0369 |

@TC014004
@Negative
Scenario: access a Time signature that does not exist
### Retrieve a time signature that does not exist
### This should return with an error

When request a "timeSignature" with id "5-4"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC014005
@Negative @Create
Scenario: create a new Time signature
### create a new time signature is not allowed

Given a "timeSignature" with
	| numerator   | integer | 5       |
	| denominator | integer | 4       |
	| beats       | json    | beats02 |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC014006
@Negative @Update
Scenario: update a Time signature
### updating time signatures is not allowed

Given update "timeSignature" with id "4-4"
	| numerator   | integer |    4    |
	| denominator | integer |    4    |
	| beats       | json    | beats01 |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "timeSignature" with id "4-4"
Then the request was successful
And the response has a status code of 200
And the response matches
	| numerator   | integer |    4    |
	| denominator | integer |    4    |
	| beats       | json    | beats02 |

@TC014007
@Negative @Delete
Scenario: delete a Time signature
### deleting time signatures is not allowed

When delete "timeSignature" with id "2-2"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "timeSignature" with id "2-2"
Then the request was successful
And the response has a status code of 200
And the response matches
	| numerator   | integer |    2    |
	| denominator | integer |    2    |
	| beats       | json    | beats01 |
