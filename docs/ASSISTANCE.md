# Assistance

## AI Suggestion

Use request-specific placeholders such as [[r1:NAME:1]] instead of using a generic placeholder such as [REDACTED].

## Decision

I chose to use this suggestion because it makes it easier to track different sensitive values and restore the correct values later.

## How I Checked It

I compared the suggested placeholder format with the example given in the project handbook. The handbook also uses request-specific placeholders containing a request ID, category, and number. I also used this format in my planned test cases to make sure repeated and invalid placeholders could be handled.