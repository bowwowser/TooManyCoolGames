# Too Many Cool Games [TMKG] (tentative)

Help keep track of all the cool games being released!

## Building

Build the app:

```shell
./gradlew build
```

Run all tests:

```shell
./gradlew test
```

Run lint checks:

```shell
./gradlew check
```

## App Overview

### Brief Summary

**TMKG** allows the user to keep track of video games and their releases, as well as their personal
status and notes relating to the game, in order to facilitate decisions with their day-to-day gaming
experience.

### Extended Summary

**TMKG** allows the user to:

* Search through one or more online APIs in order to effectively find desired game listings for
  further info
* View details about a specific game, including personal notes
* Keep track of a list of games in an easily scannable & parsable view, making it able to review

### Architecture Diagram

```
IN PROGRESS
```

### Package Overview

TLD: `com.example.toomanycoolgames`
| Name | Description | | --- | --- | | `.data` | Data layer code for fetching, caching, and storing
game-related info (info fetched from API, personal notes, etc.) | | `.di` | Dependency injection
layer; simplifies in-app dependency management | | `.ui` | UI top-layer package, for main activity
and shared UI code | | `.ui.home` | UI layer code related to home screen for viewing tracked games |
| `.ui.info` | UI layer code related to info screen for viewing detailed info for a specific game |
| `.ui.search` | UI layer code related to search screen for finding game info on API through
filters, sorting, and other means |

