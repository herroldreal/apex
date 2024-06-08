# Rick And Morty

Rick and Morty demonstrates modern Android development with Koin, Coroutines, Flow on MVVM
architecture.

# Rick And Morty Compose Version

### [Version of this app made with Jetpack Compose ](https://github.com/herroldreal/apex)

## Projects Features

- Kotlin based [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + Flow for asynchronous.
- Clean Architecture (at module level)
- MVVM (Model, View, ViewModel)
- Compose
- [Koin](https://insert-koin.io/docs/quickstart/android-compose/) - A framework to help you build
  any kind of Kotlin & Kotlin Multiplatform application, from Android mobile, Multiplatform apps to
  backend Ktor server applications Koin
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The
  Paging library makes it easier for you to load data incrementally and gracefully within your app's
  UI
- [Navigation](https://developer.android.com/guide/navigation)
- [Retrofit](https://github.com/square/retrofit) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android, Java and Kotlin
- [Timber](https://github.com/JakeWharton/timber) - Timber is a logger with a small, extensible API
  which provides utility on top of Android's normal Log class.
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin
  Coroutines.

# Demo

![Demo-Apex](https://media3.giphy.com/media/GpHUnLsnDrCVEptUuI/giphy.gif)

## Open API

Rick And Morty using [Rick And Morty API](https://rickandmortyapi.com/)

# Purpose of the project

I have developed an application based on the Clean Architecture, where I have separated each layer
into different modules to adhere to the Clean principle, which dictates a dependency solely on the
immediately lower layer. This approach has allowed me to craft small, concise, clean, reusable,
scalable, and easily testable code snippets for each layer of the application.

To work with Jetpack Compose, I have chosen to adopt an architecture based on '
Screen-Route-Coordinator.' This approach, which I have been exploring recently, effectively
decouples view components, making them more usable and easy to test. I am attaching the
documentation that served as the foundation for this approach.

[Link to the documentation](https://levinzonr.github.io/compose-ui-arch-docs/)
