package main.home;

import java.util.HashMap;
public enum Piece {
    WHITE_KING,
    WHITE_QUEEN,
    WHITE_ROOK,
    WHITE_BISHOP,
    WHITE_KNIGHT,
    WHITE_PAWN,
    BLACK_KING,
    BLACK_QUEEN,
    BLACK_ROOK,
    BLACK_BISHOP,
    BLACK_KNIGHT,
    BLACK_PAWN,
    NULL;

    public static HashMap<Piece, String> notation = new HashMap<>();
    static {
        notation.put(Piece.WHITE_KING, "K");
        notation.put(Piece.WHITE_QUEEN, "Q");
        notation.put(Piece.WHITE_ROOK, "R");
        notation.put(Piece.WHITE_BISHOP, "B");
        notation.put(Piece.WHITE_KNIGHT, "N");
        notation.put(Piece.WHITE_PAWN, "P");
        notation.put(Piece.BLACK_KING, "k");
        notation.put(Piece.BLACK_QUEEN, "q");
        notation.put(Piece.BLACK_ROOK, "r");
        notation.put(Piece.BLACK_BISHOP, "b");
        notation.put(Piece.BLACK_KNIGHT, "n");
        notation.put(Piece.BLACK_PAWN, "p");
        notation.put(Piece.NULL,"null");

    }
    public Piece getenum(String s){
        for(Piece p:notation.keySet()){
            if(Square.notation.get(p)==s)
                return p;
        }
        return null;
    }
    public static Piece getPiecebystr(String str){
        for(Piece p: notation.keySet())
            if(notation.get(p).equals(str))
                return p;
        throw new IllegalArgumentException("piece");
    }
    public static char toStr(Piece p){
        return notation.get(p).charAt(0);
    }

}
