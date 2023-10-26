package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cliques {

    int nodesCount;
    int[][] adjacencyMatrix;

    Cliques(int nodesCount, int[][] adjacencyMatrix) {
        this.nodesCount = nodesCount;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    void Bron_KerboschWithPivot(
            Set<Integer> R, Set<Integer> P, Set<Integer> X, HashSet<Set<Integer>> maximalCliques) {
        if (P.isEmpty() && X.isEmpty()) {
            if (R.size() > 2) {
            maximalCliques.add(new HashSet<>(R));
            return;
            }
            return;
        }

        int pivot = -1;
        Set<Integer> unionPX = new HashSet<>(P);
        unionPX.addAll(X);

        if (!P.isEmpty()) {
            for (int v : P) {
                pivot = v;
                break;
            }
        }

        Set<Integer> candidates = new HashSet<>(P);
        candidates.removeAll(adjacencySet(pivot));

        for (int v : candidates) {
            Set<Integer> neighborsV = adjacencySet(v);
            Set<Integer> newR = new HashSet<>(R);
            newR.add(v);

            P.remove(v);
            X.add(v);

            Bron_KerboschWithPivot(newR, intersection(P, neighborsV), intersection(X, neighborsV), maximalCliques);

           P.add(v);
           X.remove(v);
        }
    }
    int getCliqueCount(){
        int totalNodes = 0;
        Set<Integer> R = new HashSet<>();
        Set<Integer> P = new HashSet<>();
        for (int i = 0; i < nodesCount; i++) {
            P.add(i);
        }
        Set<Integer> X = new HashSet<>();
        HashSet<Set<Integer>> maximalCliques = new HashSet<>();

        Bron_KerboschWithPivot(R, P, X, maximalCliques);

        for(Set n : maximalCliques){
            totalNodes = totalNodes + n.size();
        }
        int totalMaximalCliques = maximalCliques.size();
        System.out.println();
        if(totalMaximalCliques == 0){
            return 0;
        }
        return totalNodes/totalMaximalCliques;
    }
    Set<Integer> adjacencySet(int vertex) {
        Set<Integer> neighbors = new HashSet<>();
        for (int i = 0; i < nodesCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }
    Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        Set<Integer> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }
//    int getStarCount(){
//        int starCount = 0;
//        int totalNodes = 0;
//        List<Set<Integer>> RequiredStarsList =  new ArrayList<>();
//        for(int i = 0; i < nodesCount ; i++ ){
//            Set<Integer> temp = adjacencySet(i);
//            if(temp.size() > (nodesCount - (nodesCount % 2)) / 2){
//                RequiredStarsList.add((temp));
//                totalNodes = totalNodes + temp.size();
//            }
//        }
//        starCount = RequiredStarsList.size();
//        return totalNodes/starCount;
//    }
}

