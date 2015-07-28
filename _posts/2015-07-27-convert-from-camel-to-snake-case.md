---
layout: post
title: Convert test method names from camelCase to snake_case
---

Over the last years I started abandoning the sun coding convention when it comes to test method names.
I find the so called snake_case much easier to read then the camelCase.

```java
public void thisIsWhatTheTestDoes() {...}

public void this_is_what_the_test_does() {...}
```

But when you just start writing some tests in a new fashion while others continue the old style, you quickly end up 
doing "what we have always done", because no one wants to apply the new convention to all existing tests.

Sounds familiar? Here is a one-liner that solves this problem. It scans all java test files in a directory, looks for lines that start with "public void" (assuming these are our test methods) and renames them from camelCase to snake_case.

```bash
find . -type f -name "*Test.java" -exec sed -r -i '/public void/ s/([a-z0-9])([A-Z])/\1_\L\2/g' {} \;
```

Which leads from

```java
public class DummyTest {
  @Test
  public void thisIsWhatTheTestDoes() {
    int myVariable = 0;
    doSomething(myVariable);
  }
}
```

to 

```java
public class DummyTest {
  @Test
  public void this_is_what_the_test_does() {
    int myVariable = 0;
    doSomething(myVariable);
  }
}
```

nicely keeping variable- and non-test-method-names on the way.

### Useful links

* http://unix.stackexchange.com/questions/112023/how-can-i-replace-a-string-in-a-files
