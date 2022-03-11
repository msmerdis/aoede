@Angular @Regression
Feature: Verify required static data are present
### Verify the setup of static data

@TC050001
@Positive
Scenario: Verify application starting point
### verify index.html is present

When request "/app/index.html" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "<!DOCTYPE html>"
And the aoede response contains "<html lang=\"en\">"
And the aoede response contains "<meta charset=\"utf-8\">"
And the aoede response contains "<title>Aoede</title>"
And the aoede response contains "<base href=\"/app/\">"
And the aoede response contains "<link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">"

@TC050002
@Positive
Scenario: Verify favicon.ico
### verify favicon.ico is present

When request "/app/favicon.ico" from aoede
Then the aoede response has a status code of 200
And the aoede response contains "PNG"
