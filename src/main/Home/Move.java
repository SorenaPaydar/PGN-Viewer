package main.Home;

public class Move {
    private Square to;
    private Square from;
    private Piece movingPiece;
    private Piece captured = Piece.NULL;
    private Piece promotion = Piece.NULL;
    private Piece capturedBCpromotion = Piece.NULL;

    //simple moving
    public Move(Square from, Square to, Piece movingPiece){
        this.from = from;
        this.to = to;
        this.movingPiece = movingPiece;
    }
    //square moving and getting promoted
    public Move(Square from, Square to,Piece movingPiece, Piece captured, Piece promotion){
        this.from = from;
        this.to = to;
        this.captured = captured;
        this.promotion = promotion;
        this.movingPiece = movingPiece;
    }
    public Move(Square from, Square to,Piece movingPiece, Piece captured, Piece promotion, Piece capturedBCpromotion){
        this.capturedBCpromotion = capturedBCpromotion;
        this.from = from;
        this.to = to;
        this.captured = captured;
        this.promotion = promotion;
        this.movingPiece = movingPiece;
    }
    //attacking and capturing
    public Move(Square from, Square to, Piece movingPiece, Piece captured){
        this.from = from;
        this.to = to;
        this.movingPiece = movingPiece;
        this.captured = captured;
    }


    public Square getTo() { return to; }

    public Piece getCapturedBCpromotion() { return capturedBCpromotion; }

    public Square getFrom() {
        return from;
    }

    public Piece getMovingPiece() {
        return movingPiece;
    }

    public Piece getCaptured() {
        return captured;
    }

    public Piece getPromotion() {
        return promotion;
    }
}