package org.icsd16191.algorithms;

import lombok.extern.slf4j.Slf4j;
import org.icsd16191.problem.Problem;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Utillities {
    public static Integer getPathCost(Problem.Node node){
        var node_ptr = node;
        int cost = 0;
        Set<Problem.Node> visited=new HashSet<>();
        while (node_ptr != null){
            for (var line : node_ptr.getLines()){
                if(line!=null && line.getNode() == node_ptr.getParent()){
                    cost+= line.getCost();
                }
            }
            if (!visited.contains(node_ptr)){
                visited.add(node_ptr);
            }else{
                return cost;
            }
            node_ptr=node_ptr.getParent();
        }
        return cost;
    }
}
