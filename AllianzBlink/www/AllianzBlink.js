var exec = require('cordova/exec');

exports.blinkMethod = function(arg0, success, error) {
    exec(success, error, "AllianzBlink", "blinkMethod", [arg0]);
};