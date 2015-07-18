EasyVote
========

![](https://github.com/llanox/EasyVote/blob/master/Resources/FeatureGraphic.png)

Colaborative Android app to take decisions using a voting system. This app use a mobile Backend as a service (mBaaS) https://backendless.com/. It was coded to be able to add support for another MBaaS like https://parse.com/

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
# TODO tasks
- [x] Migrate app from Eclipse to Android Studio project structure.
- [ ] Fix issue with Google + Sign up .
- [x] Add  form field validation library.
- [ ] Remove jar libraries from /lib folder and use gradle.
- [ ] Show statistical charts about answered questions.
- [ ] Create a poll of questions code to share with other users.
- [x] Change app icon.
- [ ] Create  application Theme according with the new icon colors.
- [ ] Add internalization to the app. Translate app messages into Spanish(ES-CO) and Portugues(PT-BR) languages.
- [ ] Use Travis CI to test, build and deploy that app.
- [ ] Notify changes in the poll of questions with Push Notifications.
- [ ] Create Wiki to document how to use the app.
- [ ] Add some unit tests.
 



