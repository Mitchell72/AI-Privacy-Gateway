# User Guide

## Milestone 2

Milestone 2 contains a working command-line prototype of the AI Privacy Gateway.

The program allows an academic advisor to enter synthetic advising notes. Supported sensitive information is masked locally before being passed to an offline mock provider.

## Requirements

OpenJDK 24.0.2

## Compile

Open a terminal in the `src` directory.

Run:

javac *.java

## Run the Gateway

Run:

java Main

The program will display:

AI Privacy Gateway - Advising Summary

Enter advising notes:

Enter one line of synthetic advising notes.

## Example Input

Met with Jordan Lee, student ID 123456789. Jordan Lee needs to complete prerequisite paperwork. Follow-up should be sent to jordan.lee@example.com.

## Example Masked Request

Met with [[r1:NAME:1]], student ID [[r1:STUDENT_ID:1]]. [[r1:NAME:1]] needs to complete prerequisite paperwork. Follow-up should be sent to [[r1:EMAIL:1]].

## Example Final Output

The final output contains an advising summary and action items.

After the provider response is validated, the original student name, student ID, and email address are restored locally.

## Run Automated Tests

Run:

java GatewayTests

Expected result:

PASS: Normal Input
PASS: Repeated Sensitive Values
PASS: No Sensitive Information
PASS: Invalid Placeholder Response
PASS: Different Entities

Tests Passed: 5
Tests Failed: 0

## Supported Sensitive Information

### Names

Names must currently appear in the local synthetic name list.

Supported examples include:

- Jordan Lee
- Alex Carter
- Morgan Smith

### Student IDs

Student IDs are currently detected as nine-digit numbers.

Example:

123456789

### Email Addresses

Standard email-address patterns are detected.

Example:

jordan.lee@example.com

## Important Notes

Use synthetic information only.

Do not enter real student records or other real private information.

The provider in Milestone 2 is an offline mock provider. No live AI system is contacted.

## Errors

If the provider returns a placeholder that was not created for the current request, the program returns a controlled error.

Example:

Invalid placeholder returned: [[r1:NAME:99]]