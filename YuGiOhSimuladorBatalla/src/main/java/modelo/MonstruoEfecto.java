/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sebas
 */
public class MonstruoEfecto extends Carta {

    private int bonoEfecto;

    // Único constructor en todo el diagrama para una subclase
    public MonstruoEfecto(String nombre, int puntosAtaque, int puntosDefensa, String posicion, int bonoEfecto) {
        this.configurarDatos(nombre, puntosAtaque, puntosDefensa, posicion);
        this.bonoEfecto = bonoEfecto;
    }

    @Override
    public int obtenerPoderCombate() {
        int base = POS_ATAQUE.equalsIgnoreCase(this.posicion) ? this.puntosAtaque : this.puntosDefensa;
        return base + bonoEfecto;
    }

    @Override
    public String mostrarResumen() {
        return "[Efecto +" + bonoEfecto + "] " + toString();
    }

    @Override
    public String activarEspecial() {
        return nombre + " activa su efecto aumentando +" + bonoEfecto + " puntos en batalla.";
    }
}
