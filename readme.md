✨ Features
1. News Feed

Displays a list of news articles with images, titles, and descriptions
Beautiful card-based UI with rounded corners
Smooth scrolling with LazyColumn
Loading states with CircularProgressIndicator

2. Offline-First Architecture

Automatically caches articles locally using Room database
Works offline - displays cached articles when network is unavailable
Sync strategy: Fetch from network, update cache, display to user

3. Sorting Options
   Three different sort modes accessible via dropdown menu:

Recent: Sort by publication time (newest first)
Popular: Sort by rank/popularity
None: Display in original order

4. Modern UI/UX

Material Design 3 components
Red app bar with white text
Responsive layout that adapts to different screen sizes
Smooth animations and transitions
Custom spacing - first item has extra padding

5. Error Handling

Graceful fallback to cached data on network failure
User-friendly error messages
Retry mechanisms

🛠️ Tech Stack
Core

Kotlin - 100% Kotlin codebase
Jetpack Compose - Modern declarative UI
Coroutines & Flow - Asynchronous programming
StateFlow - Reactive state management

Architecture Components

ViewModel - UI state holder
Room - Local database
Hilt - Dependency injection
Navigation Compose - Screen navigation

Libraries

Kotlinx Serialization - JSON parsing
Coil - Image loading
Material 3 - UI components
KSP - Kotlin Symbol Processing

com.corousalNews.composeuichallenge/
│
├── data/
│   ├── db/
│   │   ├── NewsDatabase.kt          # Room database
│   │   ├── ArticleDao.kt            # Database operations
│   │   └── ArticleEntity.kt         # Room entity
│   │
│   ├── dto/
│   │   └── ArticleDto.kt            # Network DTO
│   │
│   └── repository/
│       └── NewsRepositoryImpl.kt    # Repository implementation
│
├── domain/
│   ├── model/
│   │   └── Article.kt               # Domain model
│   │
│   └── repository/
│       └── NewsRepository.kt        # Repository interface
│
├── di/
│   ├── DatabaseModule.kt            # Room DI
│   └── RepositoryModule.kt          # Repository DI
│
├── presentation/
│   ├── NewsScreen.kt                # Main screen
│   ├── ArticleItem.kt               # Article card
│   ├── DropdownMenuWithSort.kt      # Sort menu
│   └── NewsViewModel.kt             # ViewModel
│
└── AndroidApp.kt                     # Application class