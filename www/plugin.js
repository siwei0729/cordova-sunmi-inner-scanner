var exec = require('cordova/exec');

var PLUGIN_NAME = 'SunmiScanner';

var SunmiScanner = {
    echo: function (phrase, cb) {
        exec(cb, null, PLUGIN_NAME, 'echo', [phrase]);
    },
    getDate: function (cb) {
        exec(cb, null, PLUGIN_NAME, 'getDate', []);
    },
    scan: function (cb) {
        exec(cb, null, PLUGIN_NAME, 'scan', []);
    }
};

module.exports = SunmiScanner;
