const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    // stompClient.subscribe('/topic/greetings', (greeting) => {
    //     showGreeting(JSON.parse(greeting.body).content);
    // });
    stompClient.subscribe('/topic/rankings', (greeting) => {
        body = JSON.parse(greeting.body);
        console.log("body", body);
        message = "<tr><td>Score</td><td>Name</td></tr>";
        for (let i = 0; i < body.length; i++) {
            message += "<tr><td>" + body[i].score + "</td><td>" + body[i].name + "</td></tr>";
        }
        $("#greetings").html(message);
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

function updateScore() {
    // stompClient.publish({
    //     destination: "/app/hello",
    //     body: JSON.stringify({ 'name': $("#name").val() })
    // });
    $.ajax({
        type: 'POST',
        url: '/api/rankings/update', // Specify the URL where you want to send the request
        contentType: 'application/json',
        data: JSON.stringify({
            "user": $("#name").val(),
            "score": parseFloat($("#score").val())
        }), // Specify the data to be sent
        success: function (response) {
            // Handle the response from the server
            $('#result').html("Success");
        },
        error: function (xhr, status, error) {
            // Handle errors
            $('#result').html("Fail");
            console.error(xhr.responseText);
        }
    });
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => updateScore());
});

