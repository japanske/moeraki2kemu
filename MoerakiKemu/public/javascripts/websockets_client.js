$(document).ready(function() {
    $('.field').click(function(e) {
         socket.send('setDot' + JSON.stringify($(this).attr("id")));
        $('#clientText').append('Clicked on ' + $(this).attr("id") + "\n");
        console.log("You pressed " + $(this).attr("id"));
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
    if(event.data.substring(0,4) == 'win('){
        alert("Der Gewinner ist: " + event.data.substring(4, 15));
        console.log("Der Gewinner ist: " + event.data.substring(4, 15));
    }
    else if(event.data.substring(0,6) == 'alert('){
        alert(event.data.substring(7, event.data.length-1));
        console.log(event.data.substring(7, event.data.length-1));
    }
    else {
        var json = JSON.parse(event.data);
        var lines = json.lines;
        for(i = 0; i < lines.length; i++){
            var line = lines[i];
            var cells = line.cells;
            for(j = 0; j < cells.length; j++){
                var cell = cells[j];
                if(cell == "StartDot") {
                    $("#" + i + '-' + j).addClass('startDot');
                    $('#clientText').append('Die Moeraki-Kugel wurde auf ' + i + '-' + j + ' gesetzt.\n');
                    console.log("Die Moeraki-Kugel wurde auf " + i + "-" + j + " gesetzt.");
                }
                else if(cell == "Spieler 1") {
                    $("#" + i + '-' + j).addClass('player1Dot');
                    $('#clientText').append('Spieler 1 hat auf ' + i + '-' + j + ' gesetzt.\n');
                    console.log("Spieler 1 hat auf " + i + "-" + j + " gesetzt.");
                }
                else if(cell == "Spieler 2") {
                    $("#" + i + '-' + j).addClass('player2Dot');
                    $('#clientText').append('Spieler 2 hat auf ' + i + '-' + j + ' gesetzt.\n');
                    console.log("Spieler 2 hat auf " + i + "-" + j + " gesetzt.");
                }
            }        
        }
    }
}