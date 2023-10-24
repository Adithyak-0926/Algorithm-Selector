package org.example;

import java.util.ArrayList;
import java.util.List;

public class Star {
    public int[][] mat;
    public Star(int[][] mat){
        this.mat = mat;
    }
    static int size;
    static int starCount = 0;
    static int starness;
    static List<Integer> requiredStarSizeList;

    public boolean containStarGraph(int mat[][]) {
        size = mat.length;
        ArrayList<Integer> Rowsum = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int sumRow = 0;
            for (int j = 0; j < size; j++) {
                sumRow = sumRow + mat[i][j];
            }
            Rowsum.add(sumRow);
        }
        requiredStarSizeList = new ArrayList<>();
        for (int i = 0; i < Rowsum.size(); i++) {
            int n = Rowsum.get(i);
            if (n > (size - (size % 2)) / 2) {
                requiredStarSizeList.add(n);
                starCount++;
            }
        }
//        System.out.println(starCount);
        return (starCount > 0);
    }

    public int getStarCount(){
        if (containStarGraph(mat)){
            int totalNodes = 0;
            for(int i : requiredStarSizeList){
                totalNodes = totalNodes + i;
            }
            return totalNodes/starCount;
        }
        else return 0;
    }
}
