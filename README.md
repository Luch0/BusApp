# BusApp

This app is in development. The goal of this app is use the MTA bustime API to create a simple and easy to use application
in order for the commuter to track the location of MTA buses according to corresponding routes.

I use the MTA bus service almost everytime I go out, either to work, appointments or other places. Since I found out that the MTA implemented a way to track buses in real time, I started using it to check where my bus was. The service is very useful and there's almost never a problem. The only thing that I did not like about it was the website that they offer. The bustime web service I feel it's not very user friendly so I decided to create an app that would be alot easier to use.

First I did some research and found out that the MTA Bustime has a developer website (http://bustime.mta.info/wiki/Developers/Index) where I could find documentation and steps to take to access the real time data. The first thing I had to do was request an API key through their website in order to have access to the data needed to create the bus app. After obtaining the API key I had to learn how to make requests using Java in order to receive the response needed. I had to learn how to parse the data received as JSON format and be able to provide the relevant information to the user.

The user interface consists of a text box for input of the bus route the user wants to look for and the text box also provides auto complete with all available MTA bus routes. There is also a dropdown box which will work as a favorite or most searched bus routes. This is one functionality that I find very useful because in comparison with the MTA bustime website, it will take just a couple of taps to search for the desired bus route. I also learned about TabLayouts and I make use of two tabs in order to show both routes for the bus, this way the user can just make a simple swipes to see each route.
