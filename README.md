# TastyApp
<p> Architecture : Model - View - ViewModel  <br>
Language: Kotlin<br>

<p>This application searchs for a given food type in the Tasty Api, list the recipes, show the recipe in detail. The Api Service is https://rapidapi.com/apidojo/api/tasty/</p>
Use cases:
<ul>
<li>Search food type recipes with different cooking times</li>
<li>See recipe detail, list instructions</li>
</ul>

Implements:<br></p>
<ul>
<li>Transition Manager for Fragments</li>
<li>Gson library to handle JSON objects</li>
<li>Coil library to load Images. This library uses coroutines</li>
<li>Dagger Hilt for dependency injection</li>
<li>DiffUtil in RecyclerView</li>
<li>Unit testing for user input validation method.</li>
<li>Desing patterns like repository, adapter, singleton</li>
</ul>
<p> In this project you migth find UI implements like:<p>
<ul>
<li>Set up different styles in themes file and costumize the views</li>

<li>Create the app icon using the  Image asset creator from the android studio</li>
</ul>
<p> 
This application has 2 fragments. One to list the search results, and one to show the recipe on detail.<br>
The viewModels use Hilt Injection, and LiveData to comunicate events.
App layers and classes: <br> 
 -> model. * dataclasses. This class holds the different data classes used in the app.<br> 
 -> network * ApiService. This class handles the api requests from the repository through Coroutines.<br>
 -> Utils.  * Extensions. Holds some extensions functions, like hide keyboard from any context.<br>
            * InputValidator. A simple class to validate user input message.<br>
 -> Views  -> Adapters * Holds the listview adapters and their interface for click handling in the fragment.<br>
            * Holds the main activity and fragments.<br>
 -> Repositories -> RecipeRepository  * Handles the requests from the viewModel and connects with the ApiService.<br>
 ->  ViewModel -> * The viewModels handles the data and provides the views.
                
  Some ScreenShots: 
  <div>
    <img src="https://user-images.githubusercontent.com/100162759/217354499-6fe59122-d3b6-459a-9b51-9f8b793e7e6f.png" width=30% height=30%>
    <img src="https://user-images.githubusercontent.com/100162759/217354506-4c31b25d-31a8-4c86-8464-23e164a36f3c.png" width=30% height=30%>
</div>

