# Design

## Idea

Academic advisor using an AI tool to help summarize ideas given in advising meetings.

## User

Academic Advisor

## AI-Assisted Task

Convert rough notes from advising meetings into a concise summary and action list for students.

## Motivation

Academic advisors spend a lot of time meeting with students and can become overwhelmed trying to help all of them to their best ability. This system could allow them to give students a good summary to look back on along with a detailed list of actions they need to complete. This can allow advisors to spend more time working with students rather than worrying if students remembered everything from the meetings and responding to emails about things they have already discussed.

## Sensitive Information

- Student names
- Student ID numbers
- Student email addresses

## Goal

Allow academic advisors to use AI to help summarize meetings with students while keeping all sensitive information local, helping save them time and give students an easier way of keeping up with everything they need to do.

## Expected Output

A short and clear summary of advising appointments containing what actions need to be done and anything important for the student to remember. The student's protected information should then be restored locally for all final outputs.

## Success Criteria

The system properly masks all sensitive information before giving it to AI, validates placeholders, restores values locally, and provides a satisfactory advising summary.

## Non-Goals

- Detect every point of sensitive information
- Replace college advising software
- Store student records long-term
- Use real student information
- Have a graphical interface

## M2 Scope

The plan for M2 is to implement one complete request through:

Input -> detection -> masking -> offline mock provider -> validation -> restoration -> output

This process will use request-local mappings, a supported subset of sensitive categories, term-list matching, and at least one structured pattern.

## Programming Language

Java will be used because it is the language I am most familiar with.

### Difference 1: Type System

Java is statically typed, so structures such as placeholder mappings, sensitive-data categories, and provider responses can be clearly defined types. Python is dynamically typed, which often makes development faster but provides fewer checks at compile time.

### Difference 2: Text Processing

Python typically requires less code for string manipulation and regular expressions, which can make these operations quicker to write. Java can perform the same operations but generally requires more structure and code.

## System Design

Advisor Notes -> Sensitive Data Detection -> Placeholder Creation / Local Mapping -> Masked Notes -> Local/External Boundary -> AI Assistance -> Local/External Boundary -> Response Validation -> Restore Values -> Final Summary

## Main Components

Input handler, sensitive data detector, placeholder manager, AI assistant, response validator, restoration system, and output handler.

## Placeholder Format

[[requestID:category:number]]

Example:

[[r1:NAME:1]]

## Initial Prompt

Summarize these academic advising notes into a short summary and add a list of important follow-up information. Preserve all placeholders exactly as they are given. Do not modify, remove, or create any placeholders.