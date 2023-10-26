package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChainCycleStar {
    static int max_len = 0;
    static int pathCount = 0;
    static boolean[] visited;

    // Data structures to track unique cycles
    static Set<List<Integer>> uniqueCycles = new HashSet<>();
    static List<Integer> currentPath;
    static ArrayList<Integer> degreeList;
    static void DFS(int[][] adjacencyMatrix, int currentNode, int currentLength, int parent, Set<Integer> currentPathSet) {
        visited[currentNode] = true;
        currentPath.add(currentNode);
        currentPathSet.add(currentNode);

        if (currentLength > max_len) {
            max_len = currentLength;
        }

        if (currentLength > (adjacencyMatrix.length) / 2) {
            pathCount++;
        }

        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[currentNode][neighbor] > 0) {

//                if neighbor is not visited then go and do DFS
                if (!visited[neighbor]) {
                    DFS(adjacencyMatrix, neighbor, currentLength + 1, currentNode, currentPathSet);
//                    If the neighbor is visited then 1) check for if it is the parent (because of backtracking)
//                                          2) check if current path set contains the neighbor indicating cycle
                } else if (neighbor != parent && currentPathSet.contains(neighbor)) {
                    // If you revisit an already visited node, you may have found a cycle.
                    // Create a set to store the cycle and add it to uniqueCycles.
                    Set<Integer> cycle = new HashSet<>(currentPath.subList(currentPath.indexOf(neighbor), currentPath.size()));
                    uniqueCycles.add(new ArrayList<>(cycle));
                }
            }
        }

        visited[currentNode] = false;
        currentPath.remove(currentPath.size() - 1);
        currentPathSet.remove(currentNode);
    }

     List<Integer> getAllRequiredParams(int[][] adjacencyMatrix) {
        int totalNodes = 0;
        int starCount = 0;
        int totalNodesOfStars = 0;
        int starNess = 0;
        int cycleNess = 0;
        int cliqueNess = 0;
        int nunmberOfTriangles = 0;
        int starDegree = 0;
        int n = adjacencyMatrix.length;
        visited = new boolean[n];
        currentPath = new ArrayList<>();
        degreeList = new ArrayList<>();
        Set<Integer> neighborSetOfaVertex = new HashSet<>();

        for (int i = 0; i < n; i++) {
            int degree = 0;
            for(int j = 0; j < n; j++){
                if (adjacencyMatrix[i][j] > 0){
                    degree++;
                    neighborSetOfaVertex.add(j);
                }
            }
            degreeList.add(degree);
            Set<Integer> neighborSetOfaVertex1 = new HashSet<>(neighborSetOfaVertex);
            Set<Integer> removalSet = new HashSet<>();
            for(int v : neighborSetOfaVertex){
                neighborSetOfaVertex1.remove(v);
                for(int u : neighborSetOfaVertex1){
                    if (adjacencyMatrix[v][u] > 0){
                        removalSet.add(u);
                    }
                }
                neighborSetOfaVertex1.add(v);
            }
            starDegree = degree - removalSet.size();
//            (n - (n % 2)) / 2
            if(starDegree > 2){
                starCount++;
                totalNodesOfStars = totalNodesOfStars + starDegree;
            }

            if (!visited[i]) {
                DFS(adjacencyMatrix, i, 0, -1, new HashSet<>());
            }
            System.out.print(degreeList.get(i) + " ");
        }
        int chainNess = max_len + pathCount;
        int cycleCount = uniqueCycles.size();
        for (List m : uniqueCycles){
            totalNodes = totalNodes + m.size();
            nunmberOfTriangles++;
        }
        if(cycleCount > 0){
            cycleNess = totalNodes/cycleCount;
        }
        if(starCount > 0){
            starNess = totalNodesOfStars/starCount;
        }
        if(nunmberOfTriangles > 0){
            Cliques cliques = new Cliques(n,adjacencyMatrix);
            cliqueNess = cliques.getCliqueCount();
        }
        List<Integer> cycleChainStarCLique = new ArrayList<>();
        cycleChainStarCLique.add(chainNess);
        cycleChainStarCLique.add(cycleNess);
        cycleChainStarCLique.add(starNess);
        cycleChainStarCLique.add(cliqueNess);
        return cycleChainStarCLique;
    }
}
