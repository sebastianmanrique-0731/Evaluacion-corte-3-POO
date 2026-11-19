/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sebas
 */
public class MonstruoNormal extends Carta {

    @Override
    public int obtenerPoderCombate() {
        return POS_ATAQUE.equalsIgnoreCase(this.posicion) ? this.puntosAtaque : this.puntosDefensa;
    }

    @Override
    public String mostrarResumen() {
        return "[Normal] " + toString();
    }

    @Override
    public String activarEspecial() {
        return nombre + " mantiene su postura normal sin efecto adicional.";
    }
}
