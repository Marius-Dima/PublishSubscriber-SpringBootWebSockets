"use strict";

//Sort of like a HashMap holder to correlate subscribed&active channels
var activeSubscriptions = {};


function subscribeToChannel(channel) {
    if (activeSubscriptions[channel] == null || !activeSubscriptions[channel].connected) {
        var socket = new SockJS('/websocket');
        var stompClient = Stomp.over(socket);

        stompClient.connect({}, function () {
            showNews('Subscribed to: ' + channel);
            stompClient.subscribe('/topic/' + channel, function (news) {
                showNews(JSON.parse(news.body).content);
            });
        });
        activeSubscriptions[channel] = stompClient;
    }
}

function unsubscribeFromChannel(channel) {
    var stompClient = activeSubscriptions[channel];
    if (stompClient != null && stompClient.connected) {
        stompClient.disconnect();
        showNews("Unsubscribed from " + channel);
    }
}


function sendNewsToChannel(channel) {
    var stompClient = activeSubscriptions[channel];
    stompClient.send("/app/" + channel, {}, JSON.stringify({'content': $("#content").val()}));
}

function showNews(message) {
    $("#news_table").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $.ajax({
        type: "GET",
        url: "/api/channels",
        dataType: "json",
        success: function (data) {
            $.each(data, function (i, obj) {
                var selectValue = "<option value='" + obj.name + "'>" + obj.name + "</option>";
                $(selectValue).appendTo('#select');
            });
        }
    });

    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#subscribe").click(function () {
        subscribeToChannel($("#select").val());
    });
    $("#unsubscribe").click(function () {
        unsubscribeFromChannel($("#select").val());
    });
    $("#send").click(function () {
        sendNewsToChannel($("#select").val());
    });
});

