"use strict";

//Sort of like a HashMap holder to correlate subscribed&active channels
var activeSubscriptions = {};
var $name = jQuery("#name");
var $description = jQuery("#description");

var channel = {
    name: $name.val(),
    description: $description.val()
};

function toggleMenu() {
    /* Toggle between adding and removing the "active" class,
     to highlight the button that controls the panel */
    var button = document.getElementById("accordion");
    button.classList.toggle("active");

    /* Toggle between hiding and showing the active panel */
    var panel = document.getElementById("panel");
    if (panel.style.maxHeight) {
        panel.style.maxHeight = null;
    } else {
        panel.style.maxHeight = panel.scrollHeight + 'px';
    }
}


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


    $("#addChannel").click(function () {
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: '/api/channels',
            dataType: "json",
            timeout: 100000,
            data: JSON.stringify(channel),
            success: function (newChannel) {
                var selectValue = "<option value='" + newChannel.name + "'>" + newChannel.name + "</option>";
                $(selectValue).appendTo('#select');
            }
        });
    });


    $("#deleteChannel").click(function () {
        $.ajax({
            type: 'DELETE',
            url: '/api/channels/' + $('#name').val(),
            dataType: "json"
        });
    });

    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#subscribe").click(function () {
        $("div.loader").show();
        subscribeToChannel($("#select").val());
    });
    $("#unsubscribe").click(function () {
        $("div.loader").hide();
        unsubscribeFromChannel($("#select").val());
    });
    $("#send").click(function () {
        sendNewsToChannel($("#select").val());
    });
});

