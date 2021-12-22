@Clef
Feature: Basic Clef CRUD functionality
### Verify the ability to create/read/update and delete Clefs

@Positive
Scenario: retrieve all available Clefs
### Retrieve the list of all available clefs
### Verify that all common clefs are populated by default
### Ensure that the clefs that are going to be used later in the test do not

When request all available "clef"
Then the request was successful
And the response has a status code of 200
And the response array contains
	| id            | type | note | spos |
	| French Violin |  G   |  64  |  -4  |
	| Treble        |  G   |  64  |  -2  |
	| Soprano       |  C   |  60  |  -4  |
	| Mezzo-soprano |  C   |  60  |  -2  |
	| Alto          |  C   |  60  |   0  |
	| Tenor         |  C   |  60  |   2  |
	| Baritone      |  F   |  53  |   0  |
	| Bass          |  F   |  53  |   2  |
	| Subbass       |  F   |  53  |   4  |
And the response array does not contain "id" with value "New Clef"
And the response array does not contain "id" with value "Updated Clef"

@Negative
Scenario: search for Clef is not available
### Attempt to search for a clef
### Verify that an error is generated

When search "clef" with keyword "Bass"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

@Positive
Scenario: access a single Clef by id
### Retrieve one of the common clefs
### Verify its contents are retrieved correctly

When request a "clef" with id "Treble"
Then the request was successful
And the response has a status code of 200
And the response matches
	|  id  | Treble |
	| type | G      |
	| note | 64     |
	| spos | -2     |

@Negative
Scenario: access a Clef that does not exist
### Retrieve a clef that does not exist
### This should return with an error

When request a "clef" with id "NotAClef"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Negative
Scenario: create a new Clef
### create a new clef is not allowed

Given a "clef" with
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | 400         |
	| text | BAD_REQUEST |

@Negative
Scenario: update a Clef
### updating clefs is not allowed

Given update "clef" with id "NotAClef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | 400         |
	| text | BAD_REQUEST |

@Negative
Scenario: delete a Clef
### deleting clefs is not allowed

When delete "clef" with id "NotAClef"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | 400         |
	| text | BAD_REQUEST |
