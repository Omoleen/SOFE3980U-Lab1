# SOFE3980U Lab 1 Report
## Software Project Management and Comprehension Tool (Apache Maven)

**Name:** Emmanuel Omole  
**Student number:** 101004432  
**Date:** 2026-09-11

**GitHub repository:** https://github.com/Omoleen/SOFE3980U-Lab1  
**Demo video:** https://www.loom.com/share/e83cc65bc3b346dd9da8c6c7d9ab8829

---

## 1. Environment and Project Setup

| Item | Value |
| --- | --- |
| Maven | 3.9.16 |
| JDK | OpenJDK 25.0.2 |
| Compiler source/target | 17 |
| groupId / artifactId / version | `com.ontariotechu.sofe3980U` / `BinaryCalculator` / `1.0.0` |

The project skeleton was produced with the Maven quickstart archetype:

```bash
mvn archetype:generate -DgroupId=com.ontariotechu.sofe3980U \
  -DartifactId=BinaryCalculator -Dversion=1.0.0 \
  -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4
```

The archetype writes `maven.compiler.source/target` of `1.7`, which modern
`javac` rejects (`source option 7 is no longer supported`). Both properties were
raised to `17`, and the locked plugin versions were raised to releases that
support a current JDK (compiler 3.13.0, surefire 3.2.5, jar 3.4.1, assembly
3.7.1, site 3.12.1, javadoc 3.7.0, surefire-report 3.2.5). JUnit was moved to
4.13.2 for the same reason; the test code still uses the JUnit 4 `@Test` and
`assertTrue` API.

`pom.xml` configures four plugins that matter to the deliverables:

- **maven-jar-plugin**: writes `Main-Class: com.ontariotechu.sofe3980U.App`
  into the manifest so the JAR is directly runnable.
- **maven-assembly-plugin**: the `jar-with-dependencies` descriptor, so the
  `joda-time` dependency is unpacked into a single self-contained JAR.
- **maven-javadoc-plugin**: placed in `<reporting>`, so `mvn site` renders the
  Javadoc comments as browsable API documentation.
- **maven-surefire-report-plugin**: placed in `<reporting>`, so the test results
  become part of the generated site.

## 2. Source Code

`src/main/java/com/ontariotechu/sofe3980U/Binary.java` stores an unsigned
binary number as a `String` of `'0'`/`'1'` characters, so operands are not
limited to 32 or 64 bits. The constructor validates every character, rejects
anything else by falling back to `"0"`, and strips leading zeros so that every
value has a single canonical representation. Three functions were added.

### 2.1 `or(Binary, Binary)`: bitwise logical OR

Both operands are walked from the least significant digit toward the most
significant. When one operand is shorter, the missing digit is treated as a
leading `'0'` instead of terminating the loop, which is what makes the operation
correct for mixed lengths. A result digit is `'1'` when either operand digit is
`'1'`. The result string is handed to the constructor, so any leading zeros it
contains are normalised away.

### 2.2 `and(Binary, Binary)`: bitwise logical AND

Same traversal as OR, with the digit rule changed to "both operands must be
`'1'`". Padding matters more here: any digit beyond the length of the shorter
operand is ANDed with an implied `'0'` and is therefore masked out, so
`1010 AND 11` is `10`, not `1011`. Because AND frequently produces leading
zeros, the constructor's normalisation is what keeps `1000 AND 111` equal to
`"0"` rather than `"0000"`.

### 2.3 `multiply(Binary, Binary)`: binary multiplication

Implemented with the shift-and-add (long multiplication) algorithm rather than
by converting to an integer, which would reintroduce a width limit. The product
starts at `"0"`; the second factor is scanned from its least significant digit,
and for every `'1'` digit at position *i* from the right, the first factor
shifted left by *i* places, that is with *i* zeros appended, is added to the
running product through the existing `add` function. Digits equal to `'0'`
contribute nothing and are skipped. Reusing `add` keeps carry handling in one
place.

Cost: `or` and `and` are O(n) in the longer operand; `multiply` performs at most
one addition per digit of the second factor, so it is O(n·m).

### 2.4 `App.java`

`App` prints the local time via `org.joda.time.LocalTime` (the dependency
exercise), builds the two binary operands, and now prints all four operations.
Running the JAR with dependencies produces:

```
The current local time is: 18:52:31.771
First binary number is 10001000
Second binary number is 111000
Their summation is 11000000
Their bitwise OR is 10111000
Their bitwise AND is 1000
Their multiplication is 1110111000000
```

Checked in decimal: 136 + 56 = 192 (`11000000`), 136 | 56 = 184
(`10111000`), 136 & 56 = 8 (`1000`), 136 × 56 = 7616 (`1110111000000`).

## 3. Testing

Tests live in `src/test/java/com/ontariotechu/sofe3980U/BinaryTest.java` and run
automatically during `mvn package` through Surefire. There are 27 tests: 6 for
the constructor, 5 for `add`, and 5 or 6 for each new function, which is above
the required minimum of three per function.

| Function | Tests | Cases covered |
| --- | --- | --- |
| `or` | 5 | equal lengths; first operand longer; first operand shorter; one operand zero; both zero |
| `and` | 5 | equal lengths; different lengths (leading digits masked out); result needing leading-zero removal; operands with no common `1` digit; one operand zero |
| `multiply` | 6 | equal lengths; first factor longer; first factor shorter; factor of one (identity); factor of zero; power-of-two factor (pure shift) |

The cases were chosen around the boundaries where the implementation could
plausibly break rather than around convenient inputs: unequal operand lengths
exercise the zero-padding branch, the "no common digit" and "leading zeros"
cases exercise constructor normalisation, and the zero and identity factors
exercise the skip-and-accumulate path in `multiply`. Every expected value was
computed independently in decimal. For example, `multiply2` asserts
`1010 × 11 = 11110`, i.e. 10 × 3 = 30.

Result of `mvn clean package assembly:single`:

```
Tests run: 27, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 4. Documentation

`mvn site` generates `target/site/index.html`. Each added function carries a
Javadoc comment describing its behaviour, its `@param` values and its
`@return` value, so `target/site/apidocs/.../Binary.html` documents `or`, `and`
and `multiply` next to the pre-existing `add`. The Surefire report at
`target/site/surefire-report.html` lists all 27 tests with a 100% success rate.

## 5. Commands Used

```bash
mvn -v                             # verify the installation
mvn clean package                  # compile, test, build the JAR
mvn clean package assembly:single  # build the JAR with dependencies
java -jar target/BinaryCalculator-1.0.0-jar-with-dependencies.jar
mvn site                           # generate documentation and test reports
```

## 6. Repository Contents

The repository holds `pom.xml`, `src/main/java` (`App.java`, `Binary.java`),
`src/test/java` (`BinaryTest.java`), `README.md` and this report. `target/` is
excluded through `.gitignore` because it holds generated build output.
