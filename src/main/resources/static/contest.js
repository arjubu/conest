'use strict';
var contestTable = document.querySelector('#contest-table');

var stompClient = null;

function connect(event) {
    //username = document.querySelector('#name').value.trim();
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    stompClient.subscribe('/topic/newContest', onMessageReceived);
}

function onError(error) {
    alert(error);
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var tableRef = document.getElementById('contest-table').getElementsByTagName('tbody')[0];
    console.log(message.id);
    tableRef.insertRow().innerHTML =
        "<td class=\"align-middle\">" +message.id+ "</td>"+
        "<td class=\"align-middle\">" +message.capacity+ "</td>"+
        "<td class=\"align-middle\">" +message.date+ "</td>"+
        "<td class=\"align-middle\">" +message.name+ "</td>"+
        "<td class=\"align-middle\">" +message.registrationAllowed+ "</td>"+
        "<td class=\"align-middle\">" +message.registrationFrom+ "</td>"+
        "<td class=\"align-middle\">" +message.registrationTo+ "</td>";
    //alert(message)
}