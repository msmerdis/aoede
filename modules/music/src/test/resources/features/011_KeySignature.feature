@KeySignature
Feature: Basic Key signature CRUD functionality
### Verify the ability to create/read/update and delete Key signatures

@TC011001
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

@TC011002
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

@TC011003
@Positive
Scenario Outline: access a single Key signature by id
### Retrieve one of the common key signatures
### Verify its contents are retrieved correctly

When request a "keySignature" with id "<id>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |    <id> |
	| major | string  | <major> |
	| minor | string  | <minor> |

Examples:
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

@TC011004
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

@TC011005
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

@TC011006
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

@TC011007
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

@TC011008
@Positive
Scenario Outline: access a major Key signature
### Retrieve a key signature using its major value

When request a key signature with major "<major>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |    <id> |
	| major | string  | <major> |
	| minor | string  | <minor> |

Examples:
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

@TC011009
@Positive
Scenario Outline: access a minor Key signature
### Retrieve a key signature using its minor value

When request a key signature with minor "<minor>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|    id | integer |    <id> |
	| major | string  | <major> |
	| minor | string  | <minor> |

Examples:
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

@TC011010
@Negative
Scenario: access a major Key signature that does not exist
### Retrieve a key signature using its major value that does not exist
### This should return with an error

When request a key signature with major "W"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC011011
@Negative
Scenario: access a minor Key signature that does not exist
### Retrieve a key signature using its minor value that does not exist
### This should return with an error

When request a key signature with minor "M"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC011012
@Positive @NoteOffset
Scenario Outline: validate Key signatures note offset
### Retrieve a key signature and validate its note offsets

When request a "keySignature" with id "<id>"
And the request was successful
Then prepare json "key" with "keySignature" as "object"
And json object "key" contains "notes"
And "key" json matches
	| notes | note offset array | <C> <C+> <D> <D+> <E> <F> <F+> <G> <G+> <A> <A+> <B> |
And store "notes" element from "key" object as "notes"
And json array "notes" contains 12 items
And "notes" json array 0 element matches "<C>" as "note offset"
And "notes" json array 1 element matches "<C+>" as "note offset"
And "notes" json array 2 element matches "<D>" as "note offset"
And "notes" json array 3 element matches "<D+>" as "note offset"
And "notes" json array 4 element matches "<E>" as "note offset"
And "notes" json array 5 element matches "<F>" as "note offset"
And "notes" json array 6 element matches "<F+>" as "note offset"
And "notes" json array 7 element matches "<G>" as "note offset"
And "notes" json array 8 element matches "<G+>" as "note offset"
And "notes" json array 9 element matches "<A>" as "note offset"
And "notes" json array 10 element matches "<A+>" as "note offset"
And "notes" json array 11 element matches "<B>" as "note offset"

Examples:
	| id |  C  |  C+ |  D  |  D+ |  E  |  F  |  F+ |  G  |  G+ |  A  |  A+ |  B  |
	| -7 |  1- |  1  |  2- |  2  |  3  |  4- |  4  |  5- |  5  |  6- |  6  |  7  |
	| -6 |  1- |  1  |  2- |  2  |  3- |  3  |  4  |  5- |  5  |  6- |  6  |  7  |
	| -5 |  0  |  1  |  2- |  2  |  3- |  3  |  4  |  5- |  5  |  6- |  6  |  7- |
	| -4 |  0  |  1  |  2- |  2  |  3- |  3  |  4- |  4  |  5  |  6- |  6  |  7- |
	| -3 |  0  |  1- |  1  |  2  |  3- |  3  |  4- |  4  |  5  |  6- |  6  |  7- |
	| -2 |  0  |  1- |  1  |  2  |  3- |  3  |  4- |  4  |  5- |  5  |  6  |  7- |
	| -1 |  0  |  1- |  1  |  2- |  2  |  3  |  4- |  4  |  5- |  5  |  6  |  7- |
	|  0 |  0  |  0+ |  1  |  1+ |  2  |  3  |  3+ |  4  |  4+ |  5  |  5+ |  6  |
	|  1 |  0  |  0+ |  1  |  1+ |  2  |  2+ |  3  |  4  |  4+ |  5  |  5+ |  6  |
	|  2 | -1+ |  0  |  1  |  1+ |  2  |  2+ |  3  |  4  |  4+ |  5  |  5+ |  6  |
	|  3 | -1+ |  0  |  1  |  1+ |  2  |  2+ |  3  |  3+ |  4  |  5  |  5+ |  6  |
	|  4 | -1+ |  0  |  0+ |  1  |  2  |  2+ |  3  |  3+ |  4  |  5  |  5+ |  6  |
	|  5 | -1+ |  0  |  0+ |  1  |  2  |  2+ |  3  |  3+ |  4  |  4+ |  5  |  6  |
	|  6 | -1+ |  0  |  0+ |  1  |  1+ |  2  |  3  |  3+ |  4  |  4+ |  5  |  6  |
	|  7 | -1  |  0  |  0+ |  1  |  1+ |  2  |  3  |  3+ |  4  |  4+ |  5  |  5+ |
