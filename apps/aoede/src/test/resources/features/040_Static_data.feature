@StaticData @Regression
Feature: Verify required static data are present
### Verify the setup of static data

@TC040001
@Positive
Scenario: Verify C clef are present
### verify C clef image are present

When request "/static/clef/C.svg" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "svg"

@TC040002
@Positive
Scenario: Verify G clef are present
### verify G clef image are present

When request "/static/clef/G.svg" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "svg"

@TC040003
@Positive
Scenario: Verify F clef are present
### verify F clef image are present

When request "/static/clef/F.svg" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "svg"

@TC040004
@Positive
Scenario: Verify terms of service are present
### verify terms of service are present

When request "/static/tos.html" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "<!DOCTYPE html>"
And the aoede response contains "Terms of Service"

