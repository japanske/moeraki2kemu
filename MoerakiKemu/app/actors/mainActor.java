package actors;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Guice;
import com.google.inject.Injector;

import play.mvc.*;
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

public class mainActor {


    private IControllerPlayer controllerPlayer = null;
    private IController controller = null;
    private List<LegacyWebSocket<String>> websockets;

    public mainActor() {
        Injector injector = Guice.createInjector(new ControllerModuleWithController());

    	controllerPlayer = new ControllerPlayer();
    	controller = new Controller(8, controllerPlayer);

    	UserInterface[] interfaces;
    	interfaces = new UserInterface[2];
    	interfaces[0] = injector.getInstance(TextUI.class);

    	websockets = new ArrayList<LegacyWebSocket<String>>(2);
    }

    public LegacyWebSocket<String> getWebSockets() {
        int size = websockets.size();
        if(size == 0 || size == 1){
            websockets.add(WebSocket.withActor(websocketActor::props));
            return websockets.get(size);
        } else {
            return null;
        }
    }

}
