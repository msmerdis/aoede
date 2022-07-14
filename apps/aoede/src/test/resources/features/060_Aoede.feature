@Aoede @Regression
Feature: Aoede functionality
### Aoede specific api

@TC060001
@Positive
Scenario: request all information required to initialize the front end

Given prepare data table "clefObject"
	| id            | type   | note    | spos    |
	| string        | string | integer | integer |
And prepare json array "clefs" of "clefObject"
	| id            | type   | note    | spos    |
	| French Violin |  G     |  64     |  -4     |
	| Treble        |  G     |  64     |  -2     |
	| Soprano       |  C     |  60     |  -4     |
	| Mezzo-soprano |  C     |  60     |  -2     |
	| Alto          |  C     |  60     |   0     |
	| Tenor         |  C     |  60     |   2     |
	| Baritone      |  F     |  53     |   0     |
	| Bass          |  F     |  53     |   2     |
	| Subbass       |  F     |  53     |   4     |
And prepare data table "keySignatureObject"
	| id      | major  | minor  |
	| integer | string | string |
And prepare json array "keys" of "keySignatureObject"
	| id      | major  | minor  |
	| -7      |   C-   |   a-   |
	| -6      |   G-   |   e-   |
	| -5      |   D-   |   b-   |
	| -4      |   A-   |   f    |
	| -3      |   E-   |   c    |
	| -2      |   B-   |   g    |
	| -1      |   F    |   d    |
	|  0      |   C    |   a    |
	|  1      |   G    |   e    |
	|  2      |   D    |   b    |
	|  3      |   A    |   f+   |
	|  4      |   E    |   c+   |
	|  5      |   B    |   g+   |
	|  6      |   F+   |   d+   |
	|  7      |   C+   |   a+   |
And prepare data table "tempoObject"
	| id               | desc                                 | minTempo | stdTempo | maxTempo |
	| string           | string                               | number   | number   | number   |
And prepare json array "tempo" of "tempoObject"
	| id               | desc                                 | minTempo | stdTempo | maxTempo |
	| Larghissimo      | very, very slow                      |   0      |  24      |  24      |
	| Adagissimo       | very slow                            |  24      |  30      |  40      |
	| Grave            | very slow                            |  25      |  40      |  45      |
	| Largo            | slow and broad                       |  40      |  50      |  60      |
	| Lento            | slow                                 |  45      |  55      |  60      |
	| Larghetto        | rather slow and broad                |  60      |  65      |  66      |
	| Adagio           | slow with great expression           |  66      |  70      |  76      |
	| Adagietto        | slower than andante                  |  72      |  75      |  76      |
	| Andante          | at a walking pace                    |  76      |  80      | 108      |
	| Andantino        | slightly faster than andante         |  80      |  82      | 108      |
	| Marcia moderato  | moderately, in the manner of a march |  83      |  84      |  85      |
	| Andante moderato | between andante and moderato         |  92      | 104      | 112      |
	| Moderato         | at a moderate speed                  | 108      | 114      | 120      |
	| Allegretto       | moderately fast                      | 112      | 116      | 120      |
	| Allegro moderato | close to, but not quite allegro      | 116      | 118      | 120      |
	| Allegro          | fast, quick, and bright              | 120      | 138      | 156      |
	| Molto Allegro    | very fast                            | 124      | 140      | 156      |
	| Vivace           | lively and fast                      | 156      | 166      | 176      |
	| Vivacissimo      | very fast and lively                 | 172      | 174      | 176      |
	| Allegrissimo     | very fast                            | 172      | 174      | 176      |
	| Presto           | very, very fast                      | 168      | 184      | 200      |
	| Prestissimo      | even faster than presto              | 200      | 200      | 512      |
And prepare data table "octaveObject"
	|   id   | name         | pitch  |
	| number | string       | number |
And prepare json array "octave" of "octaveObject"
	|   id   |         name | pitch |
	|   -1   | subsubcontra |    0  |
	|    0   |   sub-contra |   12  |
	|    1   |       contra |   24  |
	|    2   |        great |   36  |
	|    3   |        small |   48  |
	|    4   |    one-lined |   60  |
	|    5   |    two-lined |   72  |
	|    6   |  three-lined |   84  |
	|    7   |   four-lined |   96  |
	|    8   |   five-lined |  108  |
	|    9   |    six-lined |  120  |
When request "/api/aoede/preload" from aoede as "preload"
Then the aoede response has a status code of 200
And "preload" json object's "clefList" element matches "clefs" as "json"
And "preload" json object's "keysList" element matches "keys" as "json"
And "preload" json object's "tempoList" element matches "tempo" as "json"
And "preload" json object's "octaveList" element matches "octave" as "json"
