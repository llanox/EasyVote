EasyVote
========

Colaborative Android app to take decisions using a voting system. This app use a Mobile Backend as a service(MBaaS) https://backendless.com/. It was coded to be able to add support for another MBaaS like https://parse.com/

<br><a href="https://play.google.com/store/apps/details?id=com.llanox.mobile.easyvote">
  <img alt="Get it on Google Play"
       src="https://developer.android.com/images/brand/en_generic_rgb_wo_45.png" />
</a>

# Setting Up Project.

You need to create AppCredentials class into com.llanox.mobile.easyvote.util package that Constants class will contain backendless connection settings for this application.

```java
package com.llanox.mobile.easyvote.util;

public class AppCredentials {

    //In order to setting up a backendless backend for this application go to http://backendless.com/.Please, create an application
    // and generate keys to use here.

    public static final String APPLICATION_KEY = "xxxxxx-xxxx-xxxx-xxxx-xxxxxxx";
    public static final String SECRET_KEY = "xxxxx-xxxxx-xxxx-xxxx-xxxxxx";
    public static final String APP_VERSION = "vX";

}
```
# Task TODO
- [x] Changes to be able to use Android Studio 
- [ ] Fix Google + Sign up
- [x] Add  form field validation library
- [ ] Remove jar libraries from /lib folder and use gradle
- [ ] Show statistic charts about answered questions
 



