package com.mo.gamechess;

import java.util.HashMap;
import java.util.Map;

public class GameTester 
{
	public static void main(String args[])
	{
//		King king = new King("K","d2",Colour.WHITE);
//		Queen queen = new Queen("Q1","d6",Colour.WHITE);
//		Queen queen2 = new Queen("Q2","b6",Colour.BLACK);
//		Knight knight= new Knight("N","f1",Colour.BLACK);
		Pawn pawn1 = new Pawn("p1",Colour.WHITE,true);
		Pawn pawn2 = new Pawn("p2",Colour.BLACK,true);
		Pawn pawn3 = new Pawn("p3",Colour.BLACK,false);
		Pawn pawn4 = new Pawn("p4",Colour.WHITE,false);
		Map<String,ChessGamePiece> map = new HashMap<>();
		map.put("a2", pawn1);
		map.put("b7", pawn2);
		map.put("b3", pawn3);
		map.put("c6", pawn4);
//		map.put("e6", new King("K1","e6",Colour.WHITE));
//		map.put("d2", king);
//		map.put("d6", queen);
//		map.put("b6", queen2);
//		map.put("f1", knight);
//		System.out.println(king.identifyPossibleMoves(map));
		System.out.println(pawn1.getPossibleMoves(map, null));
		System.out.println(pawn2.getPossibleMoves(map, null));
		System.out.println(pawn3.getPossibleMoves(map, null));
		System.out.println(pawn4.getPossibleMoves(map, null));
	}
}
