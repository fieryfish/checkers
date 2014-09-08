package applet;

import java.awt.Point;
import java.awt.List;
import java.util.ArrayList;

// up black down white
public class Rules {

    public String[][] changeKing(String[][] strChessBoard){
        for(int c=0; c<8; c++){
            if (strChessBoard[0][c] == "W")
                strChessBoard[0][c] = "WK";
            else if (strChessBoard[7][c] == "B")
                strChessBoard[7][c] = "BK";
        }
        return strChessBoard;
    }


    public boolean isBlackKing(Node pnt, String[][] strChessBoard)
    {
        return (strChessBoard[pnt.y][pnt.x].toString() == "BK");
    }

    public boolean isBlackKing(Point pnt, String[][] strChessBoard)
    {
        return (strChessBoard[pnt.y][pnt.x].toString() == "BK");
    }

    public boolean isKingByNode(Node pnt, String[][] strChessBoard)
    {
        if ((strChessBoard[pnt.y][pnt.x].toString() == "BK") || (strChessBoard[pnt.y][pnt.x].toString() == "WK"))
            return true;
        else
            return false;
    }

    public boolean isKingByPoint(Point pnt, String[][] strChessBoard)
    {
        if ((strChessBoard[pnt.y][pnt.x].toString() == "BK") || (strChessBoard[pnt.y][pnt.x].toString() == "WK"))
            return true;
        else
            return false;
    }

    public boolean isWhiteKing(Node pnt, String[][] strChessBoard)
    {
        return (strChessBoard[pnt.y][pnt.x].toString() == "WK");
    }

    public boolean isBlack(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString().charAt(0) == 'B');
    }

    public boolean isBlackByNode(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x] == "B");
    }

    public boolean isWhiteByNode(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x] == "W");
    }

    public boolean isBlackNormal(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString() == "B");
    }

    public boolean isWhiteNormal(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString() == "W");
    }

    public boolean isWhite(Node pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString().charAt(0) == 'W');
    }

    public boolean isKingByNode(Point pnt, String[][] strChessBoard)
    {
        if ((strChessBoard[pnt.y][pnt.x].toString() == "BK") || (strChessBoard[pnt.y][pnt.x].toString() == "WK"))
            return true;
        else
            return false;
    }

    public boolean isWhiteKing(Point pnt, String[][] strChessBoard)
    {
        return (strChessBoard[pnt.y][pnt.x].toString() == "WK");
    }

    public boolean isBlack(Point pnt, String[][] strChessBoard){
    	if (strChessBoard[pnt.y][pnt.x].length() !=0)
    		return strChessBoard[pnt.y][pnt.x].toString().charAt(0) == 'B';
    	else {
    		return false;
    	}
    }

    public boolean isBlackByNode(Point pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x] == "B");
    }

    public boolean isWhiteByNode(Point pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x] == "W");
    }

    public boolean isBlackNormal(Point pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString() == "B");
    }

    public boolean isWhiteNormal(Point pnt, String[][] strChessBoard){
        return (strChessBoard[pnt.y][pnt.x].toString() == "W");
    }

    public boolean isWhite(Point pnt, String[][] strChessBoard){
    	if (strChessBoard[pnt.y][pnt.x].length() !=0)
    		return strChessBoard[pnt.y][pnt.x].toString().charAt(0) == 'W';
    	else {
    		return false;
    	}
    }

    public boolean isWhiteByStr(String str){
    	if (str.length() !=0)
    		return str.charAt(0) == 'W';
    	else {
    		return false;
    	}
    }

    public boolean isBlackByStr(String str){
    	if (str.length() !=0)
    		return str.charAt(0) == 'B';
    	else {
    		return false;
    	}
    }

    public boolean isBlackKingByStr(String str){
        return str == "BK";
    }

    public boolean isWhiteKingByStr(String str){
        return str == "WK";
    }

    public boolean isKingByStr(String str){
        if (str.length() !=0)
            return ((str== "BK") || (str== "WK"));
        else
            return false;
    }

    public boolean insideBoard(int x, int y){
        return (x >= 0 && x <=7 && y >= 0 && y <= 7);
    }

    public boolean upLeftCap(Node node,boolean isRobot){
        boolean result = false;
        if (insideBoard(node.y-2,node.x-2))
        {
            if (isWhiteByStr(node.state[node.y-1][node.x-1])  && isRobot &&
                    node.state[node.y-2][node.x-2].trim().equals(""))
                result = true;
            else if (isBlackByStr(node.state[node.y-1][node.x-1])  && !isRobot &&
                    node.state[node.y-2][node.x-2].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean upRightCap(Node node,boolean isRobot){
        boolean result = false;
        if (insideBoard(node.y-2,node.x+2))
        {
            if (isWhiteByStr(node.state[node.y-1][node.x+1])  && isRobot &&
                    node.state[node.y-2][node.x+2].trim().equals(""))
                result = true;
            else if (isBlackByStr(node.state[node.y-1][node.x+1])  && !isRobot &&
                    node.state[node.y-2][node.x+2].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean downLeftCap(Node node,boolean isRobot){
        boolean result = false;
        if (insideBoard(node.y+2,node.x-2))
        {
            if (isWhiteByStr(node.state[node.y+1][node.x-1])  && isRobot &&
                    node.state[node.y+2][node.x-2].trim().equals(""))
                result = true;
            else if(isBlackByStr(node.state[node.y+1][node.x-1])  && !isRobot &&
                    node.state[node.y+2][node.x-2].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean downRightCap(Node node,boolean isRobot){
        boolean result = false;
        if (insideBoard(node.y+2,node.x+2))
        {
            if (isWhiteByStr(node.state[node.y+1][node.x+1])  &&isRobot&&
                    node.state[node.y+2][node.x+2].trim().equals(""))
                result = true;
            else if (isBlackByStr(node.state[node.y+1][node.x+1])  &&!isRobot&&
                    node.state[node.y+2][node.x+2].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean upRightNor(Node node){
        boolean result = false;
        if (insideBoard(node.y-1,node.x+1))
        {
            if (node.state[node.y-1][node.x+1].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean upLeftNor(Node node){
        boolean result = false;
        if (insideBoard(node.y-1,node.x-1))
        {
            if (node.state[node.y-1][node.x-1].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean downRightNor(Node node){
        boolean result = false;
        if (insideBoard(node.y+1,node.x+1))
        {
            if (node.state[node.y+1][node.x+1].trim().equals(""))
                result = true;
        }
        return result;
    }

    public boolean downLeftNor(Node node){
        boolean result = false;
        if (insideBoard(node.y+1,node.x-1))
        {
            if (node.state[node.y+1][node.x-1].trim().equals(""))
                result = true;
        }
        return result;
    }

  public void printChessBoard(String[][] strChessBoard)
  {
    for (int a = 0; a < 8; a++)
      for (int b = 0; b < 8; b++)
      {

        System.out.print("y:"+a+" x:"+b+" value:"+strChessBoard[a][b]+"\t");

      }

  }
    public ArrayList<String[][]> getNodesState(ArrayList<Node> successorNode)
    {
        ArrayList<String[][]> successors = new ArrayList<String[][]>();
        for(int i=0; i< successorNode.size(); i++)
        {
            successors.add(successorNode.get(i).state);
        }
        return successors;
    }

}
