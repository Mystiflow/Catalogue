# Catalogue

Spigot and BungeeCord plugin to execute a list of multiple commands at ease

### Usage:

Add message to your 'messages.json' file:
```
    {
      "name": "ClearAnnoyances",
      "actions": [
        {
          "action": "weather clear",
          "type": "COMMAND",
          "iterations": 1
        },
        {
          "action": "time set day",
          "type": "COMMAND",
          "iterations": 1
        }
      ]
    },
```
With the following message registered, executing the command ```/bcatalogue execute ClearAnnoyances``` in game will clear the weather and make the time day.

[![Build Status](https://travis-ci.org/Mystiflow/Catalogue.svg?branch=master)](https://travis-ci.org/Mystiflow/Catalogue)
