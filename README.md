# movieApp

<div align="center">
    <img src="https://github.com/mohsin84/movieApp/blob/main/movie_app.gif" width="600px"</img> 
</div>

This app get data from  <a href="https://developers.themoviedb.org/3/movies/get-top-rated-movies">movie data base api</a> and show popular movie items in a list of horizontal shelves where each shelve show list of movie poster. Clicking any of the tile will open movie details page which show full screen poster with some movie information overlayed on the image. It’s a complete MVVM project that use dependency injection
and lifecycle aware components. It use all features of <a href="https://developer.android.com/topic/libraries/architecture/index.html">
Android Architecture Components</a> library like Room, ViewModels, LiveData and other Lifecycle-aware components.

I used <a href="https://google.github.io/dagger/">Dagger 2 </a>Dependency Injection in this project to manage dependencies, simplified 
acces to shared dependency like use of <a href="http://square.github.io/retrofit/"> Retrofit</a> to make asynchronous API calls,
also for scope instances like Singleton repositories. Inside fragment used Livedata to observe data changes in ViewModel. Used the repository to maintain separation of concern and also to keep the ViewModel lean. Converstion of rx stream to liveData is done in the viewModel.

Lazy Image loading is achieved using <a href="http://bumptech.github.io/glide/">Glide</a>library. By deafult all images will be loaded to memory. Disk cache is also possible with few strategy for caching (did not added any disk cache in this project).

Used CardView for rounded conrnering images and ConstraintLayout for optimized Layout. Added few Unit test for the viewModel

<b>List of android libraries used</b>
<ol>
  <li><a href="https://developer.android.com/topic/libraries/architecture/index.html">Android Architecture Components</a></li>
  <li><a href="https://google.github.io/dagger/">Dagger 2 </a> </li>  
  <li><a href="https://developer.android.com/training/testing/espresso/index.html"> Espresso </a></li>  
</ol>

<b>List of 3rd Party libraries</b>
<ol>
  <li> <a href="http://square.github.io/retrofit/"> Retrofit</a></li>
  <li> <a href="http://bumptech.github.io/glide/"> Glide </a></li>
  <li> <a href="https://mockk.io/"> Mockk</a></li> 
</ol>

Coding architecture/ Design Pattern:
 <ol>
<li> <a href="https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel">MVVM (Model View ViewModel)</a></li>
</ol>
