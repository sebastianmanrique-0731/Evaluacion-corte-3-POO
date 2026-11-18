/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author sebas
 */
public class MotorCombateTest {

    @Test
    public void testAtaqueVsAtaqueMayor() {
        MotorCombate motor = new MotorCombate();
        Jugador j1 = new Jugador();
        j1.inicializarDuelista("Yugi", 4000);
        Jugador j2 = new Jugador();
        j2.inicializarDuelista("Kaiba", 4000);

        MonstruoNormal c1 = new MonstruoNormal();
        c1.configurarDatos("Dragón Blanco", 3000, 2500, Carta.POS_ATAQUE);

        MonstruoNormal c2 = new MonstruoNormal();
        c2.configurarDatos("Mago Oscuro", 2500, 2100, Carta.POS_ATAQUE);

        int danoCalculado = motor.calcularDano(c1, c2);
        assertEquals(500, danoCalculado);

        motor.ejecutarAtaque(j1, c1, j2, c2);
        assertEquals(3500, j2.consultarLP());
        assertTrue(c2.isDestruida());
        assertFalse(c1.isDestruida());
    }

    @Test
    public void testAtaqueVsDefensaMenorRecibeDanoAtacante() {
        MotorCombate motor = new MotorCombate();
        Jugador j1 = new Jugador();
        j1.inicializarDuelista("Joey", 4000);
        Jugador j2 = new Jugador();
        j2.inicializarDuelista("Yugi", 4000);

        MonstruoNormal c1 = new MonstruoNormal();
        c1.configurarDatos("Espadachín de la Llama", 1800, 1600, Carta.POS_ATAQUE);

        MonstruoNormal c2 = new MonstruoNormal();
        c2.configurarDatos("Elfo Místico", 800, 2000, Carta.POS_DEFENSA);

        int dano = motor.calcularDano(c1, c2);
        assertEquals(200, dano);

        motor.ejecutarAtaque(j1, c1, j2, c2);
        assertEquals(3800, j1.consultarLP());
        assertEquals(4000, j2.consultarLP());
        assertFalse(c2.isDestruida());
    }

    @Test
    public void testAtaqueDirectoConMonstruoEfecto() {
        MotorCombate motor = new MotorCombate();
        Jugador j1 = new Jugador();
        j1.inicializarDuelista("Yugi", 4000);
        Jugador j2 = new Jugador();
        j2.inicializarDuelista("Marik", 4000);

        Carta c1 = new MonstruoEfecto("Maga Oscura", 2000, 1700, Carta.POS_ATAQUE, 300);

        int dano = motor.calcularDano(c1, null);
        assertEquals(2300, dano);

        motor.ejecutarAtaque(j1, c1, j2, null);
        assertEquals(1700, j2.consultarLP());
    }
}
