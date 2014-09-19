---
title: Clone all github repos of an organization
layout: post
category: gists
tags: 
  - github
  - tool
  - bash
published: true
---

## Clone all repos of an organization

During the live coding session of Nico, I needed to get all content of [bpmn.io](https://github.com/bpmn-io). Instead of cloning every repository on its own, I used this [gist](https://gist.github.com/caniszczyk/3856584).

And because it is such a nice feature, I wrapped it in a function:

	function git.github.clone.organization() {
		if [ $# -ne 0 ]
       	then
        	mkdir $1
        	cd $1
        	curl -s https://api.github.com/orgs/$1/repos?per_page=200 | ruby -rubygems -e 'require "json"; JSON.load(STDIN.read).each { |repo| %x[git clone #{repo["ssh_url"]} ]}'
		else
			echo "git.github.clone.organization <organization>"
        fi
    }

Thanks, [caniszczyk](https://github.com/caniszczyk)!