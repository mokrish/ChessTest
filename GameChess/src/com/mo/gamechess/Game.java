package com.mo.gamechess;

import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Game 
{
	private Map<String,ChessGamePiece> gameBoard;		
	private Map<ChessGamePiece,List<String>> simulatedPossibleMoves;
	private List<ChessGamePiece> killedPieces;
	public  static final String validPositions[][] = new String[8][8];
	
	
	static
	{  /*populate values for validPosition dynamically  (a1..h8)*/
		for(int j=1,l=0;j<9;j++,l++)
		{
			for(int i=97,k=0;i<105;i++,k++)
			{
				char temp =(char)i;			
				Game.validPositions[k][l]=""+temp+j;
			}
		}
		for(int j=0;j<8;j++)
		{
			System.out.println();			
			for (int i=0;i<8;i++)
			{
				System.out.print(" "+Game.validPositions[i][j]);
			}
		}		
	}	
	
	public Game()
	{
		gameBoard = new HashMap<>();
		simulatedPossibleMoves = new HashMap<>();
		initialize();
		killedPieces = new ArrayList<ChessGamePiece>();
	}	
	
	private boolean initialize()
	{
		//Clear contents of Game Board
		gameBoard.clear();		
		
		//Add Rook's
		gameBoard.put("a1", new Rook("wR1",Colour.WHITE));
		gameBoard.put("h1", new Rook("wR2",Colour.WHITE));
		gameBoard.put("a8", new Rook("bR1",Colour.BLACK));
		gameBoard.put("h8", new Rook("bR2",Colour.BLACK));
		
		//Add knights
		gameBoard.put("b1", new Knight("wN1",Colour.WHITE));
		gameBoard.put("g1", new Knight("wN2",Colour.WHITE));
		gameBoard.put("b8", new Knight("bN1",Colour.BLACK));
		gameBoard.put("g8", new Knight("bN2",Colour.BLACK));
		
		//Add bishops
		gameBoard.put("c1", new Bishop("wB1",Colour.WHITE));
		gameBoard.put("f1", new Bishop("wB2",Colour.WHITE));
		gameBoard.put("c8", new Bishop("bB1",Colour.BLACK));
		gameBoard.put("f8", new Bishop("bB2",Colour.BLACK));		

		//Add Queen's
		gameBoard.put("d1", new Queen("wQ ",Colour.WHITE));		
		gameBoard.put("d8", new Queen("bQ ",Colour.BLACK));
		
		//Add King's		
		gameBoard.put("e1", new King("wK ",Colour.WHITE));		
		gameBoard.put("e8", new King("bK ",Colour.BLACK));
		
		//Add Pawn's
		for(int i=97;i<105;i++)
		{
			gameBoard.put(((char)i)+"2",new Pawn("wp"+(i-96),Colour.WHITE,true));
		}
		for(int i=97;i<105;i++)
		{
			gameBoard.put(((char)i)+"7",new Pawn("bp"+(i-96),Colour.BLACK,true));
		}
		
		simulatePossibleMoves(gameBoard);
							
		return true;
	}
	
	public void simulatePossibleMoves(Map<String,ChessGamePiece> gameBoard)
	{		
		List<String> kingLocations = new ArrayList<>();
		simulatedPossibleMoves.clear();
		Set<Entry<String, ChessGamePiece>> entrySet = gameBoard.entrySet();
		Iterator<Map.Entry<String,ChessGamePiece>> iterator = entrySet.iterator();
		
		while(iterator.hasNext())
		{
			Map.Entry<String,ChessGamePiece> entry = iterator.next();
			if(entry.getValue()!=null)
			{					
				simulatedPossibleMoves.put(entry.getValue(), entry.getValue().getPossibleMoves(gameBoard, entry.getKey()));
				
				if(entry.getValue() instanceof King)
				{
					kingLocations.add(entry.getKey());
				}
			}					
		}
		
		//remove unsafe locations for Kings
//		for(String location:kingLocations)
//		{
//			King king = (King) gameBoard.get(location);
//			simulatedPossibleMoves.get(king).removeAll(getUnSafeCells(king.getColour()));
//		}
		
	}
	
	public List<String> getUnSafeCells(Colour colour)
	{
		List<String> unsafeCells = new ArrayList<String>();
		Set<Map.Entry<ChessGamePiece, List<String>>> mapEntrySet =  simulatedPossibleMoves.entrySet();
		
		for(Map.Entry<ChessGamePiece, List<String>> mapEntry: mapEntrySet)
		{
			if(mapEntry.getKey().colour!=colour)
			{
				unsafeCells.addAll(mapEntry.getValue());
			}
		}
		
		return unsafeCells;
	}	
	
	public void startGame()
	{
		boolean validMove=false;
		String str="begin";
		System.out.println("");
		System.out.println("");
		System.out.println("Start game White's turn");
		try
		(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
		{
			while(!str.equals("Exit"))
			{	
				for(Colour colour:Colour.values())
				{
					printBoard();
					if(!str.equals("Exit"))
					{
						validMove=false;
					}
					while(!validMove)
					{
						System.out.print(colour.name()+"'s Turn => Enter your move:");
						str=br.readLine();
						if(!str.equals("Exit"))
						{
							validMove=makeAMove(str,colour);
						}
						else
						{
							validMove=true;
						}
					}
					
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getInput()
	{
		try
		(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
		{
			System.out.println("Enter your move:");
//			System.out.println(br.readLine());
			String tmpString = br.readLine();
			br.close();
			return tmpString;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Exit";
		}
	}
	
	public void printBoard()
	{
		
		//To print a-h
		System.out.println("");
		System.out.println("");
		for(int i=0;i<8;i++)
		{
			System.out.print("  "+(char)(97+i)+" ");
		}
		System.out.println("");
		
		for(int j=0;j<8;j++)
		{			
			for(int i=0;i<8;i++)
			{
				if(j>0)
				{
					if(i==0)
					{
						System.out.print(j+"___");
					}
					else
					{
						System.out.print("|___");
					}
					if(i==7)
					{	// To Print right most vertical line of a row
						System.out.print("|");
					}
				}
				else
				{   // To print top horizontal line					
					System.out.print("____");
				}
			}
			System.out.println();
			for (int i=0;i<8;i++)
			{					
				// To print vertical line between cells and name of the piece in that location
				System.out.print("|");				
				System.out.print((gameBoard.get(Game.validPositions[i][j])==null)?"   ":gameBoard.get(Game.validPositions[i][j]).getName());
			}
			System.out.println("|");
		}		
		for(int i=0;i<8;i++)
		{	//To print last horizontal line
			if(i==0)
			{
				System.out.print("8___");
			}
			else
			{
				System.out.print("|___");
			}
		}
		System.out.print("|");
		System.out.println("");
		
		//Print killed pieces
		for(Colour colour:Colour.values())
		{
			System.out.println("");
			System.out.print("Killed "+colour.name()+"'s: ");
			for(ChessGamePiece killedPiece:killedPieces)
			{
				if(killedPiece.colour==colour)
				{
					System.out.print(killedPiece.getName()+",");
				}
			}
		}
		System.out.println("");
		 
	}
	
	public boolean makeAMove(String moveDescription,Colour colour)
	{
		
		if(moveDescription==null)
		{
			System.out.println("Invalid input: Move cannot be NULL");
			return false;
		}
		else if(moveDescription.length()==6)
		{
	//		String pieceType = ""+moveDescription.charAt(0);
			String sourceLocation = moveDescription.substring(1,3);
			String moveType = moveDescription.substring(3,4);
			String destinationLocation = moveDescription.substring(4, 6);
			
			
			ChessGamePiece pieceToBeMoved = gameBoard.get(sourceLocation);
			
			//Check if the move requested is possible or not
			if(pieceToBeMoved == null)
			{
				System.out.println("Invalid Move: No piece available in "+sourceLocation+"");
				return false;
			}
//			else if(pieceToBeMoved.colour==colour&&pieceToBeMoved.getPossibleMoves(gameBoard, sourceLocation).contains(destinationLocation))
			
			simulatedPossibleMoves.put(pieceToBeMoved, pieceToBeMoved.getPossibleMoves(gameBoard, sourceLocation));
			
			if(pieceToBeMoved.colour==colour&&simulatedPossibleMoves.get(pieceToBeMoved).contains(destinationLocation))
			{
				ChessGamePiece pieceKilled=gameBoard.put(destinationLocation, gameBoard.get(sourceLocation));
				gameBoard.put(sourceLocation,null);
				
				if(moveType.equals("X") && pieceKilled!=null)
				{
					killedPieces.add(pieceKilled);
				}
				else if(moveType.equals("X") && pieceKilled==null)
				{
					System.out.println("Invalid move, No opponent peiece to kill in destination position, rolling back the move");
					gameBoard.put(sourceLocation,gameBoard.get(destinationLocation));
					gameBoard.put(destinationLocation,null);
					return false;
				}
				else if(moveType.equals("-") && pieceKilled!=null)
				{
					System.out.println("Invalid move, opponent piece will be killed with this move, use 'X' instead of'-' to indicate kill ");
					gameBoard.put(sourceLocation,gameBoard.get(destinationLocation));
					gameBoard.put(destinationLocation,pieceKilled);
					return false;
				}
			}
			else if(pieceToBeMoved.colour!=colour)
			{
				System.out.println(" Invalid Move: You are not allowed to move "+pieceToBeMoved.colour+" piece");
				return false;
			}
			else
			{
				System.out.println(" Intended move to "+destinationLocation+" is not allowed: possible moves for "+pieceToBeMoved.getName()
									+" are "+simulatedPossibleMoves.get(pieceToBeMoved));
				return false;
			}
			
			if(gameBoard.get(destinationLocation) instanceof Pawn)
			{
				Pawn pawn=(Pawn)gameBoard.get(destinationLocation);
				pawn.setInInitialPosition(false);			
			}	
			
			simulatedPossibleMoves.put(pieceToBeMoved, pieceToBeMoved.getPossibleMoves(gameBoard, destinationLocation));
		}	
		else if(moveDescription.equals("O-O-O"))
		{
			King king;
			String location;
			
			if(colour==Colour.WHITE) 
			{
				location="e1";				
			}
			else
			{
				location="e8";
			}
			
			try
			{
				king = (King)gameBoard.get(location);
			}
			catch(ClassCastException e)
			{
				System.out.println(" King is not available at "+location+" Castling cannot be done");
				return false;
			}
			catch(NullPointerException e)
			{
				System.out.println(" No object is available at "+location+" Castling cannot be done");
				return false;				
			}
			
			if(colour==Colour.WHITE&&king.validateQueenSideCastling(gameBoard,colour,getUnSafeCells(colour)))
			{
				gameBoard.put("c1",gameBoard.get("e1"));
				gameBoard.put("d1",gameBoard.get("a1"));
				gameBoard.put("e1",null);
				gameBoard.put("a1",null);
			}
			else if(colour==Colour.BLACK&&king.validateQueenSideCastling(gameBoard,colour,getUnSafeCells(colour)))
			{
				gameBoard.put("c8",gameBoard.get("e8"));
				gameBoard.put("d8",gameBoard.get("a8"));
				gameBoard.put("e8",null);
				gameBoard.put("a8",null);
			}
			else
			{
				return false;
			}
		}
		else if(moveDescription.equals("O-O"))
		{
			King king;
			String location;
			
			if(colour==Colour.WHITE) 
			{
				location="e1";				
			}
			else
			{
				location="e8";
			}
			
			try
			{
				king = (King)gameBoard.get(location);
			}
			catch(ClassCastException e)
			{
				System.out.println(" King is not available at "+location+" Castling cannot be done");
				return false;
			}
			catch(NullPointerException e)
			{
				System.out.println(" No object is available at "+location+" Castling cannot be done");
				return false;				
			}
			
			if(colour==Colour.WHITE&&king.validateKingSideCastling(gameBoard,colour,getUnSafeCells(colour)))
			{
				gameBoard.put("g1",gameBoard.get("e1"));
				gameBoard.put("f1",gameBoard.get("h1"));
				gameBoard.put("e1",null);
				gameBoard.put("h1",null);
			}
			else if(colour==Colour.BLACK&&king.validateKingSideCastling(gameBoard,colour,getUnSafeCells(colour)))
			{
				gameBoard.put("g8",gameBoard.get("e8"));
				gameBoard.put("f8",gameBoard.get("h8"));
				gameBoard.put("e8",null);
				gameBoard.put("h8",null);
			}
			else
			{
				return false;
			}
		}
		else
		{
			System.out.println("Invalid Move:");
			return false;
		}
		
		return true;
	}
}
