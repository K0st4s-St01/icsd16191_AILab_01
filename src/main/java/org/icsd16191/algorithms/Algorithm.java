package org.icsd16191.algorithms;

import org.icsd16191.problem.Problem;

import java.awt.*;

public interface Algorithm {

    void render(Graphics2D g,int size);
    Problem.Node run(Problem problem);
    java.util.List<Problem.Line> expand(Problem problem,Problem.Node node);

}
