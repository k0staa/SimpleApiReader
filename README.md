# Simple API Reader
Simple application which read information for choosen City
from OpenWeather, Restcountries.eu, Fixer.io and api.nbp.pl.
Application use only SocketChannel to read from api service and
JSONObject library to read data from aquired JSON.

## Configuration
Import project and set openWeatherAPI variable in OpenWheaterInformationApi (you need to get it from
Openweather.com).
You can choose city in Main class which will be used to query info from all used api services.

## Running
Compile and run Main class.

## To-do
Add swing components to change city and country in GUI.