@Track @Validation @Negative
Feature: Basic Track field vaildation
### Verify input feelds for track actions

Background: Setup validation info table

Given a logged in user "moduleMusicTest"
And a "sheet" with
	| name | random string | sheet_{string:12} |
And prepare data table "validationInfo"
	| name   | field  | value  | error  |
	| string | string | string | string |
And prepare data table "nullValidationInfo"
	| name   | field  | value  | error  |
	| string | string | null   | string |

@TC033001
@Create
Scenario: create track with empty clef

When a "track" with
	| sheetId | key   | sheet  |
	| clef    | null  |        |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name        | field  | value  | error                    |
	| createTrack | clef   |        | Track must define a clef |

@TC033002
@Create
Scenario: create track with negative sheet id

When a "track" with
	| sheetId | int    | -1     |
	| clef    | string | Treble |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name        | field   | value  | error                                 |
	| createTrack | sheetId | -1     | Track must define a positive sheet id |

@TC033003
@Create
Scenario: create track without sheet id

When a "track" with
	| clef    | string | Treble |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name        | field   | value  | error                        |
	| createTrack | sheetId |        | Track must define a sheet id |

@TC033004
@Create
Scenario: create track without a clef

When a "track" with
	| sheetId | key   | sheet  |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name        | field  | value  | error                    |
	| createTrack | clef   |        | Track must define a clef |

@TC033005
@Update
Scenario: update track with empty clef

Given prepare composite id "trackId"
	| sheetId | integer | 1000 |
	| trackId | integer | 1000 |
When update "track" with composite id "trackId"
	| clef    | null    |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "nullValidationInfo" objects in "validations"
	| name        | field  | value  | error                    |
	| updateTrack | clef   |        | Track must define a clef |

@TC033006
@Update
Scenario: update track with invalid track id

Given prepare composite id "trackId"
	| dummy | integer | 1000 |
When update "track" with composite id "trackId"
	| clef    | null    |      |
Then the request was not successful
And the response has a status code of 400
And the response contains "validationInfo" objects in "validations"
	| name        | field  | value                | error         |
	| AccessTrack | id     | eyJkdW1teSI6MTAwMH0g | Invalid value |
