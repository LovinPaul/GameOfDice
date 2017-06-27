package com.gameofdice.games;

import com.gameofdice.users.User;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "games")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    int id;
    @OneToOne
    private User player;
    @Column(name = "player_dice_1")
    private int playerDice1;
    @Column(name = "player_dice_2")
    private int playerDice2;
    @Column(name = "npc_dice_1")
    private int npcDice1;
    @Column(name = "npc_dice_2")
    private int npcDice2;
    @Column(name = "start_time")
    private long startTime;

    public Game() {
    }

    public Game(User player, long startTime) {
        this.player = player;
        this.startTime = startTime;
    }

    public void throwDice() {
        playerDice1 = (int) (Math.random() * 6 + 1);
        playerDice2 = (int) (Math.random() * 6 + 1);
        npcDice1 = (int) (Math.random() * 6 + 1);
        npcDice2 = (int) (Math.random() * 6 + 1);
    }

    public int getId() {
        return id;
    }

    public int getPlayerID() {
        return player.getId();
    }

    public int getPlayerDice1() {
        return playerDice1;
    }

    public int getPlayerDice2() {
        return playerDice2;
    }

    public int getNpcDice1() {
        return npcDice1;
    }

    public int getNpcDice2() {
        return npcDice2;
    }

    public int getPlayerScore() {
        return playerDice1 + playerDice2;
    }

    public int getNPCScore() {
        return npcDice1 + npcDice2;
    }

    public String getWinnerName() {
        return getPlayerScore() == getNPCScore() ? " Equality "
                : getPlayerScore() > getNPCScore() ? " Player " : " NPC ";

    }

    public long getStartTime() {
        return startTime;
    }

}
