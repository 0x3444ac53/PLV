# Propositional Logic Validator

### Slightly long winded explaination

This is my attempt to build a syntax checker for Propositional Logic. Currently, it only works as a repl, and it doesn't force parenthesis. That last bit isn't a large concern of mine. 

Currently no evaluation happens (and to be totally honest I'm not entirely sure how that would even work). If the sentence is valid the repl takes input and spits out the sentence in RPN, if it is invalid it Errors. 
_There is currently a known bug where you REPL must be restarted on Error_



### Current supported symbols and operations

| **Operations** |      |      |      |
| -------------- | ---- | ---- | ---- |
| **AND**        | &    | ∧    |      |
| **IFF**        | ↔    | ⇔    |      |
| **NOT**        | ~    | ∼    | ¬    |
| **IF**         | →    | ⇒    |      |
| **OR**         | ∨    |      |      |

### Demo

[![asciicast](https://asciinema.org/a/zbW0fquITI5dFVK6mx3mIzIj3.svg)](https://asciinema.org/a/zbW0fquITI5dFVK6mx3mIzIj3)



#### Known Issues

- Currently Doesn't check for unmatched ')' at the end of sentences
- When Panic mode is entered it stops attempted to parse sentences, requiring a restart

#### Todo

- Add More Symbols
- Modify to support predicate logic

## Acknowledgements

[Crafting Interpreters](https://craftinginterpreters.com/) is a handbook for creating programming languages, 90% of the code seen here is adapted from there. It is an invaluable resource, and if you have an interest in languages you should check it out!

[This Slide Show Presentation](http://www2.cs.siu.edu/~rahimi/cs537/slides/Formal%20Presentation%20of%20Prop%20and%20Pred%20Logic.pdf) from [Shahram Rahimi](http://www2.cs.siu.edu/~rahimi/), at the University of Southern Illinois for providing an easily digestible grammar for predicate logic.

