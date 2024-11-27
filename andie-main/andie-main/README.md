## Licenses
All emojis designed by OpenMoji – the open-source emoji and icon project. License: CC BY-SA 4.0
Accessed from: https://openmoji.org

## What 👋

Welcome to Group A, this repo contains our code for part one of the 'Andie' group project.
The authors are;
1. Hayden Trow
2. Ivan Chevtchenko
3. Josh Lawson
4. Lucy Hadden
5. Shyamalima Shreya Das

#
# Part 1 - COSC202 - Group A

## Exception Handling

For each feature we have implemented exception handling. We have done this by ensuring that each feature 
needs to have an image before the feature is called on the image. 

## Test
1. BrightnessFilterTesting ->
Three different tests have been added to test the brightness filter feature is working correctly. The first test
verifies that the default constructor sets the brightness value to 1. The second test verifies that the BrightnessFilter object is not null when instantiated. Lastly, the third test tests the constructor of BrightnessFilter that takes a given brightness value.

2. ContrastFilterTesting ->
Three different tests have been added to test the brightness filter feature is working correctly. The first test
verifies that the default constructor sets the contrast value to 1. The second test verifies that the ContrastFilter object is not null when instantiated. Lastly, the third test tests the constructor of ContrastFilter that takes a given contrast value.

3. FlipHorizontialTesting ->
2 tests:
test verifies that the default constructer creates a fh object that is not null. 
test verifies that the FlipHorizontal object is not null when instantiated.

4. FlipVerticalTesting -> 
2 tests:
test verifies that the default constructer creates a fv object that is not null. 
test verifies that the FlipVertical object is not null when instantiated.

5. GaussianFilterTesting -> Three different tests have been written to check the functionality of GaussianFilter. The first test in the file is a default constructor test. If the default constructor returns 1, this confirms that the default constructor sets a value of 1. The second test in the file is for ensuring that the input of the test is not null. The final test in the file is for testing the constructor with the value of 50. If this value of 50 is taken in as the value of the radius, this test is passed.


6. ImagePanelTesting -> 2 tests: The first test is to 
check that the zoom of the image is at 100% when it is first opened. The second test tests the change in the values of the zoom after it has been set at 0. The first assertion is for the value of the zoom to have changed from the initial zoom value of 100. The second assertion is to test if the new zoom value is less than 50.

7. MeanFilterTesting -> There are two tests in the MeanFilterTest file. The first test is a default constructor test. If the default constructor returns 1, this confirms that the default constructor sets a value of 1. The second test in the file is for ensuring that the input of the test is not null. 


8. MedianFilterTesting -> Three different tests have been written to check the functionality of MedianFilter. The first test in the file is a default constructor test. If the default constructor returns 1, this confirms that the default constructor sets a value of 1. The second test in the file is for ensuring that the input of the test is not null. The final test in the file is for testing the constructor with the value of 50. If this value of 50 is taken in as the value of the radius, this test is passed.


9. ResizeTesting ->
5 tests:
test that verifies that the default constructor sets the size value to 1
test that verifies that the Resize object is not null when instantiated.
test that verifies that the default constructor sets the size value to a negative value.
test that verifies that the default constructor sets the size value to a positive value.
test that verifies that the default constructor sets the size value to a zero value.

10. SharpenFilerTesting -> Three different tests have been written to check the functionality of SharpenFilter. The first test in the file is a default constructor test. If the default constructor returns 1, this confirms that the default constructor sets a value of 1. The second test in the file is for ensuring that the input of the test is not null. The final test in the file is for testing the constructor with the value of 50. If this value of 50 is taken in as the value of the sharpen, this test is passed.

## Where 🔍
Within this repo we have added the following features:

# Filters 📑:
1. Sharpen filter - Shyamalima Shreya Das
2. Gaussian blur filter - Shyamalima Shreya Das
3. Median filter - Hayden Trow

# Adjustments 📋:
4. Brightness adjustment - Lucy Hadden
5. Contrast adjustment - Lucy Hadden

# Multilanguage support 🌐:
6. Multilingual support - Ivan Chevtchenko

