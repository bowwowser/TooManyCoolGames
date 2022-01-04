
## App Overview

### Brief Summary

**TMKG** allows the user to keep track of video games and their releases, as well as their personal
status and notes relating to the game, in order to facilitate decisions with their day-to-day gaming
experience.

### Extended Summary

**TMKG** allows the user to:

* Search through one or more online APIs in order to effectively find desired game listings for further info
* View details about a specific game, including personal notes
* Keep track of a list of games in an easily scannable & parsable view, making it able to review

### Package Overview

Top-Level Domain: `com.example.toomanycoolgames`

| Name | Description | 
| --- | --- | 
| `.data` | Data layer code for fetching, caching, and storing game-related info (info fetched from API, personal notes, etc.) | 
| `.di` | Dependency injection layer; simplifies in-app dependency management | 
| `.ui` | UI top-layer package, for main activity and shared UI code |
| `.ui.home` | UI layer code related to home screen for viewing tracked games |
| `.ui.info` | UI layer code related to info screen for viewing detailed info for a specific game |
| `.ui.search` | UI layer code related to search screen for finding game info on API through filters, sorting, and other means |

### Libraries Used

#### Core / Data

| Name | Description | Link |
|------|-------------|------|
| Android Jetpack | support for incorporating best practices & backwards compatibility | [link](https://developer.android.com/jetpack) |
| • Room | persistent data management; robust abstraction over SQLite | [link](https://developer.android.com/jetpack/androidx/releases/room) |
| • Lifecycle | lifecycle-aware components for managing UI state & interactions | [link](https://developer.android.com/jetpack/androidx/releases/lifecycle) |
| • Navigation | handles app navigation through destinations & actions | [link](https://developer.android.com/jetpack/androidx/releases/navigation) |
| • KTX | Kotlin extensions providing idiomatic interactions with Android APIs | [link](https://developer.android.com/kotlin/ktx)
| Dagger Hilt | dependency injection tailored for Android application components | [link](https://dagger.dev/hilt/) |
| IGDB API JVM | JVM wrapper for IGDB's game data API | [link](https://github.com/husnjak/IGDB-API-JVM) |

#### UI / UX
| Name | Description | Link |
|------|-------------|------|
| Material Components | official UI components providing a cohesive Material Design style | [link](https://material.io/develop/android/docs/getting-started) |
| RecyclerView | container for efficiently displaying large sets of data | [link](https://developer.android.com/jetpack/androidx/releases/recyclerview) |
| ConstraintLayout | container for flexible positioning & sizing of UI elements | [link](https://developer.android.com/jetpack/androidx/releases/constraintlayout) |
| ViewPager2 | container for efficiently paging through view/fragment components | [link](https://developer.android.com/jetpack/androidx/releases/viewpager2) |
| Glide | fast & flexible media management/image loading framework | [link](https://github.com/bumptech/glide) |

#### Development
| Name | Description | Link |
|------|-------------|------|
| dotenv-kotlin | port of Ruby `dotenv` project; load envars from a file | [link](https://github.com/cdimascio/dotenv-kotlin) |
| Result | lightweight Kotlin framework for managing operation success/failure | [link](https://github.com/kittinunf/result) |

#### Testing
| Name | Description | Link |
|------|-------------|------|
| JUnit 4 | standard Java testing framework with high level of support and compatibility | [link](https://junit.org/junit4/) |
| AndroidX Test | Jetpack libraries for writing & running all types of Android tests | [link](https://developer.android.com/jetpack/androidx/releases/test) |
