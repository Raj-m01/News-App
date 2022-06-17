# News-App 📰

### Overview :
Android news app built using Kotlin and implemented **MVVM** architecture. It requests news from <a href="https://newsapi.org/">News api</a> with help of  **Retrofit** library.

### Features :
* Display News in various categories 
* Share news with friends
* Browse news
* Bookmark news to read later/ Delete news
* Listen news with provided Text-To-Speech service

 ### Screenshots : 
 
 <table align="center">
  <tr>
    <td><img src="https://github.com/Raj-m01/News-App/blob/master/screenshots/mainactivity.jpeg" alt="News home" width=250px height=500px></td>
    <td><img src="https://github.com/Raj-m01/News-App/blob/master/screenshots/read%20news%20activity.jpeg" alt="Saved News" style="width:250px;height:500px;"></td>
    <td><img src="https://github.com/Raj-m01/News-App/blob/master/screenshots/saved%20news%20activity.jpeg" alt="Image - news sharing" style="width:250px;height:500px;"></td>
  </tr>
   
</table><br><br>

### Getting Started :

 * Generate API key from <a href="https://newsapi.org/">News api</a>
 *  Paste the key in gradle.properties
 ````API_KEY="<YOUR_API_KEY>" ````
 * Rebuild app


### MVVM Architecture : 

MVVM is one of the architectural patterns which enhances separation of concerns, it allows separating the user interface logic from the business (or the back-end) logic. Its target (with other MVC patterns goal) is to achieve the following principle "Keeping UI code simple and free of app logic in order to make it easier to manage".

<table align="center" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td >
<img style="width:600px;height:400px;" src="https://miro.medium.com/max/875/1*itYWsxQTfq7xTuvIMrVhYg.png" alt="MVVM architecture">
</td></tr>
</table>


#### MVVM components : 

* **Entity:** Annotated class that describes a database table when working with Room.

 * **SQLite database:** On device storage. The Room persistence library creates and maintains this database for you.

 * **DAO:** Data access object. A mapping of SQL queries to functions. When you use a DAO, you call the methods, and Room takes care of the rest.

 * **Room database:** Simplifies database work and serves as an access point to the underlying SQLite database (hides SQLiteOpenHelper). The Room database uses the DAO to issue queries to the SQLite database.

 * **Repository:** Used to manage multiple data sources.

 * **ViewModel:** Acts as a communication center between the Repository (data) and the UI. The UI no longer needs to worry about the origin of the data. ViewModel instances survive Activity/Fragment recreation.

 * **LiveData:** A data holder class that can be observed. Always holds/caches the latest version of data, and notifies its observers when data has changed.
 
 
### Retrofit : 
<a href="https://square.github.io/retrofit/"><b>Retrofit</b></a> is a type-safe REST client for Android, Java and Kotlin developed by Square. The library provides a powerful framework for authenticating and interacting with APIs and sending network requests with OkHttp.

### Application link : <a href="https://github.com/Raj-m01/News-App/blob/master/apk/News.apk">**Click here to download apk**</a>

### 📝 License 

Copyright (c) 2022 Raj Manjrekar

This project is [MIT](https://github.com/Raj-m01/News-App/blob/master/License) licensed.
