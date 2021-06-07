/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.io.Serializable;

/**
 *
 * @author Citlalli
 */
public class Coordenada implements Serializable{
    private Integer x;
    private Integer y;
    private Jugador player;
    public Coordenada(Integer x, Integer y, Jugador player){
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    /**
     * @return the player
     */
    public Jugador getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Jugador player) {
        this.player = player;
    }
    
}
