package org.icsd16191.algorithms;

import lombok.extern.slf4j.Slf4j;
import org.icsd16191.problem.Problem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

@Slf4j
public class Astar implements Algorithm{
    private Problem.Node initial;
    private PriorityQueue<Problem.Line> frontier;
    private HashMap<Problem.Node,Integer> reached;//path cost
    Problem.Node closestTarget = null;
    Problem.Node solution = null;
    private Utillities.Result solutionCost = null;

    @Override
    public void render(Graphics2D g, int size) {

        var solution_ptr = solution;
        if (solution!=null){
            for(var node : reached.keySet()){
                g.setColor(Color.YELLOW);
                g.fillRect(node.getX() * size * 2 + 100, node.getY() * size * 2 + 100, size + 10, size + 10);
            }
        }
        while (solution_ptr != null && solution_ptr.getParent() != null) {
            if (solution_ptr.getState().equals(Problem.State.TARGET)){
                g.setColor(Color.RED);
            }else if(solution_ptr.getState().equals(Problem.State.INITIAL)){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.MAGENTA);
            }
            g.fillRect(solution_ptr.getX() * size * 2 + 100, solution_ptr.getY() * size * 2 + 100, size + 2, size + 2);
            solution_ptr = solution_ptr.getParent();
        }
        if (solutionCost != null) {
            g.setColor(Color.RED);
            g.drawString(""+this.solutionCost,50,50);
        }
    }

    public Astar(Problem.Node initial, java.util.List<Problem.Node> targets){
        initial.setParent(null);
        this.initial = initial;
        double minDistance = Double.MAX_VALUE;
        for (var target:targets){
            double distance = Math.hypot(target.getX()-initial.getX(),target.getY()-initial.getY());
            if(minDistance > distance){
                minDistance = distance;
                closestTarget = target;
            }
        }
        this.frontier = new PriorityQueue<>((o1, o2) -> Integer.compare(
                Math.abs(closestTarget.getX()-o1.getNode().getX()) + Math.abs(closestTarget.getY()-o1.getNode().getY()) + o1.getCost(),
                Math.abs(closestTarget.getX()-o2.getNode().getX()) + Math.abs(closestTarget.getY()-o2.getNode().getY()) + o2.getCost()
        ));
        this.reached = new HashMap<>();
    }

    @Override
    public Problem.Node run(Problem problem) {
        var node = new Problem.Line(initial,0);
        frontier.add(new Problem.Line(initial,0));
        reached.put(node.getNode(), node.getCost());
        while (!frontier.isEmpty()){
            node = frontier.poll();
            log.info("Astar running current = {}",node);
            if(node.getNode().getState().equals(Problem.State.TARGET)){
                solution=node.getNode();
                log.info("FOUND SOLUTION NODE {}",node);
                solutionCost = Utillities.getPathCost(solution);
                return solution;
            }
            for (var child : expand(problem,node.getNode())){
                var parent =  node.getNode().getParent();
                if(parent!=null && parent.equals(child)) {
                    continue;
                }
                child.setParent(node.getNode());
                int newCost = reached.get(node.getNode()) + child.getCost();
                if(!reached.containsKey(child.getNode()) || newCost < reached.get(child.getNode())){
                    reached.put(child.getNode(), child.getCost());
                    frontier.add(child);
                }
            }
        }
        JOptionPane.showMessageDialog(null, "No solution found", "Message", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }

    @Override
    public List<Problem.Line> expand(Problem problem, Problem.Node node) {
        var list = new ArrayList<Problem.Line>();
        for (int i=0;i<4;i++){
            if (node.getLines()[i]!=null ){
                var parent = node.getLines()[i].getNode().getParent();
                if (!reached.containsKey(node.getLines()[i].getNode()))
                    list.add(node.getLines()[i]);
            }
        }
        return list;
    }



}
