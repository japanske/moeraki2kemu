package actors;

import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.controllerimpl.Controller;
import de.htwg.se.moerakikemu.controller.controllerimpl.ControllerPlayer;


public final class JsonRender {
    
    
    private static String getPlayer1AsJson(IController controller) {
    	return JsonEscapeValue("player1:") + JsonEscapeValue(controller.getPlayer1Name());
    }

    private static String getPlayer2AsJson(IController controller) {
    	return JsonEscapeValue("player2:") + JsonEscapeValue(controller.getPlayer2Name());
    }
    
    private static String getPlayer1PointsAsJson(IControllerPlayer playerController) {
    	return JsonEscapeValue("player1Points:") + playerController.getPlayer1Points();
    }
    
    private static String getPlayer2PointsAsJson(IControllerPlayer playerController) {
    	return JsonEscapeValue("player2Points:") + playerController.getPlayer2Points();
    }
    
    public static String getBoardAsJSON(IController controller, IControllerPlayer playerController) {
        int boardLength = controller.getEdgeLength();

        StringBuilder json = new StringBuilder('{');
        json.append(getPlayer1AsJson(controller)).append(", ");
        json.append(getPlayer2AsJson(controller)).append(", ");
        json.append(getPlayer1PointsAsJson(playerController)).append(", ");
        json.append(getPlayer2PointsAsJson(playerController)).append(", ");
        
        json.append(JsonEscapeValue("lines:"));

        json.append("[\n");

        for (int i = 0; i < boardLength; i++) {
            json.append(getJSONLine(i, controller));
            json.append(getDelimiterOrEmpty(boardLength, i));
        }
        json.append("]");

        return json.append("}").toString();
    }

    private static String getJSONLine(int lineNumber, IController controller) {
        String cellsObject = JsonEscapeValue("cells:");
    	int boardLength = controller.getEdgeLength();
    	
    	StringBuilder line = new StringBuilder("{");
    	line.append(cellsObject);
    	
    	line.append("[");
    	for (int j = 0; j < boardLength; j++) {
    		line.append(JsonEscapeValue(controller.getIsOccupiedByPlayer(lineNumber, j)));
    		line.append(getDelimiterOrEmpty(boardLength, j));
    	}
    	line.append("]\n");
    	
    	return line.append("}").toString();
    }
    
    private static String getDelimiterOrEmpty(int edgeLength, int pos) {
        String newPos = "";
        if(pos < edgeLength - 1){
            newPos = ", ";
        }
        return newPos;
    }

    private static String JsonEscapeValue(String value) {
        return "\"" + value + "\"";
    }
}
