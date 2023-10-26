package org.example;

import java.util.List;

public class AlgSelector {
    int[][] adjacencyMatrix;
    static int starness;
    static float cliqueness;
    static int chainness;
    static int cycleness;
    public AlgSelector(int[][] mat){
        this.adjacencyMatrix = mat;
    }

    public void SelectAlgorithm() {

        ChainCycleStar chainAndCycle = new ChainCycleStar();
        List<Integer> requiredVars =  chainAndCycle.getAllRequiredParams(adjacencyMatrix);
        chainness = requiredVars.get(0);
        cycleness = requiredVars.get(1);
        starness = requiredVars.get(2);
        cliqueness =requiredVars.get(3);

        int scoreOfChainCycle = chainness + cycleness;
        float scoreOfStarClique = cliqueness + starness;

        System.out.println("starness is " + starness);
        System.out.println("cliqueness is " + cliqueness);
        System.out.println("chainness is " + chainness);
        System.out.println("cycleness is " + cycleness);

        System.out.println("Score of chain/cycle is " + scoreOfChainCycle);
        System.out.println("Score of star/clique is " + scoreOfStarClique);

        if(scoreOfStarClique > scoreOfChainCycle){
            System.out.println("Select new algorithm A*+hsum");
        }
        else {
            System.out.println("Select traditional algorithm DPcpp");
        }
    }

}
