package ru.nsu.ccfit.morozov.wormio.view;

import ru.nsu.ccfit.morozov.wormio.client.Client;
import ru.nsu.ccfit.morozov.wormio.model.Action;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class PlayMouseHandler implements MouseListener, MouseMotionListener {

    private double speedRadius;
    Client client;
    JPanel window;



    public PlayMouseHandler(Client client, JPanel window) {

        this.window = window;
        this.client = client;
        this.speedRadius = window.getHeight() / 3;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        double wx = (double)window.getWidth() / 2;
        double wy = (double)window.getHeight() / 2;
        double dx =  e.getX() - wx;
        double dy =  e.getY() - wy;
        double angle = Math.atan2(dy,dx);
        double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        double speed = radius/speedRadius;
        if (radius > this.speedRadius)
            speed = 1;

        client.setAction(new Action(angle, speed,client.getToken()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        double wx = (double)window.getWidth() / 2;
        double wy = (double)window.getHeight() / 2;
        double dx =  e.getX() - wx;
        double dy =  e.getY() - wy;
        double angle = Math.atan2(dy,dx);
        double radius = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        double speed = radius/speedRadius;
        if (radius > this.speedRadius)
            speed = 1;

        client.setAction(new Action(angle, speed, client.getToken()));
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
