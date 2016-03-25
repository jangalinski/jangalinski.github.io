---
layout: post
title: Java8-Stream: Collect BestMatch for given condition
---

I just started a [snippets-repository](https://github.com/jangalinski/snippets) to collect and discuss some useful helpers or 
spikes I came along.

Today, let's start with the [MatchingCollector](https://github.com/jangalinski/snippets/blob/master/src/main/java/io/github/jangalinski/stream/MatchingCollector.java), 
which is used as a collector to terminate a stream. I believe, it is quite useful to replace a `filter(...).sorted(...).findFirst()` chain.

This simple example shows how to use the Collector to find the highest number in a stream (like Collectors.max() would do).

```java
Stream.of(1, 45, 3, 2, null, 10,7,19,4,7,39).parallel().collect(match((a, b) -> b > a))); // = Optional.of(45)
```

This will internally store the first value of the stream and then apply the given biPredicate to each following stream item, comparing the old and the new value.
45 will match because it is greater than 1, and none of the follow up items are greater, so this is the final result. As you can see, this can be used
in parallel.

## Let's have a look at the implementation:

{% gist 2471f822440aaf2f9c58 %}

1. The collector is created by passing a BiPredicate that defines under which condidition the new element is a better match than the previous stored result. 
1. The collector stores the result value internally in a AtomicReference, that is initially created via the supplier(). The supplier is called when the stream is created (can be multiple when used parallelly).
1. for each processed stream element, the BiConsumer provided by accumulator() is used. This evaluates the predicate to determine if the stream item will become the new container value.
1. When using parallel streams, the streams are merged by the function provided by the combiner(). In this case, the internal value is compared using the test() method and the better matching result is kept.
1. On finish, we wrap the internal reference value with in Optional (as it might still be null if no element matched the predicate).
1. the test() implements the BiPredicate and makes sure that null values are treated correctly even when the predicate given by the user does not deal with null.


