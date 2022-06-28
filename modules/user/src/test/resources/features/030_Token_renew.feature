@TokenRenew
Feature: Token renew functionality
### Return a refreshed token if requested

@TC030001
@Positive
Scenario: perform request using a valid user, do not request token refresh

Given a "user" with
	| status   | string | ACTIVE               |
	| username | string | noRenewTokenUserName |
	| password | string | noRenewTokenUserPass |
And the request was successful
And "noRenewTokenUserName" attempts to login with password "noRenewTokenUserPass"
And the "login" request was successful
And the "login" response returned header "X-User-Token"
And the "login" response header "Access-Control-Expose-Headers" contains "X-User-Token"
When request all available "user"
Then the request was successful
And the "user" response did not return header "X-User-Token"
And the "user" response did not return header "Access-Control-Expose-Headers"

@TC030002
@Positive
Scenario: perform request using a valid user, request token refresh

Given a "user" with
	| status   | string | ACTIVE             |
	| username | string | renewTokenUserName |
	| password | string | renewTokenUserPass |
And the request was successful
And "renewTokenUserName" attempts to login with password "renewTokenUserPass"
And the "login" request was successful
And the "login" response returned header "X-User-Token"
And the "login" response header "Access-Control-Expose-Headers" contains "X-User-Token"
And set header "X-User-Token-Renew" to "1" as "int"
When request all available "user"
Then the request was successful
And the "user" response returned header "X-User-Token"
And the "user" response header "Access-Control-Expose-Headers" contains "X-User-Token"

@TC030003
@Positive
Scenario: failed requests will renew the token

Given a "user" with
	| status   | string | ACTIVE                   |
	| username | string | failedRenewTokenUserName |
	| password | string | failedRenewTokenUserPass |
And the request was successful
And "failedRenewTokenUserName" attempts to login with password "failedRenewTokenUserPass"
And the "login" request was successful
And the "login" response returned header "X-User-Token"
And the "login" response header "Access-Control-Expose-Headers" contains "X-User-Token"
And set header "X-User-Token-Renew" to "1" as "int"
When request a "user" with id "1000"
Then the request was not successful
And the "user" response returned header "X-User-Token"
And the "user" response header "Access-Control-Expose-Headers" contains "X-User-Token"
