// from http://www.hacksparrow.com/node-js-eventemitter-tutorial.html

var Radio = require('./radio.js');

// a station object
var station = {
    freq: '80.16',
    name: 'Rock N Roll Radio',
};
// create an instance of the Radio class
var radio = new Radio(station);

// add an 'open' event listener
radio.on('open', function(station) {
    console.log('"%s" FM %s OPENED', station.name, station.freq);
    console.log('♬ ♫♬');
});

// add a 'close' event listener
radio.on('close', function(station) {
    console.log('"%s" FM %s CLOSED', station.name, station.freq);
});