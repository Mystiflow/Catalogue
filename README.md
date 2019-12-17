# Catalogue

Spigot and BungeeCord plugin to execute a list of multiple commands at ease

### Usage:

Setup your actions in the 'catalogue.json' file:
```
{
  "actions": [
    {
      "name": "ClearWeather",
      "action": "weather clear",
      "iterations": 1
    },
    {
      "name": "SetDay",
      "action": "time set day",
      "iterations": 1
    }
  ]
}
```

Setup your messages in the 'catalogue.json' file (NOTE: a message can be defined as an action):
```
{
  "messages": [
    {
      "name": "ClearAnnoyances",
      "actions": [ "SetDay", "ClearWeather" ]
    }
  ]
}
```
With the following message registered, executing the command ```/bcatalogue execute ClearAnnoyances``` in game will run this message which will perform the actions to clear the weather and make the time day.

[![Build Status](https://travis-ci.org/Mystiflow/Catalogue.svg?branch=master)](https://travis-ci.org/Mystiflow/Catalogue)
