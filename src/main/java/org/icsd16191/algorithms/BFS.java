package org.icsd16191.algorithms;

import lombok.extern.slf4j.Slf4j;
import org.icsd16191.problem.Problem;

import java.awt.*;
import java.util.*;
import java.util.List;

@Slf4j
public class BFS implements Algorithm{
    private Problem.Node initial;
    private ArrayDeque<Problem.Node> frontier;
    private HashSet<Problem.Node> reached;//path cost
    Problem.Node closestTarget = null;
    Problem.Node solution = null;
    @Override
    public void render(Graphics2D g, int size) {

            var solution_ptr = solution;
            if (solution!=null){
                for(var node : reached){
                    g.setColor(Color.YELLOW);
                    g.fillRect(node.getX() * size * 2 + 100, node.getY() * size * 2 + 100, size + 10, size + 10);
                }
            }
            while (solution_ptr != null && solution_ptr.getParent() != null) {
                if (solution_ptr.getState().equals(Problem.State.TARGET)){
                    g.setColor(Color.RED);
                }else{
                    g.setColor(Color.MAGENTA);
                }
                g.fillRect(solution_ptr.getX() * size * 2 + 100, solution_ptr.getY() * size * 2 + 100, size + 2, size + 2);
                solution_ptr = solution_ptr.getParent();
            }
            if (solution_ptr!= null){
                g.setColor(Color.green);
                g.fillRect(solution_ptr.getX() * size * 2 + 100, solution_ptr.getY() * size * 2 + 100, size + 2, size + 2);
            }
    }

    public BFS(Problem.Node initial, java.util.List<Problem.Node> targets){
        this.initial = initial;
        double minDistance = Integer.MAX_VALUE;
        for (var target:targets){
            double distance = Math.hypot(target.getX()-initial.getX(),target.getY()-initial.getY());
            if(minDistance > distance){
                minDistance = distance;
                closestTarget = target;
            }
        }
        this.frontier = new ArrayDeque<>();
        this.reached = new HashSet<>();
    }

    @Override
    public Problem.Node run(Problem problem) {
        var node = initial;
        frontier.add(initial);
        reached.add(initial);
        while (!frontier.isEmpty()){
            node = frontier.poll();
            log.info("BFS search running current = {}",node);
            if(node.getState().equals(Problem.State.TARGET)){
                solution=node;
                log.info("FOUND SOLUTION NODE {}",node);
                return node;
            }
            for (var child : expand(problem,node)){
                if(!reached.contains(child.getNode())){
                    var childNode = child.getNode();
                    childNode.setParent(node);
                    if(childNode!=null) {
                        reached.add(childNode);
                        frontier.add(childNode);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<Problem.Line> expand(Problem problem, Problem.Node node) {
        var list = new ArrayList<Problem.Line>();
        for (int i=0;i<4;i++){
            if (node.getLines()[i]!=null ){
                if (!reached.contains(node.getLines()[i].getNode()))
                    list.add(node.getLines()[i]);
            }
        }
        return list;
    }



}
