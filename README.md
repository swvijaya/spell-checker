**Spell Checker**

This application is a spell checker implemented using a Bloom Filter.

The application has a UI to key in the values and a text area that shows all the words that have spelling errors.
The controller receives the request from the UI and sends the list to the Spell Check service.
The Spell Check Service checks for the word int h filter.

The filter is intiialized from the file words.txt.

The application can be built and installed by running the maven command from the SpellChecker folder:

_**mvn clean install spring-boot:run**_



Once the application starts up, hit the following url on your browser: http://localhost:8080/index.html


