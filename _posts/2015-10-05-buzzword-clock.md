---
layout: post
title: Building a BuzzwordClock with Reactor, Atmosphere, Spring-Boot and Vaadin
---

Recently I started working with reactive streams and event based systems using
[projectreactor.io](http://projectreactor.io/). I thought it would be nice to
use an event based approach to communicate between UI and server, so I dug
around a bit and found vaadin-spring and vaadin-push.

So here is my buzzword-clock, a single file application that shows the current
time using as many fancy technologies as possible:

* Spring Boot with web-starter
* Spring @Scheduled for the ticking clock
* vaadin spring for the UI
* vaadin push with atmosphere for updating the web-UI from the server
* Reactor for the (async) EventBus.

For a full pom.xml, check out the [repo](https://github.com/jangalinski/springboot-playground).

<center>
![the clock in action](/img/buzzwordclock.png)
</center>

{% gist d102ee410961bd1d607d %}

## Main Building blocks

1. It is a spring-boot application, starting an embedded tomcat servlet container.
2. It defines a Vaadin UI spring bean that is discovered and bound to "/"
3. The "clock" bean is scheduled, its method is triggered every second.
4. Using the reactor framework, it defines an Environment and an EventBus bean that loosly couples the server and the UI.
5. Every second an event is fired (notify) containing the current local time as payload
6. The UI consumes the localtime event and updates the label with the formatted time

## Thanks to

For the push part, I leveraged the [Using Server-Push in a Vaadin app](http://www.sothawo.com/2015/06/using-server-push-in-a-vaadin-app/)
example by P.J.Meisch.

## Open points

Seams like I didn't get the @EnableReactor annotation ... I had to configure the reactor environment in a static block. 
