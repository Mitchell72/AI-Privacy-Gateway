# Evaluation

## Test 1 - Normal Input

Input:

Met with Jordan Lee, student ID 123456789. Jordan's email is jordan.lee@example.com. We discussed registering for CSCI 4330 next semester and completing the prerequisite paperwork.

Expected Result:

Name, email, and student ID should be masked before being sent to AI. After the response is validated, the information should be restored locally.

## Test 2 - Repeated Sensitive Values

Input:

Jordan Lee asked about registering early. Jordan Lee also requested that the follow-up information be sent to jordan.lee@example.com. The advisor confirmed that jordan.lee@example.com was correct.

Expected Result:

Repeated occurrences should use the same mapping and be correctly restored in the final output.

## Test 3 - No Sensitive Information

Input:

Discussed registering for next semester and completing the prerequisite form before registration opens.

Expected Result:

No sensitive information should be detected, so no placeholders need to be created.

## Test 4 - Invalid Placeholder Response

A masked request contains [[r1:NAME:1]], but the AI returns [[r1:NAME:99]].

Expected Result:

The system should recognize the placeholder as invalid and return a controlled error rather than restoring it.