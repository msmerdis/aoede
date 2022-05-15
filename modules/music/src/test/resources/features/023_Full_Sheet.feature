@Sheet
Feature: Basic Sheet CRUD functionality
### Verify the ability to read Sheets

Background: Log user in

Given a logged in user "moduleMusicFullSheetTest"
And a "sheet" with
	| name | string | fullSheet |
And the request was successful
And a "track" with
	| sheetId | key    | sheet  |
	| clef    | string | Treble |
And the request was successful
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    64   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    66   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    68   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    69   |
	| value     | fraction |   1/4   |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    71   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    73   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    75   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    76   |
	| value     | fraction |   1/4   |
And the request was successful
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    75   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    73   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    71   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    69   |
	| value     | fraction |   1/4   |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    68   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    66   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    64   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    -1   |
	| value     | fraction |   1/4   |
And the request was successful
And a "track" with
	| sheetId | key    | sheet  |
	| clef    | string | Treble |
And the request was successful
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    52   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    54   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    56   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    57   |
	| value     | fraction |   1/4   |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    59   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    61   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    63   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    64   |
	| value     | fraction |   1/4   |
And the request was successful
And a "section" with
	| trackId       | key      | track |
	| tempo         | integer  |  120  |
	| keySignature  | integer  |   0   |
	| timeSignature | fraction |  4/4  |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    63   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    61   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    59   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    57   |
	| value     | fraction |   1/4   |
And the request was successful
And a "measure" with
	| sectionId | key | section |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    56   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    54   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    52   |
	| value     | fraction |   1/4   |
And the request was successful
And a "note" with
	| measureId | key      | measure |
	| note      | integer  |    -1   |
	| value     | fraction |   1/4   |
And the request was successful

@TC023001
@Positive
Scenario: retrieve sheet with dependencies
### Retrieve the sheet with all child nodes
### Ensure that all data are returned correctly

When prepare url
	| string | api   |
	| string | sheet |
	| key    | sheet |
	| string | full  |
And request a "sheet" with id "1000"
Then the request was successful
And the response has a status code of 200
And prepare composite id "track1section1measure1NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   1   |
And prepare composite id "track1section1measure1NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   2   |
And prepare composite id "track1section1measure1NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   3   |
And prepare composite id "track1section1measure1NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   4   |
And prepare composite id "track1section1measure2NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   1   |
And prepare composite id "track1section1measure2NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   2   |
And prepare composite id "track1section1measure2NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   3   |
And prepare composite id "track1section1measure2NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   4   |
And prepare composite id "track1section2measure1NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   1   |
And prepare composite id "track1section2measure1NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   2   |
And prepare composite id "track1section2measure1NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   3   |
And prepare composite id "track1section2measure1NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   4   |
And prepare composite id "track1section2measure2NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   1   |
And prepare composite id "track1section2measure2NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   2   |
And prepare composite id "track1section2measure2NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   3   |
And prepare composite id "track1section2measure2NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   4   |
And prepare composite id "track2section1measure1NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   1   |
And prepare composite id "track2section1measure1NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   2   |
And prepare composite id "track2section1measure1NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   3   |
And prepare composite id "track2section1measure1NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
	|   noteId  | int |   4   |
And prepare composite id "track2section1measure2NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   1   |
And prepare composite id "track2section1measure2NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   2   |
And prepare composite id "track2section1measure2NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   3   |
And prepare composite id "track2section1measure2NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
	|   noteId  | int |   4   |
And prepare composite id "track2section2measure1NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   1   |
And prepare composite id "track2section2measure1NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   2   |
And prepare composite id "track2section2measure1NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   3   |
And prepare composite id "track2section2measure1NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
	|   noteId  | int |   4   |
And prepare composite id "track2section2measure2NoteKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   1   |
And prepare composite id "track2section2measure2NoteKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   2   |
And prepare composite id "track2section2measure2NoteKey3"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   3   |
And prepare composite id "track2section2measure2NoteKey4"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
	|   noteId  | int |   4   |
And prepare data table "noteObject"
	|          id | note | value    |
	| compositeId | int  | fraction |
And prepare json array "track1section1measure1NoteList" of "noteObject"
	|                             id | note | value |
	| track1section1measure1NoteKey1 |  64  |  1/4  |
	| track1section1measure1NoteKey2 |  66  |  1/4  |
	| track1section1measure1NoteKey3 |  68  |  1/4  |
	| track1section1measure1NoteKey4 |  69  |  1/4  |
And prepare json array "track1section1measure2NoteList" of "noteObject"
	|                             id | note | value |
	| track1section1measure2NoteKey1 |  71  |  1/4  |
	| track1section1measure2NoteKey2 |  73  |  1/4  |
	| track1section1measure2NoteKey3 |  75  |  1/4  |
	| track1section1measure2NoteKey4 |  76  |  1/4  |
And prepare json array "track1section2measure1NoteList" of "noteObject"
	|                             id | note | value |
	| track1section2measure1NoteKey1 |  75  |  1/4  |
	| track1section2measure1NoteKey2 |  73  |  1/4  |
	| track1section2measure1NoteKey3 |  71  |  1/4  |
	| track1section2measure1NoteKey4 |  69  |  1/4  |
