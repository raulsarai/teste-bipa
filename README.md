# Lightning Node Browser App - Bipa Test

## Project Overview

This is a Lightning Network node browser Android app built in Kotlin using Jetpack Compose. It fetches and displays the top Lightning nodes from the public Mempool.space API, allowing users to search, filter, sort, and view node details such as alias, country, number of channels, capacity, first-seen timestamp, and public key.

---

## Build Tools & Versions Used

* **Kotlin**: 2.0.21
* **Android Gradle Plugin**: 8.9.3
* **Gradle**: 8.11.1
* **Jetpack Compose**: 2.9.0
* **Material3**: 1.3.2
* **Retrofit**: 2.9.0
* **Gson Converter**: 2.10.1
* **Koin**: 3.5.3
* **Kotlin Coroutines**: 1.7.3
* **Accompanist SwipeRefresh**: 0.24.13-rc

---

## Steps to Run the App

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/lightning-node-browser.git
   cd lightning-node-browser
   ```
2. **Open in Android Studio** and let Gradle sync.
3. **Connect** an Android device or start an emulator.
4. Click **Run** (green ▶️) in Android Studio to build and install.

---

## Steps to Run Tests

* **Unit tests**:

  ```bash
  ./gradlew test
  ```
---

## Focus Areas

* **Clean Architecture**: Separation of data, domain, and presentation layers.
* **Dependency Injection**: Koin for wiring Retrofit, repositories, and ViewModels.
* **Composable UI**: Responsive layouts with Jetpack Compose (LazyColumn, SwipeRefresh).
* **Filtering & Sorting**: Multi-criteria filtering (alias, country, date, capacity) and dynamic sorting.
* **Error Handling**: Network-state checks and graceful error screens with retry logic.
* **Testing**: Unit tests for repository mapping.

---

## Purpose & Problems Solved

* Ensure a **maintainable** and **testable** codebase by decoupling layers with DI.
* Provide a **smooth UX** with instant filtering, pull-to-refresh, and error feedback.
* Showcase **Jetpack Compose** best practices, including state handling and animations.

---

## Time Spent

\~**6 hours** over a couple of days.

---

## Trade-offs & Future Improvements

* **Trade-offs**:

  * In-memory caching vs. a full local database (Room).
    
* **With more time**:

  * Offline caching and persistence.
  * Pagination for large node lists.
  * Richer animations and transitions.
  * End-to-end tests and CI integration.

---

## Weakest Part

The **offline experience**—without persistence, data is refetched on each launch. A local database would improve UX.

---

## Additional Notes

* Koin was chosen for its simplicity and Compose support.
* All network calls run on `Dispatchers.IO` to keep UI responsive.
* A splash screen feature with logo.
