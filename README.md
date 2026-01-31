# Artseum
<img width="1000" height="392" alt="Artseum branding small and screenshots" src="https://github.com/user-attachments/assets/7f47cec4-a868-4d8d-acfa-83664e71f025" />



**Artseum** is a high-performance, open-source Art Gallery application built with **Kotlin Multiplatform (KMP)**. Unlike many KMP projects that use Compose Multiplatform for the entire UI, Artseum demonstrates a **hybrid architectural approach**: 100% shared business logic and state management with **fully native UIs** (Jetpack Compose for Android and SwiftUI for iOS).

---

## Key Features

* **Shared State Management:** ViewModels, UseCases, and Data layers are 100% shared.
* **Native UI:** Uses Jetpack Compose (Android) with Material3 and SwiftUI (iOS) with Liquid Glass to ensure a native app feel and look.
* **Kotlin-Swift/ObjC Bridge:** Bridging the Kotlin domain logic with Swift/ObjC with the help of SKIE plugin and custom ViewModel Wrappers.
* **Advanced Search:** Real-time search with shared debounce logic and pagination.
* **Interactive Maps:** Native map integration (Apple Maps/Google Maps) with shared location data.

---

## Architecture

Artseum follows **Clean Architecture** principles to maximize testability and separation of concerns.

### The Hybrid KMP Pattern
To achieve a seamless Swift experience, this project utilizes a **ViewModel Wrapper Pattern**. This allows SwiftUI to observe Kotlin `StateFlow` as a native `@Published` or `@ObservableObject` property.


* **Presentation Layer:** Native views (SwiftUI/Compose) observing shared ViewModels.
* **Domain Layer:** Pure Kotlin business logic and UseCases.
* **Data Layer:** Repository pattern handling Network (Ktor).

---

## Tech Stack

| Feature | Technology |
| :--- | :--- |
| **Language** | Kotlin (Shared), Swift (iOS Native) |
| **UI** | SwiftUI, Jetpack Compose |
| **Networking** | Ktor |
| **Dependency Injection** | Koin |
| **Concurrency** | Kotlin Coroutines & Flow |
| **KMP Interop** | SKIE, ViewModel Wrappers |

---

## Screenshots

| Android (Material 3) | iOS (Human Interface) |
| :---: | :---: |
| <img width="242" height="496" alt="Screenshot 2026-01-31 at 13 09 18 1" src="https://github.com/user-attachments/assets/8786e4c7-ebf7-4a12-8ee7-6d108182cb47" /> | <img width="294" height="558" alt="Screenshot 2026-01-31 at 12 42 16 1" src="https://github.com/user-attachments/assets/40d5a8a4-645a-479e-b2dc-b102a9f5f55e" /> |
| <img width="243" height="496" alt="Screenshot 2026-01-31 at 13 10 12 1" src="https://github.com/user-attachments/assets/0d8611cd-79f9-4f23-8ecf-f365ab76ebae" /> | <img width="269" height="533" alt="Screenshot 2026-01-31 at 13 10 10 1" src="https://github.com/user-attachments/assets/50e753d4-37fb-43fc-9096-e80e66f9025a" /> |
| <img width="242" height="496" alt="Screenshot 2026-01-31 at 13 09 52 1" src="https://github.com/user-attachments/assets/841e1535-d77f-4c91-8314-e5beb8e112fc" /> | <img width="269" height="533" alt="Screenshot 2026-01-31 at 13 09 49 1" src="https://github.com/user-attachments/assets/35caa3d2-b643-4b28-84f7-ce1ce9a83672" /> |


---

## Testing

The domain and presentation modules are covered with comprehensive unit tests:
* ViewModel state transitions.

* UseCase logic.
* Mapping logic from Data to Domain entities.