And prepare json array "track1section2measure2NoteList" of "noteObject"
	|                             id | note | value |
	| track1section2measure2NoteKey1 |  68  |  1/4  |
	| track1section2measure2NoteKey2 |  66  |  1/4  |
	| track1section2measure2NoteKey3 |  64  |  1/4  |
	| track1section2measure2NoteKey4 |  -1  |  1/4  |
And prepare json array "track2section1measure1NoteList" of "noteObject"
	|                             id | note | value |
	| track2section1measure1NoteKey1 |  52  |  1/4  |
	| track2section1measure1NoteKey2 |  54  |  1/4  |
	| track2section1measure1NoteKey3 |  56  |  1/4  |
	| track2section1measure1NoteKey4 |  57  |  1/4  |
And prepare json array "track2section1measure2NoteList" of "noteObject"
	|                             id | note | value |
	| track2section1measure2NoteKey1 |  59  |  1/4  |
	| track2section1measure2NoteKey2 |  61  |  1/4  |
	| track2section1measure2NoteKey3 |  63  |  1/4  |
	| track2section1measure2NoteKey4 |  64  |  1/4  |
And prepare json array "track2section2measure1NoteList" of "noteObject"
	|                             id | note | value |
	| track2section2measure1NoteKey1 |  63  |  1/4  |
	| track2section2measure1NoteKey2 |  61  |  1/4  |
	| track2section2measure1NoteKey3 |  59  |  1/4  |
	| track2section2measure1NoteKey4 |  57  |  1/4  |
And prepare json array "track2section2measure2NoteList" of "noteObject"
	|                             id | note | value |
	| track2section2measure2NoteKey1 |  56  |  1/4  |
	| track2section2measure2NoteKey2 |  54  |  1/4  |
	| track2section2measure2NoteKey3 |  52  |  1/4  |
	| track2section2measure2NoteKey4 |  -1  |  1/4  |
And prepare composite id "track1section1MeasureKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
And prepare composite id "track1section1MeasureKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
And prepare composite id "track1section2MeasureKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
And prepare composite id "track1section2MeasureKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
And prepare composite id "track2section1MeasureKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   1   |
And prepare composite id "track2section1MeasureKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
	| measureId | int |   2   |
And prepare composite id "track2section2MeasureKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   1   |
And prepare composite id "track2section2MeasureKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
	| measureId | int |   2   |
And prepare data table "measureObject"
	|          id | notes |
	| compositeId | json  |
And prepare json array "track1section1MeasureList" of "measureObject"
	|                        id | notes                          |
	| track1section1MeasureKey1 | track1section1measure1NoteList |
	| track1section1MeasureKey2 | track1section1measure2NoteList |
And prepare json array "track1section2MeasureList" of "measureObject"
	|                        id | notes                          |
	| track1section2MeasureKey1 | track1section2measure1NoteList |
	| track1section2MeasureKey2 | track1section2measure2NoteList |
And prepare json array "track2section1MeasureList" of "measureObject"
	|                        id | notes                          |
	| track2section1MeasureKey1 | track2section1measure1NoteList |
	| track2section1MeasureKey2 | track2section1measure2NoteList |
And prepare json array "track2section2MeasureList" of "measureObject"
	|                        id | notes                          |
	| track2section2MeasureKey1 | track2section2measure1NoteList |
	| track2section2MeasureKey2 | track2section2measure2NoteList |
And prepare composite id "track1sectionKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   1   |
And prepare composite id "track1sectionKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
	| sectionId | int |   2   |
And prepare composite id "track2sectionKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   1   |
And prepare composite id "track2sectionKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
	| sectionId | int |   2   |
And prepare data table "sectionObject"
	|          id | tempo | timeSignature | measures |
	| compositeId | int   | fraction      | json     |
And prepare json array "track1sectionList" of "sectionObject"
	|                id | tempo | timeSignature | measures                  |
	| track1sectionKey1 |  120  |     4 / 4     | track1section1MeasureList |
	| track1sectionKey2 |  120  |     4 / 4     | track1section2MeasureList |
And prepare json array "track2sectionList" of "sectionObject"
	|                id | tempo | timeSignature | measures   |
	| track2sectionKey1 |  120  |     4 / 4     | track2section1MeasureList |
	| track2sectionKey2 |  120  |     4 / 4     | track2section2MeasureList |
And prepare json "clefJson"
	| id   | string | Treble |
	| type | string | G      |
	| note | int    | 64     |
	| spos | int    | -2     |
And prepare composite id "trackKey1"
	|  sheetId  | key | sheet |
	|  trackId  | int |   1   |
And prepare composite id "trackKey2"
	|  sheetId  | key | sheet |
	|  trackId  | int |   2   |
And prepare data table "trackObject"
	|          id | clef | sections |
	| compositeId | json | json     |
And prepare json array "trackList" of "trackObject"
	|        id | clef     | sections          |
	| trackKey1 | clefJson | track1sectionList |
	| trackKey2 | clefJson | track2sectionList |
And the response matches
	|  name  |   key  | sheet     |
	|  name  | string | fullSheet |
	| tracks |  json  | trackList |
