package aoc2022;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Day9_B {
    ArrayList <Node> snake = new ArrayList<>();

    static class Node{
        int r;
        int c;

        final int name;
        private static int counter;

        Node (int r, int c){
            name = counter++;
            this.r = r;
            this.c = c;

        }
        public String toString (){
            return r + ","+ c;
        }

        private void move (char cmd){
            if (cmd == 'U') {
                r -= 1;
            }
            else if (cmd=='D'){
                r += 1;
            }
            else if (cmd == 'L'){
                c -= 1;
            }
            else if (cmd == 'R'){
                c += 1;
            }
            else return;
        }

        private void moveCloser (Node node) {
            // horizontally
            if (node.r == r) {
                c = (c + node.c) / 2;
            }

            // vertically
            else if (node.c == c) {
                r = (r + node.r) / 2;
            }

            // diagonally
            else {
                // vertical diag.
                if (Math.abs(r - node.r) > Math.abs(c - node.c)) {
                    r = (r + node.r) / 2;
                    c = node.c;
                } else {
                    // horiz.diag
                    c = (c + node.c) / 2;
                    r = node.r;
                }
            }
        }

        private boolean isTooFar (Node node){

            if (node.r> r+1 || node.r < r-1
                    || node.c > c+1 || node.c < c-1) return true;
            else return false;
        }
    }


    public static void main(String[] args) {
        List<Node> snake = new LinkedList<>();
        int startingRow = 400;   // 17 for test9.txt input
        int startingCol = 400;   // 12 for test9.txt input
        int rows = 800;          // 30 for test9.txt input
        int columns = 800;       // 30 for test9.txt input
        int result = 0;
        Node head = new Node(startingRow,startingCol);
        snake.add(head);
        int[][] resultsArray = new int[rows][columns];

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/aoc2022/inputs/day9.txt"));
            String line = reader.readLine();

            while (line != null){
                String []  lineA = line.split(" ");
                char cmd = line.charAt(0);
                int x = Integer.parseInt(lineA[1]);

                // we set 10 segments starting at the same points after each other
                // MOVING HEAD x times

                for (int i = 0; i < x; i++) {
                    head.move(cmd);
                    if (Node.counter<10){
                        snake.add(new Node(startingRow, startingCol));
                    }

                    // each time the whole snake moves;
                    for (int j=1; j<snake.size(); j++ ) {
                        Node front = snake.get(j - 1);
                        Node back = snake.get(j);

                        if (front.isTooFar(back)) {
                            back.moveCloser(front);
                        }
                        // if visited by tail
                        if (back.name == 9) {
                            if (resultsArray[back.r][back.c] == 0) {
                                resultsArray[back.r][back.c] = 1;
                            }
                        }
                    }

                }

                line = reader.readLine();
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //calculate visited by tail
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < columns; j++) {
                System.out.print(resultsArray[i][j]);
                result += resultsArray[i][j];
            }
        }
        System.out.println();
        System.out.println("visited by tail:" + result);


    }




}
