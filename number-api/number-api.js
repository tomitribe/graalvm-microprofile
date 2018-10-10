const leftPad = require('left-pad');
const express = require("express");
const app = express();

var config = Java.type("org.eclipse.microprofile.config.ConfigProvider").getConfig();
var port = config.getOptionalValue("NUMBER_API_PORT", Java.type("java.lang.Integer")).orElse(3000);

app.listen(port, function () {
    console.log("Server running on port " + port);
});

app.get("/number-api/number/generate", function (request, response) {
    response.send("MV-" + leftPad(Math.floor(Math.random() * 9999999) + 1, 7, 0));
});
