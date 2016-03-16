// Example from Brad Dayley
// https://github.com/bwdbooks/nodejs-mongodb-angularjs-web-development
var express = require('express');
var url = require('url');
var app = express();
app.listen(80);
app.get('/download', function (req, res) {
  res.sendfile('./views/word.docx', 'new.docx');
});