package controllers;

import javax.inject.Singleton;

import play.mvc.*;

import views.html.*;
import actors.lobbyActor;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    private lobbyActor lobbyActor = new LobbyActor();
    
        public Result lobby() {
            return ok(lobby.render(lobbyActor.getNumberOfGames()));
        }
    
    public Result login() {
        return ok(login.render());
    }
    public Result index(int gameIndex) {

        return ok(index.render(gameIndex));
    }
    public Result gameHelp() {

        return ok(help.render());
    }
    
	public LegacyWebSocket<String> socket(int gameIndex) {

        return  lobbyActor.getSocket(gameIndex);
    }
}
