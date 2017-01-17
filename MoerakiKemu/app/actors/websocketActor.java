package actors;

import akka.actor.*;

import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.util.observer.ObserverObserver;
import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.IControllerPlayer;

public class websocketActor extends UntypedActor implements UserInterface, ObserverObserver {
    
    private IController controller;
    private IControllerPlayer controllerPlayer;
    
    public static Props props(ActorRef out){
        return Props.create(websocketActor.class, out);
    }
    
    private final ActorRef out;

    
    public websocketActor(ActorRef out, IController controller, IControllerPlayer controllerPlayer) {
        this.out = out;
        this.controller = controller;
        this.controllerPlayer = controllerPlayer;
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
    public void update() {
        out.tell(JsonRender.getBoardAsJSON(controller, controllerPlayer), self());
    }
    
    private String occupyAndGetBoard(String coord){
        int ex = coord.indexOf("-");
        int xy[] = {Integer.parseInt(coord.substring(0, ex)), Integer.parseInt(coord.substring(ex+1))};
        controller.occupy(xy[0],xy[1]);
        return JsonRender.getBoardAsJSON(controller, controllerPlayer);
    }
    
    
}