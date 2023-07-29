# Khabri-news app

![Khabri-news app logo](link_to_your_logo.png)

Khabri-news app is an Android application that allows users to access the latest news articles from various categories. It uses the NewsAPI to fetch the news data and offers features like news category selection, search, offline usage, and periodic notifications of the latest news.

## Features

- See the latest news articles from multiple sources.
- Choose news categories to view specific types of news.
- Search for news articles based on keywords.
- Save articles for offline reading.
- Get periodic notifications of the latest news.

## Technology Stack

- Kotlin
- MVVM architecture pattern
- Retrofit for API communication
- Room Database for offline storage
- WorkManager for scheduling notifications
- Firebase Analytics and Crashlytics for app analytics and crash reporting
- Authorization using API key in headers
- Data Binding for UI
- Dependency injection using Dagger Hilt
- Coroutines for asynchronous programming
- LiveData for reactive data observation

## Getting Started

To use this app, you will need a NewsAPI key. Follow these steps to get started:

1. Obtain a NewsAPI key from [NewsAPI website](https://newsapi.org/).
2. Clone the repository: `https://github.com/samir-sayyed/Khabri-news-app.git`
3. Open the project in Android Studio.
4. Inside the `AppConstants.kt` file, assign your NewsAPI key to the `API_KEY` variable.
5. Build and run the app on an Android device or emulator.

## Contributing

Contributions are welcome! If you find any issues or want to enhance the app, feel free to open an issue or submit a pull request. Please make sure to follow the [contribution guidelines](link_to_contributing_guidelines.md).

## License

This project is licensed under the [MIT License](link_to_license_file).

## Acknowledgments

- Thanks to [NewsAPI](https://newsapi.org/) for providing the news data.

## Contact

If you have any questions or suggestions, feel free to contact the team at [sayyedsamir11@gmail.com](mailto:sayyedsamir11@gmail.com).
