requirejs.config({
    baseUrl: "/js",
    paths: {
        "jquery": "3p/jquery.min",
        "backbone": "3p/backbone",
        "underscore": "3p/underscore",
        "text": "3p/text",
        "handlebars": "3p/handlebars",
        "moment": "3p/moment",

        "devices": "app/devices"
    }
});

(function () {
    require(["App"], function (app) {
        var appName = app.name;
        if (appName) {
            require([appName], function (app) {
                $(document).ready(app.init);
            });
        }
    })

})();