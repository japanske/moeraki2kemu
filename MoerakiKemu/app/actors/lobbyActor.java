package actors;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import play.mvc.LegacyWebSocket;

@Singleton
public class lobbyActor {
    
    private List<mainActor> games;
    
    public lobbyActor() {
        games = new ArrayList<mainActor>();
    }
    
    public LegacyWebSocket<String> getSocket(int index){
        if(index < 0 || index >= games.size()){
            return null;
        }
        else {
            return games.get(index).getWebSockets();
        }
    }
    
    public int startGame() {
        games.add(new mainActor());
        return games.size() - 1;
    }
    
    public int getNumberOfGames(){
        return games.size();
    }
    
}