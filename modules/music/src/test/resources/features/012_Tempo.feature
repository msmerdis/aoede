@Tempo
Feature: Basic Tempo CRUD functionality
### Verify the ability to create/read/update and delete tempo

@TC012001
@Positive
Scenario: retrieve all available tempos
### Retrieve the list of all available tempos
### Verify that all common tempos are populated by default
### Ensure that the tempos that are going to be used later in the test do not

When request all available "tempo"
Then the request was successful
And the response has a status code of 200
And prepare data table "tempoObject"
	| id               | desc                                 | minTempo | stdTempo | maxTempo |
	| string           | string                               | number   | number   | number   |
And the response array contains "tempoObject" objects
	| id               | desc                                 | minTempo | stdTempo | maxTempo |
	| Larghissimo      | very, very slow                      |   0      |  24      |  24      |
	| Adagissimo       | very slow                            |  24      |  30      |  40      |
	| Grave            | very slow                            |  25      |  40      |  45      |
	| Largo            | slow and broad                       |  40      |  50      |  60      |
	| Lento            | slow                                 |  45      |  55      |  60      |
	| Larghetto        | rather slow and broad                |  60      |  65      |  66      |
	| Adagio           | slow with great expression           |  66      |  70      |  76      |
	| Adagietto        | slower than andante                  |  72      |  75      |  76      |
	| Andante          | at a walking pace                    |  76      |  80      | 108      |
	| Andantino        | slightly faster than andante         |  80      |  82      | 108      |
	| Marcia moderato  | moderately, in the manner of a march |  83      |  84      |  85      |
	| Andante moderato | between andante and moderato         |  92      | 104      | 112      |
	| Moderato         | at a moderate speed                  | 108      | 114      | 120      |
	| Allegretto       | moderately fast                      | 112      | 116      | 120      |
	| Allegro moderato | close to, but not quite allegro      | 116      | 118      | 120      |
	| Allegro          | fast, quick, and bright              | 120      | 138      | 156      |
	| Molto Allegro    | very fast                            | 124      | 140      | 156      |
	| Vivace           | lively and fast                      | 156      | 166      | 176      |
	| Vivacissimo      | very fast and lively                 | 172      | 174      | 176      |
	| Allegrissimo     | very fast                            | 172      | 174      | 176      |
	| Presto           | very, very fast                      | 168      | 184      | 200      |
	| Prestissimo      | even faster than presto              | 200      | 200      | 512      |
And "tempo" returned array of size 22

@TC012002
@Negative
Scenario: search for tempo is not available
### Attempt to search for a tempo
### Verify that an error is generated

When search "tempo" with keyword "Adagio"
Then the request was not successful
And the response has a status code of 501
And the response matches
	| code | integer | 501             |
	| text | string  | NOT_IMPLEMENTED |

@TC012003
@Positive
Scenario Outline: access a single tempo by id
### Retrieve one of the common tempos
### Verify its contents are retrieved correctly

When request a "tempo" with id "<id>"
Then the request was successful
And the response has a status code of 200
And the response matches
	|       id | string  |       <id> |
	|     desc | string  |     <desc> |
	| minTempo | integer | <minTempo> |
	| stdTempo | integer | <stdTempo> |
	| maxTempo | integer | <maxTempo> |

Examples:
	| id               | desc                                 | minTempo | stdTempo | maxTempo |
	| Larghissimo      | very, very slow                      |   0      |  24      |  24      |
	| Adagissimo       | very slow                            |  24      |  30      |  40      |
	| Grave            | very slow                            |  25      |  40      |  45      |
	| Largo            | slow and broad                       |  40      |  50      |  60      |
	| Lento            | slow                                 |  45      |  55      |  60      |
	| Larghetto        | rather slow and broad                |  60      |  65      |  66      |
	| Adagio           | slow with great expression           |  66      |  70      |  76      |
	| Adagietto        | slower than andante                  |  72      |  75      |  76      |
	| Andante          | at a walking pace                    |  76      |  80      | 108      |
	| Andantino        | slightly faster than andante         |  80      |  82      | 108      |
	| Marcia moderato  | moderately, in the manner of a march |  83      |  84      |  85      |
	| Andante moderato | between andante and moderato         |  92      | 104      | 112      |
	| Moderato         | at a moderate speed                  | 108      | 114      | 120      |
	| Allegretto       | moderately fast                      | 112      | 116      | 120      |
	| Allegro moderato | close to, but not quite allegro      | 116      | 118      | 120      |
	| Allegro          | fast, quick, and bright              | 120      | 138      | 156      |
	| Molto Allegro    | very fast                            | 124      | 140      | 156      |
	| Vivace           | lively and fast                      | 156      | 166      | 176      |
	| Vivacissimo      | very fast and lively                 | 172      | 174      | 176      |
	| Allegrissimo     | very fast                            | 172      | 174      | 176      |
	| Presto           | very, very fast                      | 168      | 184      | 200      |
	| Prestissimo      | even faster than presto              | 200      | 200      | 512      |

@TC012004
@Negative
Scenario: access a tempo that does not exist
### Retrieve a tempo that does not exist
### This should return with an error

When request a "tempo" with id "yes"
Then the request was not successful
And the response has a status code of 404
And the response matches
	| code | integer | 404       |
	| text | string  | NOT_FOUND |

@TC012005
@Negative @Create
Scenario: create a new tempo
### create a new tempo is not allowed

Given a "tempo" with
	|       id | string  | yes |
	|     desc | string  | yes |
	| minTempo | integer |  0  |
	| stdTempo | integer | 256 |
	| maxTempo | integer | 512 |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC012006
@Negative @Update
Scenario: update a tempo
### updating tempos is not allowed

Given update "tempo" with id "Grave"
	|       id | string  | Grave |
	|     desc | string  |  yes  |
	| minTempo | integer |   0   |
	| stdTempo | integer |  100  |
	| maxTempo | integer |  512  |
And the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "tempo" with id "Grave"
Then the request was successful
And the response has a status code of 200
And the response matches
	|       id | string  |   Grave   |
	|     desc | string  | very slow |
	| minTempo | integer |     25    |
	| stdTempo | integer |     40    |
	| maxTempo | integer |     45    |

@TC012007
@Negative @Delete
Scenario: delete a tempo
### deleting tempos is not allowed

When delete "tempo" with id "Grave"
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |
When request a "tempo" with id "Grave"
Then the request was successful
And the response has a status code of 200
And the response matches
	|       id | string  |   Grave   |
	|     desc | string  | very slow |
	| minTempo | integer |     25    |
	| stdTempo | integer |     40    |
	| maxTempo | integer |     45    |
