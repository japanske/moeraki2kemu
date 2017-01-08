package actor;

import com.google.inject.Guice;
import com.google.inject.Injector;

import akka.actor.*;
import de.htwg.se.moerakikemu.controller.ControllerModuleWithController;
import de.htwg.se.moerakikemu.controller.IController;
import de.htwg.se.moerakikemu.controller.controllerimpl.Controller;
import de.htwg.se.moerakikemu.view.UserInterface;
import de.htwg.se.moerakikemu.view.viewimpl.TextUI;
import de.htwg.se.moerakikemu.view.viewimpl.gui.GUI;
import de.htwg.se.util.observer.ObserverObserver;
import de.htwg.se.util.observer.IObserverSubject;

import de.htwg.se.moerakikemu.controller.IControllerPlayer;
import de.htwg.se.moerakikemu.controller.controllerimpl.ControllerPlayer;

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
        out.tell("", self());
    }
    
    @Override
    public void onReceive(Object msg) throws Throwable {
        
    }
    
}
