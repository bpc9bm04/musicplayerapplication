# Muzix

## Problem Statement

Build a system to manage music.

## Requirements:

1. The application needs to fetch existing playlists from [https://www.last.fm/api](https://www.last.fm/api) or [https://developer.napster.com/](https://developer.napster.com/)
2. A frontend where the user can **play music**, **bookmark** it and should be able to create **playlist** out of it.
3. Based on the music played and playlist created the application should be able to recommend with similar music to the user.

## Modules:
1. AccountManager - Should be able to manage user accounts
2. MuzixUI -
  - User should be able to
    - search music
    - bookmark music
    - create playlists
    - should be able to see bookmarked music and playlists created by him
3. MuzixManager - Application should be able to store all his
  - bookmarks
  - playlists
  - searches

## Flow of Modules

- **Building Frontend**
  1. Building **Responsive** Views:
    1. Build a View to show all music
      1. Music - Populating from external api
      2. Build a view to show created playlist
      3. A view to authenticate users
  2. Using **Services** to populate these data in views
  3. Stitching these views using **Routes and Guards**
  4. E2E test cases and unit test cases
  5. **Writing CI configuration file**
  6. **Dockerizing the frontend**

- **Building the Account Manager**
  1. Creating a server in Spring Boot to 
    1. **facilitate registration** and login using **JWT token** and **MySQL**
  2. Writing Swagger Documentation
  3. Unit Testing
  4. Write CI Configuration
  5. Dockerize the application

- **Building the Muzix Manager**
  1. Creating a server in Spring Boot to 
    1. **facilitate CRUD operation** over music, playlist and bookmarked resources stored in MySQL
  2. Writing Swagger Documentation
  3. Unit Testing
  4. Write CI Configuration
  5. Dockerize the application

- **Write docker-compose file to build both frontend and backend application**

- **Demonstrate the entire application**