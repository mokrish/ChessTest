package com.mo.gamechess;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Knight extends ChessGamePiece 
{

	public Knight(String name,Colour colour)
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
		System.out.println("i:"+position.getI()+", j:"+position.getJ());
		try
		{	
			
			if(position.getI()-1>=0 && position.getJ()-2>=0)
			{
				str=Game.validPositions[position.getI()-1][position.getJ()-2];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()-1>=0 && position.getJ()+2<8)
			{
				str=Game.validPositions[position.getI()-1][position.getJ()+2];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()+1<8 && position.getJ()-2>=0)
			{
				str=Game.validPositions[position.getI()+1][position.getJ()-2];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()+1<80 && position.getJ()+2<8)
			{
				str=Game.validPositions[position.getI()+1][position.getJ()+2];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}			
			
			
			
			if(position.getI()-2>=0 && position.getJ()-1>=0)
			{
				str=Game.validPositions[position.getI()-2][position.getJ()-1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()-2>=0 && position.getJ()+1<8)
			{
				str=Game.validPositions[position.getI()-2][position.getJ()+1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()+2<8 && position.getJ()-1>=0)
			{
				str=Game.validPositions[position.getI()+2][position.getJ()-1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			
			if(position.getI()+2<8 && position.getJ()+1<8)
			{
				str=Game.validPositions[position.getI()+2][position.getJ()+1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
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
