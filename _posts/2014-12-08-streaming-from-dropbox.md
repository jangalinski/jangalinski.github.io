---
layout: post
title: "Streaming from dropbox"
subtitle: "Build a little mans youtube"
description: "How you can use the dropbox public directory as a streaming infrastructure instead of using youtube, vimeo and so on."
header-img: "img/about-bg.jpg"
---

I was looking for a simple solution to provide some private family videos (it's this time of the year again) without
uploading them to a video streaming platform and forcing my parents to log in with a google account to see their
grandchildren singing christmas songs.

I already offered those video with some php-cms on my shared server, but since streaming is not supported, the
whole video had to be downloaded before the player started.

I searched a bit and found the solution on [lockergnome](http://www.lockergnome.com/media/2012/06/26/how-use-dropbox-host-stream-videos/).

It is extremely easy:

1. convert your video to mp4 (I used "permute" for this).
2. Move the video to /Dropbox/public
3. right click the video and copy the public link
4. enter some HTML5 code in your page, using the link you just copied to the clipboard.
5. you are done.


```
<videowidth="512" height="288" controls="controls">
<source src="https://www.dropbox.com/s/os60r63ogwfl408/your_video_here.mp4" type="video/mp4" />
</video>
```

Nice, clean, simple (ok, I used one american file hoster instead of an american video portal) and I can just remove the
vids any time with a simple keystroke.


// TODO: still have to figure out how syntax highlighting works
