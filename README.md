Привет! Меня зовут Феодор Маслов, и я являюсь Android-разработчиком, специализирующимся на использовании Kotlin. В данном репозитории представлен мой проект, в котором я разработал все - от иконок и дизайна до логики приложения. Этот проект отражает мой опыт и навыки в области разработки приложений для Android.

# Appetit

Appetit - это приложение, которое позволяет пользователям искать рецепты, получать случайные и сохранять свои любимые. Это приложение демонстрирует мои навыки в следующих областях:

### Работа с сетью
Я использовал Retrofit для выполнения HTTP-запросов к API Edamam и получения данных о рецептах. Из-за специфики API (оно выдает рецепты только на английском языке) я решил весь интерфейс сделать тоже на английском. 

### База данных
Я использовал Room для сохранения избранных рецептов пользователя в локальной базе данных. Это позволяет пользователям сохранять и быстро получать доступ к своим любимым рецептам.

### Dependency Injection (DI)
Я использовал Hilt для управления зависимостями в приложении, что повышает модульность и тестируемость кода. Это также упрощает управление жизненным циклом зависимостей и делает код более чистым и поддерживаемым.

### Чистая архитектура
Я применил принципы чистой архитектуры, разделив приложение на несколько слоев: presentation, domain и data. Это обеспечивает высокую масштабируемость, тестируемость и поддерживаемость кода. 
- **Presentation Layer**: Содержит фрагменты, активности и адаптеры, отвечающие за отображение данных пользователю.
- **Domain Layer**: Включает use cases и бизнес-логику.
- **Data Layer**: Содержит репозитории и источники данных.

### Читабельность кода
Я уделил особое внимание написанию читаемого и понятного кода, используя осмысленные имена переменных и методов, следуя принципам чистого кода. Каждый метод выполняет одну задачу, что делает код более понятным и легким в поддержке.

### Особенности приложения
- **Поиск рецептов**: Пользователи могут искать рецепты по ключевым словам и фильтрам, таким как тип кухни, тип блюда и здоровье.
- **Случайные рецепты**: Пользователи могут получать случайные рецепты, встряхивая устройство, что добавляет элемент игры.
- **Избранные рецепты**: Пользователи могут сохранять рецепты в избранное и быстро получать к ним доступ через отдельный раздел приложения.

### Персонализация
Я использовал Shared Preferences для сохранения настроек пользователя, таких как тема приложения (светлая/темная). Это позволяет сохранять пользовательские предпочтения даже после перезапуска приложения.

*Сборка стандартная: (Build -> Build App Bundle(s) / APK(s) -> Build APK(s))*

Я надеюсь, что этот проект продемонстрирует мои навыки и опыт в разработке Android-приложений с использованием Kotlin. Буду рад ответить на любые вопросы или обсудить дальнейшие детали. Спасибо за рассмотрение моей кандидатуры!

*************************

Hello! My name is Feodor Maslov, and I am an Android developer specializing in Kotlin. This repository showcases my project, where I developed everything from icons and design to application logic. This project reflects my experience and skills in Android app development.

# Appetit

Appetit is an application that allows users to search for recipes, get random ones, and save their favorites. This app demonstrates my skills in the following areas:

### Networking
I used Retrofit to perform HTTP requests to the Edamam API to fetch recipe data. Due to the nature of the API (it only provides recipes in English), I decided to make the entire interface in English as well.

### Database
I used Room to save users' favorite recipes in a local database. This allows users to save and quickly access their favorite recipes.

### Dependency Injection (DI)
I used Hilt to manage dependencies in the application, enhancing code modularity and testability. This also simplifies dependency lifecycle management and makes the code cleaner and more maintainable.

### Clean Architecture
I applied clean architecture principles, dividing the application into several layers: presentation, domain, and data. This ensures high scalability, testability, and maintainability of the code.
- **Presentation Layer**: Contains fragments, activities, and adapters responsible for displaying data to the user.
- **Domain Layer**: Includes use cases and business logic.
- **Data Layer**: Contains repositories and data sources.

### Code Readability
I paid special attention to writing readable and understandable code, using meaningful variable and method names, and following clean code principles. Each method performs one task, making the code more understandable and easier to maintain.

### Application Features
- **Recipe Search**: Users can search for recipes by keywords and filters such as cuisine type, dish type, and health.
- **Random Recipes**: Users can get random recipes by shaking the device, adding a playful element.
- **Favorite Recipes**: Users can save recipes to favorites and quickly access them through a separate section of the app.

### Personalization
I used Shared Preferences to save user settings such as app theme (light/dark). This allows user preferences to be retained even after restarting the app.

*Standard build: (Build -> Build App Bundle(s) / APK(s) -> Build APK(s))*

I hope this project demonstrates my skills and experience in developing Android applications using Kotlin. I am happy to answer any questions or discuss further details. Thank you for considering my application!
