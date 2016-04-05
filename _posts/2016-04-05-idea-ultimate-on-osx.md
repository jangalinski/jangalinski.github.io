---
layout: post
title: Intellij Idea Ultimate on OSX El Capitan
---

Just got my new extended personal license for Intellij Idea 2016.1. Had some trouble getting it running because it now *requires* 
Java8 while I could not get the previous version running *with* Java8.

So the first thing I had to do was googling around til I found this: [Selecting the JDK version](https://intellij-support.jetbrains.com/hc/en-us/articles/206544879-Selecting-the-JDK-version-the-IDE-will-run-under).

```bash
echo "/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk" > ~/Library/Preferences/IntelliJIdea2016.1/idea.jdk
```

Now Idea will start with the set jdk no matter what my JAVA_HOME might be. Check!
