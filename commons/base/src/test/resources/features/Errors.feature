@Error
Feature: Verify handling of error cases
### Verify invalid requests are processed as expected

@Negative
Scenario: test bad request exceptions
### force application to throw a bad request exception and verify the generated response

When testing "badRequest" error
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |

@Negative
Scenario: test confict exceptions
### force application to throw a conflict exception and verify the generated response

When testing "confict" error
Then the response has a status code of 409 and matches
	| code | 409      |
	| text | CONFLICT |

@Negative
Scenario: test forbidden exceptions
### force application to throw a forbidden exception and verify the generated response

When testing "forbidden" error
Then the response has a status code of 403 and matches
	| code | 403       |
	| text | FORBIDDEN |

@Negative
Scenario: test method not allowed exceptions
### force application to throw a method not allowed exception and verify the generated response

When testing "methodNotAllowed" error
Then the response has a status code of 405 and matches
	| code | 405                |
	| text | METHOD_NOT_ALLOWED |

@Negative
Scenario: test not found exceptions
### force application to throw a not found exception and verify the generated response

When testing "notFound" error
Then the response has a status code of 404 and matches
	| code | 404       |
	| text | NOT_FOUND |

@Negative
Scenario: test timeout exceptions
### force application to throw a timeout exception and verify the generated response

When testing "timeout" error
Then the response has a status code of 408 and matches
	| code | 408             |
	| text | REQUEST_TIMEOUT |

@Negative
Scenario: test unauthorized exceptions
### force application to throw an unauthorized exception and verify the generated response

When testing "unauthorized" error
Then the response has a status code of 401 and matches
	| code | 401          |
	| text | UNAUTHORIZED |
