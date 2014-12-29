---
layout: post
title: Easy MySQL setup with docker
subtitle: EasyMysql makes your life ... easy
header-img: "img/about-bg.jpg"
---

While looking for an easy to handle DB setup to develop my [camunda-bpm-dropwizard](http://github.com/jangalinski/camunda-bpm-dropwizard) examples (and failing dramatically with h2-server), I chose to give mysql another try. Back in the days when I did CMS and web-programming, I used to love mysql, but over the years in the JEE environment, its become mostly h2 or oracle.

Installing mysql on OSX is simple, but messy. No matter if you choose the pkg installation from mysql.com or use homebrew, your clean mac system gets polluted with lots and lots of files and commands in lots of different locations that you either do not want or do not need. So I made a brutal uninstall (using sudo rm -rf ..., had to run time machine to recover accidentally deleted directories on the way) and went for this "docker" everyone keeps talking about.

After setting everything up for the last 100 minutes, I must say, I am very pleased. Mysql starts up in a docker container, using a sql-dump I provide online to create the database, tables and initial data.

I used [EasyMySQL](https://github.com/nkratzke/EasyMySQL) by Nane Kratzke (thanks!) and the very well written documentation he provides. So heres what you do:

* Homebrew everything: `brew install docker boot2docker`
* Initialize the VM: `boot2docker init`
* ... export the DOCKER_* env vars it tells you
* run `docker build -t mysqldb github.com/nkratzke/easymysql` to build your local image
* run `docker run -d -p 3306:3306 -e user="camunda" -e password="camunda" -e right="WRITE" -e url="https://raw.githubusercontent.com/jangalinski/camunda-bpm-dropwizard/master/examples/src/test/resources/camunda_mysql.sql" mysqldb`
* done

Of course you should use your own user/pw settings and sql-dump. If you run mine, you get a fresh camunda database with the default users and invoice process instances.

You can connect to the DB with the user/pw/db settings you chose and the `boot2docker ip` address where your vm is running. And thats it.

Related note: For a very simple DB admin tool, checkout [Adminer](http://www.adminer.org/de/) and just run `php -S localhost:8000 adminer-4.1.0.php` on the single file alternative to phpMyAdmin. 


