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
Then check user status
And login is successful
And login results match
	| status   | string | ACTIVE         |
	| username | string | activeUserName |

@TC022002
@Negative
Scenario: not successful login for active user
### create an active new user and login with wrong credentials
### login should be succesful

Given a "user" with
	| status   | string | ACTIVE        |
	| username | string | wrongPassName |
	| password | string | wrongPassPass |
And the request was successful
And the response has a status code of 201
And the response matches
	| status   | string | ACTIVE        |
	| username | string | wrongPassName |
When "wrongPassName" attempts to login with password "wrongPassPass"
And login is successful
And login results match
	| status   | string | ACTIVE        |
	| username | string | wrongPassName |
Then "wrongPassName" attempts to login with password "thisIsNotTheRightPassword"
And login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
And check user status
And login has failed
And login results match
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC022003
@Negative
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
And login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
Then check user status
And login has failed
And login results match
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC022004
@Negative
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
And login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
Then check user status
And login has failed
And login results match
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC022005
@Negative
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
And login has failed
And login results match
	| code | integer | 401          |
	| text | string  | UNAUTHORIZED |
Then check user status
And login has failed
And login results match
	| code | integer | 400         |
	| text | string  | BAD_REQUEST |

@TC022006
@Positive @Create
Scenario: create and login a user for testing
### create a new user and login
### login should be succesful

Given a logged in user "test" with password "test"
When check user status
Then login is successful
And login results match
	| status   | string | ACTIVE |
	| username | string | test   |

@TC022007
@Positive
Scenario: activate user to use for testing
### create a suspended new user and login
### login should be succesful

Given a "user" with
	| status   | string | SUSPENDED      |
	| username | string | toActivateName |
	| password | string | toActivatePass |
And a logged in user "toActivateName" with password "toActivatePass"
When check user status
Then login is successful
And login results match
	| status   | string | ACTIVE         |
	| username | string | toActivateName |

@TC022008
@Positive
Scenario: change users password to use for testing
### create a new user and reset password to login
### login should be succesful

Given a "user" with
	| status   | string | ACTIVE               |
	| username | string | toChangePasswordName |
	| password | string | toChangePasswordPass |
And a logged in user "toChangePasswordName" with password "test"
When check user status
Then login is successful
And login results match
	| status   | string | ACTIVE               |
	| username | string | toChangePasswordName |
