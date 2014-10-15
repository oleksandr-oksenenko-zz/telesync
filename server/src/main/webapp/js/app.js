requirejs.config({
    baseUrl: "/js",
    paths: {
        "jquery": "3p/jquery.min",
        "backbone": "3p/backbone-min",
        "underscore": "3p/underscore-min",
        "text": "3p/text",
        "moment": "3p/moment"
    }
});

require([
        "jquery",
        "backbone",
        "underscore",
        "moment",
        "text!../tpl/device.tpl"
    ],
    function ($, Backbone, _, moment, DeviceTemplate) {
        _.helpers = {
            convertJodaToJs: function(dateTimeArray) {
                var date = new Date(
                    dateTimeArray[0],
                    dateTimeArray[1],
                    dateTimeArray[2],
                    dateTimeArray[3],
                    dateTimeArray[4],
                    dateTimeArray[5],
                    dateTimeArray[6]
                );

                return moment(date).format("DD.MM.YYYY hh:mm:ss");
            }
        };

        var Device = Backbone.Model.extend({
            changeUrl: function (url) {
                this.save({ deviceUrl: url });
            }
        });

        var Devices = Backbone.Collection.extend({

            url: "/api/devices",

            model: Device,

            initialize: function () {
                this.fetch();
            }
        });

        var DeviceView = Backbone.View.extend({
            tagName: "tr",

            template: _.template(DeviceTemplate),

            events: {
                "click button.btn-change-url": "changeUrl",
                "click button.btn-save-url": "saveUrl",
                "click button.btn-remove-device": "removeDevice"
            },

            initialize: function () {
                this.listenTo(this.model, "change", this.render);
                this.listenTo(this.model, "destroy", this.removeSelf)
            },

            changeUrl: function () {
                var urlValue = this.url.text();
                this.urlInput.val(urlValue);

                // hide
                this.url.addClass("hidden");
                this.changeBtn.addClass("hidden");

                // show
                this.urlInput.removeClass("hidden");
                this.saveBtn.removeClass("hidden");
            },

            saveUrl: function () {
                // save
                var urlValue = this.urlInput.val();
                this.model.changeUrl(urlValue);

                // hide
                this.urlInput.addClass("hidden");
                this.saveBtn.addClass("hidden");

                // show
                this.url.removeClass("hidden");
                this.changeBtn.removeClass("hidden");
            },

            removeDevice: function () {
                this.model.destroy();
            },

            removeSelf: function () {
                this.remove();
            },

            render: function () {
                $(this.el).html(this.template(this.model.toJSON()));
                this.url = this.$(".url");
                this.urlInput = this.$(".url-input");
                this.changeBtn = this.$(".btn-change-url");
                this.saveBtn = this.$(".btn-save-url");

                return this;
            }
        });

        var DevicesView = Backbone.View.extend({
            el: $("#deviceTable"),

            initialize: function () {
                this.listenTo(this.collection, "sync", this.render);
            },

            render: function () {
                this.collection.each(function (device) {
                    var deviceView = new DeviceView({ model: device });
                    this.$el.append(deviceView.render().el);
                }, this);
            }

        });

        $(document).ready(function () {
            var devices = new Devices;
            var devicesView = new DevicesView({ collection: devices });
        });
    }
);