@Validation @Track @Negative @Create
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |
And prepare "C" scale as "C scale"
And store "tracks" element from "C scale" object as "track array"
And store element 0 from "track array" array as "track"

@TC050001
Scenario: create a Track with a null clef
### create a new sheet with a track having a null clef
### operation should fail and a validation error should be returned

And set "track" object's "clef" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field          | value  | error                    |
	| sheet | tracks[0].clef |        | Track must define a clef |

@TC050002
Scenario: create a Track with an invalid clef
### create a new sheet with a track having an invalid clef
### operation should fail and a validation error should be returned

And set "track" object's "clef" element to "invalid" as "string"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field          | value   | error                         |
	| sheet | tracks[0].clef | invalid | Track defined an invalid clef |

@TC050003
Scenario: create a Track with a very slow tempo
### create a new sheet with a track having a tempo lower than the minimum
### operation should fail and a validation error should be returned

And set "track" object's "tempo" element to "31" as "int"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field           | value | error                       |
	| sheet | tracks[0].tempo | 31    | minimum allowed tempo is 32 |

@TC050004
Scenario: create a Track with a very fast tempo
### create a new sheet with a track having a tempo larger than the maximum
### operation should fail and a validation error should be returned

And set "track" object's "tempo" element to "513" as "int"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field           | value | error                        |
	| sheet | tracks[0].tempo | 513   | maximum allowed tempo is 512 |

@TC050005
Scenario: create a Track with no key signature
### create a new sheet with a track having no key signature
### operation should fail and a validation error should be returned

And set "track" object's "keySignature" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field                  | value | error                            |
	| sheet | tracks[0].keySignature |       | Track must define a keySignature |

@TC050006
Scenario: create a Track with invalid key signature
### create a new sheet with a track having an invalid key signature
### operation should fail and a validation error should be returned

And set "track" object's "keySignature" element to "100" as "int"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field                  | value | error                                  |
	| sheet | tracks[0].keySignature | 100   | Track defined an invalid key signature |

@TC050007
Scenario: create a Track with no time signature
### create a new sheet with a track having no time signature
### operation should fail and a validation error should be returned

And set "track" object's "timeSignature" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field                   | value | error                              |
	| sheet | tracks[0].timeSignature |       | Track must define a time signature |

@TC050008
Scenario: create a Track with invalid time signature
### create a new sheet with a track having an invalid time signature
### operation should fail and a validation error should be returned

And set "track" object's "timeSignature" element to "-1/4" as "fraction"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name  | field                             | value | error                              |
	| sheet | tracks[0].timeSignature.numerator | -1    | numerator must be a positive value |

@TC040009
Scenario: create a Track with null measures field
### create a new sheet defining the measures field as null
### operation should fail and a validation error should be returned

And set "track" object's "measures" element to "" as "null"
When a "sheet" with "C scale" json element
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "nullValidationInfo" objects in "validations"
	| name  | field              | value | error                      |
	| sheet | tracks[0].measures |       | Track must define measures |
