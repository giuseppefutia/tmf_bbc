# TMF BBC Enhancer

This repository contains the BBC enhancement module of [TellMeFirst](https://github.com/TellMeFirst/TellMeFirst).

This module directly interacts with [The BBC News Labs APIs](http://docs.bbcnewslabs.co.uk/Juicer-2.html) in order to retrieve news related to the results of the classification system of TellMeFirst.

## API usage

Once you bulid this module with Maven, you can write the following code for testing:

``` java
    BBCEnhancer bbcEnhancer = new BBCEnhancer();
    String API_KEY = bbcEnhancer.getPropValues();
    String URL = bbcEnhancer.createURL("http://dbpedia.org/resource/Barack_Obama", API_KEY);
    String result = bbcEnhancer.getResultFromAPI(URL, "application/json");
```
