# SaltFeed Day 3

## A. Scenario

The CTO comes rushing down the hall and burst into your mob room. He's out of breath while he gives you the news:

> Cecilia, the CEO, her son built a Star Wars fan site with an API and now, for a reason that only the Force can fathom, she wants us to use that API. ASAP!

He rests his hands on his knees and continues under clenched teeth:

> Can you please just use it and hide it somewhere. But make it proper and test the shit out of that API. I wouldn't mind us finding a bug in the "wünderkid's" work. He's such a menace... joining our board meetings and everything...

You agree and get the URL to the API - <https://swapi.py4e.com/>
Man! This looks proper! The wünderkid knows what he's doing...

## B. What you will be working on today

Today we will hook up the Star Wars API (<https://swapi.py4e.com/>) to our own API, making two endpoints that returns:

- A list of heroes (characters) from the Star Wars universe
- Details about individual characters

In order to do that we need to implement another controller and a "service" that calls out to the Star Wars API endpoints.

This is a common approach, hiding the implementation logic in a service that our own API then uses. By this we hide the calling of the Star Wars API from our web application and it will only need to know about 1 API.

Another common approach is to encapsulate the response to a client in separate class (a DTO), often we add additional information related to the request, to the actual data. Hence the `HeroesListResponse` class.

## C. Lab instructions

We have left some skeleton code for you, and a bunch of falling tests to guide you through the exercise. You have the GreetingsController and all the other controllers that you can use as inspiration for how to setup your code.

### Tips

- Be sure to go slow and take breaks.
- Take some time to use the excellent site at <https://swapi.py4e.com/> to navigate around and get to know the API.
- Test's are great, but this time we are working with a lot of new tools. Swagger is a geat resource to just see the result of API calls to your own application. Main suggestino is to just have the result from swapi be tunneled through!
- It's important that your API call to swapi ends with a "/"! Else you get a 301!
- When an external API returns data we often map the JSON returned to a class to return. `.bodyToMono(Your.class)` is here to help you!

---

Good luck and have fun!
