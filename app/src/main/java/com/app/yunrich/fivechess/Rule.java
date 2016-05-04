package com.app.yunrich.fivechess;

import android.graphics.Point;

import java.util.Stack;

/**
 * Created by Administrator on 2016/5/3.
 * 规则---核心算法
 */
public class Rule {

    /**
     * 0 没有 1 黑 2 白
     *
     * 广度优先搜索
    * */
    public int check(Mesh mesh){
        int res = 0;
        int[][] map = new int[mesh.height][mesh.width];
        copyToMap(map,mesh);
        if (checkMap(map, 1))
            res = 1;
        if (checkMap(map, 2))
            res = 2;
        return res;
    }

    /**检测map
     * */
    private boolean checkMap(int[][] map, int v){
        for (int row = 0; row<map[0].length; row++)
            for (int col = 0; col < map.length; col++){
                if (map[col][row] == v){
                    boolean res = checkPoint(map, row,col, v);
                    if (res)
                        return true;
                }
            }
        return false;
    }

    /**检测改点是否可以满足
     * */
    private boolean checkPoint(int[][] map, int row, int col, int v){
        Stack<Point> stack = new Stack<Point>();
        Point point = new Point(row,col);
        /**
         *     ********** 1
         *    **  *
         *  *  *    *
         4     *     * 2
         *     3
         *
         * */
        int dir = 1;
        forward(map,row,col+1,v, dir, stack);
        if (stack.size() >= 4)
            return true;
        else
           stack.clear();
        dir = 2;
        forward(map,row+1,col+1,v, dir, stack);
        if (stack.size() >= 4)
            return true;
        else
            stack.clear();
        dir = 3;
        forward(map,row+1,col,v, dir, stack);
        if (stack.size() >= 4)
            return true;
        else
            stack.clear();
        dir = 4;
        forward(map,row-1,col-1, v, dir, stack);
        if (stack.size() >= 4)
            return true;
        else
            stack.clear();
        return false;
    }

    /**前进方式
     * */
    protected void forward(int[][] map, int row, int col, int v, int dir, Stack<Point> stack){
        if (row < 0 || row >= map[0].length || col < 0 || col >= map.length){
            return;
        }
        switch (dir){
            case  1:
                if (row >= map[0].length){//到达边界不可使用
                    return;
                }
                if (map[col][row] != v)//短路
                    return;
                stack.push(new Point(row,col));
                //进入下个阶段
                forward(map, row,col+1, v, dir, stack);
                break;
            case 2:
                if (row >= map[0].length || col >= map.length){//到达边界不可使用
                    return;
                }
                if (map[col][row] != v)//短路
                    return;
                stack.push(new Point(row,col));
                //进入下个阶段
                forward(map, row+1,col+1, v, dir, stack);
                break;
            case 3:
                if (col >= map.length){//到达边界不可使用
                    return;
                }
                if (map[col][row] != v)//短路
                    return;
                stack.push(new Point(row,col));
                //进入下个阶段
                forward(map, row+1,col, v, dir, stack);
                break;
            case 4:
                if (col < 0  || row < 0){//到达边界不可使用
                    return;
                }
                if (map[col][row] != v)//短路
                    return;
                stack.push(new Point(row,col));
                //进入下个阶段
                forward(map, row-1,col-1, v, dir, stack);
                break;
        }
    }

    private void copyToMap(int[][] map, Mesh mesh){
        int rows = map[0].length;
        int cols = map.length;

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++){
                map[col][row] = mesh.getPoint(row, col);
            }
    }

}
