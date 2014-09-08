package applet;

import java.awt.Point;
import java.awt.List;
import java.util.*;

public class Node{
    Rules rules = new Rules();
    Point currentPiece;
    String pieceName;
    String[][] state = new String[8][8];

    int x;
    int y;

    Node parent = null;


    Node(int x, int y, String[][] strChessBoard, Node parent) 
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                this.state[i][j] = strChessBoard[i][j];
            }
        }
        this.parent = parent;
        this.pieceName = strChessBoard[y][x];
        this.x = x;
        this.y = y;
    }


	Vector<Node> getPath(Vector<Node> v) {
        v.insertElementAt(this, 0);
        if (parent != null) v = parent.getPath(v);
        return v;
     }

     Vector<Node> getPath() { return(getPath(new Vector<Node>())); }


    public String[][] copyChessBoard(String[][] strChessBoard)
    {
        String[][] tmpBoard = new String[8][8];
        for (int a = 0; a < 8; a++)
            for (int b = 0; b < 8; b++)
            {
                tmpBoard[a][b] = strChessBoard[a][b];

            }
        return tmpBoard;
    }

    public void printChessBoard(String[][] strChessBoard)
    {
        for (int a = 0; a < 8; a++)
            for (int b = 0; b < 8; b++)
            {

                System.out.print("y:"+a+"x:"+b+"value:"+strChessBoard[a][b]);

            }
    }
    public boolean twoDarrayEmpty(String[][] user)
    {
        boolean notNull=false;
        for(String[] array : user){
            for(String val : array){
                if(val!=null){
                    notNull=true;
                    break;
                }
            }
        }
        if(notNull){
            return false;
        }
        else{
            return true;
        }
    }
}
