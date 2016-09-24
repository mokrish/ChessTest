package com.mo.gamechess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Bishop extends ChessGamePiece 
{

	
//	public Bishop(String name,String currentPosition,Colour colour)
//	{
//		this.name=name;
//		this.currentPosition=currentPosition;
//		this.colour=colour;
//	}	

	public Bishop(String name,Colour colour)
	{
		this.name=name;
		this.colour=colour;
	}
	
	@Override
	public List<String> getPossibleMoves(Map<String, ChessGamePiece> gameBoard, String currentPosition) 
	{
		String str;
		List<String> possiblePositions = new ArrayList<>();
		IndexPositions position = ChessGamePiece.getIndexPositions(currentPosition);
		
		try
		{
			for(int i=position.getI()-1,j=position.getJ()-1;i>=0&&j>=0;i--,j--)
			{
				str=Game.validPositions[i][j];
				if(gameBoard.get(str)==null)
				{				
					possiblePositions.add(str);
				}
				else if((gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);									
					i=-1;
					j=-1;
				}
				else
				{					
					i=-1;
					j=-1;
				}
			}
			
			for(int i=position.getI()-1,j=position.getJ()+1;i>=0&&j<=7;i--,j++)
			{
				str=Game.validPositions[i][j];
				if(gameBoard.get(str)==null)
				{				
					possiblePositions.add(str);
				}
				else if((gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);									
					i=-1;
					j=8;
				}
				else
				{					
					i=-1;
					j=8;
				}
			}	
			
			for(int i=position.getI()+1,j=position.getJ()-1;i<=7&&j>=0;i++,j--)
			{
				str=Game.validPositions[i][j];
				if(gameBoard.get(str)==null)
				{				
					possiblePositions.add(str);
				}
				else if((gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);									
					i=8;
					j=-1;
				}
				else
				{					
					i=8;
					j=-1;
				}
			}			
			
			for(int i=position.getI()+1,j=position.getJ()+1;i<=7&&j<=7;i++,j++)
			{
				str=Game.validPositions[i][j];
				if(gameBoard.get(str)==null)
				{				
					possiblePositions.add(str);
				}
				else if((gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);									
					i=8;
					j=8;
				}
				else
				{					
					i=8;
					j=8;
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
	public boolean move(String moveDescription) {
		// TODO Auto-generated method stub
		return false;
	}

}
