/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sebas
 */
import java.util.ArrayList;
import java.util.List;

public class Jugador {

    public static final int LP_INICIALES_OFICIALES = 4000;
    public static int totalJugadoresRegistrados = 0;

    private String nombreDuelista;
    private int puntosVida;
    private List<Carta> monstruosEnCampo = new ArrayList<>(); // Asociación

    public void inicializarDuelista(String nombre, int lpIniciales) {
        this.nombreDuelista = nombre;
        this.puntosVida = lpIniciales;
        this.monstruosEnCampo.clear();
        totalJugadoresRegistrados++;
    }

    // Sobrecarga de método
    public void inicializarDuelista(String nombre) {
        inicializarDuelista(nombre, LP_INICIALES_OFICIALES);
    }

    public void reducirLP(int cantidad) {
        if (cantidad > 0) {
            this.puntosVida = Math.max(0, this.puntosVida - cantidad);
        }
    }

    public void invocarMonstruo(Carta monstruo) {
        this.monstruosEnCampo.clear();
        this.monstruosEnCampo.add(monstruo);
    }

    public Carta obtenerMonstruoActivo() {
        for (Carta c : monstruosEnCampo) {
            if (!c.isDestruida()) {
                return c;
            }
        }
        return null;
    }

    public void limpiarCampo() {
        this.monstruosEnCampo.clear();
    }

    public String obtenerNombreDuelista() {
        return nombreDuelista;
    }

    public int consultarLP() {
        return puntosVida;
    }
}
