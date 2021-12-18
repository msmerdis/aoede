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

@Positive
Scenario: create a new Clef
### create a new clef and verify the clef is created with the same data as provided
### retrieve the clef and verify the same data are returned

Given a "clef" with
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
When request a "clef" with id "New Clef"
Then the request was successful
And the response has a status code of 200
And the response matches
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |

@Negative
Scenario: create dublicate Clef
### attempt to create a clef that already exists
### this should generate an error

Given a "clef" with
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
Then the request was not successful
And the response has a status code of 409
And the response matches
	| code | 409      |
	| text | CONFLICT |

@Positive
Scenario: update an existing Clef
### update the clef created before
### updates both the clef id and its contents
### verify that the clef is now accessible through the new id
### verify that the clef contents have been updated

Given update "clef" with id "New Clef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
And the request was successful
And the response has a status code of 204
When request a "clef" with id "Updated Clef"
And the request was successful
And the response has a status code of 200
And the response matches
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |

@Negative
Scenario: update a non existing Clef
### attempt to update a clef that does not exist
### this should generate an error

When update "clef" with id "NotAClef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: delete an existing Clef
### delete the previously updated clef
### verify that the clef is no longer accessible

Given delete "clef" with id "Updated Clef"
And the request was successful
And the response has a status code of 204
When request a "clef" with id "Updated Clef"
And the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Negative
Scenario: delete a non existing Clef
### attempt to delete a clef that does not exist
### this should generate an error

When delete "clef" with id "NotAClef"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

@Positive
Scenario: create a new Clef (list)
### create a new clef and verify the clef is created with the same data as provided
### retrieve list of all the clefs and verify the new clef is returned in the list

Given a "clef" with
	|  id  | New Clef |
	| type | C        |
	| note | 60       |
	| spos | 4        |
And the request was successful
And the response has a status code of 201
And the response matches
	|  id  | New Clef |
	| type | C        |
	| note | 60       |
	| spos | 4        |
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
	| New Clef      |  C   |  60  |   4  |
And the response array does not contain "id" with value "Updated Clef"

@Positive
Scenario: update an existing Clef (list)
### update an existing clef and verify the clef
### retrieve list of all the clefs and verify the clef is returned in the list with its new name and contents
### verify that a clef with the previous id does not exist

Given update "clef" with id "New Clef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
And the request was successful
And the response has a status code of 204
When request all available "clef"
And the request was successful
Then the response has a status code of 200
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
	| Updated Clef  |  A   |  1   |   2  |
And the response array does not contain "id" with value "New Clef"
