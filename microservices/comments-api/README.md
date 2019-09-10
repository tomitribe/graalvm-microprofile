# comments-api

Movie comments RESTful Web APIs with Node.js, Express, sqlite and body-parser.

## Requirements
 * [GraalVM](https://www.graalvm.org/downloads/) or
 * [NodeJS](https://nodejs.org/en/)


## Build
        npm install
        npm install --nodedir=<GraalVMHome>/jre/languages/js --build-from-source sqlite3

## Run
        node app.js

It will initialize the server at port 3000.

---
**NOTE**

When running node or npm commands, make sure that you use the the binaries from the bin folder inside GraalVM.

---

## Usage
Curl examples:

        #check if app is working
        curl http://localhost:3000

        #Get all the comments
        curl http://localhost:3000/comments
        
        #Post a new comment
        curl -X POST http://localhost:3000/comments/70 -H 'Content-Type: application/json' \
             -d '{ "author": "Mr. Bean","comment": "Chuck Norris has root access to your system.","email": "bean@light.com","timestamp": "20180729164540-0600"}

        #Get a comment for movie with id 70
        curl http://localhost:3000/comments/70
