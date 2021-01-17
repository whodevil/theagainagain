# The Again Again
This is a toy project that might become more. I want to explore technology and potentially 
build this into a home page. As I've attempted like 30,000 versions of a home page
I'm not going to hold my breath that this will keep my attention that long, but in the
meantime, I'll try having fun with it. I make music under the name the Again Again, so I guess
I was thinking this could be a place to share my music and art, but I then again I'm skeptical
it will get that far.

## develop
Start the ui `cd ui && yarn install && yarn start` then start the webservice `./gradlew run`.

Create a `user.gradle` containing the following:

```gradle
ext {
    adobeApiKey = "YOUR_ADOBE_API_KEY"
}
```

