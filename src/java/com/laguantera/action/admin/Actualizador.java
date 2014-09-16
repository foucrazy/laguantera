package com.laguantera.action.admin;

import java.awt.*;

import java.util.Timer;

public class Actualizador extends Thread {

    /*Minutos que transcurren entre cada aviso*/
    public int minutos = 1440;
    Timer timer;
    public TrayIcon trayIcon = null;

    //CONSTRUCTOR
    public Actualizador() {
    }

    @Override
    public void run() {
        timer = new Timer(true); // Que sea como un demonio
        timer.schedule(new UpdateEESSTimer(), this.minutos * 60 * 1000, this.minutos * 60 * 1000);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Actualizador main = new Actualizador();
        main.start();
    }
}
