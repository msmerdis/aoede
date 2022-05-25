@Section @Validation @Negative
Feature: Basic Section field vaildation
### Verify input fields for section actions

Background: Setup validation info table

Given prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |
And prepare composite id "validTrackId"
	|  sheetId  | integer | 1000 |
	|  trackId  | integer | 1000 |

@TC043001
@Create
Scenario: create section with negative fraction numerator and denominator

When a "section" with
	| trackId       | compositeId | validTrackId |
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |     -3/-4    |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field                     | value | error                                |
	| createSection | timeSignature.numerator   | -3    | numerator must be a positive value   |
	| createSection | timeSignature.denominator | -4    | denominator must be a positive value |

@TC043002
@Create
Scenario: create section with zero fraction numerator and denominator

When a "section" with
	| trackId       | compositeId | validTrackId |
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      0/0     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field                     | value | error                                |
	| createSection | timeSignature.numerator   | 0     | numerator must be a positive value   |
	| createSection | timeSignature.denominator | 0     | denominator must be a positive value |

@TC043003
@Create
Scenario: create section with empty time signature

When a "section" with
	| trackId       | compositeId | validTrackId |
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | null        |              |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name          | field         | value | error                                |
	| createSection | timeSignature |       | Section must define a time signature |

@TC043004
@Create
Scenario: create section with too small tempo

When a "section" with
	| trackId       | compositeId | validTrackId |
	| tempo         | integer     |       31     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      3/4     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field | value | error                       |
	| createSection | tempo |   31  | minimum allowed tempo is 32 |

@TC043005
@Create
Scenario: create section with too great of tempo

When a "section" with
	| trackId       | compositeId | validTrackId |
	| tempo         | integer     |      513     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      3/4     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field | value | error                        |
	| createSection | tempo |  513  | maximum allowed tempo is 512 |

@TC043006
@Create
Scenario: create section with no track id

When a "section" with
	| tempo         | integer     |       120      |
	| keySignature  | integer     |        0       |
	| timeSignature | fraction    |       3/4      |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name          | field   | value | error                          |
	| createSection | trackId |       | Section must define a track id |

@TC043007
@Update
Scenario: update section with negative fraction numerator and denominator

Given prepare composite id "sectionId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "sectionId"
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |     -3/-4    |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field                     | value | error                                |
	| updateSection | timeSignature.numerator   | -3    | numerator must be a positive value   |
	| updateSection | timeSignature.denominator | -4    | denominator must be a positive value |

@TC043008
@Update
Scenario: update section with zero fraction numerator and denominator

Given prepare composite id "sectionId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "sectionId"
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      0/0     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field                     | value | error                                |
	| updateSection | timeSignature.numerator   | 0     | numerator must be a positive value   |
	| updateSection | timeSignature.denominator | 0     | denominator must be a positive value |

@TC043009
@Update
Scenario: update section with empty time signature

Given prepare composite id "sectionId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "sectionId"
	| tempo         | integer     |      120     |
	| keySignature  | integer     |       0      |
	| timeSignature | null        |              |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name          | field         | value | error                                |
	| updateSection | timeSignature |       | Section must define a time signature |

@TC0430010
@Update
Scenario: update section with too small tempo

Given prepare composite id "sectionId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "sectionId"
	| tempo         | integer     |       31     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      3/4     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field | value | error                       |
	| updateSection | tempo |   31  | minimum allowed tempo is 32 |

@TC0430011
@Update
Scenario: update section with too great of tempo

Given prepare composite id "sectionId"
	|   sheetId | integer | 1000 |
	|   trackId | integer | 1000 |
	| sectionId | integer | 1000 |
When update "section" with composite id "sectionId"
	| tempo         | integer     |      513     |
	| keySignature  | integer     |       0      |
	| timeSignature | fraction    |      3/4     |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field | value | error                        |
	| updateSection | tempo |  513  | maximum allowed tempo is 512 |

@TC0430012
@Update
Scenario: update section with invalid id

Given prepare composite id "invalidSectionId"
	| dummy | integer | 1000 |
When update "section" with composite id "invalidSectionId"
	| tempo         | integer     |       120      |
	| keySignature  | integer     |        0       |
	| timeSignature | fraction    |       3/4      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name          | field | value                | error         |
	| AccessSection | id    | eyJkdW1teSI6MTAwMH0g | Invalid value |

