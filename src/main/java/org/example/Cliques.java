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

    int getCliqueCount() {
        int totalNodesWeightsOfCliques = 0;
        Set<Integer> R = new HashSet<>();
        Set<Integer> P = new HashSet<>();
        for (int i = 0; i < nodesCount; i++) {
            P.add(i);
        }
        Set<Integer> X = new HashSet<>();
        HashSet<Set<Integer>> maximalCliques = new HashSet<>();

        Bron_KerboschWithPivot(R, P, X, maximalCliques);

// Introducing weights here
        for (Set n : maximalCliques) {
            System.out.println(n);
            for (Object a : n) {
                for (Object b : n) {
                    totalNodesWeightsOfCliques = totalNodesWeightsOfCliques + adjacencyMatrix[(int) a][(int) b];
                }
            }
        }
        totalNodesWeightsOfCliques = totalNodesWeightsOfCliques/2;
        int totalMaximalCliques = maximalCliques.size();
        System.out.println();
        if (totalMaximalCliques == 0) {
            return 0;
        }
        return totalNodesWeightsOfCliques / totalMaximalCliques;
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
}

