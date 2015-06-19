# TMF BBC Enhancer

This repository contains the BBC enhancement module of [TellMeFirst](https://github.com/TellMeFirst/TellMeFirst).

This module directly interacts with [The BBC News Labs APIs](http://docs.bbcnewslabs.co.uk/Juicer-2.html) in order to retrieve news related to the results of the classification system of TellMeFirst.

## API usage

Once you bulid this module with Maven, you can invoke getNewsFromNYTimes().

``` java

	BBCEnhancer bbcEnhancer = new BBCEnhancer();
    String URL = bbcEnhancer.createURL("http://dbpedia.org/resource/Barack_Obama");
    String result = bbcEnhancer.getResultFromAPI(URL, "application/json");
```