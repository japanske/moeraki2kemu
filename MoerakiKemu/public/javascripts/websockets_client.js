$(document).ready(function() {
    $('.field').click(function(e) {
         socket.send('setDot' + JSON.stringify($(this).attr("id")));
        $('#clientText').text('Clicked on ' + $(this).attr("id"));
        console.log("You pressed " + $(this).attr("id"));
        return false;
    });
});

var socket = new WebSocket("ws://localhost:9000/ws");

socket.onopen = function(e) {
    $('#clientText').text("Verbindung hergestellt.");
    console.log("Verbindung hergestellt.");
};

socket.onerror = function(e) {
    $('#clientText').text("Leider ist ein Fehler aufgetreten!");
    console.log("Leider ist ein Fehler aufgetreten");
};

socket.onclose = function(e) {
    $('#clientText').text("Verbindung getrennt.");
    console.log("Verbindung getrennt");
};

socket.onmessage = function(e) {
    if(event.data.substring(0,4) == 'win('){
        alert("Der Gewinner ist: " + event.data.substring(4, 15));
        console.log("Der Gewinner ist: " + event.data.substring(4, 15));
    }
    else if(event.data.substring(0,6) == 'alert('){
        alert(event.data.substring(7, event.data.length-1));
        console.log(event.data.substring(7, event.data.length-1));
    }
    else {
        var json = JSON.parse(event.data)
        var lines = json.lines;
        for(i = 0; i < lines.length; i++){
            var line = lines[i];
            var cells = line.cells;
            for(j = 0; j < cells.length; j++){
                var cell = cells[j];
                if(cell == "StartDot") {
                    $("#" + i + '-' + j).addClass('startDot');
                    $('#clientText').text('Die Moeraki-Kugel wurde auf ' + i + '-' + j + ' gesetzt.');
                    console.log("Die Moeraki-Kugel wurde auf " + i + "-" + j + " gesetzt.");
                }
                else if(cell == "Spieler 1") {
                    $("#" + i + '-' + j).addClass('player1Dot');
                    $('#clientText').text('Spieler 1 hat auf ' + i + '-' + j + ' gesetzt.');
                    console.log("Spieler 1 hat auf " + i + "-" + j + " gesetzt.");
                }
                else if(cell == "Spieler 2") {
                    $("#" + i + '-' + j).addClass('player2Dot');
                    $('#clientText').text('Spieler 2 hat auf ' + i + '-' + j + ' gesetzt.');
                    console.log("Spieler 2 hat auf " + i + "-" + j + " gesetzt.");
                }
            }        
        }
    }
}