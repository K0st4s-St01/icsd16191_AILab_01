package org.icsd16191.gui;

import org.icsd16191.algorithms.*;
import org.icsd16191.problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Handler implements MouseListener {
    private Problem problem = new Problem(10,0.2d,5);
    private int size=32;
    private Algorithm algorithm = null;
    private java.util.List<Button> buttons = new ArrayList<>();
    private Thread algorythmThread;

    public Handler() {
        this.buttons.add(
                new Button(13,1,200,50,"new Problem") {
                    @Override
                    public void effect() {
                        problem=new Problem(10,0.2d,5);
                        try {
                            algorythmThread.join();
                        } catch (InterruptedException e) {
                           e.printStackTrace();
                        }
                        algorithm=null;
                    }
                }
        );
        this.buttons.add(
                new Button(13,3,200,50,"BestFirstSearch") {
                    @Override
                    public void effect() {
                        try {
                            if (algorythmThread!=null)
                                algorythmThread.join();
                        } catch (InterruptedException e) {
                           e.printStackTrace();
                        }
                        algorithm=null;
                        int sx = problem.getSx();
                        int sy = problem.getSy();
                        int tx0 = problem.getTx_0();
                        int ty0 = problem.getTy_0();
                        int tx1 = problem.getTx_1();
                        int ty1 = problem.getTy_1();
                        //initial , list(targets)
                        algorithm = new BestFirstSearch(
                                problem.getNodes()[sx][sy]
                                ,List.of(
                                        problem.getNodes()[tx0][ty0],
                                        problem.getNodes()[tx1][ty1]
                        )
                        );
                        algorythmThread = new Thread(() -> algorithm.run(problem));
                        algorythmThread.start();

                    }
                }
        );

        this.buttons.add(
                new Button(13,5,200,50,"BFS") {
                    @Override
                    public void effect() {
                        try {
                            if (algorythmThread!=null)
                                algorythmThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        algorithm=null;
                        int sx = problem.getSx();
                        int sy = problem.getSy();
                        int tx0 = problem.getTx_0();
                        int ty0 = problem.getTy_0();
                        int tx1 = problem.getTx_1();
                        int ty1 = problem.getTy_1();
                        //initial , list(targets)
                        algorithm = new BFS(
                                problem.getNodes()[sx][sy]
                                ,List.of(
                                problem.getNodes()[tx0][ty0],
                                problem.getNodes()[tx1][ty1]
                        )
                        );
                        algorythmThread = new Thread(() -> algorithm.run(problem));
                        algorythmThread.start();

                    }
                }
        );

        this.buttons.add(
                new Button(13,7,200,50,"UCS") {
                    @Override
                    public void effect() {
                        try {
                            if (algorythmThread!=null)
                                algorythmThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        algorithm=null;
                        int sx = problem.getSx();
                        int sy = problem.getSy();
                        int tx0 = problem.getTx_0();
                        int ty0 = problem.getTy_0();
                        int tx1 = problem.getTx_1();
                        int ty1 = problem.getTy_1();
                        //initial , list(targets)
                        algorithm = new UCS(
                                problem.getNodes()[sx][sy]
                                ,List.of(
                                problem.getNodes()[tx0][ty0],
                                problem.getNodes()[tx1][ty1]
                        )
                        );
                        algorythmThread = new Thread(() -> algorithm.run(problem));
                        algorythmThread.start();

                    }
                }
        );

        this.buttons.add(
                new Button(13,7,200,50,"A*") {
                    @Override
                    public void effect() {
                        try {
                            if (algorythmThread!=null)
                                algorythmThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        algorithm=null;
                        int sx = problem.getSx();
                        int sy = problem.getSy();
                        int tx0 = problem.getTx_0();
                        int ty0 = problem.getTy_0();
                        int tx1 = problem.getTx_1();
                        int ty1 = problem.getTy_1();
                        //initial , list(targets)
                        algorithm = new Astar(
                                problem.getNodes()[sx][sy]
                                ,List.of(
                                problem.getNodes()[tx0][ty0],
                                problem.getNodes()[tx1][ty1]
                        )
                        );
                        algorythmThread = new Thread(() -> algorithm.run(problem));
                        algorythmThread.start();

                    }
                }
        );
    }

    public void render(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,2000,2000);
        problem.render(g,size);
        for (var b : buttons){
            b.render(g,size);
        }
        if (algorithm!=null){
            algorithm.render(g,size);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!SwingUtilities.isLeftMouseButton(e)) return;
        for (var b:buttons){
            int x = e.getX();
            int y = e.getY();
            x/=size*2;
            y/=size;
            if(b.clicked(x,y)){
                b.effect();
                return;
            }
        }
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
}
