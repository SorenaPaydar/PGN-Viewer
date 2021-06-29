package main.home;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private Map<String,String> GameDetails = new HashMap<>();
    private Board board;
    public Game(String whitePlayer, String blackPlayer, Board board, String opening, String result){
        this.board = board;
        GameDetails.put("White",whitePlayer);
        GameDetails.put("Black",blackPlayer);
        GameDetails.put("opening",opening);
        GameDetails.put("Result",result);
    }
    public String makeDetails(){
        String res="";
        for(String k:this.GameDetails.keySet()){
            res+=k+": "+this.GameDetails.get(k)+"\n";
        }
        return res;
    }

    public Game(){
        this.board = new Board();
    }
    public void setBoard(Board board){ this.board = board;}

    //get

    public Board getBoard() { return board; }
    public String getWhitePlayer(){ return GameDetails.getOrDefault("White","not available"); }
    public String getBlackPlayer(){
        return GameDetails.getOrDefault("Black","not available");
    }
    public String getResult(){
        return GameDetails.getOrDefault("Result","not available");
    }
    public Map<String,String> getGameDetails(){ return GameDetails; }


}
