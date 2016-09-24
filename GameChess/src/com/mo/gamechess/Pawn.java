package com.mo.gamechess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Pawn extends ChessGamePiece 
{
	private boolean inInitialPosition;
	
	public Pawn(String name,Colour colour,boolean inInitialPosition)
	{
		this.name=name;		
		this.colour=colour;
		this.inInitialPosition=inInitialPosition;	
	}	
	
	public void setInInitialPosition(boolean inInitiailPosition)
	{
		this.inInitialPosition=inInitiailPosition;
	}
	
	@Override
	public List<String> getPossibleMoves(Map<String, ChessGamePiece> gameBoard, String currentPosition) 
	{
		String str;
		List<String> possiblePositions = new ArrayList<>();
		IndexPositions position = ChessGamePiece.getIndexPositions(currentPosition);
		System.out.println("i:"+position.getI()+", j:"+position.getJ());
		try
		{	
			if(colour==Colour.WHITE)
			{
				
				if(inInitialPosition && position.getJ()+2<8)
				{
					str=Game.validPositions[position.getI()][position.getJ()+2];
					if(gameBoard.get(str)==null)
					{
						possiblePositions.add(str);
					}
				}
				if(position.getJ()+1<8)
				{
					str=Game.validPositions[position.getI()][position.getJ()+1];
					if(gameBoard.get(str)==null)
					{
						possiblePositions.add(str);
					}					
				}
				if(position.getJ()+1<8 && position.getI()+1<8)
				{
					str=Game.validPositions[position.getI()+1][position.getJ()+1];
					if(gameBoard.get(str)!=null && (gameBoard.get(str)).getColour()!=this.getColour())
					{
						possiblePositions.add(str);
					}					
				}
				if(position.getJ()+1<8 && position.getI()-1>=0)
				{
					str=Game.validPositions[position.getI()-1][position.getJ()+1];
					if(gameBoard.get(str)!=null && (gameBoard.get(str)).getColour()!=this.getColour())
					{
						possiblePositions.add(str);
					}					
				}				
			}
			else
			{
				if(inInitialPosition && position.getJ()-2>=0)
				{
					str=Game.validPositions[position.getI()][position.getJ()-2];
					if(gameBoard.get(str)==null)
					{
						possiblePositions.add(str);
					}
				}
				if(position.getJ()-1>=0)
				{
					str=Game.validPositions[position.getI()][position.getJ()-1];
					if(gameBoard.get(str)==null)
					{
						possiblePositions.add(str);
					}					
				}
				if(position.getJ()-1>=0 && position.getI()-1>=0)
				{
					str=Game.validPositions[position.getI()-1][position.getJ()-1];
					if(gameBoard.get(str)!=null && (gameBoard.get(str)).getColour()!=this.getColour())
					{
						possiblePositions.add(str);
					}					
				}
				if(position.getJ()-1>=0 && position.getI()+1<8)
				{
					str=Game.validPositions[position.getI()+1][position.getJ()-1];
					if(gameBoard.get(str)!=null && (gameBoard.get(str)).getColour()!=this.getColour())
					{
						possiblePositions.add(str);
					}					
				}
			}
			 						
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Looking for invalid arrayIndex id's: "+position.getI()+" "+position.getJ());
			e.printStackTrace();			
		}
		
		return possiblePositions;		
	}

	@Override
	public boolean move(String moveDescription) 
	{
		setInInitialPosition(true);
		return true;
	}

}
