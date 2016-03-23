// from http://www.hacksparrow.com/node-js-eventemitter-tutorial.html

var util = require('util');
var EventEmitter = require('events').EventEmitter;

// @station - an object with `freq` and `name` properties
var Radio = function(station) {

    // we need to store the reference of `this` to `self`, so that we can use the current context in the setTimeout (or any callback) functions
    // using `this` in the setTimeout functions will refer to those funtions, not the Radio class
    var self = this;
    
    // emit 'open' event instantly
    setTimeout(function() {
        self.emit('open', station);
    }, 0);
    
    // emit 'close' event after 5 secs
    setTimeout(function() {
        self.emit('close', station);
    }, 5000);
    
    // EventEmitters inherit a single event listener, see it in action
    this.on('newListener', function(listener) {
        console.log('Event Listener: ' + listener);
    });
    
};

// extend the EventEmitter class using our Radio class
util.inherits(Radio, EventEmitter);

// we specify that this module is a refrence to the Radio class
module.exports = Radio;