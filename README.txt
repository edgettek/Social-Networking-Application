README

Contact Information:

Name : Kyle Edgette
Course : CSC 296 FALL 2015
Assignment : Project 02 - Social Network
TA : Nate


Academic Honesty Pledge:

I affirm that I did not give or receive any unauthorized help on this project and that all
work is my own.

- Kyle Edgette


Description:

A social networking Android App.

LoginActivity - Allows the user to input an email and a password. If they are new and they 
try to login, they will be prompted to create a new profile instead. If they already have an 
account and try to create another one, they will be prompted to login. Supports orientation 
changes.

ProfileCreationActivity - Allows new user to add a profile picture, and add their Name, Hometown, 
Birthday, and Biography. Existing users can also use this activity to update their information 
BUT THEY MUST INPUT ALL OF THEIR INFORMATION (i.e. they cannot just change their biography without 
re-inputting their hometown, take a new picture, etc. They must re-update EVERYTHING). Supports 
orientation changes.

SocialNetworkingApp - Contains two recycler views one for all users (other that the current user) 
and one for the users that the current user has favorited. There is a refresh button that propagates 
the changes in the database into the recycler views. To favorite a user from the "All Users" list, 
you click the view holder which starts a dialog with a favorite button. The same mechanism is used 
to unfavorite users in the "Favorites" list. Supports orientation changes.

FeedActivity - Allows the current user to post an update, either text and picture, just text, or 
just a picture. There is another Recycler View that holds all the posts from the favorited users 
of the current user. Supports orientation changes.

DataBase - The schema is from Professor St. Jacques' video. The idea for the DataAccessObject is 
also from the video, although most of the methods were written myself, aside from the basic 
ones outlined in the video.

Model - The majority of the model is also from the video, with the exception of the static User 
currentUser in the User class which keeps track of which user is currently operating the device.
