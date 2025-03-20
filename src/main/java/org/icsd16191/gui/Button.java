package org.icsd16191.gui;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
@AllArgsConstructor
@Slf4j
public abstract class Button {
    private int x,y,w,h;
    private String title;
    public abstract void effect();


    public void render(Graphics2D g,int size){
        g.setColor(Color.BLACK);
        g.fillRect(x*size*2,y*size,w,h);
        g.setColor(Color.WHITE);
        g.drawString(title,x*size*2+w/3,y*size+h/2);
    }
    public boolean clicked(int cx,int cy){
        if (cx >= x  && cx <= x + w/100 && cy >= y && cy <= y + h/100) {
            log.info("{} clicked",title);
            return true;
        }
        return false;
    }
}
