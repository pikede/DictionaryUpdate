# DictionaryUpdate: A Dictionary App by Prince Ikede
This is a Dictionary app that allows a user to do the following:

- Written in Kotlin.
- Allows users to enter the term they wish to search.
- Shows the list of resulting definitions, with number of thumbs up and thumbs down votes, using a
RecyclerView for this list.
- Allows users to sort by either most thumbs up or down.
- Shows a progress indication while the Dictionary API call is being made.
- Handles configuration changes gracefully
- Cache results for limited offline operation
- Uses Dependency injection for dependencies 
- Documents a few assumptions 

----------------------------------------------------------------------------------------------------

Tools used: Android SDK, Dependency Injection (Koin), Kotlin, MutableLiveData, Coroutines, MVVM Architecture, 
Retrofit, Room Database, Coroutines 

----------------------------------------------------------------------------------------------------

Results:

- Edit text for getting user's search word
- Search button used for searching user's search word
- Up/Down sort filter for sorting based on thumbs up or thumbs down
- Clear Filter button for clearing UI (including users entered text)
- Progress bar to show loading of data
- Room database for caching data from api calls locally to prevent unnecessary network calls
- RecycleView for displaying data received from either database or api(if data doesn't exist in the local cache, database)
- Instrumented test to ensure database works properly
- Unit test to ensure sorting (either by thumbs up or by thumbs down) algorithm works properly
- Dependency injection using Koin
- Handler & Runnable for progress bar multithreading

----------------------------------------------------------------------------------------------------

Future improvements that can be made:
- user tracking and analytics (most searched word by location)
- test coverage for search and database transactions
- mutex lock for database transaction
- recycler view can be styled a bit more

# API
- https://mashape-community-urban-dictionary.p.rapidapi.com
- https://rapidapi.com/community/api/urban-dictionary
