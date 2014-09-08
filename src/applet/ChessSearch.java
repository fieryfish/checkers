package applet;

import java.awt.Point;
import java.awt.List;
import java.util.ArrayList;
// This file is about minimax algorithms for AI
public class ChessSearch{

    Rules rules = new Rules();

    public ArrayList<Node> getCapSuccessorsNodeWithMiddle(String[][] strChessBoard, boolean maxPlayer)
    {
        ArrayList<Node> successorsNode = new ArrayList<Node>();
        ArrayList<Node> tmp = new ArrayList<Node>();
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                if (maxPlayer == true)
                {
                    if(rules.isBlackByStr(strChessBoard[r][c]))
                    {
                        Node parentPiece = new Node(c, r, strChessBoard, null);
                        tmp = getCapturingSuccessorsNodeByPiece(parentPiece,maxPlayer);
                        if (!(tmp.size() == 0))
                        {
                            successorsNode.addAll(tmp);
                        }
                    }
                }
                else
                {
                    if(rules.isWhiteByStr(strChessBoard[r][c]))
                    {
                        Node parentPiece = new Node(c, r, strChessBoard, null);

                        tmp = getCapturingSuccessorsNodeByPiece(parentPiece,maxPlayer);
                        if (!(tmp.size() == 0))
                        {
                            successorsNode.addAll(tmp);
                        }
                    }

                }

            }
        return successorsNode;
    }

    public ArrayList<Node> getValidPieces(String[][] strChessBoard)
    {
        ArrayList<Node> validPieces = new ArrayList<Node>();
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                if(rules.isWhiteByStr(strChessBoard[r][c]))
                {
                    Node parentPiece = new Node(c, r, strChessBoard, null);
                    if (getNorSuccessorsNodeByPiece(parentPiece).size() != 0)
                    {
                        validPieces.add(parentPiece);
                    }

                }
            }
        return validPieces;
    }

    public ArrayList<Node> getValidCapPieces(String[][] strChessBoard,boolean isRobot)
    {
        ArrayList<Node> validPieces = new ArrayList<Node>();
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                if(rules.isWhiteByStr(strChessBoard[r][c]))
                {
                    Node parentPiece = new Node(c, r, strChessBoard, null);
                    if (getCapturingSuccessorsNodeByPiece(parentPiece,isRobot).size() != 0)
                    {
                        validPieces.add(parentPiece);
                    }

                }
            }
        return validPieces;
    }

  public ArrayList<ArrayList<Integer>> getNodeAxis(ArrayList<Node> nodes)
  {
	  ArrayList<ArrayList<Integer>> nodeAxises = new ArrayList<ArrayList<Integer>>();

	  for(int i=0;i<nodes.size();i++)
	  {
		  ArrayList<Integer> nodeAxis = new ArrayList<Integer>();
		  int x = nodes.get(i).x;
		  int y = nodes.get(i).y;
		  nodeAxis.add(x);
		  nodeAxis.add(y);
		  nodeAxises.add(nodeAxis);
	  }
	  return nodeAxises;

  }

    public ArrayList<String[][]> getSuccessors(String[][] strChessBoard, boolean maxPlayer)
    {
        ArrayList<String[][]> successors = new ArrayList<String[][]>();
        ArrayList<Node> successorCapNode = new ArrayList<Node>();
        ArrayList<Node> successorNorNode = new ArrayList<Node>();
        successorCapNode = getCapSuccessorsNodeWithMiddle(strChessBoard, maxPlayer);
        //if (!((successors.size()==1) && (successors.get(0).size()==0))) // not [[]]
        if (!(successorCapNode.size() == 0))
        {
            successors = rules.getNodesState(successorCapNode);

        }
        else
        {
            successorNorNode = getNormalSuccessors(strChessBoard, maxPlayer);

            successors = rules.getNodesState(successorNorNode);
        }

        return successors;
    }


    public ArrayList<Node> getCapturingSuccessorsNodeByPiece(Node parent, boolean isRobot)
    {
        ArrayList<Node> nodeSuccessors = new ArrayList<Node>();
        ArrayList<Node> path = new ArrayList<Node>();
        path.add(parent);  // [parent]

        while (path.size() > 0) {
            Node newParent = path.remove(0); 
            ArrayList<Node> successor = get1LayerCaptureSuccessorsByPiece(newParent,isRobot);

            if (successor.size()==0 && (!(parent.equals(newParent))))
                nodeSuccessors.add(newParent);
            else
            {
                for (int i = 0; i < successor.size(); i++) {
                    Node child = successor.get(i); 

                    path.add(child);
                }
            }
        }

        return nodeSuccessors;
    }



    // Normal 
    public ArrayList<Node> getNormalSuccessors(String[][] strChessBoard, boolean maxPlayer)
    {
        ArrayList<Node> successorsNode = new ArrayList<Node>();
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
            {
                if(maxPlayer){
                    if(rules.isBlackByStr(strChessBoard[r][c]))
                    {
                        Node parentPiece = new Node(c, r, strChessBoard, null);
                        successorsNode.addAll(getNorSuccessorsNodeByPiece(parentPiece));
                    }
                }
                else{
                    if(rules.isWhiteByStr(strChessBoard[r][c]))
                    {
                        Node parentPiece = new Node(c, r, strChessBoard, null);
                        successorsNode.addAll(getNorSuccessorsNodeByPiece(parentPiece));
                    }
                }
            }
        return successorsNode;
    }

    public ArrayList<Node> getNorSuccessorsNodeByPiece(Node parent)
    {
        ArrayList<Node> nodeSuccessors = new ArrayList<Node>();

        if (rules.isKingByNode(parent, parent.state))
        {
            if (rules.downRightNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downRightNor(child);
                    nodeSuccessors.add(child);
            }
            if (rules.downLeftNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downLeftNor(child);
                    nodeSuccessors.add(child);
            }
            if (rules.upRightNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upRightNor(child);
                    nodeSuccessors.add(child);
            }
            if (rules.upLeftNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upLeftNor(child);
                    nodeSuccessors.add(child);
            }
        }
        else if (rules.isBlackNormal(parent, parent.state))
        {
            if (rules.downRightNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downRightNor(child);
                    nodeSuccessors.add(child);
            }
            if (rules.downLeftNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downLeftNor(child);
                    nodeSuccessors.add(child);
            }
        }
        else if (rules.isWhiteNormal(parent, parent.state))
        {
            if (rules.upRightNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upRightNor(child);
                    nodeSuccessors.add(child);
            }
            if (rules.upLeftNor(parent))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upLeftNor(child);
                    nodeSuccessors.add(child);
            }
        }
        return nodeSuccessors;
    }

    //  return node of capturingSuccessors maybe []
    public ArrayList<Node> get1LayerCaptureSuccessorsByPiece(Node parent,boolean isRobot)
    {
        ArrayList<Node> capturingSuccessors = new ArrayList<Node>();

        if (rules.isKingByNode(parent, parent.state)) //black king capture
        {
            // [string[][], string[][]]
            capturingSuccessors = getKingCapturing(parent, isRobot);
        }
        else
            capturingSuccessors = getNormalCapturing(parent,isRobot);
        return capturingSuccessors;
    }

    public ArrayList<Node> getNormalCapturing(Node parent, boolean isRobot)
    {
        ArrayList<Node> norCapturingSuccessors = new ArrayList<Node>();
        if (parent.pieceName == "B")
        {
            if (rules.downRightCap(parent,isRobot))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downRightCapture(child);
                norCapturingSuccessors.add(child);
            }
            if (rules.downLeftCap(parent,isRobot))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = downLeftCapture(child);
                norCapturingSuccessors.add(child);
            }
        }
        else
        {
            if (rules.upRightCap(parent,isRobot))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upRightCapture(child);
                norCapturingSuccessors.add(child);
            }
            if (rules.upLeftCap(parent,isRobot))
            {
                Node child = new Node(parent.x, parent.y, parent.state, parent);
                child = upLeftCapture(child);
                norCapturingSuccessors.add(child);
            }
        }
        return norCapturingSuccessors;
    }

    public ArrayList<Node> getKingCapturing(Node parent,boolean isRobot)
    {
        ArrayList<Node> kingCapturingSuccessors = new ArrayList<Node>();
        if (rules.downRightCap(parent, isRobot))
        {
            Node child = new Node(parent.x, parent.y, parent.state, parent);
            child = downRightCapture(child);
            kingCapturingSuccessors.add(child);
        }

        if (rules.upRightCap(parent, isRobot))
        {
            Node child = new Node(parent.x, parent.y, parent.state, parent);
            child = upRightCapture(child);
            kingCapturingSuccessors.add(child);
        }

        if (rules.downLeftCap(parent, isRobot))
        {
            Node child = new Node(parent.x, parent.y, parent.state, parent);
            child = downLeftCapture(child);
            kingCapturingSuccessors.add(child);
        }
        if (rules.upLeftCap(parent, isRobot))
        {
            Node child = new Node(parent.x, parent.y, parent.state, parent);
            child = upLeftCapture(child);
            kingCapturingSuccessors.add(child);
        }
        return kingCapturingSuccessors;
    }


    public Node downLeftNor(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y+1][node.x-1] = pieceName;
        node.y = node.y+1;
        node.x = node.x-1;
        return node;
    }

    public Node downRightNor(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y+1][node.x+1] = pieceName;
        node.y = node.y+1;
        node.x = node.x+1;
        return node;
    }

    public Node upLeftNor(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y-1][node.x-1] = pieceName;
        node.y = node.y-1;
        node.x = node.x-1;
        return node;
    }

    public Node upRightNor(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y-1][node.x+1] = pieceName;
        node.y = node.y-1;
        node.x = node.x+1;
        return node;
    }

    public Node upLeftCapture(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y-1][node.x-1] = "";
        node.state[node.y-2][node.x-2] = node.pieceName;
        node.y = node.y-2;
        node.x = node.x-2;
        return node;
    }

    public Node upRightCapture(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y-1][node.x+1] = "";
        node.state[node.y-2][node.x+2] = node.pieceName;
        node.y = node.y-2;
        node.x = node.x+2;
        return node;
    }

    public Node downRightCapture(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y+1][node.x+1] = "";
        node.state[node.y+2][node.x+2] = node.pieceName;
        node.y = node.y+2;
        node.x = node.x+2;
        return node;
    }

    public Node downLeftCapture(Node node)
    {
        String pieceName = node.pieceName;
        node.state[node.y][node.x]     = "";
        node.state[node.y+1][node.x-1] = "";
        node.state[node.y+2][node.x-2] = node.pieceName;
        node.y = node.y+2;
        node.x = node.x-2;
        return node;
    }
}
