# ktings
A simulation of the CS 125 gradebook in Kotlin.  Uses KTOR to simulate grade updates and class statistics, as well as Kotlin-Statistics to calculate those values.

## API Documentation

| **field**               |**result**    |
| -------------           |:------------- |
| `/netid`                | if a studen with the`netid` is registered in the course, overall student grade with `netid` and without drops |
| `/netid/component`      | displays component grade, with and without drops, for student with corresponding `netid` (Lecture, Lab, Exam, Quiz, Final Project, etc.)      |
| `/course`               | display course's general performance with and without drops      |
