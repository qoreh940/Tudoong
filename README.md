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

---

## 📁 Project Structure

com.chch.tudoong
├── application/              // TudoongApplication (Hilt)
├── di/                       // Hilt modules (DB/DAO binding)
├── data/
│   ├── repository/           // Data access layer
│   └── local/                // Room database, DAO, Entity, Converters
├── domain/                   // Business models
├── presentation/
│   ├── viewmodel/            // ViewModel
│   └── ui/                   // Compose UI components and theme

---

## 🗂️ Modules & Features

- **TudoongApplication**: Hilt setup
- **TudoongDatabase**: Room DB with multiple DAOs
- **TudoongViewModel**: Manages UI state and business logic
- **Composable UI**: Animations, checkboxes, and custom components