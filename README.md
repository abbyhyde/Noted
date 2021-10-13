# Noted
Noted is an Android note application. We chose to create this application so that people can quickly record and organize their ideas.

## Design Pattern
We used MVC with the database, the DAO, and our classes to control the views.

## Data Storage
To store information about our folders and notes, we have a relational database with 2 tables. One contains information for the folders which includes a UUID, the folder title, and the folder color. The other contains information about the notes, such as its UUID, the note title, the body text, the note color, the location where it was made, and folder it resides in. 

## Mobile Sensor/Network Component
We utilized the GPS to get the location longitude and latitude of the location of the phone for when the note was made. Using the RetrofitAPI and the OpenWeather API from our previous assignment, we were about to get the name of the location to store. We also are able to share the contents of our notes depending on what applications are on the device, but this can include email and storing it in a user's Google Drive. Originally we were going to use the camera feature but realized that because we were sharing a .txt file, the image would not be sent. We opted then for using the GPS/location feature beacuse it was another common thing we saw on other note applications.

## Additional Notes
When sharing a file, the formatting is not maintained but the actual text contents are the same. In addition to that, when hititng the bold or the italics button, it will only format text that is selected. This means that when clicked, it will not format any following, new text.

## Design Achievements
- Allowing users to select different colors for folder and notes
- Users are able to store notes into different folders as a means of organization

## Technical Achievements
- Formatting text using Spanables
- Being able to share the note to other applications


