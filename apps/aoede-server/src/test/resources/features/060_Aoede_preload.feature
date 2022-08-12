@Aoede @Preload @Regression
Feature: Aoede preload functionality
### Aoede preload specific api

Background: Request preload data

Given request "/api/aoede/preload" from aoede as "preload"
Then the aoede response has a status code of 200

@TC060001
@Positive
Scenario: verify clef in preload info

When prepare data table "clefObject"
	| id            | type   | note    | spos    |
	| string        | string | integer | integer |
And prepare json array "clefs" of "clefObject"
	| id            | type   | note    | spos    |
	| French Violin |  G     |  67     |  -4     |
	| Treble        |  G     |  67     |  -2     |
	| Soprano       |  C     |  60     |  -4     |
	| Mezzo-soprano |  C     |  60     |  -2     |
	| Alto          |  C     |  60     |   0     |
	| Tenor         |  C     |  60     |   2     |
	| Baritone      |  F     |  53     |   0     |
	| Bass          |  F     |  53     |   2     |
	| Subbass       |  F     |  53     |   4     |
Then "preload" json object's "clefList" element matches "clefs" as "json"

@TC060002
@Positive
Scenario: verify keys in preload info

When prepare data table "keySignatureObject"
	| id      | major  | minor  | notes                                |
	| integer | string | string | note offset array                    |
And prepare json array "keys" of "keySignatureObject"
	| id      | major  | minor  | notes                                |
	| -7      |   C-   |   a-   |  1- 1  2- 2  3  4- 4  5- 5  6- 6  7  |
	| -6      |   G-   |   e-   |  1- 1  2- 2  3- 3  4  5- 5  6- 6  7  |
	| -5      |   D-   |   b-   |  0  1  2- 2  3- 3  4  5- 5  6- 6  7- |
	| -4      |   A-   |   f    |  0  1  2- 2  3- 3  4- 4  5  6- 6  7- |
	| -3      |   E-   |   c    |  0  1- 1  2  3- 3  4- 4  5  6- 6  7- |
	| -2      |   B-   |   g    |  0  1- 1  2  3- 3  4- 4  5- 5  6  7- |
	| -1      |   F    |   d    |  0  1- 1  2- 2  3  4- 4  5- 5  6  7- |
	|  0      |   C    |   a    |  0  0+ 1  1+ 2  3  3+ 4  4+ 5  5+ 6  |
	|  1      |   G    |   e    |  0  0+ 1  1+ 2  2+ 3  4  4+ 5  5+ 6  |
	|  2      |   D    |   b    | -1+ 0  1  1+ 2  2+ 3  4  4+ 5  5+ 6  |
	|  3      |   A    |   f+   | -1+ 0  1  1+ 2  2+ 3  3+ 4  5  5+ 6  |
	|  4      |   E    |   c+   | -1+ 0  0+ 1  2  2+ 3  3+ 4  5  5+ 6  |
	|  5      |   B    |   g+   | -1+ 0  0+ 1  2  2+ 3  3+ 4  4+ 5  6  |
	|  6      |   F+   |   d+   | -1+ 0  0+ 1  1+ 2  3  3+ 4  4+ 5  6  |
	|  7      |   C+   |   a+   | -1  0  0+ 1  1+ 2  3  3+ 4  4+ 5  5+ |
Then "preload" json object's "keysList" element matches "keys" as "json"

@TC060003
@Positive
Scenario: verify tempos in preload info

When prepare data table "tempoObject"
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
And "preload" json object's "tempoList" element matches "tempo" as "json"

@TC060004
@Positive
Scenario: verify tempos in preload info

When prepare data table "octaveObject"
	|   id   | name         | pitch  |
	| number | string       | number |
And prepare json array "octave" of "octaveObject"
	|   id   |         name | pitch  |
	|   -1   | subsubcontra |    0   |
	|    0   |   sub-contra |   12   |
	|    1   |       contra |   24   |
	|    2   |        great |   36   |
	|    3   |        small |   48   |
	|    4   |    one-lined |   60   |
	|    5   |    two-lined |   72   |
	|    6   |  three-lined |   84   |
	|    7   |   four-lined |   96   |
	|    8   |   five-lined |  108   |
	|    9   |    six-lined |  120   |
Then "preload" json object's "octaveList" element matches "octave" as "json"

@TC060005
@Positive
Scenario: verify time signatures in preload info

When prepare data table "timeSignatureObject"
	| numerator | denominator | beats     |
	| integer   | integer     | json      |
And prepare json array "beats01"
	| integer | 0 |
	| integer | 1 |
And prepare json array "beats012"
	| integer | 0 |
	| integer | 1 |
	| integer | 2 |
And prepare json array "beats0123"
	| integer | 0 |
	| integer | 1 |
	| integer | 2 |
	| integer | 3 |
And prepare json array "beats03"
	| integer | 0 |
	| integer | 3 |
And prepare json array "beats036"
	| integer | 0 |
	| integer | 3 |
	| integer | 6 |
And prepare json array "beats0369"
	| integer | 0 |
	| integer | 3 |
	| integer | 6 |
	| integer | 9 |
And prepare json array "timeSignatures" of "timeSignatureObject"
	| numerator | denominator | beats     |
	|     2     |      4      | beats01   |
	|     2     |      2      | beats01   |
	|     3     |      8      | beats012  |
	|     3     |      4      | beats012  |
	|     3     |      2      | beats012  |
	|     4     |      8      | beats0123 |
	|     4     |      4      | beats0123 |
	|     4     |      2      | beats0123 |
	|     6     |      8      | beats03   |
	|     6     |      4      | beats03   |
	|     9     |      8      | beats036  |
	|     9     |      4      | beats036  |
	|    12     |      8      | beats0369 |
	|    12     |      4      | beats0369 |
Then "preload" json object's "timeList" element matches "timeSignatures" as "json"
