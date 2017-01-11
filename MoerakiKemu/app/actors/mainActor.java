package actors;

import com.google.inject.Guice;
import com.google.inject.Injector;
import akka.actor.*;
import de.htwg.se.moerakikemu.controller.ControllerModuleWithController;
import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.controllerimpl.Controller;
import de.htwg.se.moerakikemu.controller.controllerimpl.ControllerPlayer;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.moerakikemu.view.viewimpl.TextUI;
import de.htwg.se.moerakikemu.view.viewimpl.gui.GUI;
import de.htwg.se.util.observer.ObserverObserver;
import de.htwg.se.util.observer.IObserverSubject;

public class mainActor extends UntypedActor implements UserInterface, ObserverObserver {


    private IControllerPlayer playerController = null;
    private IController controller = null;

    public static Props props(ActorRef out){
        return Props.create(mainActor.class, out);
    }

    private final ActorRef out;

    public mainActor(ActorRef out) {
        this.out = out;
        Injector injector = Guice.createInjector(new ControllerModuleWithController());


    	playerController = new ControllerPlayer();
    	controller = new Controller(8, playerController);

    	UserInterface[] interfaces;
    	interfaces = new UserInterface[2];
    	interfaces[0] = injector.getInstance(TextUI.class);
    	interfaces[1] = new GUI(controller, playerController);

    	for (int i = 0; i < interfaces.length; i++) {
    		((IObserverSubject) controller).attatch((ObserverObserver) interfaces[i]);
    		interfaces[i].drawCurrentState();
    	}
        ((IObserverSubject) controller).attatch((ObserverObserver) this);
    }

    @Override
    public void onReceive(Object msg) throws Throwable {
        if(msg instanceof String){
            final String message = (String) msg;

            String command = "setDot";
            if (message.startsWith(command) && message.length() > command.length()) {
                occupyAndGetBoard(message.substring(command.length() + 1, message.length() - 1));
            }
        }
    }
    
    @Override
    public void addPoints(int point0, int point1) {
    }

    @Override
    public void drawCurrentState() {
        
    }
    
    @Override
    public void printMessage(String msg) {
        
    }
    
    @Override
    public void queryPlayerName() {
        
    }
    
    @Override
    public void quit(){
        
    }
    
    @Override
    public void update(){
        out.tell(getBoardAsJSON(), self());
    }

    private String occupyAndGetBoard(String coord){
        int ex = coord.indexOf("-");
        int xy[] = {Integer.parseInt(coord.substring(0, ex)), Integer.parseInt(coord.substring(ex+1))};
        controller.occupy(xy[0],xy[1]);
        return getBoardAsJSON();
    }

    private String getBoardAsJSON(){
        String linesObject = "\"lines\":";
        final int boardLength = controller.getEdgeLength();

        StringBuilder json = new StringBuilder("{");
        json.append(linesObject);

        json.append("[\n");

        for(int i = 0; i < boardLength; i++){
            json.append(getLinesAsJSON(i));
            json.append(getDelOrEmpty(boardLength, i));
        }
        json.append("],\n");
        json.append("\"player1\":"+"\""+playerController.getPlayer1Name()+"\",\n");
        json.append("\"player2\":"+"\""+playerController.getPlayer2Name()+"\",\n");
        json.append("\"player1Points\":"+"\""+playerController.getPlayer1Points()+"\",\n");
        json.append("\"player2Points\":"+"\""+playerController.getPlayer2Points()+"\",\n");
        json.append("\"lastMove\":"+"\""+playerController.getCurrentPlayerName()+" hat gesetzt\"\n");
        System.out.println(json);

        return json.append("}").toString();
    }


    private String getLinesAsJSON(int pos){
        String cellsObject = "\"cells\":";
        int boardLength = controller.getEdgeLength();

        StringBuilder json = new StringBuilder("{");
        json.append(cellsObject);

        json.append("[");

        for(int i = 0; i < boardLength; i++){
            json.append("\"" + controller.getIsOccupiedByPlayer(pos, i) + "\"");
            json.append(getDelOrEmpty(boardLength, i));
        }

        json.append("]\n");

        return json.append("}").toString();
    }

    private String getDelOrEmpty(int edgeLength, int pos){
        String ret = "";
        if(pos < edgeLength-1){
            ret = ", ";
        }
        return ret;
    }
}
