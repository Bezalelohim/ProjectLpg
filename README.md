
# ProjectLPG

ProjectLPG is an Android application that connects and manages IoT-enabled LPG cylinders, providing real-time monitoring and configuration capabilities over Wi-Fi. The app offers a user-friendly interface for monitoring gas levels, configuring device settings, and receiving alerts on various metrics.

## Features

- **Real-Time Monitoring**: Displays current LPG levels in a visual format using a custom gas cylinder graphic.
- **Multiple Device Management**: Allows users to add, configure, and manage multiple LPG cylinder devices.
- **Wi-Fi Connectivity**: Facilitates communication and data synchronization over a local Wi-Fi network.
- **Alert Notifications**: Sends notifications when LPG levels reach predefined thresholds.
- **Profile Management**: Users can create and manage profiles for different devices or setups.

## Technologies Used

- **Android Studio**: Primary IDE for app development.
- **Kotlin**: Main programming language.
- **Jetpack Compose**: Used for building native UI components.
- **Room Database**: Manages local database for device and configuration data.
- **Hilt-Dagger**: Provides dependency injection to manage dependencies across the app.
- **Kotlin Flows and LiveData**: Handles asynchronous data streams and updates to the UI.

## Getting Started

### Prerequisites

Ensure you have the latest version of Android Studio installed, along with the Android SDK, Tested on Android 12 and above. (As per February 2024)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Bezalelohim/ProjectLpg.git
   ```
2. Open the project in Android Studio.
3. Build the project by navigating to `Build > Make Project`.

## Usage

1. Launch the app on your Android device or emulator.
2. Connect to your local Wi-Fi network.
3. Use the "Scan" button on the Devices screen to detect new IoT LPG cylinders.
4. Add and configure new devices as they appear.
5. Monitor gas levels from the Home screen.

