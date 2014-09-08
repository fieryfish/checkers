package applet;

import java.awt.*;

import java.lang.Object;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Vector;
import javax.swing.*;
import java.applet.*; 
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.ArrayList;

//This is the main file to start with
public class frmChessBoard extends Applet implements ActionListener, MouseListener
{
//	init configure
	private static final int MIN = -Integer.MAX_VALUE;
	private static final int MAX = Integer.MAX_VALUE;
	private static int DEPTH = 3;
	private static final int KingValue= 20;
	private static final int NormalValue= 5;
	private static boolean started = false;
	int xAdjustment;
	JLabel chessPiece;
	int yAdjustment;
	boolean canNorMove = true;
	boolean validPressed = true;
	boolean justCapMove= false;
	private static ArrayList<ArrayList<String>> BestSuccessors = new ArrayList<ArrayList<String>>();
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseMoved(MouseEvent e) {}
	Timer timer = new Timer();

	Rules rules = new Rules();
	ChessSearch space = new ChessSearch();
	JPanel top = new JPanel();
	private JPanel[][] pnlChessPieces = new JPanel[8][8];
	Point justReleased = new Point();
	boolean isMuti = false;
	boolean justCap = false;
	private JPanel pnlMain = new JPanel(new GridLayout(8,8));
	// "W" "B" is normal piece, "BK" "WK" is king piece
	private String[][] strChessBoard = new String[][] { {"B", " ", "B", " ", "B","  ", "B", " "}, {" ", "B", " ", "B", " ", "B", " ", "B"}, 
			{"B", "  ", "B", "  ", "B", "  ", "B", "  "}, {"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, 
			{"  ", "  ", "  ", "  ", "  ", "  ", "  ", "  "}, {" ", "W", "  ", "W", "  ", "W", "  ", "W"}, 
			{"W", "  ", "W", "  ", "W", "  ", "W", "  "}, {"  ", "W", "  ", "W", "  ", "W", "  ", "W"} };

	String s = System.getProperty("user.dir");
	String ss = s.substring(0,s.length()-4);
	private ImageIcon BlackIcon = new ImageIcon(ss + "/images/block.jpg");
	private ImageIcon WhiteIcon = new ImageIcon(ss + "/images/wblock.jpg");

	private ImageIcon WhiteKing = new ImageIcon(ss + "/images/wking.jpg");
	private ImageIcon BlackKing = new ImageIcon(ss + "/images/bking.jpg");
	private boolean boolMoveSelection = false, bWhite = true, wMyTurn = true;
	private Point pntMoveFrom, pntMoveTo;
	private Container container;
	private Object pnt;
	Button start = new Button("New");
	JComboBox box = new JComboBox();
	JLabel level = new JLabel("Level: ");
	JButton about = new JButton("About");
	String About = "The object is to eliminate all opposing checkers or to create a situation in which\n it is impossible for your opponent to make any move.\n Normally, the victory will be due to complete elimination.\n"+
			"Non-capturing Move\n"+
			"Black moves first and play proceeds alternately. From their initial positions,\n checkers may only move forward. There are two types of moves that can be made, \ncapturing moves and non-capturing moves. Non-capturing moves are simply a diagonal move forward from one square to an adjacent square.\n (Note that the white squares are never used.) Capturing moves occur when a player jumps an opposing piece. \nThis is also done on the diagonal and can only happen when the square behind (on the same diagonal) is also open.\n This means that you may not jump an opposing piece around a corner."+
			"Capturing Move\n"+
			"On a capturing move, a piece may make multiple jumps. \nIf after a jump a player is in a position to make another jump then he may do so. \nThis means that a player may make several jumps in succession, capturing several pieces on a single turn.";
	public void init() {
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't successfully complete");
		}
	}
	private void createGUI() {
//		GUI configure
		setSize(550, 550);
		setBackground(new Color(204, 204, 204));
		this.setLayout(null);
		pnlMain.setBounds(40, 40, 460, 460);
		pnlMain.setBackground(new Color(255, 255, 255));
		this.add(pnlMain);
		this.add(top);
		top.setBounds(0,0,480,35);
		top.setLayout(null);
		top.add(start);
		top.add(box);
		top.add(level);
		top.add(about);
		box.setBounds(135,0,80,30);
		box.addItem(0);
		box.addItem(1);
		box.addItem(2);
		box.addItem(3);
		box.addItem(4);
		box.addActionListener(this);
		level.setBounds(90,0,60,30);
		about.setBounds(250,0,70,30);
		about.addActionListener(this);

		start.setBounds(0,0,80,30);
		start.addActionListener(this);
		// mouseClicked
		//show();
		timer = new Timer();
		timer.schedule(new MutipleDisplay(), new Date(), 3000);
	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource().equals(start) && started == false){ 
			this.drawChessBoard();
			this.drawChessPieces();
			resize(550, 549);
			started = true;
			//show();
		}
		else if(e.getSource().equals(start) && started == true){ 
			JOptionPane.showMessageDialog(null, "Plz close the window and play again");
		}
		if(e.getSource() instanceof JComboBox){
			int index = box.getSelectedIndex();
			DEPTH = index+2;
		}
		else if(e.getSource().equals(about)){ 
			JOptionPane.showMessageDialog(null, About);
		}

	}
	// This method checks if attmpted move is valid or not

	private boolean isMoveValid()
	{
		boolean isMoveValid = true;
		return isMoveValid;
	}

	// This method makes the selected chess piece looks like selected

	private void makeChessPieceDifferent(boolean bSelected)
	{
		for(int z = 0; z < this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponentCount(); z++)
			if(this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(z).getClass().toString().indexOf("JLabel") > -1)
			{
				JLabel lblTemp = (JLabel)this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(z);
				lblTemp.setEnabled(!bSelected);
			}
	}

	// If class level variables Point-From and Point-To are set,
	// then this method actually moves a piece, if any exists, from
	// one cell to the other
	public void removeShadow(){
		for(int z = 0; z < this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].getComponentCount(); z++)
			if(this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].getComponent(z).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].remove(z);
				this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].repaint();
			}

		for(int z = 0; z < this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponentCount(); z++)
			if(this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].getComponent(0).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].remove(z);
				this.pnlChessPieces[this.pntMoveFrom.y][this.pntMoveFrom.x].repaint();
			}
	}

	public void removeCapturingShadow(){
		removeShadow();
		// delete middle piece
		for(int z = 0; z < this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].getComponentCount(); z++)
			if(this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].getComponent(z).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].remove(z);
				this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].repaint();
			}
		for(int z = 0; z < this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].getComponentCount(); z++)
			if(this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].getComponent(0).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].remove(z);
				this.pnlChessPieces[(this.pntMoveFrom.y+this.pntMoveTo.y)/2][(this.pntMoveFrom.x+this.pntMoveTo.x)/2].repaint();
			}
	}

	public void removeShadowByIndex(int y, int x){
		for(int z = 0; z < this.pnlChessPieces[y][x].getComponentCount(); z++)
			if(this.pnlChessPieces[y][x].getComponent(0).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[y][x].remove(z);
				this.pnlChessPieces[y][x].repaint();
			}
		for(int z = 0; z < this.pnlChessPieces[y][x].getComponentCount(); z++)
			if(this.pnlChessPieces[y][x].getComponent(z).getClass().toString().indexOf("JLabel") > -1)
			{
				this.pnlChessPieces[y][x].remove(z);
				this.pnlChessPieces[y][x].repaint();
			}

	}

	private void moveChessPiece() // deal with GUI Piece
	{
		// delete pntMoveFrom shadow

		removeShadow();
		this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].add(this.getPieceObject(this.strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x]), BorderLayout.CENTER);
		this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].validate();
	}

	// Given the code of a piece as a string, this method instantiates

	// a label object with the right image inside it

	private JLabel getPieceObject(String strPieceName)
	{
		JLabel lblTemp;
		if(strPieceName.trim()=="B")
			lblTemp = new JLabel(this.BlackIcon);
		else if(strPieceName.trim() == "W")
			lblTemp = new JLabel(this.WhiteIcon);
		else if(strPieceName.trim() == "WK")
			lblTemp = new JLabel(this.WhiteKing);
		else if(strPieceName.trim() == "BK")
			lblTemp = new JLabel(this.BlackKing);
		else
			lblTemp = new JLabel();
		return lblTemp;

	}

	// This method reads strChessBoard two-dimensional array of string

	// and places chess pieces at their right positions

	private void drawChessPieces()
	{
		for(int y = 0; y < 8; y++)
			for(int x = 0; x < 8; x++)
			{
				this.pnlChessPieces[y][x].add(this.getPieceObject(strChessBoard[y][x]), BorderLayout.CENTER);
				this.pnlChessPieces[y][x].validate();
			}
	}
	// This method draws chess board, i.e. black and white cells on the board

	private void drawChessBoard()
	{
		for (int y = 0; y < 8; y++)
			for (int x = 0; x < 8; x++)
			{
				pnlChessPieces[y][x] = new JPanel(new BorderLayout());
				pnlChessPieces[y][x].addMouseListener(this);
				pnlMain.add(pnlChessPieces[y][x]);
				if (y % 2 == 0)
					if (x % 2 != 0)
						pnlChessPieces[y][x].setBackground(Color.DARK_GRAY);
					else
						pnlChessPieces[y][x].setBackground(Color.WHITE);
				else
					if (x % 2 == 0)
						pnlChessPieces[y][x].setBackground(Color.DARK_GRAY);
					else
						pnlChessPieces[y][x].setBackground(Color.WHITE);
			}
	}


	public boolean isCapturingMove(){
		return ((Math.abs(this.pntMoveTo.x - this.pntMoveFrom.x) == 2) && (Math.abs(this.pntMoveTo.y - this.pntMoveFrom.y) == 2));
	}

	private boolean validMove(){ // move diagonal forward
		if (rules.isKingByPoint(pntMoveFrom,this.strChessBoard))
		{
			return  ((Math.abs(this.pntMoveTo.x - this.pntMoveFrom.x) == 1) && (Math.abs(this.pntMoveTo.y - this.pntMoveFrom.y) == 1));
		}
		else if (rules.isBlack(pntMoveFrom, this.strChessBoard )){
			return ((this.pntMoveTo.y - this.pntMoveFrom.y) == 1) && (Math.abs(this.pntMoveTo.x - this.pntMoveFrom.x) == 1);
		}
		else{
			return ((this.pntMoveTo.y - this.pntMoveFrom.y) == -1) && (Math.abs(this.pntMoveTo.x - this.pntMoveFrom.x) == 1);
		}
	}

	private boolean strictValidCapturingMove(){
		if (rules.isKingByPoint(pntMoveFrom,this.strChessBoard))
		{
			if ((strChessBoard[(this.pntMoveTo.y+this.pntMoveFrom.y)/2][(this.pntMoveTo.x+this.pntMoveFrom.x)/2]).trim().length()>0)
			{
				return (strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x].trim().equals("") &&
						strChessBoard[(this.pntMoveTo.y+this.pntMoveFrom.y)/2][(this.pntMoveTo.x+this.pntMoveFrom.x)/2].charAt(0) == 'B' &&
						strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].charAt(0) == 'W');
			}
			else
			{
				return false;
			}
		}
		else
		{
			if (((strChessBoard[(this.pntMoveTo.y+this.pntMoveFrom.y)/2][(this.pntMoveTo.x+this.pntMoveFrom.x)/2]).trim().length()>0)&&
					((this.pntMoveTo.y - this.pntMoveFrom.y) == -2))
			{
				return (strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x].trim().equals("") &&
						strChessBoard[(this.pntMoveTo.y+this.pntMoveFrom.y)/2][(this.pntMoveTo.x+this.pntMoveFrom.x)/2].charAt(0) == 'B' &&
						strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].charAt(0) == 'W');
			}
			else 
				return false;
		}
	}

	private boolean validCapturingMove(){
		return strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x].trim().equals("");
	}
	public void fromMovement(Point pntMoveFrom){
		if(this.boolMoveSelection)
			this.makeChessPieceDifferent(true);
	}

	public boolean existFromMove(Point pntMoveFrom){ // Do not click blank piece
		return this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString().trim() != " ";
	}

	public void capturingMove(){ // deal with GUI Pieces
		removeCapturingShadow();
		this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].add(this.getPieceObject(this.strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x]), BorderLayout.CENTER);
		this.pnlChessPieces[this.pntMoveTo.y][this.pntMoveTo.x].validate();
	}

	public boolean toMovement(){
		if (isCapturingMove() && validCapturingMove()){
			this.strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x] = this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString();
			this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x] = " ";
			this.strChessBoard[(this.pntMoveTo.y+this.pntMoveFrom.y)/2][(this.pntMoveTo.x+this.pntMoveFrom.x)/2] = " ";
			this.capturingMove();
			return true;
		}
		else if(validMove() && existFromMove(this.pntMoveFrom))
		{
			this.strChessBoard[this.pntMoveTo.y][this.pntMoveTo.x] = this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x].toString();
			this.strChessBoard[this.pntMoveFrom.y][this.pntMoveFrom.x] = " ";
			this.moveChessPiece();
			return false;
		}
		else
		{
			this.makeChessPieceDifferent(false);  // piece setEnabled(false)
			return false;
		}
	}

	public boolean movePiece()
	{
		boolean isCap = false;
		if ((this.pntMoveTo.x != this.pntMoveFrom.x) && (this.pntMoveTo.y != this.pntMoveFrom.y))
			isCap = toMovement();
		return isCap;
	}


	public void changeTurn(){
		this.wMyTurn = !this.wMyTurn;
	}


	public void mousePressed(MouseEvent e){
		validPressed = true;
		justCap = false;
		Point moveWhichPoint;
		Object source = e.getComponent();
		JPanel pnlTemp = (JPanel)source;

		int intX = (pnlTemp.getX()/57);
		int intY = (pnlTemp.getY()/57);
		moveWhichPoint = new Point(intX, intY);
		if (isMuti == true)
		{
			if ((moveWhichPoint.x != justReleased.x)&& (moveWhichPoint.y != justReleased.y))
			{
				JOptionPane.showMessageDialog(null, "Mutiple Jump! please!");
			}
		}

		if ((this.wMyTurn) && rules.isWhite(moveWhichPoint, this.strChessBoard))       // if white is my turn
		{

			ArrayList<Node> validCapPieces = space.getValidCapPieces(this.strChessBoard,false);
			ArrayList<ArrayList<Integer>> validCapAxis = space.getNodeAxis(validCapPieces);
			ArrayList<Node> validPieces = space.getValidPieces(this.strChessBoard);
			ArrayList<ArrayList<Integer>> validAxis = space.getNodeAxis(validPieces);
			//capture
			if (validCapPieces.size() >0){
				for (int i = 0; i < validCapAxis.size(); i++) {
					if (validCapAxis.get(i).get(0) == moveWhichPoint.x &&validCapAxis.get(i).get(1) == moveWhichPoint.y )
					{
						pntMoveFrom = moveWhichPoint;
						break;
					}
				}
				if ((pntMoveFrom.x != moveWhichPoint.x)&&(pntMoveFrom.y != moveWhichPoint.y))
				{
					JOptionPane.showMessageDialog(null, "from invalid move");
					validPressed = false;
					return;
				}
				justCap = true;
			}
			else if (validPieces.size() >0){
				//normal
				for (int i = 0; i < validAxis.size(); i++) {
					if (validAxis.get(i).get(0) == moveWhichPoint.x &&validAxis.get(i).get(1) == moveWhichPoint.y )
					{
						pntMoveFrom = moveWhichPoint;
						break;
					}
				}
				if ((pntMoveFrom.x != moveWhichPoint.x)&&(pntMoveFrom.y != moveWhichPoint.y))
				{
					validPressed = false;
					JOptionPane.showMessageDialog(null, "from2 invalid move");
					return;
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "You lose");
			}

		}
	}

	public void mouseReleased(MouseEvent e){
		if (!(validPressed))
			return;
		isMuti = false;
		Point moveWhichPoint;
		//pntMoveTo;
		boolean isCap = false;
		Object source = e.getComponent();
		JPanel pnlTemp = (JPanel)source;
		int newX;
		int newY;
		if (e.getX()<0)
			newX= e.getX()-57;
		else
			newX= e.getX();

		if (e.getY()<0)
			newY= e.getY()-57;
		else
			newY= e.getY();

		int intX = pntMoveFrom.x+(newX/57);
		int intY = pntMoveFrom.y+(newY/57);
		moveWhichPoint = new Point(intX, intY);
		pntMoveTo = moveWhichPoint;
		if ((strictValidCapturingMove() && justCap)|| (validMove() && !justCap))
		{
			isCap = movePiece();
			changeKing(this.strChessBoard);

			Node parentPiece = new Node(pntMoveTo.x, pntMoveTo.y, strChessBoard, null);
			if ((space.getCapturingSuccessorsNodeByPiece(parentPiece,false)).size()>0 && isCap)
			{
				JOptionPane.showMessageDialog(null, "Mutiple Jump");
				justReleased = pntMoveTo;
				isMuti =true;
			}
			else {
				changeTurn();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "to invalid move");
		}
	}

	public void changeKing(String[][] chessBoard)
	{
		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
			{
				if ((r==0) && rules.isWhiteByStr(chessBoard[r][c]))
				{
					removeShadowByIndex(r, c);
					this.strChessBoard[r][c] = "WK";
					this.pnlChessPieces[r][c].add(this.getPieceObject(this.strChessBoard[r][c]), BorderLayout.CENTER);
					this.pnlChessPieces[r][c].validate();
				}
				if ((r==7) && rules.isBlackByStr(chessBoard[r][c]))
				{
					removeShadowByIndex(r, c);
					this.strChessBoard[r][c] = "BK";
					this.pnlChessPieces[r][c].add(this.getPieceObject(this.strChessBoard[r][c]), BorderLayout.CENTER);
					this.pnlChessPieces[r][c].validate();
				}
			}
	}

	public int getDepth()
	{
		return DEPTH;
	}

	public void robotMove(String[][] strChessBoard)
	{
		if (!wMyTurn)
		{
			run(strChessBoard);
			//after robot then player move
			changeTurn();
			changeKing(this.strChessBoard);
		}
	}

	void run(String[][] strChessBoard){
		ArrayList<String[][]> bestBoards = new ArrayList<String[][]>();
		String[][] bestBoard = new String[8][8];
		ArrayList<String[][]> successors = new ArrayList<String[][]>();
		ArrayList<ArrayList<String[][]>> fullSuccessors = new ArrayList<ArrayList<String[][]>>();
		ArrayList<Node> fullSuccessorsNode = new ArrayList<Node>();

		ArrayList<String[][]> unfullSuccessors = new ArrayList<String[][]>();
		int bestValue = MIN;
		String[][] tmpBoard = new String[8][8];
		tmpBoard = copyChessBoard(strChessBoard);

		// player lose?
		successors = space.getSuccessors(tmpBoard, true);
		if (successors.size() ==0){
			JOptionPane.showMessageDialog(null, "You win");
		}

		for(int i=0;i<successors.size();i++)
		{
			int value = minValue(successors.get(i), MIN, MAX, DEPTH-1);//下一层取最小值

			if (bestValue < value)
			{
				bestValue = value;
				bestBoards.clear();
				bestBoards.add(successors.get(i));
			}
		}

		int randomIndex = (int)(Math.random() * bestBoards.size());

		bestBoard = bestBoards.get(randomIndex);

		//
		//      mutiple jump effect
		fullSuccessorsNode = space.getCapSuccessorsNodeWithMiddle(strChessBoard, true);

		if (!(fullSuccessorsNode.size() == 0))
		{
			for (int i=0; i< fullSuccessorsNode.size();i++)
			{
				if (Arrays.deepEquals(bestBoard, (String[][])fullSuccessorsNode.get(i).state))
				{

					Node target = fullSuccessorsNode.get(i);

					Vector<Node> targetList = target.getPath();
					System.out.println(targetList);
					ArrayList<Node> targetListArr = new ArrayList<Node>(targetList);
					ArrayList<String[][]> chessBoards = new ArrayList<String[][]>();
					chessBoards = rules.getNodesState(targetListArr);
					int size = chessBoards.size();//3
					for(int j=0;j<chessBoards.size()-1;j++)
					{
						moveThebestBoard(chessBoards.get(j+1),chessBoards.get(j));
						try {
							Thread.sleep(400);
						} catch (InterruptedException e) {
							e.printStackTrace(); 
						}
						// sleep
					}
					break;
				}
				else
				{
					moveThebestBoard(bestBoard, strChessBoard);
				}
			}
		}
		else
		{
			moveThebestBoard(bestBoard, strChessBoard);
		}
		//change strChessBoard
		this.strChessBoard = bestBoard;

		tmpBoard= copyChessBoard(this.strChessBoard);
		successors = space.getSuccessors(tmpBoard, false);
		if (successors.size()==0)
			JOptionPane.showMessageDialog(null, "You lose");
	}

	public void moveThebestBoard(String[][] newChessBoard, String[][] originChessBoard)
	{
		ArrayList<Integer> remove = new ArrayList<Integer>();
		ArrayList<Integer> add = new ArrayList<Integer>();
		for(int y = 0; y < 8; y++)
			for(int x = 0; x < 8; x++)
			{
				if (newChessBoard[y][x] != originChessBoard[y][x])
				{
					//remove from origin
					if (newChessBoard[y][x].trim().equals(""))
					{
						remove.add(y);
						remove.add(x);
					}
					// add of origin
					else
					{
						add.add(y);
						add.add(x);
					}
				}
			}
		for(int i = 0; i< remove.size(); i+=2)
		{
			removeShadowByIndex(remove.get(i),remove.get(i+1));
		}
		for(int i = 0; i< add.size(); i+=2)
		{
			this.pnlChessPieces[add.get(i)][add.get(i+1)].add(this.getPieceObject(newChessBoard[add.get(i)][add.get(i+1)]), BorderLayout.CENTER);
			this.pnlChessPieces[add.get(i)][add.get(i+1)].validate();
		}
	}


	private int maxValue(String[][] strChessBoard, int a,int b, int depth)
	{

		if(depth==0) 
			return getValue(strChessBoard);

		int bestValue = MIN;
		int minValue = MIN;
		ArrayList<String[][]> successors = new ArrayList<String[][]>();
		String[][] tmpBoard = new String[8][8];
		tmpBoard= copyChessBoard(strChessBoard);
		successors = space.getSuccessors(tmpBoard, true);  //second params is maximizingPlayer
		for(int i=0;i<successors.size();i++)
		{
			int tmp = minValue(successors.get(i), a, b, depth-1);//下一层取最小值
			if(minValue < tmp)
				minValue = tmp;

			bestValue = bestValue > minValue ? bestValue : minValue;

			if(bestValue>=b)
				return bestValue;

			a = a>bestValue ? a : bestValue;
		}
		return bestValue;
	}

	private int minValue(String[][] strChessBoard, int a, int b, int depth)
	{

		if(depth==0)
			return getValue(strChessBoard);

		int bestValue = MAX;
		int maxValue = MAX;
		ArrayList<String[][]> successors = new ArrayList<String[][]>();
		String[][] tmpBoard = new String[8][8];
		tmpBoard= copyChessBoard(strChessBoard);
		successors = space.getSuccessors(tmpBoard, false);  //second params is maximizingPlayer
		for(int i=0;i<successors.size();i++)
		{
			int tmp = maxValue(successors.get(i), a, b, depth-1);//下一层取最小值
			if(maxValue > tmp)
				maxValue = tmp;

			bestValue = bestValue < maxValue ? bestValue : maxValue;
			if(bestValue<=a)
				return bestValue;

			b = b<bestValue ? b: bestValue;
		}
		return bestValue;
	}

	public int getValue(String[][] strChessBoard){
		int whiteNum=0;
		int blackNum=0;
		int blackKingNum=0;
		int whiteKingNum=0;
		int value = 0;

		for (int r = 0; r < 8; r++)
			for (int c = 0; c < 8; c++)
			{
				if (rules.isBlackKingByStr(strChessBoard[r][c]))
					blackKingNum += 1;
				else if (rules.isWhiteKingByStr(strChessBoard[r][c]))
					whiteKingNum += 1;
				else if (rules.isBlackByStr(strChessBoard[r][c]))
					blackNum += 1;
				else if (rules.isWhiteByStr(strChessBoard[r][c]))
					whiteNum += 1;
			}

		if ((blackKingNum + blackNum)==0)
			value = MIN;
		else if ((whiteKingNum + whiteNum)==0)
			value = MAX;
		else
			value = blackKingNum*KingValue + blackNum*NormalValue - whiteKingNum*KingValue - whiteNum*NormalValue;

		return value;
	}

	public String[][] copyChessBoard(String[][] strChessBoard)
	{
		String[][] tmpBoard=new String[8][8];
		for (int a = 0; a < 8; a++)
			for (int b = 0; b < 8; b++)
			{
				tmpBoard[a][b] = strChessBoard[a][b];
			}
		return tmpBoard;
	}

	protected class MutipleDisplay extends TimerTask
	{

		public void run(){
			if (wMyTurn == false)
			{
				robotMove(strChessBoard);
				justCapMove =false;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
