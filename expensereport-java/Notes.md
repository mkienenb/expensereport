# Expense Report Design Notes

## Design issues
- Handling of different expense types is spread over three sections and intermingled.
- Constants dealing with expense types are hardcoded in code rather than part of data model

## Testing Issues
- Presentation (System.out) and business logic are intermixed.
- System.out is difficult to test against
- System datetime is not easily mocked
