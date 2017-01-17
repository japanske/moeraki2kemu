package controllers;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.CompletionStage;

import javax.inject.Singleton;

import com.google.inject.Guice;
import com.google.inject.Injector;

import actors.mainActor;
import akka.stream.javadsl.Flow;

import play.http.websocket.Message;
import play.libs.F.Either;
import play.*;
import play.mvc.*;
import play.mvc.Http.RequestHeader;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result login() {
        return ok(login.render(""));
    }
    public Result index() {

        return ok(index.render(""));
    }
    public Result gameHelp() {

        return ok(help.render());
    }
    
	public LegacyWebSocket<String> socket() {

        return WebSocket.withActor(mainActor::props);
    }
}
