@Validation @Sheet @Negative @Generate
Feature: Basic Sheet CRUD functionality
### Verify the ability to create/read/update and delete Sheets

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |

@TC042001
Scenario Outline: generate a Sheet with invalid name
### create a new sheet with an invalid name
### operation should fail and a validation error should be returned

And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | <type>   |        |
	| clef          | string   | Treble |
	| tempo         | integer  | 120    |
	| keySignature  | integer  | 1      |
	| timeSignature | fraction | 5/4    |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          | field  | value  | error                  |
	| generateSheet | name   |        | Sheet must have a name |

Examples:
	| type   | validation         |
	| string | validationInfo     |
	| null   | nullValidationInfo |

@TC042002
Scenario Outline: generate a Sheet with invalid clef
### create a new sheet with an invalid clef
### operation should fail and a validation error should be returned

And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | <type>   | <value> |
	| tempo         | integer  | 125     |
	| keySignature  | integer  | 2       |
	| timeSignature | fraction | 7/4     |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          | field |  value  |  error  |
	| generateSheet | clef  | <value> | <error> |

Examples:
	| type   | value | validation         | error                         |
	| string | abcde | validationInfo     | Sheet defined an invalid clef |
	| string |       | validationInfo     | Sheet defined an invalid clef |
	| null   |       | nullValidationInfo | Sheet must have a clef        |

@TC042003
Scenario Outline: generate a Sheet with invalid tempo
### create a new sheet with an invalid tempo
### operation should fail and a validation error should be returned

And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | string   | Bass    |
	| tempo         | <type>   | <value> |
	| keySignature  | integer  | 2       |
	| timeSignature | fraction | 7/4     |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          | field |  value  |  error  |
	| generateSheet | tempo | <respv> | <error> |

Examples:
	| type    | value | respv | validation     | error                        |
	| integer | 31    | 31    | validationInfo | minimum allowed tempo is 32  |
	| integer | 513   | 513   | validationInfo | maximum allowed tempo is 512 |
	| null    |       | 0     | validationInfo | minimum allowed tempo is 32  |

@TC042004
Scenario Outline: generate a Sheet with invalid key
### create a new sheet with an invalid key
### operation should fail and a validation error should be returned

And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | string   | Alto    |
	| tempo         | integer  | 112     |
	| keySignature  | <type>   | <value> |
	| timeSignature | fraction | 2/2     |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          | field        |  value  |  error  |
	| generateSheet | keySignature | <value> | <error> |

Examples:
	| type    | value | validation         | error                                  |
	| integer | -8    | validationInfo     | Sheet defined an invalid key signature |
	| integer |  8    | validationInfo     | Sheet defined an invalid key signature |
	| null    |       | nullValidationInfo | Sheet must have a keySignature         |

@TC042005
Scenario Outline: generate a Sheet with invalid time
### create a new sheet with an invalid time
### operation should fail and a validation error should be returned

And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | string   | Soprano |
	| tempo         | integer  | 116     |
	| keySignature  | integer  | 2       |
	| timeSignature | <type>   | <value> |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          |  field  |  value  |  error  |
	| generateSheet | <field> | <respv> | <error> |

Examples:
	| type     | value | field                     | respv | validation         | error                                |
	| fraction | -8/7  | timeSignature.numerator   |  -8   | validationInfo     | numerator must be a positive value   |
	| fraction |  0/7  | timeSignature.numerator   |   0   | validationInfo     | numerator must be a positive value   |
	| fraction | 3/-4  | timeSignature.denominator |  -4   | validationInfo     | denominator must be a positive value |
	| fraction | 3/0   | timeSignature.denominator |   0   | validationInfo     | denominator must be a positive value |
	| null     |       | timeSignature             |       | nullValidationInfo | Sheet must have a time signature     |

@TC042006
Scenario Outline: generate a Sheet with invalid beats
### create a new sheet with an invalid beats
### operation should fail and a validation error should be returned

And prepare json array "beats"
	| <beat1type> | <beat1value> |
	| <beat2type> | <beat2value> |
And prepare json "time"
	| numerator   | integer | 7     |
	| denominator | integer | 4     |
	| beats       | json    | beats |
And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | string   | Treble  |
	| tempo         | integer  | 48      |
	| keySignature  | integer  | -2      |
	| timeSignature | json     | time    |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "<validation>" objects in "validations"
	| name          |  field  |  value  |  error                               |
	| generateSheet | <field> | <value> | Time signature defined invalid beats |

Examples:
	| beat1type | beat1value | beat2type | beat2value | validation     | field         | value                                                                    |
	| integer   |     0      | integer   |     0      | validationInfo | timeSignature | TimeSignature(super=Fraction(numerator=7, denominator=4), beats=[0, 0])  |
	| integer   |     2      | integer   |     8      | validationInfo | timeSignature | TimeSignature(super=Fraction(numerator=7, denominator=4), beats=[2, 8])  |
	| integer   |    -1      | integer   |     0      | validationInfo | timeSignature | TimeSignature(super=Fraction(numerator=7, denominator=4), beats=[-1, 0]) |
	| integer   |     3      | integer   |     0      | validationInfo | timeSignature | TimeSignature(super=Fraction(numerator=7, denominator=4), beats=[3, 0])  |

@TC042007
Scenario: generate a Sheet with empty beats
### create a new sheet with an empty beats
### operation should fail and a validation error should be returned

And prepare empty json array "beats"
And prepare json "time"
	| numerator   | integer | 7     |
	| denominator | integer | 4     |
	| beats       | json    | beats |
And prepare url "/api/sheet/generate"
And a "sheet" with
	| name          | string   | name    |
	| clef          | string   | Treble  |
	| tempo         | integer  | 40      |
	| keySignature  | integer  | -3      |
	| timeSignature | json     | time    |
Then the request was not successful
And the response has a status code of 400
And the response matches
	| code | integer | 400               |
	| text | string  | BAD_REQUEST       |
	| desc | string  | Validation errors |
And the response contains "validationInfo" objects in "validations"
	| name          | field         | value                                                               |  error                               |
	| generateSheet | timeSignature | TimeSignature(super=Fraction(numerator=7, denominator=4), beats=[]) | Time signature defined invalid beats |
