package algorithm;

import static ui.NumOnlyMainFrame.*;

public class NumOnlySolutionAlgorithm {
    private static int solvesCount = 0;
    private static boolean isGetSolution = false;

    public static boolean checkModeDFS(String[][] cells, int mode) {
        solvesCount = 0;
        isGetSolution = false;
        advancedDFS(0, 0, cells);
        switch (mode){
            case IDIOT:
                return solvesCount < 84;
            case EASY:
                return solvesCount < 100;
            case NORMAL:
                return solvesCount <= 300 && solvesCount > 100;
            case HARD:
                return solvesCount <= 1000 && solvesCount > 300;
            case HELL:
                return solvesCount > 1000;
            default:
                return false;
        }
    }

    private static void advancedDFS(int i, int j, String[][] cells) {
        solvesCount++;
        if (i == 8 && j == 9) {
            //成功
            isGetSolution = true;
            solvesCount++;
            return;
        }
        //已经到了列末尾了，还没到行尾，就换行
        if (j == 9) {
            i++;
            j = 0;
        }
        //如果i行j列是空格，那么才进入给空格填值的逻辑
        if (cells[i][j].trim().equals("")) {
            for (int k = 1; k <= 9; k++) {
                //判断给i行j列放1-9中的任意一个数是否能满足规则
                if (check(i, j, k, cells)) {
                    //将该值赋给该空格，然后进入下一个空格
                    cells[i][j] = k + "";
                    advancedDFS(i, j + 1, cells);
                    //初始化该空格
                    if (isGetSolution)
                        return;
                    cells[i][j] = "";
                }
            }
        } else {
            //如果该位置已经有值了，就进入下一个空格进行计算
            advancedDFS(i, j + 1, cells);
        }
    }

    private static boolean check(int row, int line, int number, String[][] cells) {
        //判断该行该列是否有重复数字
        for (int i = 0; i < 9; i++) {
            if (cells[row][i].trim().equals(number + "") || cells[i][line].trim().equals(number + "") ) {
                return false;
            }
        }
        //判断小九宫格是否有重复
        int tempRow = row / 3;
        int tempLine = line / 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[tempRow * 3 + i][tempLine * 3 + j].trim().equals(number + "")) {
                    return false;
                }
            }
        }

        return true;
    }

}
