# Launcher Lab

Launcher Lab is a sample Android application that demonstrates how to dynamically change an app’s launcher icon at runtime. It showcases a few **Jetpack Compose** features—such as modal bottom sheets, sliders, and lazy lists—while maintaining a modern and visually appealing UI with a **gradient background**.

## Table of Contents

1. [Overview](#overview)  
2. [Features](#features)  
3. [Implementation Details](#implementation-details)  
4. [Getting Started](#getting-started)  
5. [Usage](#usage)  
6. [Preview](#preview)  
7. [Contributing](#contributing)  
8. [License](#license)

---

## Overview

**Launcher Lab** uses multiple activity aliases, each mapped to a different icon, and toggles between them at runtime. This approach allows your app’s icon to switch without requiring users to reinstall or clear data.

---

## Features

1. **Dynamic Icon Switching**  
   Change the app’s launcher icon from within the app using multiple activity aliases.

2. **Modal Bottom Sheet**  
   A bottom sheet (with Android’s Material 3 design) confirms changes before applying them.

3. **Lazy Column Grid**  
   Displays available icons in a scrollable list that you can easily extend or customize.

---

## Implementation Details

1. **Activity Aliases**  
   - Defined in the `AndroidManifest.xml`.
   - Each alias has its own `themeName` and custom icon (`ic_launcher`, `my_launch_icon_one`, etc.).

2. **AppLauncherIcons Enum**  
   - An enum class maps each theme/alias to a corresponding icon resource.

3. **Switching Icons**  
   - The app retrieves the current alias from a shared preference (`CURRENT_ICON_KEY`).
   - If the user selects a new icon, the app:
     - Enables the newly selected alias via `PackageManager.setComponentEnabledSetting`.
     - Disables or resets the previously used alias.
     - Updates the saved preference.

4. **Jetpack Compose UI**  
   - **Scaffold** is used for the main layout.
   - **Slider** adjusts icon size.
   - **LazyColumn** lists available icons.
   - **ModalBottomSheet** confirms changing the launcher icon.

---

## Upcomng Feature

App icon change remotely.

## 🚀 About Me
Hi! My name is Shivangi Mundra, I work as a Software Developer and like to expand my skill set in my spare time.

If you have any questions or want to connect, feel free to reach out to me on :

- [LinkedIn](https://www.linkedin.com/in/shivangi-mundra-9a31b65b/)
