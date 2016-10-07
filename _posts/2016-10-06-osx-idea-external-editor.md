---
layout: post
title: Using external editors in IDEA/OSX
subtitle: once and for all open your files in the system default editor
---

When working with IDEA on OSX, some files can not be handled by the editor itself (for me, these are mostly camunda bpmn, cmmn and dmn files).

Here's howto configure IDEA to open those files with the default system editor:

### configure OSX's magic `open` command as external tool

![External Tool](/img/2016-10-07-idea-external-tools-1.png)

### add a keymap shortcut to the new external command

![Keymap shortcut](/img/2016-10-07-idea-external-tools-2.png)

### Done ...

now press `cmd+shift+o` after selecting the file in the project tree.

Thanks to Martin Schimack who show me this trick a while ago ... just needed it again.
