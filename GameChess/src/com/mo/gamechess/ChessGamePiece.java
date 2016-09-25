package com.mo.gamechess;

import java.util.*;

public abstract class ChessGamePiece 
{
	protected String name;	
	protected Colour colour;
		
	public ChessGamePiece()
	{
		
	}
	
	public String getName() 
	{
		return name;
	}
 
	public Colour getColour() {
		return colour;
	}

	public abstract List<String> getPossibleMoves(Map<String,ChessGamePiece> gameBoard, String currentPosition);
	public abstract boolean move(String moveDescription);
	
	public boolean move2(String moveDescription)
	{
		return true;
	}
	
	public static IndexPositions getIndexPositions(String str)
	{
		int i=(int)(str.charAt(0))-97;
		int j=(int)(str.charAt(1))-49;
		
		return new IndexPositions(i,j);
	}

	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		return super.equals(arg0);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	
}
