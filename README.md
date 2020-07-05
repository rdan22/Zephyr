# Zephyr
In 2017, I created an Android application in Java using Android Studio that provides a user-friendly environment for a Diabetic Ketoacidosis (DKA) patient to detect and manage the ketone body Acetone in their breath. 

## The Device
I developed a prototype that takes a unique approach for the detection of the ketone body Acetone in a Diabetic Ketoacidosis (DKA) patient’s breath, through the use of an electrochemical gas sensor. I used an Arduino Uno microcontroller, an LM7805L 5V voltage regulator IC, three micro switches, a DHT11 sensor, and the electrochemical gas sensor (specifically the Figaro TGS822 sensor) to build the prototype. Using 100% pure acetone, a beaker, tubing, and a hot plate, I created vaporized acetone that was successfully detected by my prototype.

## The App
In order to create a more user-friendly environment and a reliable classifier, I created a cloud-based web application service using Django and Python. I deployed this web service, along with a Postgres database, and coded an Android application in Java using Android Studio as a front-end to this service. The web service was created to provide a mechanism to store user data, as well as to support multiple users. The Android application was created for the user to view his or her acetone level on a mobile device and to determine the risk level of DKA. The data collected from the Figaro electrochemical gas sensor was sent to the microcontroller. The data was then posted by the microcontroller in JSON (JavaScript Object Notation) format using an HTTP POST command to the cloud-based web service, where the web server stored the data under a unique and encrypted JSON web token created during the login process. The Android application was then able to retrieve user-specific data based on that user’s unique token, using an HTTP GET request. Finally, the retrieved data was rendered on the user’s mobile device in a user-friendly way.

## One Step Further
The project involved the innovation of breath testing as well as a software system. I wanted to take it one step further by building a binary classifier to determine if a user tested positive for DKA. I programmed a Support Vector Machine (SVM) model and algorithm in Python. SVMs are Supervised Learning algorithms that implement pattern recognition features and training for original data mapped in a new space. It was used to delineate binary classification of the data collected in the experiment.

This project uses **No License**: open source for educational purposes only. Look, but do not use for any thing including personal and commercial activities unless you obtain my explicit permission.
