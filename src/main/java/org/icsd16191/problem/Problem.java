package org.icsd16191.problem;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.*;
import java.util.List;

@Slf4j
@Getter
public class Problem {
    private Integer n;
    private Double p;
    private Integer maximumCost;
    private static final Integer minimumCost =1;
    private static final Random random = new Random();
    private Node[][] nodes;
    private int sx;
    private int sy;
    private int tx_1;
    private int ty_1;
    private int tx_0;
    private int ty_0;


    public Problem(Integer n, Double p, Integer maximumCost) {
        this.n = n;
        this.p = p;
        this.maximumCost = maximumCost;
        nodes = new Node[n][n];
        populateNodes();
        randomlyRemoveLines();
        chooseStartAndTargets();
    }
    private void populateNodes(){
        log.info("populating nodes");
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                var node = new Node();
                node.setState(State.SIMPLE_NODE);
                node.setX(i);
                node.setY(j);
                node.id=(long)(i+j*n);
                this.nodes[i][j]=node;
                for (int k = 0; k < 4; k++) {
                    nodes[i][j].setLine(k,null);
                }
                log.info("({},{}) -> {}",i,j,node);
            }
        }
        log.info("generating all lines");
        for (int i=0;i<n;i++){
            for (int j=0;j<n;j++){
                for (int k : List.of(1,-1)) {
                    if(i+k >= 0 && i+k<n){
                       if(k == 1){
                           nodes[i][j].lines[1]=new Line(nodes[i+k][j],random.nextInt(minimumCost,maximumCost+1));//upper bound exclusive
                           nodes[i+k][j].lines[0]=new Line(nodes[i][j],nodes[i][j].lines[1].cost);//upper bound exclusive
                       }else{
                           nodes[i][j].lines[0]=new Line(nodes[i+k][j],random.nextInt(minimumCost,maximumCost+1));//upper bound exclusive
                           nodes[i+k][j].lines[1]=new Line(nodes[i][j],nodes[i][j].lines[0].cost);//upper bound exclusive
                       }
                    }
                    if(j+k >= 0 && j+k<n){
                        if(k == 1){
                            nodes[i][j].lines[3]=new Line(nodes[i][j+k],random.nextInt(minimumCost,maximumCost+1));//upper bound exclusive
                            nodes[i][j+k].lines[2]=new Line(nodes[i][j],nodes[i][j].lines[3].cost);//upper bound exclusive
                        }else{
                            nodes[i][j].lines[2]=new Line(nodes[i][j+k],random.nextInt(minimumCost,maximumCost+1));//upper bound exclusive
                            nodes[i][j+k].lines[3]=new Line(nodes[i][j],nodes[i][j].lines[2].cost);//upper bound exclusive
                        }
                    }
                }
                log.info("current {} -> {}",this.nodes[i][j], Arrays.toString(this.nodes[i][j].lines));
            }
        }

    }

    private void randomlyRemoveLines(){
        log.info("randomly removing connecting lines with probability {}",p);
        int linesRemoved=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k=0;k<4;k++){
                    if(nodes[i][j].lines[k]!=null && random.nextDouble() < p){
                        linesRemoved++;
                        nodes[i][j].lines[k] = null;
                        switch (k){
                            case 0:
                                nodes[i-1][j].setLine(1,null);
                                break;
                            case 1:
                                nodes[i+1][j].setLine(0,null);
                                break;
                            case 2:
                                nodes[i][j-1].setLine(3,null);
                                break;
                            case 3:
                                nodes[i][j+1].setLine(2,null);
                                break;
                            default:
                                log.error("k>=4 in randomlyRemoveLines");
                                throw new RuntimeException("k>=4 in randomlyRemoveLines");
                        }
                    }
                }
            }
        }
        log.info("{} lines removed",linesRemoved);
    }
    private void chooseStartAndTargets(){
        sx=random.nextInt(0,n);
        sy= random.nextInt(0,n);
        tx_0=random.nextInt(0,n);
        ty_0= random.nextInt(0,n);
        tx_1=random.nextInt(0,n);
        ty_1= random.nextInt(0,n);
        nodes[sx][sy].setState(State.INITIAL);
        nodes[tx_0][ty_0].setState(State.TARGET);
        nodes[tx_1][ty_1].setState(State.TARGET);
    }

    public void render(Graphics2D g,int size) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < 4; k++) {
                    int ni = k == 0 ? i-1 : i;// if left
                    ni = k == 1 ? i+1 : ni;//if right
                    int nj = k == 2? j-1:j;//if up
                    nj = k ==3? j+1:nj;//if down
                    if (nodes[i][j].lines[k]!=null) {
                        g.setColor(Color.BLACK);
                        g.drawLine(100+i*size*2+size/2,100+size/2+j*size*2,100+ni*size*2+size/2,100+size/2+nj*size*2);
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                g.setColor(new Color(100,100,255));
                if(nodes[i][j] != null) {
                    if (nodes[i][j].state == State.INITIAL){
                        g.setColor(Color.green);
                    }else if(nodes[i][j].state == State.TARGET){
                        g.setColor(Color.BLUE);
                    }
                    g.fillRect(100+i*size*2,100+j*size*2,size,size);

                }
            }
        }
        for (int i=0;i<n;i++)
            for(int j=0;j<n;j++)
                for (int k = 0; k < 4; k++) {
                    int ni = k == 0 ? i - 1 : i;// if left
                    ni = k == 1 ? i + 1 : ni;//if right
                    int nj = k == 2 ? j - 1 : j;//if up
                    nj = k == 3 ? j + 1 : nj;//if down

                    int x1 = 100 + i * size * 2 + size / 2;
                    int y1 = 100 + j * size * 2 + size / 2;
                    int x2 = 100 + ni * size * 2 + size / 2;
                    int y2 = 100 + nj * size * 2 + size / 2;

                    int midX = (x1 + x2) / 2;
                    int midY = (y1 + y2) / 2;
                    if (nodes[i][j].lines[k] != null) {
                        g.setColor(Color.RED);
                        g.drawString("" + nodes[i][j].lines[k].cost, midX, midY );
                    }
                }
    }
    public enum State{
        INITIAL,
        TARGET,
        SIMPLE_NODE,
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class Node{
        private Node parent = null;
        private Integer x,y;
        private Long id;
        private Line[] lines = new Line[4];
        private State state;

        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;
            Node node = (Node) object;
            return Objects.equals(x, node.x) && Objects.equals(y, node.y) && Objects.equals(id, node.id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, id);
        }
        // 0 left 1 right 2 up 3 down

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", id=" + id +
                    ", state=" + state +
                    '}';
        }

        public void setLine(int i, Line line){
            if(i>=0 && i<4){
                this.lines[i] = line;
            }
        }
    }
    @AllArgsConstructor
    @Setter
    @Getter
    @ToString
    public static class Line{
        Node node;
        Integer cost;
    }
}
