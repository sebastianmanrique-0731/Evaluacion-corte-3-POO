/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sebas
 */
public abstract class Carta implements ICombatible {

    protected String nombre;
    protected int puntosAtaque;
    protected int puntosDefensa;
    protected String posicion;
    protected boolean destruida;

    public static final String POS_ATAQUE = "ATAQUE";
    public static final String POS_DEFENSA = "DEFENSA";

    public abstract String activarEspecial();

    // Polimorfismo de Sobrecarga (1): recibe todos los datos base
    public void configurarDatos(String nombre, int atq, int def, String pos) {
        this.nombre = nombre;
        this.puntosAtaque = atq;
        this.puntosDefensa = def;
        this.posicion = pos;
        this.destruida = false;
    }

    // Polimorfismo de Sobrecarga (2): asume posición de ATAQUE por defecto
    public void configurarDatos(String nombre, int atq, int def) {
        configurarDatos(nombre, atq, def, POS_ATAQUE);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosAtaque() {
        return puntosAtaque;
    }

    public void setPuntosAtaque(int puntosAtaque) {
        this.puntosAtaque = puntosAtaque;
    }

    public int getPuntosDefensa() {
        return puntosDefensa;
    }

    public void setPuntosDefensa(int puntosDefensa) {
        this.puntosDefensa = puntosDefensa;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public boolean isDestruida() {
        return destruida;
    }

    public void setDestruida(boolean destruida) {
        this.destruida = destruida;
    }

    @Override
    public String toString() {
        return nombre + " [ATQ: " + puntosAtaque + " | DEF: " + puntosDefensa
                + " | POS: " + posicion + " | Destruida: " + (destruida ? "Sí" : "No") + "]";
    }
}
