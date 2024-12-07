# Forvia Technical Challenge

### Overview
This is a simple Android app that fetches and displays a list of applications from Aptoide's API using Retrofit2 and Coroutines. The app features an "App Details" screen, a simulated download dialog, 30-minute notifications using WorkManager, and offline support with Room for local data caching. Built with MVVM clean architecture , Unit testing Compose, I enjoyed a lot while developing this project and solving challenges. I also implemented the bonus task by integrating notifications and local data caching features.

### Environment Requirements

To run this project, you need the following:

- Android Studio LadyBug or later.
- Android SDK with API level 28 or higher.
- Kotlin version 2.0.0 or later.
- Gradle version 8.9.
- Jetpack Compose BOM Version: 2024.04.01
- Pixel emulator

### Getting Started

1. Clone this repository to your local machine.
2. Open the project in Android Studio LadyBug or later.
3. Build the project to download dependencies.
4. Run the app on an emulator or physical device.
5. You can get the APK file for immediate testing in the folder latest.
6. https://drive.google.com/file/d/1wa1oCWoXh2PkAKphjqxvh1QF16RJaLrG/view?usp=sharing You can download APK via this link.
  
# Used Technologies and Design Patterns

### Architecture
### MVVM (Model-View-ViewModel)
The project follows the MVVM architecture pattern, which separates the user interface logic from the business logic and data handling.
The project adopts Clean Architecture principles to maintain separation of concerns and improve testability, scalability, and maintainability.
The objective is to separate the responsibility, make it testable, and avoid any strong dependencies on UI, frameworks, and databases. We could change a dependency smoothly without affecting the whole structure. we should follow the Separation of Concerns. It helps us to have an efficient application design by separating logic, data, and UI.
-  A reactive and layered architecture.
- Unidirectional Data Flow (UDF) in all layers of the app.
- A UI layer with state holders to manage the complexity of the UI.
- Coroutines and flows.
- Dependency injection best practices.

### Dependency Injection Hilt
Hilt is used for dependency injection, providing a standard way to manage dependencies and simplify testing.

### Mockito
Mockito is used for mocking objects in unit tests, allowing isolated testing of individual components.

### Turbine
I used testing library for Kotlin Flows, making it easier to test and verify the emissions from a Flow.

### JUnit 4
JUnit 4 is the testing framework used for writing and executing unit tests.

### State Flow for View State Management
State Flow is used to manage view state changes in the project. It provides a reactive way to update UI components based on changes in the application state.

### Channel Flow
Handle one-time single events, such as error messages from the API. This approach ensures that error messages are displayed only once and not repeated on configuration changes.

### Room (Local data base)
Room is used for local data storage, providing offline support and efficient data management.

### Work Manager
Work Manager is utilized to schedule periodic notifications, ensuring tasks are executed reliably even if the app is not running.

### Retrofit 
Retrofit is employed for network requests, simplifying the process of fetching data from the Aptoide API.

### Permission 
Runtime Permissions for notification.

### Kotlin Coroutine.
Kotlin Coroutines are used for asynchronous programming, making the code more readable and efficient by handling background tasks seamlessly.

### SSOT (Single Source of Truth) Principle.
I used this principle to handle cache logic via local database with the help of flow local and remote data.

### SOLID Principles.
- Every layer data , domain and presentation seperate to each other. Every Single responsibility , with the help of interface or abstract classes in repository pattern e.g respositoryImpl follow open extension close for 
  modification principle.
- Liskov Substitution Principle child class like our usecases or repositoryImpl doesnot break the parent class functionailty.
- In this app there is no any inteferce segeration not huge 8 to 9 interfaces methods. I make them smaller and more specific interface abstract methods.
- Dependency Inversion principle used by Hilt. ensure that high-level modules do not depend on low-level modules, but both depend on abstractions. This approach, combined with Clean MVVM architecture and Hilt for dependency 
  injection, enhances modularity, testability, and maintainability.

### DRY (Dont Repeat Yourself) Principle.
I followed the DRY principle to avoid code duplication, ensuring the codebase is more maintainable and easier to update.

### Kotlin Serializable
From sending data from one screen to other screen.

### Extension Functions
Extension functions are utilized to add functionality to existing classes without modifying their code, enhancing code readability and reusability.

### Jetpack Compose
- Utilized for building the user interface, its modern approach to UI development.
- Integrated Material 3 Design components and guidelines to ensure a consistent and visually appealing UI.
- Dark theme or Light theme (Depend on the user device theme)
- Leveraged the Animation API in Jetpack Compose to add delightful animations and transitions to the UI, enhancing user experience.
- Utilized Jetpack Compose Metrics to gather insights into UI performance and user interactions, aiding in optimization and refinement of the application.
- Routing via Type-safe Navigation (Jetpack compose type-safe navigation library).

### Known Bugs / Issues
1. WorkManager testing: There is no comprehensive test implemented for WorkManager-related background tasks.

### What I Would Improve Given More Time
1. I will write integration tests for WorkManager to ensure that background tasks, such as periodic notifications, are functioning correctly. Since WorkManager cannot be directly unit tested on the host machine, these tests will   
   be executed on an emulator or real device to verify behavior across various states.
2. UI Screen Testing: Implement robust UI testing to cover the user flow, ensuring all screens and interactions (e.g., navigation, API response handling, and offline caching) are working as expected.
3. I will improve my current ResultWrapper class by implementing inline extension functions for error handling and data transformation. These changes will allow for cleaner, more concise, and reusable success/error handling logic using functional paradigms.
Inline Success & Error Handling:
Using inline functions like onSuccess, onError, and map, I will streamline result processing by allowing transformation and side-effects without unnecessary boilerplate code. 
