/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

/**
 *
 * @author Citlalli
 */
public class Semaforo{
    private Boolean turnoRojo;
    private Boolean turnoAzul;

    public Semaforo(Boolean turnoRojo, Boolean turnoAzul){
        this.turnoRojo = turnoRojo;
        this.turnoAzul = turnoAzul;
    }
    public Boolean getTurno(Jugador player){
        if(player.getEquipo() == Equipo.EquipoRojo){
            return turnoRojo;
        }else{
            return turnoAzul;
        }
    }
        public void setTurno(Jugador player, Boolean turno){
        if(player.getEquipo() == Equipo.EquipoRojo){
            turnoRojo = turno;
            turnoAzul = !turno;
        }else{
            turnoAzul = turno;
            turnoRojo = !turno;
        }
    }

    public Boolean getTurnoRojo() {
        return turnoRojo;
    }

    public void setTurnoRojo(Boolean turnoRojo) {
        this.turnoRojo = turnoRojo;
    }

    public Boolean getTurnoAzul() {
        return turnoAzul;
    }

    public void setTurnoAzul(Boolean turnoAzul) {
        this.turnoAzul = turnoAzul;
    }
}
