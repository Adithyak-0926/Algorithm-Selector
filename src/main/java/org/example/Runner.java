package org.example;

public class Runner {
    public static void main(String[] args) {

        int[][] mat = {
                {0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 0}};

        int[][] adjacencyMatrix1 = {
                {0, 1, 1, 0},
                {1, 0, 1, 0},
                {1, 1, 0, 1},
                {0, 0, 1, 0}
        };
        int[][] adjacencyMatrix2 = {
                {0, 1, 1, 0, 0, 0},
                {1, 0, 1, 1, 0, 0},
                {1, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1},
                {0, 0, 0, 1, 0, 1},
                {0, 0, 0, 1, 1, 0},
        };

        int[][] adjacencyMatrixDiscussedOne = {
                {0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 1, 0, 0},
                {1, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        AlgSelector algSelector = new AlgSelector(adjacencyMatrixDiscussedOne);
        algSelector.SelectAlgorithm();
    }
}