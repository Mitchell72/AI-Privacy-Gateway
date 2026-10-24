# Evaluation

## Milestone 2 Testing

The planned Milestone 1 cases were converted into automated tests for Milestone 2.

A fifth test was added to verify that different entities in the same request receive different placeholders.

## Test 1 - Normal Input

Input:

Met with Jordan Lee, student ID 123456789. Email: jordan.lee@example.com.

Expected Result:

The name, student ID, and email address should all be detected and replaced with placeholders.

Executed Result:

PASS

The system created placeholders for all three supported categories.

## Test 2 - Repeated Sensitive Values

Input:

Jordan Lee met with the advisor. Jordan Lee asked about registration.

Expected Result:

Both occurrences of Jordan Lee should use the same placeholder.

Executed Result:

PASS

Both occurrences were replaced using the same request-specific NAME placeholder.

## Test 3 - No Sensitive Information

Input:

Discussed registration requirements for next semester.

Expected Result:

No sensitive information should be detected and the placeholder mapping should remain empty.

Executed Result:

PASS

No placeholders were created.

## Test 4 - Invalid Placeholder Response

Original valid placeholder:

[[r1:NAME:1]]

Simulated invalid provider placeholder:

[[r1:NAME:99]]

Expected Result:

The system should reject the provider response and return a controlled error instead of restoring information.

Executed Result:

PASS

Observed error:

Invalid placeholder returned: [[r1:NAME:99]]

## Test 5 - Different Entities

Input:

Jordan Lee and Alex Carter both attended advising.

Expected Result:

The two different names should receive separate placeholders.

Expected placeholders:

[[r4:NAME:1]]

[[r4:NAME:2]]

Executed Result:

PASS

The two names received separate placeholders.

## Automated Test Results

PASS: Normal Input

PASS: Repeated Sensitive Values

PASS: No Sensitive Information

PASS: Invalid Placeholder Response

PASS: Different Entities

Tests Passed: 5

Tests Failed: 0

## End-to-End Prototype Result

The complete command-line pipeline was also executed manually.

Input contained:

- Jordan Lee
- Student ID 123456789
- jordan.lee@example.com

The masked request contained only placeholders for those protected values.

The offline mock provider returned the placeholders unchanged.

The response validator accepted the valid placeholders.

The original values were then restored locally in the final output.

## Current Limitations

The name detector only recognizes names included in the local synthetic term list.

Student IDs must currently contain exactly nine digits.

The offline provider does not evaluate the usefulness of a real AI-generated response.

More advanced failure handling and policy behavior will be added in later milestones.