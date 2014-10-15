define("devices",
    [ "jquery",
        "backbone",
        "underscore",
        "handlebars",
        "moment",
        "text!../tpl/deviceTemplate.html" ],
    function ($, Backbone, _, Handlebars, moment, DeviceTemplate) {

        var DeviceModel = Backbone.Model.extend({});

        var DeviceListModel = Backbone.Collection.extend({
            model: DeviceModel,

            url: "/api/devices",

            initialize: function () {
                this.fetch();
            }
        });

        var DeviceListView = Backbone.View.extend({
            initialize: function () {
                this.template = _.template(DeviceTemplate);
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

                this.collection.bind("sync", this.render, this);
                this.collection.bind("reset", this.render, this);
            },

            render: function () {
                var data = _.extend({ collection: this.collection.toJSON() }, this.convertJodaToJs);
                var renderedContent = this.template(data);
                this.$el.children("tbody").html(renderedContent);
                return this;
            }
        });

        return {
            init: function () {
                var deviceList = new DeviceListModel();
                var deviceListView = new DeviceListView({
                    el: $("#deviceTable"),
                    collection: deviceList
                });

                setInterval(function () {
                    deviceList.fetch({ })
                }, 5000);
            }
        }
    });
