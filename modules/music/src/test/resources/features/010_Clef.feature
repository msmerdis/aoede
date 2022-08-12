@Clef
Feature: Basic Clef CRUD functionality
### Verify the ability to create/read/update and delete Clefs

@TC010001
@Positive
Scenario: retrieve all available Clefs
### Retrieve the list of all available clefs
### Verify that all common clefs are populated by default
### Ensure that the clefs that are going to be used later in the test do not

When request all available "clef"
Then the request was successful
And the response has a status code of 200
And prepare data table "clefObject"
	| id            | type   | note    | spos    |
	| string        | string | integer | integer |
And the response array contains "clefObject" objects
	| id            | type | note | spos |
	| French Violin |  G   |  67  |  -4  |
	| Treble        |  G   |  67  |  -2  |
	| Soprano       |  C   |  60  |  -4  |
	| Mezzo-soprano |  C   |  60  |  -2  |
	| Alto          |  C   |  60  |   0  |
	| Tenor         |  C   |  60  |   2  |
	| Baritone      |  F   |  53  |   0  |
	| Bass          |  F   |  53  |   2  |
	| Subbass       |  F   |  53  |   4  |
And the response array does not contain "id" with "string" value "New Clef"
And the response array does not contain "id" with "string" value "Updated Clef"
And "clef" returned array of size 9

@TC010002
@Negative
Scenario: search for Clef is not available
### Attempt to search for a clef
### Verify that an error is generated

When search "clef" with keyword "Bass"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC010003
@Positive
Scenario Outline: access a single Clef by id
### Retrieve one of the common clefs
### Verify its contents are retrieved correctly

When request a "clef" with id "<name>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|  id  | string  | <name> |
	| type | string  | <type> |
	| note | integer | <note> |
	| spos | integer | <spos> |

Examples:
	| name          | type | note | spos |
	| French Violin |  G   |  67  |  -4  |
	| Treble        |  G   |  67  |  -2  |
	| Soprano       |  C   |  60  |  -4  |
	| Mezzo-soprano |  C   |  60  |  -2  |
	| Alto          |  C   |  60  |   0  |
	| Tenor         |  C   |  60  |   2  |
	| Baritone      |  F   |  53  |   0  |
	| Bass          |  F   |  53  |   2  |
	| Subbass       |  F   |  53  |   4  |

@TC010004
@Negative
Scenario: access a Clef that does not exist
### Retrieve a clef that does not exist
### This should return with an error

When request a "clef" with id "NotAClef"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC010005
@Negative @Create
Scenario: create a new Clef
### create a new clef is not allowed

Given a "clef" with
	|  id  | string  | New Clef |
	| type | string  | G        |
	| note | integer | 64       |
	| spos | integer | 0        |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC010006
@Negative @Update
Scenario: update a Clef
### updating clefs is not allowed

Given update "clef" with id "NotAClef"
	|  id  | string  | Updated Clef |
	| type | string  | A            |
	| note | integer | 1            |
	| spos | integer | 2            |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC010007
@Negative @Delete
Scenario: delete a Clef
### deleting clefs is not allowed

When delete "clef" with id "NotAClef"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
