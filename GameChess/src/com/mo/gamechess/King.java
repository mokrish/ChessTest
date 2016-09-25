package com.mo.gamechess;

import java.util.List;
import java.util.Map; 
import java.util.ArrayList; 

public class King extends ChessGamePiece 
{

	
	public King(String name,Colour colour)
	{
		this.name=name;
		this.colour=colour;
	}	
		
	@Override
	public List<String> getPossibleMoves(Map<String,ChessGamePiece> gameBoard, String currentPosition) 
	{
		String str;
		List<String> possiblePositions = new ArrayList<>();
		IndexPositions position = ChessGamePiece.getIndexPositions(currentPosition);
		try
		{
			if(position.getI()-1>0)
			{
				str=Game.validPositions[position.getI()-1][position.getJ()];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}
			}
			if(position.getI()+1<8)
			{
				str=Game.validPositions[position.getI()+1][position.getJ()];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}
			if(position.getJ()-1>0)
			{
				str=Game.validPositions[position.getI()][position.getJ()-1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}
			if(position.getJ()+1<8)
			{
				str=Game.validPositions[position.getI()][position.getJ()+1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}		
			if(position.getI()-1>0 && position.getJ()-1>0)
			{
				str=Game.validPositions[position.getI()-1][position.getJ()-1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}
			if(position.getI()-1>0 && position.getJ()+1<8)
			{
				str=Game.validPositions[position.getI()-1][position.getJ()+1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}
			if(position.getI()+1<8 && position.getJ()-1>0)
			{
				str=Game.validPositions[position.getI()+1][position.getJ()-1];
				if(gameBoard.get(str)==null||(gameBoard.get(str)).getColour()!=this.getColour())
				{
					possiblePositions.add(str);
				}				
			}
			if(position.getI()+1<8 && position.getJ()+1<8)
			{
				str=Game.validPositions[position.getI()+1][position.getJ()+1];
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
		
//		possiblePositions.removeAll(Game.getUnSafeCells(colour));
		
		return possiblePositions;
	}

	@Override
	public boolean move(String moveDescription) 
	{
		// TODO Auto-generated method stub
		return true;
	}

	public boolean validateQueenSideCastling(Map<String,ChessGamePiece> gameBoard,Colour colour, List<String> list)
	{ 
		if(colour==Colour.WHITE)
		{
			if(gameBoard.get("b1")==null&&gameBoard.get("c1")==null&&gameBoard.get("d1")==null)
			{
				for(String loc:list)
				{
					if(loc.equals("a1")||loc.equals("b1")||loc.equals("c1")||loc.equals("d1")||loc.equals("e1"))
					{
						System.out.println(" For Queen side castling locations a1,b1,c1,d1 and e1 should not be reachable to oppnonents");
						return false;
					}
				}
			}
			else
			{
				System.out.println(" For Queen side castling locations b1,c1 and d1 should be empty");
				return false;
			}
		}
		else if(colour==Colour.BLACK)
		{
			if(gameBoard.get("b8")==null&&gameBoard.get("c8")==null&&gameBoard.get("d8")==null)
			{
				for(String loc:list)
				{
					if(loc.equals("a8")||loc.equals("b8")||loc.equals("c8")||loc.equals("d8")||loc.equals("e8"))
					{
						System.out.println(" For Queen side castling locations a8,b8,c8,d8 and e8 should not be reachable to oppnonents");
						return false;
					}
				}
			}
			else
			{
				System.out.println(" For Queen side castling locations b8,c8 and d8 should be empty");
				return false;
			}			
		}
		else
		{
			return false;
		}
		return true;
	}
	
	public boolean validateKingSideCastling(Map<String,ChessGamePiece> gameBoard,Colour colour, List<String> list)
	{
		if(colour==Colour.WHITE)
		{
			if(gameBoard.get("f1")==null&&gameBoard.get("g1")==null)
			{
				for(String loc:list)
				{
					if(loc.equals("e1")||loc.equals("f1")||loc.equals("g1")||loc.equals("h1"))
					{
						System.out.println(" For King side castling locations e1,f1,g1 and h1 should not be reachable to oppnonents");
						return false;
					}
				}
			}
			else
			{
				System.out.println(" For King side castling locations f1 and g1 should be empty");
				return false;
			}
		}		
		else if(colour==Colour.BLACK)
		{
			if(gameBoard.get("f8")==null&&gameBoard.get("g8")==null)
			{
				for(String loc:list)
				{
					if(loc.equals("e8")||loc.equals("f8")||loc.equals("g8")||loc.equals("h8"))
					{
						System.out.println(" For King side castling locations e8,f8,g8 and h8 should not be reachable to oppnonents");
						return false;
					}
				}
			}
			else
			{
				System.out.println(" For King side castling locations f8 and g8 should be empty");
				return false;
			}
		}
		else
		{
			return false;
		}
		return true;
	}	
	
}