# Transformations 🧭: 
7. Image resize - Josh Lawson
8. Image rotations: 90◦ - Ivan Chevtchenko
9. left; 90◦ - Ivan Chevtchenko
10. right; 180◦ - Ivan Chevtchenko
11. Image flip: Horizontal; Vertical - Josh Lawson

# Image exporting 📦:
12. Image export - Hayden Trow

# Error and exception handling 🎌:
13. Exception handling - group contributions
14. Other error avoidance/prevention - group contributions


#
# Part 2 - COSC202 - Group A
the folloiwng features have been implemented during part 2 of the COSC202 lab book. We have also added our own additional features during this part. These features include a database, preferences menu, sticker menu, help menu and a negative filter operation.

# Database Feature

The database feature provides the following functionalities:

## Login

Upon opening the application, users are presented with a login screen where they can enter their credentials to access the system. The login process verifies the entered credentials against the information stored in the database.

## Create Account

Users who don't have an account can create a new account by providing a unique username and a password. The system checks if the chosen username is available and securely stores the new user's information in the database.

## Skip Login

Users also have the option to skip the login process, allowing them to explore the application without accessing personalized features. This option is useful for users who prefer to browse the application without logging in.

The database stores the following user information:

- **Username**: The unique identifier chosen by the user during account creation.
- **Password**: The password associated with the user's account. Please note that for the purpose of this project, encryption techniques for passwords have not been implemented. Therefore, passwords are stored in plain text format within the database. Please do not use any sensitive information when using this feature.
- **Theme**: The user's selected theme for the andie application. Once a theme has been selected while logged in, the theme will be stored in the database and will be applied to the andie application each time that user logs in.
- **Language**: The user's preffered language. When creating an account, the user is prompted to chose a lanaguage, this is then stored in the database. Each time the user logs in, their preffered langauge is automatically set, rather than having to select a language each time. The user can also change their preffered language within the andie application in the preferences menu option.


# Preferences Feature

The Preferences feature allows users to customize certain aspects of the application according to their preferences. It provides the following functionality:

## Theme Selection

Users can choose a theme for the application interface. The available themes are displayed in a menu, and selecting a theme applies it to the entire application. Themes allow users to personalize the visual appearance of the application according to their preferences. If the user is logged in, their selected theme will be stored in the database and will be applied to the application on each log in.

## Language Selection

Users can select a preferred language for the application. The language options are presented in a menu, and selecting a language changes the application's language settings. This feature enhances accessibility and user experience for users who are more comfortable using a specific language. If the user is logged in, their selected langauge will be stored in a database and will be applied to the application on each log in.

## Usage

To access the Preferences feature, follow these steps:

1. Open the application.
2. Locate the menu bar at the top of the application interface.
3. Click on the "Preferences" menu.
4. A drop-down menu will appear with the available preference options.
5. Select the desired preference option (e.g., "Themes" or "Languages") to further customize the application.

## Theme Selection

The Theme Selection option allows users to change the visual appearance of the application. Follow these steps to select a theme:

1. Open the application and access the Preferences feature as described above.
2. From the drop-down menu, select the "Themes" option.
3. A theme selector will be displayed, presenting various theme options.
4. Choose a theme by clicking on the desired option.
5. The selected theme will be applied to the entire application, updating the visual elements accordingly.

## Language Selection

The Language Selection option enables users to change the language settings of the application. To select a language, follow these steps:

1. Open the application and access the Preferences feature as described above.
2. From the drop-down menu, select the "Languages" option.
3. The available language options will be displayed.
4. Choose the desired language by clicking on the corresponding option.
5. The application will update its language settings to the selected language, providing a localized experience.

Please note that some changes may require restarting the application for the preferences to take effect.


# Help Menu

The Help Menu provides information about various actions and features available in the application. Here is a brief description of each action:

## File Actions

This menu contains actions related to file operations, such as opening, saving, saving as, and exporting images.

- **Keyboard Shortcut**: Ctrl + O (Open), Ctrl + S (Save), Ctrl + A (Save as), Ctrl + E (Export), Ctrl + X (Exit).

