# Custom ruleset for [ktlint](https://github.com/pinterest/ktlint)

## Rules
Provides the ruleset `texify` with the following rules:
- `newline-before-keyword`: `else`, `finally`, and `catch` on new line
- `newline-after-class-header`: empty line after class headers
- `keyword-spaces`: Space required after keywords like `if` and space forbidden after `get` and `set`

A rule can be disabled by adding `texify:<rule-id>` to the `disabled_rules` in your `.editorconfig` file.