package com.gameofdice.data;

import com.gameofdice.games.Game;
import com.gameofdice.users.User;
import java.util.List;

public interface Persistence {
    
    void saveObj(Object object);
    void updateObject(Object object);
    User getUser(String userName);
    List<Game> getLastGames(String userName, int nrOfGames);
}
