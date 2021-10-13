# Noted
Noted is an Android note application. We chose to create this application so that people can quickly record and organize their ideas.

## Design Pattern
We used MVC with the database, the DAO, and our classes to control the views.

## Data Storage
To store information about our folders and notes, we have a relational database with 2 tables. One contains information for the folders which includes a UUID, the folder title, and the folder color. The other contains information about the notes, such as its UUID, the note title, the body text, the note color, the location where it was made, and folder it resides in. 

## Mobile Sensor/Network Component
We utilized the GPS to get the location longitude and latitude of the location of the phone for when the note was made. Using the RetrofitAPI and the OpenWeather API from our previous assignment, we were about to get the name of the location to store. We also are able to share the contents of our notes depending on what applications are on the device, but this can include email and storing it in a user's Google Drive. Originally we were going to use the camera feature but realized that because we were sharing a .txt file, the image would not be sent. We opted then for using the GPS/location feature beacuse it was another common thing we saw on other note applications.

## Performance
Overall there is not much usage in the CPU, network, nor energy, which makes sense because it is a relatively simple app. In terms of memory usage, in the video, the application took up 98.4 MB, which can also vary depending on how many notes/folders are stored in the database.

## Additional Notes
When sharing a file, the formatting is not maintained but the actual text contents are the same. In addition to that, when hititng the bold or the italics button, it will only format text that is selected. This means that when clicked, it will not format any following, new text. There may be some bugs when hitting the back button when going from the folder popup fragment to the folder list.

## Changes from the Checkpoints
Some of our major deviations from our original task list includes our camera feature and our use of the file system. We chose to work with the GPS instead of the camera because our sharing feature exported our note as a .txt file. The images wouldn't have been included in the end, so we opted for another feature which is typically found in other notes apps. We also chose to work with a database instead of the file system due to time constraints and having to learn how to access the files. In addition to that, if we were to format our text and export it as a .txt file or pdf, it would have taken a lot longer to learn how to maintain the formatting in the note editor.

## Design Achievements
- Allowing users to select different colors for folder and notes: Many productiveity apps use colors as a means to organize. By having different colors, it allows users to organize their notes in a more visual way. In addition to that, we have not worked with radio buttons in previous projects. 
- Users are able to store notes into different folders as a means of organization: This is also another feature in many productivity apps. We can add notes to specific folders and those notes will also only be in that folder. We did not have this in our presentation, but when deleting the folder, it will delete the notes within it as well.

## Technical Achievements
- Formatting text using Spannables: Being able to format text is a great way to emphasize different points, especially for a note application. We have yet to use spannables in our projects and with these components, you can add a lot more features with them such as underlining or adding images inside the EditText.
- Being able to share the note to other applications: Sharing the note is another great feature to have for a note app. To start off with, you are able to simply copy it or share it based on the applications found on the device. 


