/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sebas
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MotorCombate {

    public static final String ARCHIVO_HISTORIAL = "historial_combate.txt";
    private List<String> registroTurnos = new ArrayList<>();

    // Cálculo matemático independiente para validación con JUnit
    public int calcularDano(Carta atacante, Carta defensor) {
        if (atacante == null || !Carta.POS_ATAQUE.equalsIgnoreCase(atacante.getPosicion())) {
            return 0;
        }
        int atqAtacante = atacante.obtenerPoderCombate();

        // Ataque Directo
        if (defensor == null || defensor.isDestruida()) {
            return atqAtacante;
        }

        int poderDefensor = defensor.obtenerPoderCombate();

        // Ataque vs Ataque
        if (Carta.POS_ATAQUE.equalsIgnoreCase(defensor.getPosicion())) {
            return Math.abs(atqAtacante - poderDefensor);
        } else {
            // Ataque vs Defensa
            if (atqAtacante < poderDefensor) {
                return poderDefensor - atqAtacante;
            }
            return 0;
        }
    }

    // Relación de Dependencia con Jugador y Carta
    public String ejecutarAtaque(Jugador jAtacante, Carta cAtacante, Jugador jDefensor, Carta cDefensor) {
        if (cAtacante == null || cAtacante.isDestruida()) {
            return "El duelista " + jAtacante.obtenerNombreDuelista() + " no tiene un monstruo activo para atacar.";
        }
        if (!Carta.POS_ATAQUE.equalsIgnoreCase(cAtacante.getPosicion())) {
            return "El monstruo " + cAtacante.getNombre() + " está en DEFENSA y no puede atacar.";
        }

        int atq = cAtacante.obtenerPoderCombate();
        int dano = calcularDano(cAtacante, cDefensor);
        String resultado;

        // 1. Ataque Directo
        if (cDefensor == null || cDefensor.isDestruida()) {
            jDefensor.reducirLP(dano);
            resultado = "ATAQUE DIRECTO: " + cAtacante.getNombre() + " (" + atq + ") inflige "
                    + dano + " de daño directo a " + jDefensor.obtenerNombreDuelista()
                    + " (LP: " + jDefensor.consultarLP() + ").";
        } // 2. Ataque vs Ataque
        else if (Carta.POS_ATAQUE.equalsIgnoreCase(cDefensor.getPosicion())) {
            int atqDef = cDefensor.obtenerPoderCombate();
            if (atq > atqDef) {
                cDefensor.setDestruida(true);
                jDefensor.reducirLP(dano);
                resultado = "ATQ vs ATQ: " + cAtacante.getNombre() + " (" + atq + ") destruye a "
                        + cDefensor.getNombre() + " (" + atqDef + "). " + jDefensor.obtenerNombreDuelista()
                        + " pierde " + dano + " LP (LP: " + jDefensor.consultarLP() + ").";
            } else if (atq < atqDef) {
                cAtacante.setDestruida(true);
                jAtacante.reducirLP(dano);
                resultado = "ATQ vs ATQ: " + cAtacante.getNombre() + " (" + atq + ") es destruido por "
                        + cDefensor.getNombre() + " (" + atqDef + "). " + jAtacante.obtenerNombreDuelista()
                        + " pierde " + dano + " LP (LP: " + jAtacante.consultarLP() + ").";
            } else {
                cAtacante.setDestruida(true);
                cDefensor.setDestruida(true);
                resultado = "EMPATE ATQ vs ATQ: Ambos monstruos (" + atq + ") se destruyen. Daño: 0.";
            }
        } // 3. Ataque vs Defensa
        else {
            int def = cDefensor.obtenerPoderCombate();
            if (atq > def) {
                cDefensor.setDestruida(true);
                resultado = "ATQ vs DEF: " + cAtacante.getNombre() + " (" + atq + ") destruye a "
                        + cDefensor.getNombre() + " en defensa (" + def + "). Daño a LP: 0.";
            } else if (atq < def) {
                jAtacante.reducirLP(dano);
                resultado = "ATQ vs DEF: " + cDefensor.getNombre() + " (" + def + ") resiste el ataque de "
                        + cAtacante.getNombre() + " (" + atq + "). " + jAtacante.obtenerNombreDuelista()
                        + " pierde " + dano + " LP (LP: " + jAtacante.consultarLP() + ").";
            } else {
                resultado = "ATQ vs DEF: " + cAtacante.getNombre() + " (" + atq + ") empata contra la defensa de "
                        + cDefensor.getNombre() + " (" + def + "). Nadie es destruido. Daño: 0.";
            }
        }

        registroTurnos.add(resultado);
        guardarEnHistorialTxt(resultado);
        return resultado;
    }

    public void guardarEnHistorialTxt(String linea) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_HISTORIAL, true))) {
            bw.write("[" + timestamp + "] " + linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en " + ARCHIVO_HISTORIAL + ": " + e.getMessage());
        }
    }

    public List<String> obtenerHistorialMemoria() {
        return registroTurnos;
    }
}
