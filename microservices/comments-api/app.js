var sqlite3 = require('sqlite3').verbose();
var db = new sqlite3.Database(':memory:');
// //for file base DB:
// var db = new sqlite3.Database('db.data');

db.serialize(function() {
    db.run("CREATE TABLE IF NOT EXISTS comment (id INTEGER, author TEXT, comment TEXT, email TEXT, timestamp TEXT)");
    // //Commented out for starting the DB with data during startup.
    // db.run("INSERT INTO comment (id, author, comment, email, timestamp) VALUES (?,?,?,?,?)", 1, "Ana Molly","My first comment for movie id 1","ferne.bode@gmail.com","20180805215654-0600");
    // db.run("INSERT INTO comment (id, author, comment, email, timestamp) VALUES (?,?,?,?,?)", 1, "Cyril Walker","My second comment for movie id 1","dolly.schumm@hotmail.com","20180424202248-0600");
    // db.run("INSERT INTO comment (id, author, comment, email, timestamp) VALUES (?,?,?,?,?)", 22, "Jewel Schmeler","First comment for movie id 22","laney.wuckert@hotmail.com","20180731185914-0600");
});


var express = require('express');
var bodyParser = require('body-parser')
var restapi = express();

//To parse json body in POST requests.
restapi.use(bodyParser.urlencoded({ extended: false }))
restapi.use(bodyParser.json())

console.log("[GET]  Registering endpoint: /");
restapi.get('/', function(req, res){
    res.send('hello world comments api');
});


console.log("[GET]  Registering endpoint: /comments");
restapi.get('/comments', function(req, res){
    db.all("SELECT author, comment, id, email, timestamp FROM comment", function(err, row){
        res.json(row);
    });
});


console.log("[GET]  Registering endpoint: /comments/:id");
restapi.get('/comments/:id', function(req, res){
    db.all("SELECT author, comment, id, email, timestamp FROM comment where id = ?", req.params.id, function(err, row){
        res.json(row);
    });
});

console.log("[POST] Registering endpoint: /comments/:id");
restapi.post('/comments/:id', function(req, res){        
        db.run("INSERT INTO comment (author, comment, id, email, timestamp) VALUES (?,?,?,?,?)" , [req.body.author,req.body.comment, req.params.id, req.body.email,req.body.timestamp], function(err, row){
        if (err){
            console.log(err);
            res.status(500);
        }
        else {
            res.status(202);
        }
        res.end();
    });
});


restapi.listen(3000);

console.log("\nCURL EXAMPLES:");
console.log("curl http://localhost:3000/");
console.log("curl http://localhost:3000/comments");
console.log("curl -X POST http://localhost:3000/comments/70 -H 'Content-Type: application/json' \\ \n     -d '{ \"author\": \"Mrs. Bean\",\"comment\": \"Chuck Norris has root access to your system.\",\"email\": \"gudrun.orn@gmail.com\",\"timestamp\": \"20180729164540-0600\"}'");
console.log("curl http://localhost:3000/comments/70");