# AI Privacy Gateway

## Project Scenario

This project creates a local privacy gateway for an academic advisor using AI to summarize advising meetings.

The AI-assisted task is to convert rough advising notes into a concise summary and action list while keeping selected sensitive student information local.

## Milestone 2 Status

Milestone 2 contains a working Java prototype that processes one complete request through:

Input -> Detection -> Masking -> Offline Mock Provider -> Validation -> Restoration -> Output

The prototype currently supports:

- Student names using a local synthetic term list
- Student email addresses using pattern matching
- Nine-digit student IDs using pattern matching
- Request-specific placeholders
- Repeated sensitive values
- Multiple different entities in one request
- Response validation
- Local restoration of protected values
- Controlled errors for invalid placeholders
- Automated tests

The provider used in Milestone 2 is an offline deterministic mock provider. No real student information or live AI service is used.

## Runtime

OpenJDK 24.0.2

## Source Files

The Java source files are located in the `src` directory.

Main program:

`Main.java`

Automated tests:

`GatewayTests.java`

## Compile

From the `src` directory:

`javac *.java`

## Run the Gateway

`java Main`

The program will ask the user to enter advising notes.

### Example Input

`Met with Jordan Lee, student ID 123456789. Jordan Lee needs to complete prerequisite paperwork. Follow-up should be sent to jordan.lee@example.com.`

### Example Masked Request

`Met with [[r1:NAME:1]], student ID [[r1:STUDENT_ID:1]]. [[r1:NAME:1]] needs to complete prerequisite paperwork. Follow-up should be sent to [[r1:EMAIL:1]].`

### Example Final Output

Advising Summary:

Met with Jordan Lee, student ID 123456789. Jordan Lee needs to complete prerequisite paperwork. Follow-up should be sent to jordan.lee@example.com.

Action Items:

- Review registration requirements.
- Complete any required forms before registration.

The protected student information is restored locally only after the mock provider response has been validated.

## Run Automated Tests

`java GatewayTests`

Expected result:

PASS: Normal Input  
PASS: Repeated Sensitive Values  
PASS: No Sensitive Information  
PASS: Invalid Placeholder Response  
PASS: Different Entities

Tests Passed: 5  
Tests Failed: 0

The automated tests cover:

1. Normal input
2. Repeated sensitive values
3. No sensitive information
4. Invalid placeholder response
5. Different entities in one request

## Current Limitations

- Only synthetic names in the local term list are detected as names.
- Student IDs are currently expected to be nine digits.
- Email detection uses a regular-expression pattern.
- The AI provider is an offline mock rather than a live AI service.
- The prototype currently handles a single request at a time.