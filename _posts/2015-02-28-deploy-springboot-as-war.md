---
layout: post
title: Dual build springboot jar and war
---

One of the great advantages of spring-boot applications is that they can run as single jar using an embedded
web server or be deployed on an existing application server (or servlet container).

This post describes how to configure a maven dual build that allows to create the executable jar and the deployable war file with one pom.xml set up.

First, define a property for the `<packaging>` attribute (I use "jar" as default):

    <packaging>${packaging.type}</packaging>
    <properties>
        <packaging.type>jar</packaging.type>
    </properties>

Now you could switch to a war packaging via property switch `-Ppackaging.type=war`. However, that would build a war file that includes the embedded tomcat used in the executable jar. To get rid of the superfluous dependencies, let's define a profile and overwrite the scope. As we already use a profile, we can use it to set the property as well:

    <profiles>
        <profile>
            <id>war</id>
            <properties>
                <packaging.type>war</packaging.type>
            </properties>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                    <scope>provided</scope>
                </dependency>
            </dependencies>
        </profile>
    </profiles>

Now, when I run `mvn -Pwar clean package`, I get a war file that I can deploy in any servlet-container, when I ommit the profile switch, I get a standalone, executable jar that includes an embedded tomcat.

Nice. But thats not all. We need to configure our Spring Boot Application main class so it registers
our controllers and views on deployment in the container.

This is described in [howto-traditional-deployment](http://docs.spring.io/spring-boot/docs/current/reference/html/howto-traditional-deployment.html): Define a `SpringBootServletInitializer` that adds your main class to the sources.
I just let my Application extend the Initializer and overwrote the configure method:

    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

Of course, now I have an Initializer in my code even when I run the executable jar. It doesn't hurt much, but it's more invasive then I want it to be. I could think of generating the Servlet-Initializer based on the chosen profile, since it only has to add one class to the sources. I'll keep you updated.

Check out the complete example on github: [springboot-playground](https://github.com/jangalinski/springboot-playground).
