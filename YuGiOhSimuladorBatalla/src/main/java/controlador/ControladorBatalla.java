/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Carta;
import modelo.Jugador;
import modelo.MonstruoEfecto;
import modelo.MonstruoNormal;
import modelo.MotorCombate;
import vista.VistaBatalla;

/**
 *
 * @author sebas
 */

public class ControladorBatalla implements ActionListener {
    private VistaBatalla vista;
    private MotorCombate motor;
    private Jugador jugador1;
    private Jugador jugador2;

    public void iniciarControlador(VistaBatalla vista, MotorCombate motor) {
        this.vista = vista;
        this.motor = motor;
        this.jugador1 = new Jugador();
        this.jugador1.inicializarDuelista("Jugador 1 (Yugi)");
        this.jugador2 = new Jugador();
        this.jugador2.inicializarDuelista("Jugador 2 (Kaiba)");

        this.vista.obtenerBtnInvocarJ1().addActionListener(this);
        this.vista.obtenerBtnInvocarJ2().addActionListener(this);
        this.vista.obtenerBtnAtacarJ1().addActionListener(this);
        this.vista.obtenerBtnAtacarJ2().addActionListener(this);
        this.vista.obtenerBtnReiniciar().addActionListener(this);

        actualizarInterfaz();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == vista.obtenerBtnInvocarJ1()) {
                invocarCarta(jugador1,
                        vista.obtenerTxtNombreC1().getText(),
                        vista.obtenerTxtAtqC1().getText(),
                        vista.obtenerTxtDefC1().getText(),
                        vista.obtenerCmbPosicionC1().getSelectedItem().toString(),
                        vista.obtenerChkEfectoC1().isSelected());
            } else if (e.getSource() == vista.obtenerBtnInvocarJ2()) {
                invocarCarta(jugador2,
                        vista.obtenerTxtNombreC2().getText(),
                        vista.obtenerTxtAtqC2().getText(),
                        vista.obtenerTxtDefC2().getText(),
                        vista.obtenerCmbPosicionC2().getSelectedItem().toString(),
                        vista.obtenerChkEfectoC2().isSelected());
            } else if (e.getSource() == vista.obtenerBtnAtacarJ1()) {
                realizarCombate(jugador1, jugador2);
            } else if (e.getSource() == vista.obtenerBtnAtacarJ2()) {
                realizarCombate(jugador2, jugador1);
            } else if (e.getSource() == vista.obtenerBtnReiniciar()) {
                jugador1.inicializarDuelista("Jugador 1 (Yugi)");
                jugador2.inicializarDuelista("Jugador 2 (Kaiba)");
                vista.obtenerTxtAreaHistorial().setText("=== NUEVO DUELO INICIADO ===\n");
                actualizarInterfaz();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Validación: Los puntos de ATQ y DEF deben ser valores numéricos enteros.",
                    "Error de Formato", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void invocarCarta(Jugador jugador, String nombre, String atqStr, String defStr, String pos, boolean esEfecto) {
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe ingresar el nombre del monstruo.");
            return;
        }
        int atq = Integer.parseInt(atqStr.trim());
        int def = Integer.parseInt(defStr.trim());
        if (atq < 0 || def < 0) {
            JOptionPane.showMessageDialog(vista, "ATQ y DEF no pueden ser negativos.");
            return;
        }

        Carta nuevaCarta;
        if (esEfecto) {
            nuevaCarta = new MonstruoEfecto(nombre.trim(), atq, def, pos, 300);
        } else {
            MonstruoNormal normal = new MonstruoNormal();
            normal.configurarDatos(nombre.trim(), atq, def, pos);
            nuevaCarta = normal;
        }
        jugador.invocarMonstruo(nuevaCarta);
        vista.obtenerTxtAreaHistorial().append("-> " + jugador.obtenerNombreDuelista() 
                + " invocó a: " + nuevaCarta.mostrarResumen() + "\n");
        actualizarInterfaz();
    }

    private void realizarCombate(Jugador atacante, Jugador defensor) {
        Carta cAtacante = atacante.obtenerMonstruoActivo();
        Carta cDefensor = defensor.obtenerMonstruoActivo();

        String resultado = motor.ejecutarAtaque(atacante, cAtacante, defensor, cDefensor);
        vista.obtenerTxtAreaHistorial().append(resultado + "\n");
        actualizarInterfaz();

        if (jugador1.consultarLP() <= 0 || jugador2.consultarLP() <= 0) {
            String ganador = (jugador1.consultarLP() > 0) 
                    ? jugador1.obtenerNombreDuelista() : jugador2.obtenerNombreDuelista();
            JOptionPane.showMessageDialog(vista, "¡Fin del Duelo! Ganador: " + ganador);
        }
    }

    private void actualizarInterfaz() {
        vista.obtenerLblLpJ1().setText("LP: " + jugador1.consultarLP());
        vista.obtenerLblLpJ2().setText("LP: " + jugador2.consultarLP());

        Carta c1 = jugador1.obtenerMonstruoActivo();
        Carta c2 = jugador2.obtenerMonstruoActivo();

        vista.obtenerLblCartaActivaJ1().setText(c1 != null ? c1.mostrarResumen() : "Sin monstruo en campo");
        vista.obtenerLblCartaActivaJ2().setText(c2 != null ? c2.mostrarResumen() : "Sin monstruo en campo");
    }
}