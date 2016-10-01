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
	public boolean equals(Object arg0) 
	{
		if(arg0==null)
		{
			return false;
		}
		
		if(this==arg0)
		{
			return true;
		}
		
		if(arg0 instanceof ChessGamePiece)
		{
			ChessGamePiece piece = (ChessGamePiece) arg0;
			if(piece.getColour().equals(this.getColour()) && piece.getName().equals(this.getName()))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else 
		{
			return false;
		}
	}

	@Override
	public int hashCode() 
	{
		int result=17;
		result=result*31+this.getName().hashCode();
		result=result*31+this.getColour().hashCode();
		return result;
	}
	
}
