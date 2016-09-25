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
	private Map<Integer,List<String>> gameMoves;
	private boolean inCheckMode;
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
		gameMoves = new HashMap<>();
		inCheckMode=false;
	}	
	
	private boolean initialize()
	{
		//Clear contents of Game Board
		gameBoard.clear();		
		
		//Add Rook's
		gameBoard.put("a1", new Rook("wR1",Colour.WHITE,true));
		gameBoard.put("h1", new Rook("wR2",Colour.WHITE,true));
		gameBoard.put("a8", new Rook("bR1",Colour.BLACK,true));
		gameBoard.put("h8", new Rook("bR2",Colour.BLACK,true));
		
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
		gameBoard.put("e1", new King("wK ",Colour.WHITE,true));		
		gameBoard.put("e8", new King("bK ",Colour.BLACK,true));
		
		//Add Pawn's
		for(int i=97;i<105;i++)
		{
			gameBoard.put(((char)i)+"2",new Pawn("wp"+(i-96),Colour.WHITE,true));
		}
		for(int i=97;i<105;i++)
		{
			gameBoard.put(((char)i)+"7",new Pawn("bp"+(i-96),Colour.BLACK,true));
		}
		
		simulatePossibleMoves();
							
		return true;
	}
	
	public void simulatePossibleMoves()
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
		
		//commented below code, as player should be able to judge safe location for his King
		//remove unsafe locations for Kings
