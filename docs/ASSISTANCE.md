# Assistance

## Milestone 1 Assistance

### AI Suggestion

Use request-specific placeholders such as [[r1:NAME:1]] instead of a generic placeholder such as [REDACTED].

### Decision

The suggestion was accepted because request-specific placeholders make it possible to track and restore different protected values.

### How It Was Checked

The placeholder format was compared with the example in the project handbook.

The same format was also used in the planned test cases.

---

## Milestone 2 Assistance

### AI Suggestion

Separate the working prototype into small Java components instead of placing the entire gateway inside one class.

The suggested responsibilities were:

- SensitiveDataMasker for detection and masking
- MaskingResult for masked text and mappings
- MockProvider for deterministic offline provider behavior
- ResponseProcessor for validation and restoration
- Main for the complete request flow
- GatewayTests for automated testing

### Decision

I accepted the suggestion because the separate classes make each part of the pipeline easier to understand, test, and modify.

### How It Was Checked

Each component was tested as part of the complete pipeline.

The program was run with synthetic advising information and correctly produced:

Input -> Detection -> Masking -> Offline Mock Provider -> Validation -> Restoration -> Output

The masking output was manually checked to confirm that the original name, student ID, and email were not present in the provider-facing text.

The automated test program was also run.

Observed result:

Tests Passed: 5

Tests Failed: 0

### Additional AI Assistance

AI was also used to suggest test cases for:

- Repeated sensitive values
- No sensitive information
- Invalid placeholders
- Multiple different entities in one request

### Decision

These suggestions were accepted because they directly matched required M2 behaviors.

### How They Were Checked

The tests were implemented and executed locally in Java.

All five automated tests passed.