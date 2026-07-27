# LanguageSelection
This is a language selector screen that displays a list of available languages and lets the user pick one as the app's active language.


## Project :

Description: Build a language selection screen. It should display a list of available languages and let the user pick one as the app's active language.

Constraints: Use a standard layered architecture with a clear separation of concerns.

Complexity: Basic. Code must compile and run in a standard emulator.


### Approach :

The team is using AI, Claude and ChatGPT, to increase productivity.

I took the approach to use Gemini that is already integrated into my Android Studio.


### Time completion :

1.5 hours → development:  giving prompts to Gemini, analyzing Gemini suggestions, making changes

15 mins   → write up of this README document.


### Tech stack :

UI:             Kotlin, Jetpack Compose

Architecture:   MVVM, Repository pattern

Asynchronicity: Kotlin Coroutines, Kotlin Flows


### Versioning :

Min SDK: 29

Target SDK: 36

Compile SDK: 37

Kotlin Version: 2.2.10

Gradle Version: 9.2.1


### Initial prompt given to Gemini :

Recommend how to modify this application to use MVVM and repository pattern to Build a language selection screen. It should display a list of available languages and let the user pick one as the app's active language. Use a standard layered architecture with a clear separation of concerns. Code must compile and run in a standard emulator.

Use coroutines, suspend functions, and flows as needed to run tasks in background threads.

Updating views should be done on the main thread.

Show the available languages in a LazyColumn.

Once a different language is selected, the app should restart as expected to reflect the new language.

The default selected language is English.

If the selected language is English, the available languages to show in the LazyColumn are in English as such: English, Spanish, Swedish, Finnish

If the selected language is Spanish, the available languages to show in the LazyColumn are in Spanish as such: Inglés, Español, Sueco, Finlandés

If the selected language is Swedish, the available languages to show in the LazyColumn are in Swedish as such: Engelska, Spanska, Svenska, Finska

If the selected language is Finnish, the available languages to show in the LazyColumn are in Finnish as such: Englanti, Espanja, Ruotsi, Suomi

Add a Text above the LazyColumn to display a string reflecting the selected language; the string should use the correct language translation for "Hello, you have selected <language>":

If the selected language is English, then the string should be "Hello, you have selected English."

If the selected language is Spanish, then the string should be "Hola, has seleccionado español."   

If the selected language is Swedish, then the string should be "Hej, du har valt svenska."

If the selected language is Finnish, then the string should be "Hei, olet valinnut suomen kielen."


### What I changed from the code Gemini generated :

#### (1) Updated AndroidManifest:

Added: locales_config.xml (new file) and AppLocalesMetadataService

Why?

a. Lists the languages the app will support.

b. Ensures that AppCompatDelegate can persist and restore language settings when the app restarts to support legacy devices of API level 32 or lower.  The min API level for this app is 29.

c. Ensures the app will be listed in this settings screen specifying the currently selected language.

       Android Preferences → Settings → System → Languages → App Languages

#### (2) Moved the ViewModelProvider logic from MainActivity into the LanguageViewModel with a helper function 'provide(...)'

Why?

Maintains a clear separation between data, business logic, and UI.

a. MainActivity does not need to know how the LanguageViewModel is created.

b. MainActivity just focuses purely on lifecycle and UI setup.


#### (3) Set the background color for current app locale in the list to light gray.

Why?

Gives the user clear indication in the list of the currently selected locale from the other available locales


#### What I thought about but opted not to do :

Change MainActivity's base class to AppCompatActivity to ComponentActivity.

Reason to use AppCompatActivity:

    → a heavier, a subclass of ComponentActivity that has support for legacy XML views, Fragments, and older UI components

Reason to use ComponentActivity:

    → a lightweight base Activity class to create pure Jetpack Compose app

Why decline?

In the build.gradle.kts, the minSdk is set to 29.

Calling AppCompatDelegate.setApplicationLocales(appLocale) to change the application language

    on api level 32 or lower will not work.

The method requires using AppCompatActivity's lifecycle to rebind localized resources,

    including to use the correct localized strings.

# Result:

The Android application remains to be implemented in Jetpack Compose and allowing locale change.

Optional usage of legacy layouts are not needed.

The resulting structure provides a clear separation between data, business logic, and UI.

## Screenshots:

### English is selected

<img width="464" height="461" alt="LanguageSelection_screenshot_English" src="https://github.com/user-attachments/assets/3c07ae5f-f879-44e5-aaef-8df36d8324fa" />

### Spanish is selected

<img width="464" height="461" alt="LanguageSelection_screenshot_Espanol" src="https://github.com/user-attachments/assets/b69c0a86-e4f2-4e8c-a863-c65960d5c0df" />

### Swedish is selected

<img width="464" height="461" alt="LanguageSelection_screenshot_Svenska" src="https://github.com/user-attachments/assets/1448e57c-3ccd-4ac4-839a-deb276fcc988" />

### Finnish is selected

<img width="464" height="461" alt="LanguageSelection_screenshot_Suomi" src="https://github.com/user-attachments/assets/fbbd466e-97da-4aff-9a51-aac6e2c5696b" />

