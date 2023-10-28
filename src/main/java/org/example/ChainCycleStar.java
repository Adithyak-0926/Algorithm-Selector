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
    static Set<Set<Integer>> allowedPaths = new HashSet<>();
    static List<Integer> currentPath;
    static ArrayList<Integer> degreeList;

    static void DFS(int[][] adjacencyMatrix, int currentNode, int currentLength, int parent, Set<Integer> currentPathSet, int totalDegreeWeight,Set<Set<Integer>> allowedPaths) {
        visited[currentNode] = true;
        currentPath.add(currentNode);
        currentPathSet.add(currentNode);

        if (currentLength > max_len) {
            max_len = currentLength;
        }

        if (currentLength > totalDegreeWeight / 2) {
            allowedPaths.add(Set.copyOf(currentPathSet));
        }

        for (int neighbor = 0; neighbor < adjacencyMatrix.length; neighbor++) {
            if (adjacencyMatrix[currentNode][neighbor] > 0) {

//                if neighbor is not visited then go and do DFS
                if (!visited[neighbor]) {
                    DFS(adjacencyMatrix, neighbor, currentLength + adjacencyMatrix[currentNode][neighbor], currentNode, currentPathSet, totalDegreeWeight,allowedPaths);
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
        int totalNodesWeightOfCycles = 0;
        int totalNodesWeightOfStars = 0;
        int totalNodesWeightOfChains = 0;
        int starCount = 0;
        int totalNodesOfStars = 0;
        int chainNess = 0;
        int starNess = 0;
        int cycleNess = 0;
        int cliqueNess = 0;
        int nunmberOfTriangles = 0;
        int totalDegreeWeight = 0;
        float avgDegreeWeight;
        int starDegree = 0;
        int starDegreeWeight = 0;
        int n = adjacencyMatrix.length;
        visited = new boolean[n];
        currentPath = new ArrayList<>();
        degreeList = new ArrayList<>();
        Set<Integer> neighborSetOfaVertex;
//        to get the total degree of graph
        for (int k = 0; k < n; k++) {
            for (int p = 0; p < n; p++) {
                if (adjacencyMatrix[k][p] > 0) {
                    totalDegreeWeight = totalDegreeWeight + adjacencyMatrix[k][p];
                }
            }
        }
        totalDegreeWeight = totalDegreeWeight/2;
        avgDegreeWeight = totalDegreeWeight / n;

        for (int i = 0; i < n; i++) {
            int degree = 0;
            int degreeWeight = 0;
            neighborSetOfaVertex = new HashSet<>();
            for (int j = 0; j < n; j++) {
                if (adjacencyMatrix[i][j] > 0) {
                    degree++;
                    degreeWeight = degreeWeight + adjacencyMatrix[i][j];
                    neighborSetOfaVertex.add(j);
                }
            }
            degreeList.add(degree);
//            getting the neighborhood of a vertex and
//            checking whether there are any edges among them to not consider them for star
            Set<Integer> removalSet = new HashSet<>();
            for (int v : neighborSetOfaVertex) {
                for (int u : neighborSetOfaVertex) {
                    if (adjacencyMatrix[v][u] > 0 && v!=u) {
                        removalSet.add(u);
                    }
                }
            }
            starDegree = degree - removalSet.size();

            for (int r : removalSet) {
                starDegreeWeight = starDegreeWeight + adjacencyMatrix[i][r];
            }
            starDegreeWeight = degreeWeight - starDegreeWeight;

            double AvgStarDegreeWeight = 0;
            if (starDegree > 0) {
                AvgStarDegreeWeight = starDegreeWeight / starDegree;
            }
            if (starDegree > 2 && AvgStarDegreeWeight >= avgDegreeWeight) {
                starCount++;
                totalNodesWeightOfStars = totalNodesWeightOfStars + starDegreeWeight;
            }

            if (!visited[i]) {
                DFS(adjacencyMatrix, i, 0, -1, new HashSet<>(), totalDegreeWeight,allowedPaths);
            }
            System.out.print(degreeList.get(i) + " ");
        }

//        int chainNess = max_len + pathCount;
        int cycleCount = uniqueCycles.size();

//      Getting total weight of all cycles and counting number of cycles
        for (List m : uniqueCycles) {
            for (int a = 0; a < m.size() - 1; a++) {
                totalNodesWeightOfCycles = totalNodesWeightOfCycles + adjacencyMatrix[(int) m.get(a)][(int) m.get(a + 1)];
            }
            totalNodesWeightOfCycles = totalNodesWeightOfCycles + adjacencyMatrix[(int) m.get(m.size() - 1)][(int) m.get(0)];
            if (m.size() == 3) {
                nunmberOfTriangles++;
            }
        }
        for(Set p : allowedPaths){
            for(Object m : p){
                for (Object o : p){
                    totalNodesWeightOfChains = totalNodesWeightOfChains + adjacencyMatrix[(int) m][(int) o];
                }
            }
        }
        totalNodesWeightOfChains = totalNodesWeightOfChains/2;
        pathCount = allowedPaths.size();
        if(pathCount>0){
            chainNess = totalNodesWeightOfChains/pathCount;
        }
        if (cycleCount > 0) {
            cycleNess = totalNodesWeightOfCycles / cycleCount;
        }
        if (starCount > 0) {
            starNess = totalNodesWeightOfStars / starCount;
        }
        if (nunmberOfTriangles > 0) {
            Cliques cliques = new Cliques(n, adjacencyMatrix);
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
