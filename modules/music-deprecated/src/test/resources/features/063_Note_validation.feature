@Note @Validation @Negative
Feature: Basic Note field vaildation
### Verify input fields for note actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |
And prepare composite id "validMeasureId"
	| sheetId   | integer | 1000 |
	| trackId   | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |

@TC063001
@Create
Scenario: create note with negative fraction numerator and denominator

When a "note" with
	| measureId | compositeId | validMeasureId |
	| note      | integer     |       64       |
	| value     | fraction    |     -3/-4      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field             | value | error                                |
	| createNote | value.numerator   | -3    | numerator must be a positive value   |
	| createNote | value.denominator | -4    | denominator must be a positive value |

@TC063002
@Create
Scenario: create note with zero fraction numerator and denominator

When a "note" with
	| measureId | compositeId | validMeasureId |
	| note      | integer     |       64       |
	| value     | fraction    |      0/0       |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field             | value | error                                |
	| createNote | value.numerator   | 0     | numerator must be a positive value   |
	| createNote | value.denominator | 0     | denominator must be a positive value |

@TC063003
@Create
Scenario: create note below the allowed limit

When a "note" with
	| measureId | compositeId | validMeasureId |
	| note      | integer     |       -2       |
	| value     | fraction    |      3/4       |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field | value | error                            |
	| createNote | note  |  -2   | minimum allowed note value is -1 |

@TC063004
@Create
Scenario: create note above the allowed limit

When a "note" with
	| measureId | compositeId | validMeasureId |
	| note      | integer     |      128       |
	| value     | fraction    |      3/4       |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field | value | error                             |
	| createNote | note  |  128  | maximum allowed note value is 127 |

@TC063005
@Create
Scenario: create note without a measure id

When a "note" with
	| note      | integer     | 111 |
	| value     | fraction    | 3/4 |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name       | field     | value | error                         |
	| createNote | measureId |       | Note must define a measure id |

@TC063006
@Update
Scenario: update note with negative fraction numerator and denominator

Given prepare composite id "noteId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|    noteId | integer | 1000 |
When update "note" with composite id "noteId"
	| note      | integer     |   64  |
	| value     | fraction    | -3/-4 |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field             | value | error                                |
	| updateNote | value.numerator   | -3    | numerator must be a positive value   |
	| updateNote | value.denominator | -4    | denominator must be a positive value |

@TC063007
@Update
Scenario: update note with zero fraction numerator and denominator

Given prepare composite id "noteId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|    noteId | integer | 1000 |
When update "note" with composite id "noteId"
	| note      | integer     |  64 |
	| value     | fraction    | 0/0 |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field             | value | error                                |
	| updateNote | value.numerator   | 0     | numerator must be a positive value   |
	| updateNote | value.denominator | 0     | denominator must be a positive value |

@TC063008
@Update
Scenario: update note below the allowed limit

Given prepare composite id "noteId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|    noteId | integer | 1000 |
When update "note" with composite id "noteId"
	| note      | integer     |  -2 |
	| value     | fraction    | 3/4 |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field | value | error                            |
	| updateNote | note  |  -2   | minimum allowed note value is -1 |

@TC063009
@Update
Scenario: update note above the allowed limit

Given prepare composite id "noteId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
	| measureId | integer | 1000 |
	|    noteId | integer | 1000 |
When update "note" with composite id "noteId"
	| note      | integer     | 128 |
	| value     | fraction    | 3/4 |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field | value | error                             |
	| updateNote | note  |  128  | maximum allowed note value is 127 |

@TC063010
@Update
Scenario: update note with invalid id

Given prepare composite id "invalidNoteId"
	| dummy | integer | 1000 |
When update "note" with composite id "invalidNoteId"
	| note      | integer     | 111 |
	| value     | fraction    | 3/4 |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name       | field | value                | error         |
	| AccessNote | id    | eyJkdW1teSI6MTAwMH0g | Invalid value |

