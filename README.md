# BinaryCalculator (SOFE3980U Lab 1)

Maven project for unsigned binary arithmetic on arbitrary-length binary strings.

- `groupId`: `com.ontariotechu.sofe3980U`
- `artifactId`: `BinaryCalculator`
- `version`: `1.0.0`

## Build

```bash
mvn clean package                  # compile, run tests, build JAR
mvn clean package assembly:single  # also build the JAR with dependencies
mvn site                           # generate Javadoc + Surefire HTML reports
```

## Run

```bash
java -jar target/BinaryCalculator-1.0.0-jar-with-dependencies.jar
```

## Binary class API

| Method | Description |
| --- | --- |
| `Binary(String)` | Validates the digits, drops leading zeros, defaults to `"0"` |
| `getValue()` | Returns the stored binary string |
| `add(Binary, Binary)` | Binary addition with carry propagation |
| `or(Binary, Binary)` | Bitwise logical OR |
| `and(Binary, Binary)` | Bitwise logical AND |
| `multiply(Binary, Binary)` | Binary multiplication (shift-and-add) |

## Documentation

After `mvn site`, open `target/site/index.html`. Javadoc is under
`target/site/apidocs/`, test results under `target/site/surefire-report.html`.
