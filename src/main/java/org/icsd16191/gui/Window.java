package org.icsd16191.gui;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame implements Runnable{
    private Thread thread;
    private boolean running = false;
    private Handler handler=new Handler();

    public Window() throws HeadlessException {
        setTitle("icsd16191.Lab_01");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,900);
        setResizable(true);

        this.addMouseListener(handler);
        setVisible(true);
    }
    public void start(){
        running=true;
        this.thread = new Thread(this);
        thread.start();
    }
    public void stop() throws InterruptedException {
        running=false;
        thread.join();
    }
    private void render(){
        var bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            bs = this.getBufferStrategy();
        }
        var g=(Graphics2D) bs.getDrawGraphics();
        g.setFont(new Font("arial black",Font.BOLD,14));
        Toolkit.getDefaultToolkit().sync();
        handler.render(g);


        bs.show();
    }
    @Override
    public void run() {
        while (running){
            render();
        }
    }
}
