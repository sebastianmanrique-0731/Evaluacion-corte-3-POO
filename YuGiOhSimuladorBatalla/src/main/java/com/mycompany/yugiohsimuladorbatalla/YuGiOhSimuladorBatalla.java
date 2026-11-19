 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.yugiohsimuladorbatalla;

import controlador.ControladorBatalla;
import java.util.ArrayList;
import java.util.List;
import modelo.Carta;
import modelo.MonstruoEfecto;
import modelo.MonstruoNormal;
import modelo.MotorCombate;
import vista.VistaBatalla;
/**
 *
 * @author sebas
 */

public class YuGiOhSimuladorBatalla {

    public static void main(String[] args) {
        // Estructura dinámica para generar los 2 reportes exigidos en la rúbrica
        List<Carta> coleccionCartas = new ArrayList<>();

        MonstruoNormal c1 = new MonstruoNormal();
        c1.configurarDatos("Dragón Blanco de Ojos Azules", 3000, 2500, Carta.POS_ATAQUE);
        coleccionCartas.add(c1);

        coleccionCartas.add(new MonstruoEfecto("Mago Oscuro", 2500, 2100, Carta.POS_ATAQUE, 300));

        MonstruoNormal c3 = new MonstruoNormal();
        c3.configurarDatos("Guardián Celta", 1400, 1200, Carta.POS_DEFENSA);
        coleccionCartas.add(c3);

        coleccionCartas.add(new MonstruoEfecto("Kuriboh Alado", 300, 200, Carta.POS_DEFENSA, 500));

        generarReporteCartasEnAtaque(coleccionCartas);
        generarReporteEstadisticasPoder(coleccionCartas);

        java.awt.EventQueue.invokeLater(() -> {
            VistaBatalla vista = new VistaBatalla();
            MotorCombate motor = new MotorCombate();
            ControladorBatalla ctrl = new ControladorBatalla();
            ctrl.iniciarControlador(vista, motor);
            vista.setLocationRelativeTo(null);
            vista.setVisible(true);
        });
    }

    // REPORTE 1: Monstruos en posición de ATAQUE y su efecto polimórfico
    public static void generarReporteCartasEnAtaque(List<Carta> lista) {
        System.out.println("=== REPORTE 1: MONSTRUOS EN POSICIÓN DE ATAQUE ===");
        for (Carta c : lista) {
            if (Carta.POS_ATAQUE.equalsIgnoreCase(c.getPosicion())) {
                System.out.println(" - " + c.mostrarResumen() + " | " + c.activarEspecial());
            }
        }
        System.out.println();
    }

    // REPORTE 2: Monstruo de mayor poder de combate y promedio general de ATQ
    public static void generarReporteEstadisticasPoder(List<Carta> lista) {
        System.out.println("=== REPORTE 2: ESTADÍSTICAS DE PODER (ESTRUCTURA DINÁMICA) ===");
        if (lista.isEmpty()) return;

        Carta masFuerte = lista.get(0);
        double sumaAtaque = 0;

        for (Carta c : lista) {
            sumaAtaque += c.getPuntosAtaque();
            if (c.obtenerPoderCombate() > masFuerte.obtenerPoderCombate()) {
                masFuerte = c;
            }
        }
        double promedio = sumaAtaque / lista.size();
        System.out.println(" - Carta con mayor poder efectivo: " + masFuerte.getNombre() 
                + " (" + masFuerte.obtenerPoderCombate() + " pts)");
        System.out.println(" - Promedio de ATQ base: " + promedio + " pts");
        System.out.println("==============================================================\n");
    }
}