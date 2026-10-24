# Design

## Scenario

The intended user is an academic advisor.

The AI-assisted task is to convert rough notes from advising meetings into a short summary and action list for students.

## Motivation

Academic advisors spend a large amount of time meeting with students and may need to organize information from many separate appointments. This system is intended to help create clear summaries while keeping selected sensitive student information local.

## Sensitive Information

The current prototype supports these categories:

- NAME
- EMAIL
- STUDENT_ID

Names are detected using a local synthetic term list.

Email addresses and student IDs are detected using structured regular-expression patterns.

## Goal

Allow academic advisors to use an AI-assisted workflow for summarizing advising notes while preventing selected sensitive information from being sent outside the local system.

## Working M2 Pipeline

Advisor Notes (Local) -> Sensitive Data Detection -> Local Masking + Request Mapping -> Masked Notes -> Offline Mock Provider -> Response Validation -> Local Restoration -> Final Advising Summary
All original sensitive values and placeholder mappings remain local.
Only the masked notes are passed to the provider,
and returned placeholders are validated before local restoration.

## Main Components

### Main.java

Handles command-line input and runs one complete request through the gateway.

### SensitiveDataMasker.java

Detects supported sensitive information and replaces it with request-specific placeholders.

### MaskingResult.java

Stores the masked text and the request-local placeholder mapping.

### MockProvider.java

Acts as a deterministic offline replacement for an external AI provider.

It receives only masked text and returns a predictable advising summary and action list.

### ResponseProcessor.java

Checks placeholders returned by the provider.

If an unknown placeholder is found, the response is rejected with a controlled error.

If the response is valid, authorized values are restored locally.

### GatewayTests.java

Runs automated tests for the M2 prototype.

## Request-Local Mapping

Each request creates its own mapping between placeholders and original values.

Example:

[[r1:NAME:1]] -> Jordan Lee

[[r1:EMAIL:1]] -> jordan.lee@example.com

[[r1:STUDENT_ID:1]] -> 123456789

The original values remain local.

## Placeholder Format

[[requestID:CATEGORY:number]]

Examples:

[[r1:NAME:1]]

[[r1:EMAIL:1]]

[[r1:STUDENT_ID:1]]

Repeated occurrences of the same value use the same placeholder.

Different values in the same category receive different numbers.

Example:

Jordan Lee -> [[r4:NAME:1]]

Alex Carter -> [[r4:NAME:2]]

## Detection

### Term-List Matching

The prototype contains a small local list of synthetic student names:

- Jordan Lee
- Alex Carter
- Morgan Smith

This list is used for exact name matching.

### Structured Patterns

Email addresses are detected using a regular-expression pattern.

Student IDs are detected as nine-digit numbers using a regular-expression pattern.

## Validation

The response processor searches the provider response for placeholder syntax.

Every returned placeholder must exist in the current request's local mapping.

For example, if the request contains:

[[r1:NAME:1]]

but the provider returns:

[[r1:NAME:99]]

the system rejects the response rather than restoring a value.

## Restoration

After successful validation, placeholders are replaced with the original values from the local mapping.

Restoration occurs only after the provider response returns to the local system.

## Offline Mock Provider

Milestone 2 uses a deterministic offline mock provider instead of a live AI service.

The mock provider produces a predictable advising summary and action list so the privacy gateway can be tested without sending information to an external service.

## Prompt Version 1

The planned provider prompt is:

"Summarize these academic advising notes into a short summary and add a list of important follow-up information. Preserve all placeholders exactly as they are given. Do not modify, remove, or create any placeholders."

The M2 offline mock does not call a live language model, but this prompt represents the instructions that would be sent to a future provider.

## Programming Language Choice

Java is used for this project because its static type system and class-based structure fit the design of the privacy gateway.

The gateway contains several pieces of data that need to stay clearly separated. For example, `MaskingResult` stores the masked text and a `Map<String, String>` containing the placeholder mappings. Java's static typing makes the expected types of these values explicit and allows some mistakes to be detected during compilation instead of only while the program is running.

Java also works well for separating the gateway into components with clear responsibilities. The prototype uses separate classes for masking, storing results, the mock provider, response processing, the main program, and automated tests. This structure makes the pipeline easier to understand and modify as later milestones add more behavior.

Python would require less code for some string-processing and regular-expression tasks and could make a small prototype faster to write. However, Java's stronger compile-time checking and structured class design are useful for this project because the placeholder mappings, validation steps, and component boundaries need to remain clear and predictable.

For this project, the extra structure required by Java is a reasonable trade-off for clearer types, organization, and error checking.

## M2 Scope

Milestone 2 implements one complete single-turn request through the system.

It currently includes:

- Local detection
- Local masking
- Request-specific placeholder mappings
- Term-list matching
- Structured-pattern matching
- Repeated-value handling
- Multiple-entity handling
- Offline mock provider
- Response validation
- Local restoration
- Controlled invalid-placeholder errors
- Automated tests

## Current Limitations

- Only synthetic names in the local term list are detected as names.
- Student IDs are currently limited to nine-digit values.
- The mock provider uses deterministic hard-coded output rather than real AI generation.
- The program currently handles one request at a time.
- Full MASK/REMOVE policy behavior is not implemented yet.
- Cross-request isolation is not implemented yet.

Those later behaviors are outside the required M2 scope.

## JSON I/O Contract Note

The project handbook refers to a separate shared CSCI4330 Project I/O Contract for M2. That separate document was not available in the course materials when this prototype was implemented.

The current M2 prototype therefore uses command-line text input and output. The I/O layer can be adapted to the required JSON fields once the separate contract is available.