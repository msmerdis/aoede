@DataManipulation @Positive
Feature: Verify steps to manage custom data
### Verify steps to create and validate data

@TC000001
Scenario: prepare composite key
# prepare a composite key and verify its value

Given prepare json "expected" with "eyJrZXkxIjoxLCJrZXkyIjoyfSAg" as "string"
When prepare composite id "key"
	| key1 | int | 1 |
	| key2 | int | 2 |
Then "key" json matches "expected" json
And "expected" json matches "key" json

@TC000002
Scenario: add element to json object

Given prepare json "json"
	| key1 | int | 1 |
	| key2 | int | 2 |
And prepare json "expected"
	| key1 | int | 1 |
	| key2 | int | 2 |
	| key3 | int | 3 |
When set "json" object's "key3" element to "3" as "int"
Then json object "json" contains "key3"
And "json" json object's "key3" element matches "3" as "int"
And "json" json matches "expected" json
And "expected" json matches "json" json

@TC000003
Scenario: replace element to json object

Given prepare json "json"
	| key1 | int | 1 |
	| key2 | int | 2 |
And prepare json "expected"
	| key1 | int | 1 |
	| key2 | int | 3 |
When set "json" object's "key2" element to "3" as "int"
And "json" json matches "expected" json
And "expected" json matches "json" json

@TC000004
Scenario: remove element from json object

Given prepare json "json"
	| key1 | int | 1 |
	| key2 | int | 2 |
And prepare empty json object "expected"
When remove "key1" element from "json"
And remove "key2" element from "json"
And "json" json matches "expected" json
And "expected" json matches "json" json
