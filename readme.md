# Jott Compiler

## Authors:
- Kyle Baptiste
- Leah Dibble
- Benson Haley
- Sunny Khan
- Sol Kumar

# How To Run
## Phase 1:
To run the given test cases, simply run the JottTokenizerTester in `src/testers`.

To see a list of tokens from a given file, compile Main.java with `javac Main.java` and then run the file with command line arguments specifying the file to be read.  For example, `java Main tokenizerTestCases/strings.jott output.jott Jott` would run the tokenization step on `strings.jott` and ignore the next two arguments, outputting a list of found tokens.

## Phase 2:
Run the testcases by launching `main` from `JottParserTester.java`.

## Phase 3:
Compile and run main as previously explained.

## Phase 4:
Run with:
- `java Jott reallyLong.jott out.c C`
- `java Jott reallyLong.jott out.java Java`
- `java Jott reallyLong.jott out.py Python`
