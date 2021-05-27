# Rock Paper Scissors Classifier
***
This repos Contains an Android App that you can download.

**Download the app APK [here](https://drive.google.com/file/d/1m6T0w9EKBTIoCa7rLa_mqWjsQR7OIw9g/view?usp=sharing)**

The app does not collect your informations. It is a demo app, you will therefore need to allow the app to be installed on your phone.

**DEMO:**
[demo](https://user-images.githubusercontent.com/35850997/119910565-b4b8c500-bf57-11eb-8fe1-729239a4e8cf.mp4)
## Table of Contents
***
1. [Model Transer Learning and Compression](#model-transer-learning-and-compression)
2. [The Android App](#the-android-app)
3. [Build the App APK](#build-the-app-apk)
## Model Transer Learning and Compression
***
In the folder `TransferLearningAndCompression` you will find a `README` file that explain every step you need to run the transer learning and compression part.
## The Android App
The app is made with android studio in **kotlin**. You want to add change to it, install android studio on your machine and load the `RockPaperScissorsApp` folder.

It is a simple app, it only have 1 page named `MainActivity`. The UI is stored in `activity_main.xml` and the code is in `MainActivity.kt`. There is also our model that is load in the `ml` folder.

If you want to change the model:
1. Right-click on the module you would like to use the TFLite model or click on `File`, then `New` > `Other` > `TensorFlow Lite Model`
![right_click_menu](https://user-images.githubusercontent.com/35850997/119832468-7e029080-befe-11eb-8bef-d79b3c252cd7.png)
2. Select the location of your TFLite file.
![impot_tf_lite_model](https://user-images.githubusercontent.com/35850997/119832984-ff5a2300-befe-11eb-9dc5-0bf0ce1b3e6b.png)
3. Click `Finish`.
4. The following screen will appear after the import is successful. To start using the model, copy and paste the code under the `Sample Code` section.
![model_details](https://user-images.githubusercontent.com/35850997/119833372-5a8c1580-beff-11eb-9319-d81e57b64eeb.png)
5. Then in `MainActivity.kt` line 100, change the function that call the model.
![change_model_call](https://user-images.githubusercontent.com/35850997/119833834-b9ea2580-beff-11eb-80dd-665503788162.png)

## Build the App APK
***
If you need to rebuild the app apk, open Andoid Studio:
1. ***Build*** menu
2. ***Generate Signed APK...***
3. Fill in the keystore information (you only need to do this once manually and then let Android Studio remember it)
4. Select ***realese build*** and mark ***V1*** and ***V2*** signature version, then click `finish`
![generate_apk](https://user-images.githubusercontent.com/35850997/119834835-98d60480-bf00-11eb-96e3-91a67a2add5f.png)


Once th build done you will find your new apk in: `RockPaperScissors_Classifier/RockPaperScissorsApp/app/release/`
