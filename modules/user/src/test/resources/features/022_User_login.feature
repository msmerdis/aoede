@User @Login
Feature: Basic User update functionality
### Verify different cases for updating users

@TC022001
@Positive
Scenario: successful login for active user
### create an active new user and login
### login should be succesful

Given a "user" with
	| status   | string | ACTIVE         |
	| username | string | activeUserName |
	| password | string | activeUserPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE         |
	| username | string | activeUserName |
When "activeUserName" attempts to login with password "activeUserPass"
And login is successful
And login results match
	| status   | string | ACTIVE         |
	| username | string | activeUserName |
Then "activeUserName" attempts to login with password "notActiveUserPass"
And login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC022002
@Positive
Scenario: successful login for pending user
### create a pending new user and login
### login should not be succesful

Given a "user" with
	| status   | string | PENDING         |
	| username | string | pendingUserName |
	| password | string | pendingUserPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | PENDING         |
	| username | string | pendingUserName |
When "pendingUserName" attempts to login with password "pendingUserPass"
Then login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC022003
@Positive
Scenario: successful login for locked user
### create a locked new user and login
### login should not be succesful

Given a "user" with
	| status   | string | LOCKED         |
	| username | string | lockedUserName |
	| password | string | lockedUserPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | LOCKED         |
	| username | string | lockedUserName |
When "lockedUserName" attempts to login with password "lockedUserPass"
Then login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |

@TC022004
@Positive
Scenario: successful login for suspended user
### create a suspended new user and login
### login should not be succesful

Given a "user" with
	| status   | string | SUSPENDED         |
	| username | string | suspendedUserName |
	| password | string | suspendedUserPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | SUSPENDED         |
	| username | string | suspendedUserName |
When "suspendedUserName" attempts to login with password "suspendedUserPass"
Then login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