//		for(String location:kingLocations)
//		{
//			King king = (King) gameBoard.get(location);
//			simulatedPossibleMoves.get(king).removeAll(getUnSafeCells(king.getColour()));
//		}
		
	}
	
	//Returns list of all possible moves for all opposite colour pieces
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
	
	public String getKingLocation(Colour colour)
	{
		Set<Map.Entry<String, ChessGamePiece>> mapEntrySet =  gameBoard.entrySet();
		
		for(Map.Entry<String, ChessGamePiece> mapEntry: mapEntrySet)
		{
			if((mapEntry.getValue() instanceof King) && mapEntry.getValue().colour==colour)
			{
				return mapEntry.getKey();
			}
		}
		
		return null;
	}
	
	public void startGame()
	{
		boolean validMove=false;
		int counter=0;
		List<String> tempList = new ArrayList<>();
		killedPieces.clear();
		gameMoves.clear();
		String str="begin";
		System.out.println("");
		System.out.println("");
		System.out.println("Start game White's turn");
		try
		(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
		{			
			while(!str.equals("Exit"))
			{
				counter++;
				tempList = new ArrayList<>();
				for(Colour colour:Colour.values())
				{
					printBoard();
					if(!str.equals("Exit"))
					{
						validMove=false;
					}
					
					if(inCheckMode)
					{
						System.out.println("in CHECK mode: Save your King");
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
					tempList.add(str);
					
				}
				gameMoves.put(counter, tempList);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void printAllMoves()
	{
		Set<Map.Entry<Integer,List<String>>> entrySet = gameMoves.entrySet();
		
		System.out.println("");
		System.out.println("Game Moves:");
		for(Map.Entry<Integer, List<String>> entry:entrySet)
		{
			System.out.println("");
			System.out.print(" "+entry.getKey()+":   ");
			for(String moves:entry.getValue())
			{
				System.out.print(moves+"     ");
			}
		}
		
	}
	
//	public String getInput()
//	{
//		try
//		(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));)
//		{
//			System.out.println("Enter your move:");
////			System.out.println(br.readLine());
//			String tmpString = br.readLine();
//			br.close();
//			return tmpString;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			return "Exit";
//		}
//	}
	
	//To undo the changes made,in case the move made is invalid
	public void rollback(String moveType,String sourceLocation,String destinationLocation,ChessGamePiece pieceKilled)
	{
		if(moveType.equals("-"))
		{
			gameBoard.put(sourceLocation,gameBoard.get(destinationLocation));
			gameBoard.put(destinationLocation,pieceKilled);	
		}
		else if(moveType.equals("X"))
		{
			gameBoard.put(sourceLocation,gameBoard.get(destinationLocation));
			gameBoard.put(destinationLocation,pieceKilled);
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
		String kingLocation;
//		String pieceType;
		String sourceLocation;
		String moveType;
		String destinationLocation;
		ChessGamePiece pieceToBeMoved;
		ChessGamePiece pieceKilled;
		
		if(moveDescription==null)
		{
			System.out.println("Invalid input: Move cannot be NULL");
			return false;
		}
		else if(moveDescription.length()==6 
				|| (moveDescription.length()==7 && (moveDescription.substring(6).equals("+")))
				|| (moveDescription.length()==8 && (moveDescription.substring(6,7).equals("++"))))
		{
	//		pieceType = ""+moveDescription.charAt(0);
			sourceLocation = moveDescription.substring(1,3);
			moveType = moveDescription.substring(3,4);
			destinationLocation = moveDescription.substring(4, 6);
			
			
			pieceToBeMoved = gameBoard.get(sourceLocation);
			
			//Check if the move requested is possible or not
			if(pieceToBeMoved == null)
			{
				System.out.println("Invalid Move: No piece available in "+sourceLocation+"");
				return false;
			}
			
			if(pieceToBeMoved.colour==colour&&simulatedPossibleMoves.get(pieceToBeMoved).contains(destinationLocation))
			{
				pieceKilled=gameBoard.put(destinationLocation, gameBoard.get(sourceLocation));
				gameBoard.put(sourceLocation,null);
				
				if(moveType.equals("X") && pieceKilled!=null)
				{
					killedPieces.add(pieceKilled);
				}
				else if(moveType.equals("X") && pieceKilled==null)
				{
					System.out.println("Invalid move, No opponent peiece to kill in destination position, rolling back the move");
					rollback(moveType,sourceLocation,destinationLocation,pieceKilled);
					return false;
				}
				else if(moveType.equals("-") && pieceKilled!=null)
				{
					System.out.println("Invalid move, opponent piece will be killed with this move, use 'X' instead of'-' to indicate kill ");
					rollback(moveType,sourceLocation,destinationLocation,pieceKilled);
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
			else if(gameBoard.get(destinationLocation) instanceof Rook)
			{
				Rook rook=(Rook)gameBoard.get(destinationLocation);
				rook.setInInitialPosition(false);				
			}
			else if(gameBoard.get(destinationLocation) instanceof King)
			{
				King kingTemp=(King)gameBoard.get(destinationLocation);
				kingTemp.setInInitialPosition(false);				
			}
			
			simulatePossibleMoves();
			
			if(inCheckMode)
			{
				kingLocation=getKingLocation(colour);
				if(getUnSafeCells(colour).contains(kingLocation))
					{
						inCheckMode=true;
						System.out.println("You are in check mode, the recent move doesn't save the King from Check");
						rollback(moveType,sourceLocation,destinationLocation,pieceKilled);
						return false;
					}
				else
					{
						inCheckMode=false;
						return true;
					}
			}
			else if(moveDescription.length()>6)
			{
				for(Colour tempColour:Colour.values())
				{
					if(tempColour!=colour)
					{
						kingLocation=getKingLocation(tempColour);
						if(getUnSafeCells(tempColour).contains(kingLocation))
							{
								inCheckMode=true;
								
								return true;
							}
						else
							{
								System.out.println("Not a valid move for Check: Move made doesn't put the opposite king in Risk");
								inCheckMode=false;
								return false;
							}						
					}
				}
				
				System.out.println("Failed to find King location");
				return false;				
			}
		}	
		else if(moveDescription.equals("O-O-O"))
		{
			King king;
			Rook rook;
			String rookLocation;
			
			if(colour==Colour.WHITE) 
			{
				rookLocation="a1";
				kingLocation="e1";				
			}
			else
			{
				rookLocation="a8";
				kingLocation="e8";
			}
			
			try
			{
				king = (King)gameBoard.get(kingLocation);
				rook = (Rook)gameBoard.get(rookLocation);
			}
			catch(ClassCastException e)
			{
				System.out.println(" King/Rook is not available at their respective location "+kingLocation+" and "+rookLocation+"; Castling cannot be done");
				return false;
			}
			catch(NullPointerException e)
			{
				System.out.println(" No object is available at locations a"+kingLocation+"/"+rookLocation+" Castling cannot be done");
				return false;				
			}
			
			if(king.isInInitialPosition()==false || rook.isInInitialPosition()==false)
			{
				System.out.println("King and Rook should not be moved from their initial position to allow castling ");
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
			Rook rook;
			String rookLocation;
			
			if(colour==Colour.WHITE) 
			{
				kingLocation="e1";		
				rookLocation="h1";
			}
			else
			{
				kingLocation="e8";
				rookLocation="h8";
			}
			
			try
			{
				king = (King)gameBoard.get(kingLocation);
				rook = (Rook)gameBoard.get(rookLocation);
			}
			catch(ClassCastException e)
			{
				System.out.println(" King/Rook is not available at their respective location "+kingLocation+" and "+rookLocation+"; Castling cannot be done");
				return false;
			}
			catch(NullPointerException e)
			{
				System.out.println(" No object is available at locations a"+kingLocation+"/"+rookLocation+" Castling cannot be done");
				return false;				
			}
			
			if(king.isInInitialPosition()==false || rook.isInInitialPosition()==false)
			{
				System.out.println("King and Rook should not be moved from their initial position to allow castling ");
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
		
		//SimulatePossibleMoves
		simulatePossibleMoves();
		return true;
	}
}
