package main.home;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//string gameo bezan to games*****************************************************************************************

public class pgnreader {
    private String address;
    private ArrayList<Game> fgames = null;
    private ArrayList<Game> games = new ArrayList<>();
    private ArrayList<String> gameNames=null;

    public pgnreader(String address) throws FileNotFoundException {
        this.address=address;
        fgames=makeGamesFromPGN(address);
        for(Game g:fgames){
            if(g.getBoard().boards.get(0)[0][0]!='z')
                games.add(g);
        }
        gameNames=makeGameNames(games);
    }
    public pgnreader(){
        
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<String> getGameNames() {
        return gameNames;
    }

    private ArrayList<String> makeGameNames(ArrayList<Game> games){
        ArrayList<String> gamenames = new ArrayList<>();
        for(Game g:games){
            String name="";
            name+=(g.getWhitePlayer()+" v "+g.getBlackPlayer());
            gamenames.add(name);
        }
        return gamenames;
    }
    public ArrayList<ArrayList<String>> getGamesFromPGN(String address) throws FileNotFoundException {
        BufferedReader br = null;
        String line;
        br = new BufferedReader(new FileReader(address));
        ArrayList<ArrayList<String>> gamesString = new ArrayList<>();
        int emptylines=0;
        String thismoves="";
        ArrayList<String> thisgame = new ArrayList<>();
        try{
            while((line = br.readLine()) != null){
                if(line.trim().equals(""))
                    emptylines++;
                else if(line.charAt(0)=='[')
                    thisgame.add(line);
                else
                    thismoves+=line;
                if(emptylines==2){
                    thisgame.add(thismoves);
                    gamesString.add(new ArrayList<>(thisgame));
                    thisgame.clear();
                    thismoves="";
                    emptylines-=2;
                }
            }thisgame.add(thismoves);
        }
        catch(IOException e){

        }

        return gamesString;
    }




    public ArrayList<Game> makeGamesFromPGN(String address) throws FileNotFoundException {
        ArrayList<ArrayList<String>> gamesList = getGamesFromPGN(address);
        ArrayList<Game> gamesObjs = new ArrayList<>();
        for(int i=0;i<gamesList.size();i++){
            Game gameobj = new Game();
            for(int j=0;j<gamesList.get(i).size()-1;j++){
                String info = gamesList.get(i).get(j);
                gameobj.getGameDetails().put(digkey(info),digvalue(info));
            }
            ArrayList<String> moves = digmoves(gamesList.get(i).get(gamesList.get(i).size()-1));
            Board board = new Board(moves,gamesList.get(i).get(gamesList.get(i).size()-1));
            gameobj.setBoard(board);
            gamesObjs.add(gameobj);


        }


        this.fgames=gamesObjs;
        return gamesObjs;
    }
    public static String digkey(String str){
        return str.substring(1,str.indexOf(' '));
    }
    public static String digvalue(String str){
        return str.substring(str.indexOf('"')+1,str.length()-2);
    }
    private static ArrayList<String> digmoves(String str){
        ArrayList<String> moves = new ArrayList<>();
        String notationRegex = "((O-O-O)|(O-O)|[A-Z]?+[a-z]+[0-9](=[A-Z]?[a-z]?)?\\+?#?)";
        Pattern notationPattern = Pattern.compile(notationRegex);
        Matcher notationMatcher = notationPattern.matcher(str);
        while (notationMatcher.find()) {
            String notation = notationMatcher.group();
            moves.add(notation);
        }
        return moves;
    }
}