## Macro Actions

The Macro menu allows users to record and replay macro actions. It includes options to start recording, stop recording, and replay the recorded macro actions.

- **Keyboard Shortcut**: Ctrl + F2 (Start/Stop Recording), Ctrl + J (Replay Macro).

## Edit Actions

The Edit menu provides options for undoing and redoing operations, cropping images, and drawing shapes on the image.

- **Keyboard Shortcut**: Ctrl + Z (Undo), Ctrl + Y (Redo), Ctrl + C (Crop).

## View Actions

The View menu allows users to zoom in, zoom out, and reset the zoom level to the default 100%.

- **Keyboard Shortcut**: Ctrl + Equals (=) (Zoom In), Ctrl + Minus (-) (Zoom Out), Ctrl + \` (backslash) (Zoom Full).

## Transform Actions

The Transform menu contains actions that affect the display of the image. These actions do not modify the image content. Available actions include rotating the image left, right, or 180 degrees.

- **Keyboard Shortcut**: None specified.

## Filter Actions

The Filter menu includes various image filters such as sharpen, Gaussian blur, and median filter. Each filter provides options for adjusting specific properties.

- **Keyboard Shortcut**: Ctrl + R.

## Colour Actions

The Colour menu offers actions for adjusting image brightness and contrast.

- **Keyboard Shortcut**: Ctrl + F6.

## Sticker Actions

The Sticker menu provides options for adding stickers to the image.

- **Keyboard Shortcut**: Ctrl + F7.

## Preference Actions

The Preference menu allows users to customize application preferences.

- **Keyboard Shortcut**: Ctrl + ; (semicolon).

Please note that some actions have associated keyboard shortcuts for quick access.

Feel free to explore these menus and their actions to enhance your image editing experience.


# Stickers

To enhance your image editing experience, we have added a sticker feature to the application. You can now add stickers to your images.

To access the sticker feature, click on the "Sticker" menu in the application. A window will open displaying various stickers that you can choose from.

To add a sticker to the image, simply click on the desired sticker in the sticker selection window. The sticker will appear on the image, and you can move it around by clicking and dragging. Once the sticker is in the desired position, click again to place the sticker.

Please note that the sticker feature is in sticker mode, which means that other features will be disabled while the sticker feature is active. To exit sticker mode and regain access to other features, simply close the sticker selection window.

We hope you enjoy using the sticker feature to add some fun and creativity to your images!


# Macro Operations

This repository contains the code for the Macro Operations feature implemented during part 2 of the COSC202 lab book. The Macro Operations feature allows users to record and replay macro actions performed on images.

## Files

- `MacroActions.java`: Contains the actions for the Macro menu, including starting and stopping the recording of macro actions and replaying the recorded macro.
- `MacroOpenAction.java`: Represents the action for opening a macro file.
- `StartRecording.java`: Represents the action for starting the recording of macro actions.

## How to Use Macro Operations

1. Open an image: Use the File menu to open an image file.

2. Start recording: Select the "Start Recording" option from the Macro menu or press Ctrl + Q. The application will start recording all the actions performed on the image.

3. Perform actions: Perform various operations on the image, such as applying filters, adjustments, transformations, or any other supported operations.

4. Stop recording: Select the "Stop Recording" option from the Macro menu or press Ctrl + Q again. The application will stop recording and save the recorded macro actions.

5. Replay macro: Select the "Replay Macro" option from the Macro menu or press Ctrl + J to replay the recorded macro actions. The application will automatically perform the recorded actions on the image, reproducing the same modifications.

Please note that the Macro Operations feature allows users to record and replay multiple sets of macro actions for different images.


## Folder Structure 📁

The workspace contains two folders by default, where:
The repository follows a standard folder structure:

- `src`: Contains the source code files.
- `lib`: Stores the dependencies (external libraries).
- `doc`: Contains the JavaDoc HTML files.
- `cosc202/andie`: Contains the main group project files.
- `test/cosc202/andie`: Contains the testing files for each feature. html files

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
