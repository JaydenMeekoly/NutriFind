---

# NutriFind – Intelligent Nutrition and Recipe Assistant

NutriFind is a mobile application developed to support healthier eating habits through smart recipe discovery, nutritional analysis, and tailored dietary guidance. It integrates third-party APIs, offline capabilities, and gamified features to deliver a comprehensive and engaging user experience.

---

## Key Features

### Recipe Discovery

- Users can explore recipes based on ingredients, cuisine styles, or dietary restrictions.
- Recipe data is sourced from the Spoonacular API, ensuring a wide variety and current content.

### Nutritional Analysis

- Each recipe includes a detailed nutritional profile, covering calories, macronutrients, and essential vitamins.
- Nutritional data is presented using visual aids such as charts and progress indicators for ease of interpretation.

### Secure Access

- Supports Google Single Sign-On (SSO) for streamlined authentication.
- Offers biometric login options (fingerprint) for quick and secure access. (coming soon)

### Customized User Profiles

- Users can specify dietary preferences (e.g., vegan, gluten-free, low-carbohydrate).
- The app provides personalized recipe suggestions and nutrition targets based on these preferences.

### Offline Availability and Synchronization

- Saved recipes and user settings are accessible without an internet connection.
- Any changes made offline are automatically synchronized once connectivity is restored. (coming soon)

### Notifications (coming soon)

- Utilizes Firebase Cloud Messaging (FCM) to deliver personalized alerts.
- Notifications include updates on new recipes, nutritional advice, and reminders for dietary goals.

### Engagement and Gamification

- Tracks user progress through healthy eating streaks and awards achievement badges. (coming soon) 
- Designed to encourage regular app usage and sustained engagement.

---

## Functional Specifications

### 1. Authentication

- Implements Google SSO via Firebase Authentication.
- Supports biometric login methods (fingerprint).
- Session tokens enable automatic login for returning users.

### 2. Recipe Storage

- Uses RoomDB for local storage to support offline access.
- Recipes are synchronized across devices via cloud services.
- Background synchronization ensures data consistency when connectivity resumes.

### 3. Recipe Search

- Users can search recipes by ingredient, meal category, preparation time, or dietary needs.
- Spoonacular API is used to retrieve recipe and nutrition data.
- Results include images and summary cards for efficient browsing.

### 4. Nutritional Information

- Displays key nutritional metrics such as calories, protein, fat, carbohydrates, and vitamins.
- Information is visualized using bar and pie charts.

### 5. Offline Support

- Recently viewed recipes are cached locally.
- Actions taken offline are queued and synced later.
- Managed through RoomDB and background sync mechanisms.

### 6. Notifications

- Integrated with Firebase Cloud Messaging.
- Users can customize notification settings to enable or disable alerts.

### 7. Multilingual Support

- Available in English, isiZulu, and Afrikaans. (maybe)
- Language can be switched dynamically using `strings.xml`.

### 8. Settings Interface

Includes configuration options for:

- Light and dark themes
- Language selection
- Notification preferences
- Biometric authentication toggle

### 9. Gamification

- Tracks user engagement and awards badges such as “Healthy Starter” and “Consistency King/Queen.”
- Progress is stored and synced with the user’s backend profile.

---

## System Architecture Overview

| Layer              | Description                                                       |
|--------------------|-------------------------------------------------------------------|
| Presentation Layer | Built using Android XML layouts and optionally Jetpack Compose.   |
| Data Layer         | Employs RoomDB for local caching and Retrofit for API communication. |
| Business Logic     | Handles core operations such as recipe filtering and streak tracking. |
| Backend Services   | Combines Firebase (Authentication and Messaging) with Spoonacular API. |

---

## Technology Stack

| Component              | Technology                  |
|------------------------|-----------------------------|
| Programming Language   | Kotlin                      |
| Architecture Pattern   | MVVM (Model-View-ViewModel) |
| Local Database         | Room (SQLite)               |
| Networking             | Retrofit with Gson          |
| Authentication         | Firebase Authentication     |
| Notifications          | Firebase Cloud Messaging    |
| Data Visualization     | MPAndroidChart or equivalent|
| Offline Storage        | Room and SharedPreferences  |
| External API           | Spoonacular API             |

---

## Project Directory Structure

```
NutriFind/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/nutrifind/
│   │   │   │   ├── ui/           # Activities and Fragments
│   │   │   │   ├── viewmodel/    # ViewModel classes
│   │   │   │   ├── data/         # Repository and RoomDB
│   │   │   │   ├── network/      # Retrofit API service
│   │   │   │   └── utils/        # Utility classes and constants
│   │   │   ├── res/              # Layouts, drawables, and string resources
│   │   │   └── AndroidManifest.xml
│   └── build.gradle
├── README.md
└── gradle.properties
```

---

## Installation and Setup Instructions

1. Clone the repository:

   ```bash
   [git clone (https://github.com/JaydenMeekoly/NutriFind.git)]
   cd NutriFind
   ```

2. Open the project in Android Studio and allow Gradle to synchronize.

3. Add API credentials:

   - Obtain a Spoonacular API key from [spoonacular.com/food-api](https://spoonacular.com/food-api).
   - Insert the key into `local.properties` or `gradle.properties`:

     ```
     SPOONACULAR_API_KEY=your_api_key_here
     ```

4. Configure Firebase:

   - Download `google-services.json` from the Firebase Console.
   - Place the file in the `/app/` directory.

5. Launch the application on an emulator or physical device.

---

## Development Approach

- Follows Agile methodology with iterative feature development and testing cycles.
- Includes unit and instrumentation tests to verify core functionality.
- UI testing ensures smooth operation of recipe browsing, login, and offline synchronization.

---

## Planned Enhancements

- Integration of AI-driven meal planning.
- Social features for recipe sharing and community ratings.
- Compatibility with wearable fitness devices.
- Multi-Language mode
- Voice-enabled search and improved accessibility features.

---

## References

- Anon. (2022). *Spoonacular API Documentation*.
- Anon. (2025). *Firebase Cloud Messaging and Google Authentication Guide*.

---
