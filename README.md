# UsersListApp


## Architecture

The app is built using **Clean Architecture** and **MVI (Model-View-Intent)** pattern:

- **Domain Layer:**  
  - UseCases for business logic
  - Entity for UI Data 

- **Data Layer:**  
  - Repository  handles data fetching  
  - Remote API interface using **Retrofit + Gson**  
  - Mappers convert ResponseModels to Entity 

- **Presentation Layer:**  
  - Jetpack Compose UI  
  - ViewModels manage **StateFlow and Shared Flow** for UI state  
  - ViewIntent for User Actions
  - ViewState for UI State
  - ViewEffect for One-Time Effects  

---

## Technologies Used

- **Kotlin**  
- **Jetpack Compose**  
- **Retrofit + Gson** for network calls  
- **Dagger Hilt** for dependency injection  
- **Kotlin Coroutines + Flow** for asynchronous programming  
- **StateFlow and SharedFlow** for reactive UI state management  
- **Clean Architecture + MVI**  
- **JUnit + MockK + Truth + Turbine** for unit testing  

---

Demo

## Demo

![Demo](https://github.com/ghanshyam12312/UserListApp/blob/main/demo/ANZUserListAppDemo.gif)
