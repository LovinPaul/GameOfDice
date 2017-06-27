package com.gameofdice.users.sessions;

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
@Table(name = "sessions")
public class UserSession implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    int id;
    @OneToOne
    private User player;
    @Column(name = "start_time")
    private long startTime;
    @Column(name = "end_time")
    private long endTime;


    public UserSession() {
    }

    public UserSession(User player) {
        this.player = player;
        this.startTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public User getPlayer() {
        return player;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isSessionEnded(){
        return endTime!=0;
    }
    
    public long getActiveTime() {
        if (endTime == 0) {
            return System.currentTimeMillis() - startTime;
        }else{
            return endTime - startTime;
        }
    }

    public void endSession() {
        if (endTime == 0) {
            endTime = System.currentTimeMillis();
        }else{
            throw new IllegalStateException();
        }
    }

}
