$(document).ready(function() {
    $('.field').click(function(e) {
         socket.send('setDot' + JSON.stringify($(this).attr("id")));
        return false;
    });
});

var socket = new WebSocket("ws://localhost:9000/ws");

socket.onopen = function(e) {
    $('#clientText').append("Verbindung hergestellt.\n");
    console.log("Verbindung hergestellt.");
};

socket.onerror = function(e) {
    $('#clientText').append("Leider ist ein Fehler aufgetreten!\n");
    console.log("Leider ist ein Fehler aufgetreten");
};

socket.onclose = function(e) {
    $('#clientText').append("Verbindung getrennt.\n");
    console.log("Verbindung getrennt");
};

socket.onmessage = function(e) {
    var json = JSON.parse(e.data);
    var lines = json.lines;
    var player1 = json.player1;
    var player2 = json.player2;
    var player1Points = json.player1Points;
    var player2Points = json.player2Points;
    var lastMove = json.lastMove;
    
    console.log(json);
    
    console.log(player1Points);
    console.log(player2Points);
    
    for(i = 0; i < lines.length; i++){
        var line = lines[i];
        var cells = line.cells;
        for(j = 0; j < cells.length; j++){
            var cell = cells[j];
            if(cell == "StartDot") {
                $('#' + i + '-' + j).addClass('startDot');
            }
            else if(cell == player1) {
                $('#' + i + '-' + j).addClass('player1Dot');
            }
            else if(cell == player2) {
                $('#' + i + '-' + j).addClass('player2Dot');
            }
        }        
    }
    
    $('#player1Name').text(player1);
    $('#player2Name').text(player2);
    $('#player1Counter').text(player1Points);
    $('#player2Counter').text(player2Points);
    $('#clientText').append(lastMove+"\n");
}