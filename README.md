# Tudoong

👉 **Download or clone from GitHub**: [https://github.com/your-org/tudoong](https://github.com/qoreh94/Tudoong)

Tudoong is a simple and intuitive Android app that helps users manage their daily tasks and routines.  
Built with Jetpack Compose, Hilt, and Room.

## 🧩 Tech Stack

- **Jetpack Compose** – Declarative UI
- **Hilt** – Dependency Injection
- **Room** – Local database (Entity, DAO, TypeConverter)
- **ViewModel & UI State** – Lifecycle-aware state management
- **Kotlin** – Primary language
- **Glance** – App widget with Jetpack Glance
---

## 📁 Project Structure

com.chch.tudoong
├── application/              // TudoongApplication (Hilt entry)
├── di/                       // Hilt module: DatabaseModule
├── data/
│   ├── repository/           // TudoongRepository
│   └── local/                // Room: Database, DAO, Entity, TypeConverter
├── domain/
│   └── model/                // Business model: TodoType
├── presentation/
│   ├── viewmodel/            // TudoongViewModel (UI state & logic)
│   └── ui/                   // Compose UI components and theme
├── widget/                   // Glance-based home screen widget
├── utils/                    // Date/time and formatting utilities
---

## 🗂️ Modules & Features

- **TudoongApplication**: Initializes Hilt for dependency injection across the app.
- **DatabaseModule**: Provides Room database, DAO, and TypeConverters as Hilt singletons.
- **TudoongDatabase**: Central Room DB holding todo-related entities and access objects.
- **TudoongRepository**: Data access layer that abstracts database operations.
- **TodoEntity / TodoDao**: Defines Room schema and DAO interfaces for to-do items.
- **TodoType**: Enum representing the category/type of to-do tasks.
- **TudoongViewModel**: Controls UI state with `StateFlow`, handles business logic (e.g. toggling tasks, updating reset time).
- **Composable UI**: Includes components like `CheckableRow`, `AnimatedCheckBox`, `SettingsPopover`, and `ResetTimeDialog`.
- **Theme**: App-wide styling with `Color.kt`, `Type.kt`, `Theme.kt` for consistent appearance.
- **Glance Widget**: Implements a home screen widget using Jetpack Glance (e.g., `TudoongAppWidget`, `TudoongWidgetContent`).
- **Utility Functions**: Extensions and formatters to support date/time display and logic.