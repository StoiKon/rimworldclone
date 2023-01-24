/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package world;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import main.Handler;

/**
 *
 * @author xator
 */
public class SettlementControls implements MouseListener,MouseMotionListener,KeyListener{
    private Settlement currentSettlement;
    private Handler handler;
    private boolean enabled=false;

    public SettlementControls(Settlement currentSettlement, Handler handler) {
        this.currentSettlement = currentSettlement;
        this.handler = handler;
    }

    

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent me) {
        
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
        
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(enabled){
        int key=ke.getKeyCode();
        if(key==KeyEvent.VK_Q && handler.isInSettlement()){
            handler.enterWorld();
        }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        
    }

    public void setSettlement(Settlement currentSettlement) {
        this.currentSettlement=currentSettlement;
    }
    
}
