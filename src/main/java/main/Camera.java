/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author xator
 */
public class Camera implements KeyListener {

    private int x;
    private int y;
    private int z;
    private int zoom;

    public Camera() {
        x = 0;
        y = 0;
        z = 0;
        zoom = 32;
    }

    public Camera(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public Camera(int x, int y, int zoom) {
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    public int getZoom() {
        return zoom;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

        int key = ke.getKeyCode();
        if (key == KeyEvent.VK_W) {
            this.y += this.zoom/4;
        }
        if (key == KeyEvent.VK_S) {
            this.y -= this.zoom/4;
        }
        if (key == KeyEvent.VK_A) {
            this.x += this.zoom/4;
        }
        if (key == KeyEvent.VK_D) {
            this.x -= this.zoom/4;
        }
        if (key == KeyEvent.VK_Z) {
            this.z -= 1;
        }
        if (key == KeyEvent.VK_X) {
            this.z += 1;
        }
        if (key == KeyEvent.VK_PAGE_UP && zoom > 10) {
            this.zoom = zoom - 2;
        }
        if (key == KeyEvent.VK_PAGE_DOWN && zoom < 256) {
            this.zoom = zoom + 2;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    public int getZ() {
        return z;
    }

    void setZoom(int i) {
        this.zoom=i;
    }

}
