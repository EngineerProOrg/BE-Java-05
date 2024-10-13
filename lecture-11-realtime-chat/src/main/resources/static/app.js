const hostWithPort = window.location.host

const stompClient = new StompJs.Client({    
    brokerURL: `ws://${hostWithPort}/ws`
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/user/queue/messages', (greeting) => {
        console.log("got msg", JSON.parse(greeting.body));
        appendNewMessage(JSON.parse(greeting.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    const chatMessage = {
        'receiver': $("#userId").val(),
        'content': $("#messageContent").val(),
    };
    stompClient.publish({
        destination: "/app/chat",
        body: JSON.stringify(chatMessage)
    });
}

function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({ 'name': $("#messageContent").val() })
    });
}

function appendNewMessage(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});

