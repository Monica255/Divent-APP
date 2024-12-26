# Divent - Dicoding Event App

Divent is an Android app that provides information about upcoming and finished events from Dicoding, a popular learning platform offering courses and seminars. The app uses real API data from Dicoding and presents it in a user-friendly interface with features such as dark mode, event search, and daily reminders.

## Features
- **Home**: Displays upcoming and finished events.
- **Upcoming Events**: A list of upcoming events with a search bar to filter events.
- **Finished Events**: A list of finished events with a search bar to filter events.
- **Dark Mode**: Toggle dark mode for a better user experience, using DataStore for persistence.
- **Daily Reminder**: Set reminders for events using WorkManager.
- **Clean Architecture**: Follows a clean architecture pattern for maintainability and scalability.
- **Animations**: Utilizes Lottie for smooth animations.

## Screenshots
Here are some screenshots of the app, including dark mode:

### Light Mode
![Home Screen - Light Mode](path_to_light_mode_screenshot.png)

### Dark Mode
![Home Screen - Dark Mode](path_to_dark_mode_screenshot.png)

---

## Technologies Used
- **Language**: Kotlin
- **Architecture**: Clean Architecture
- **Libraries**:
  - **RxJava**: For reactive programming.
  - **Hilt**: For dependency injection.
  - **DataStore**: For storing settings like dark mode preference.
  - **Retrofit**: For making network requests and fetching data from the Dicoding API.
  - **WorkManager**: For handling background tasks like daily reminders.
  - **Room Database**: For local caching of event data.
  - **Lottie**: For adding animations.
  - **Navigation Component**: For navigation between screens.
  - **Bottom Navigation**: For app navigation.
  - **DiffUtil**: For efficient list updates in RecyclerViews.
  - **Coroutines**: For managing asynchronous operations.

---

## Conclusion
Divent provides an efficient and easy-to-use interface for accessing and managing Dicoding events, with thoughtful features like dark mode, reminders, and smooth navigation. Built using modern Android technologies and clean architecture, this app ensures a robust, scalable, and enjoyable user experience. We hope you enjoy using Divent and stay updated with your learning journey through Dicoding events!