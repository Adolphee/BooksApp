# Books App, and android project

One of my assignments in college was to create an Android application that consumes the Google Books API.
It uses Retrofit to load the json data into POJO Classes and SQLite to store your favorite books locally.
In this example, I decided to simply store the entire book object as a json String in the SQLite database.

This allows me to limit the network traffic and thus minimize the number of threads my app has running at all times.
The user can of course query the Books API and search through his favorite books.

Since we're using SQLite, I decided not to include a login and registration procedure.
I figured this would be silly since the user data is being stored locally, 
rather then pushed and pulled from a remote database server.
