@Clef
Feature: Basic Clef CRUD functionality
### Verify the ability to create/read/update and delete Clefs

Scenario: retrieve all available Clefs
When request all available "clef"
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
And the response array does not contain "id" with value "New Clef"
And the response array does not contain "id" with value "Updated Clef"

Scenario: search for Clef is not available
When search "clef" with keyword "Bass"
Then the response has a status code of 501
And the response matches
	| code | 501             |
	| text | NOT_IMPLEMENTED |

Scenario: access a single Clef by id
When request a "clef" with id "Treble"
Then the response has a status code of 200
And the response matches
	|  id  | Treble |
	| type | G      |
	| note | 64     |
	| spos | -2     |

Scenario: create a new Clef
Given a "clef" with
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
And the response has a status code of 201
And the response matches
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |
When request a "clef" with id "New Clef"
Then the response has a status code of 200
And the response matches
	|  id  | New Clef |
	| type | G        |
	| note | 64       |
	| spos | 0        |

Scenario: update an existing Clef
Given update "clef" with id "New Clef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
And the response has a status code of 204
When request a "clef" with id "Updated Clef"
And the response has a status code of 200
And the response matches
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |

Scenario: delete an existing Clef
Given delete "clef" with id "Updated Clef"
And the response has a status code of 204
When request a "clef" with id "Updated Clef"
And the response has a status code of 404
And the response matches
	| code | 404       |
	| text | NOT_FOUND |

Scenario: create a new Clef (list)
Given a "clef" with
	|  id  | New Clef |
	| type | C        |
	| note | 60       |
	| spos | 4        |
And the response has a status code of 201
And the response matches
	|  id  | New Clef |
	| type | C        |
	| note | 60       |
	| spos | 4        |
When request all available "clef"
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
	| New Clef      |  C   |  60  |   4  |
And the response array does not contain "id" with value "Updated Clef"

Scenario: update an existing Clef (list)
Given update "clef" with id "New Clef"
	|  id  | Updated Clef |
	| type | A            |
	| note | 1            |
	| spos | 2            |
And the response has a status code of 204
When request all available "clef"
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
