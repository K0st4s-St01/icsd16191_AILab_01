package org.icsd16191.gui;

import lombok.extern.slf4j.Slf4j;
import org.icsd16191.algorithms.*;
import org.icsd16191.problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
@Slf4j
public class Handler implements MouseListener {
    private Problem problem = new Problem(10,0.2d,5);
    private int size=32;
    private Algorithm algorithm = null;
    private java.util.List<Button> buttons = new ArrayList<>();
    private Thread algorythmThread;
    private Utillities.Result bfs = new Utillities.Result(0,0,0,0);
    private Utillities.Result ucs = new Utillities.Result(0,0,0,0);
    private Utillities.Result bestFirst = new Utillities.Result(0,0,0,0);
    private Utillities.Result astar = new Utillities.Result(0,0,0,0);

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
                new Button(13,9,200,50,"A*") {
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

        this.buttons.add(
                new Button(13,11,200,50,"Give me 50") {
                    @Override
                    public void effect() {
                        var problemList = new ArrayList<Problem>();
                        for (int i = 0; i < 50; i++) {
                            problemList.add(new Problem(10,0.2,5));
                        }
                        ExecutorService executor = Executors.newFixedThreadPool(4);
                        List<Future<Utillities.Result>> results_BFS = new ArrayList<>();
                        List<Future<Utillities.Result>> results_UCS = new ArrayList<>();
                        List<Future<Utillities.Result>> results_BestFirst = new ArrayList<>();
                        List<Future<Utillities.Result>> results_Astar = new ArrayList<>();
                        for (var prob : problemList) {
                            int sx = prob.getSx();
                            int sy = prob.getSy();
                            int tx0 = prob.getTx_0();
                            int ty0 = prob.getTy_0();
                            int tx1 = prob.getTx_1();
                            int ty1 = prob.getTy_1();

                            Future<Utillities.Result> task_0 = executor.submit(()->{
                                Utillities.Result result = null;
                                var algorithm = new BFS(prob.getNodes()[sx][sy]
                                        ,List.of(
                                        problem.getNodes()[tx0][ty0],
                                        problem.getNodes()[tx1][ty1]));
                                algorithm.setOptionPane(false);
                                var solution_node = algorithm.run(prob);
                                return algorithm.getSolutionCost();
                            });
                            results_BFS.add(task_0);

                            Future<Utillities.Result> task_1 = executor.submit(()->{
                                Utillities.Result result = null;
                                var algorithm = new BestFirstSearch(prob.getNodes()[sx][sy]
                                        ,List.of(
                                        problem.getNodes()[tx0][ty0],
                                        problem.getNodes()[tx1][ty1]));
                                algorithm.setOptionPane(false);
                                var solution_node = algorithm.run(prob);
                                return algorithm.getSolutionCost();
                            });
                            results_BestFirst.add(task_1);


                            Future<Utillities.Result> task_2 = executor.submit(()->{
                                Utillities.Result result = null;
                                var algorithm = new UCS(prob.getNodes()[sx][sy]
                                        ,List.of(
                                        problem.getNodes()[tx0][ty0],
                                        problem.getNodes()[tx1][ty1]));
                                algorithm.setOptionPane(false);
                                var solution_node = algorithm.run(prob);
                                return algorithm.getSolutionCost();
                            });
                            results_UCS.add(task_2);


                            Future<Utillities.Result> task_3 = executor.submit(()->{
                                Utillities.Result result = null;
                                var algorithm = new Astar(prob.getNodes()[sx][sy]
                                        ,List.of(
                                        problem.getNodes()[tx0][ty0],
                                        problem.getNodes()[tx1][ty1]));
                                algorithm.setOptionPane(false);
                                var solution_node = algorithm.run(prob);
                                return algorithm.getSolutionCost();
                            });
                            results_Astar.add(task_3);
                        }
                        bfs = new Utillities.Result(0,0,0,0);
                        for (var future : results_BFS) {
                            try {
                                bfs = bfs.add(future.get());
                                log.info("Computed result: {}", bfs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        ucs = new Utillities.Result(0,0,0,0);
                        for (var future : results_UCS) {
                            try {
                                ucs = ucs.add(future.get());
                                log.info("Computed result: {}", ucs);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                        bestFirst = new Utillities.Result(0,0,0,0);
                        for (var future : results_BestFirst) {
                            try {
                                bestFirst = bestFirst.add(future.get());
                                log.info("Computed result: {}", bestFirst);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        astar = new Utillities.Result(0,0,0,0);
                        for (var future : results_Astar) {
                            try {
                                astar = astar.add(future.get());
                                log.info("Computed result: {}", astar);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        executor.close();
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
        g.setColor(Color.BLACK);
        g.drawString("bfs: "+bfs,1100,100);
        g.drawString("ucs: "+ucs,1100,200);
        g.drawString("bf : "+bestFirst,1100,300);
        g.drawString("a* : "+astar,1100,400);
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
