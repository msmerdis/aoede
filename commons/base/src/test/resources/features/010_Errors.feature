@Error
Feature: Verify handling of error cases
### Verify invalid requests are processed as expected

@TC010001
@Negative
Scenario: test bad request exceptions
### force application to throw a bad request exception and verify the generated response

When testing "badRequest" error
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |

@TC010002
@Negative
Scenario: test confict exceptions
### force application to throw a conflict exception and verify the generated response

When testing "confict" error
Then the response has a status code of 409 and matches
	| code | 409      |
	| text | CONFLICT |

@TC010003
@Negative
Scenario: test forbidden exceptions
### force application to throw a forbidden exception and verify the generated response

When testing "forbidden" error
Then the response has a status code of 403 and matches
	| code | 403       |
	| text | FORBIDDEN |

@TC010004
@Negative
Scenario: test method not allowed exceptions
### force application to throw a method not allowed exception and verify the generated response

When testing "methodNotAllowed" error
Then the response has a status code of 405 and matches
	| code | 405                |
	| text | METHOD_NOT_ALLOWED |

@TC010005
@Negative
Scenario: test not found exceptions
### force application to throw a not found exception and verify the generated response

When testing "notFound" error
Then the response has a status code of 404 and matches
	| code | 404       |
	| text | NOT_FOUND |

@TC010006
@Negative
Scenario: test timeout exceptions
### force application to throw a timeout exception and verify the generated response

When testing "timeout" error
Then the response has a status code of 408 and matches
	| code | 408             |
	| text | REQUEST_TIMEOUT |

@TC010007
@Negative
Scenario: test unauthorized exceptions
### force application to throw an unauthorized exception and verify the generated response

When testing "unauthorized" error
Then the response has a status code of 401 and matches
	| code | 401          |
	| text | UNAUTHORIZED |

@TC010008
@Negative
Scenario: test http media type not acceptable exceptions
### force application to throw an http media type not acceptable exception and verify the generated response

When testing "httpMediaTypeNotAcceptable" error
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |

@TC010009
@Negative
Scenario: test method argument type mismatch exceptions
### force application to throw a method argument type mismatch exception and verify the generated response

When testing "methodArgumentTypeMismatch" error
Then the response has a status code of 400 and matches
	| code | 400         |
	| text | BAD_REQUEST |

@TC010010
@Negative
Scenario: test method argument type mismatch exceptions
### force application to throw a generic exception container and verify the generated response

When testing "genericExceptionContainer" error
Then the response has a status code of 208 and matches
	| code | 208              |
	| text | ALREADY_REPORTED |

@TC010011
@Negative
Scenario: test authentication exceptions
### force application to throw a method authentication exception and verify the generated response

When testing "authentication" error
Then the response has a status code of 401 and matches
	| code | 401          |
	| text | UNAUTHORIZED |

@TC010012
@Negative
Scenario: test validation exceptions
### force application to throw a validation exception and verify the generated response

When testing "validation" error
Then the response has a status code of 400 and matches
	| code | 400               |
	| text | BAD_REQUEST       |
	| desc | Validation errors |
And prepare data table "validationInfo"
	| field  | value  | error  |
	| string | null   | string |
And the response contains "validationInfo" objects in "validations"
	| field | value  | error            |
	| valid | null   | test validations |
