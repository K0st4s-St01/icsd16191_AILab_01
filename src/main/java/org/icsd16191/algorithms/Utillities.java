package org.icsd16191.algorithms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.icsd16191.problem.Problem;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Utillities {
    @Setter
    @Getter
    @AllArgsConstructor
    public static class Result{
        private int cost;
        private int depth;
        private int reached_nodes;
        private int failed;

        @Override
        public String toString() {
            return "{" +
                    "cost=" + cost +
                    ", depth=" + depth +
                    ", reached_nodes=" + reached_nodes +
                    ", failed=" + failed +
                    '}';
        }

        public Result add(Result b){
            return new Result(cost+b.cost,depth+b.depth,reached_nodes+b.reached_nodes,failed+b.failed);
        }
    }
    public static Result getPathCost(Problem.Node node,int reached,int failed){
        var node_ptr = node;
        int cost = 0;
        int depth=0;
        while (node_ptr != null){
            for (var line : node_ptr.getLines()){
                if(line!=null && line.getNode() == node_ptr.getParent()){
                    cost+= line.getCost();
                }
            }
            depth++;
            node_ptr=node_ptr.getParent();
        }
        return new Result(cost,depth,reached,failed);
    }
}
