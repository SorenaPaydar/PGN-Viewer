package main.Home;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    ArrayList<Move> moves = null;
    private char[][] finalPositions = new char[8][8];
    private ArrayList<Piece> capturedPieces = new ArrayList<>();
    private HashMap<Square, Piece> grid = new HashMap<>();
    public ArrayList<char[][]> boards = new ArrayList<>();
    public int numberofmoves;
    public int movenum = 1;
    public String moveString;




    public Board(){
        this.moves = new ArrayList<>();
        this.capturedPieces = new ArrayList<>();
        rearrangeBoard();
    }



    public Board(ArrayList<String> moves, String moveString){
        this.moveString = moveString;
        rearrangeBoard();
        numberofmoves = moves.size();
        for(int i = 0;i< moves.size();i++) {
            try {
                String move = moves.get(i);
                boolean iswhite = false;
                if (i % 2 == 0)
                    iswhite = true;
                if (move.contains("O-O"))
                    boards.add(castle(iswhite, move, boards.get(boards.size() - 1)));
                else boards.add(analyseMove(move,iswhite));
                finalPositions = boards.get(boards.size() - 1);
            }
            catch(Exception e){
                boards.clear();
                char[][] bugboard = new char[8][8];
                bugboard[0][0]='z';
                boards.add(bugboard);
                break;
            }

        }
    }
    public static void print(char[][] f){
        String l="";
        for(int i=7;i>=0;i--){
            for (int j=0;j<8;j++){
                l+=f[i][j];
            }
            System.out.println(l);
            l="";
        }
    }

    public void printresult(int index){
        String boardstr="";
        char[][] board = boards.get(index);
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++)
                boardstr += String.valueOf(board[i][j]);
            System.out.println(boardstr);
            boardstr="";
        }
    }


//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************

    private char[][] analyseMove(String movestr,boolean iswhite){
        boolean isattacking=false;
        boolean haspromotion=false;
        if(movestr.contains("#"))
            movestr = movestr.replaceFirst(String.valueOf(movestr.charAt(movestr.indexOf("#"))), "");
        if(movestr.contains("+"))
            movestr = movestr.substring(0,movestr.length()-1);
//      checking if the move is a promotion******************************************************************
        Piece promotion = Piece.NULL;
        if(movestr.contains("=")){
            int indexofeq = movestr.indexOf('=');
            haspromotion=true;
            promotion = Piece.BLACK_BISHOP.getPiecebystr(String.valueOf(movestr.charAt(indexofeq + 1)));
            movestr=movestr.substring(0,indexofeq);
        }


        if(movestr.contains("x")) {
            isattacking = true;
            movestr = movestr.replaceFirst(String.valueOf(movestr.charAt(movestr.indexOf("x"))), "");
        }

        int indexofnumber=2;
        for(int i=0;i<movestr.length();i++){
            if(Character.isDigit(movestr.charAt(i))) {
                indexofnumber = i;
                break;
            }
        }

//      finding to square************************************************************************************
        Square to = Square.valueOf((movestr.substring(indexofnumber-1,indexofnumber+1)).toUpperCase());
        //      finding moving piece*********************************************************************************
        Piece movingPiece = Piece.NULL;
        for(int i=0;i<indexofnumber;i++){
            if(Character.isUpperCase(movestr.charAt(i))){
                String s = String.valueOf(Character.toUpperCase(movestr.charAt(i)));
                if(iswhite){
                    movingPiece=Piece.getPiecebystr(s.toUpperCase());
                }
                else
                    movingPiece=Piece.getPiecebystr(s.toLowerCase());
                break;
            }
        }
        if(movingPiece==Piece.NULL) {
            if (iswhite)
                movingPiece = Piece.WHITE_PAWN;
            else
                movingPiece = Piece.BLACK_PAWN;
        }
        Piece capturedBCp = Piece.NULL;
        if(haspromotion) {
            capturedBCp = movingPiece;
        }
//      finding special string*********************************************************************************
        String special = "";
        if(indexofnumber-2>=0 && Character.isLowerCase(movestr.charAt(indexofnumber-2)))
            special = String.valueOf(movestr.charAt(indexofnumber-2));

        Square from = getfrom(to,movingPiece,special,iswhite,isattacking);
//      looking for captured piece****************************************************************************
        Piece captured = Piece.NULL;
        if(isattacking){
            int[] indexesforcapture = getIndexBySquare(to.toString());
            if(isEnPassent(indexesforcapture,movingPiece))
                return doEnPassent(finalPositions,from,to,movingPiece);

            captured = Piece.getPiecebystr(String.valueOf(finalPositions[indexesforcapture[0]][indexesforcapture[1]]));
        }

        return domovep(createmove(from,to,movingPiece,captured,capturedBCp,promotion),finalPositions);

    }
    private char[][] castle(boolean iswhite, String movestr,char[][] lastboard){
        char[][] newboard = lastboard;
        boolean kingside = true;
        if(movestr.contains("O-O-O"))
            kingside = false;
        if(kingside && iswhite){
            newboard[0][4]=' ';
            newboard[0][5]='R';
            newboard[0][6]='K';
            newboard[0][7]=' ';
        }
        if(!kingside && iswhite){
            newboard[0][4]=' ';
            newboard[0][3]='R';
            newboard[0][2]='K';
            newboard[0][0]=' ';
        }
        if(kingside && !iswhite){
            newboard[7][4]=' ';
            newboard[7][5]='r';
            newboard[7][6]='k';
            newboard[7][7]=' ';
        }
        if(!kingside && !iswhite){
            newboard[7][4]=' ';
            newboard[7][3]='R';
            newboard[7][2]='K';
            newboard[7][0]=' ';
        }

        return newboard;
    }



    private Move createmove(Square from,Square to,Piece movingpiece,Piece captured,Piece capturedBCp,Piece promotion){
        if(capturedBCp==Piece.NULL && promotion==Piece.NULL && captured==Piece.NULL)
            return new Move(from,to,movingpiece);
        if(capturedBCp==Piece.NULL && promotion==Piece.NULL && captured!=Piece.NULL)
            return new Move(from,to,movingpiece,captured);
        if(capturedBCp==Piece.NULL && promotion!=Piece.NULL && captured!=Piece.NULL)
            return new Move(from,to,movingpiece,captured,promotion);
        if(capturedBCp!=Piece.NULL && promotion!=Piece.NULL && captured!=Piece.NULL)
            return new Move(from,to,movingpiece,captured,promotion,capturedBCp);
        throw new IllegalArgumentException("cant make the move");


    }



    private Square getfrom(Square to,Piece movingpiece,String special,boolean iswhite,boolean isattacking){
        switch (movingpiece){
            case WHITE_ROOK:case BLACK_ROOK:
                return rookmove(to,special,iswhite);
            case WHITE_KNIGHT:case BLACK_KNIGHT:
                return knightmove(to,special,iswhite);
            case WHITE_BISHOP:case BLACK_BISHOP:
                return bishopmove(to,special,iswhite);
            case WHITE_KING:case BLACK_KING:
                return kingmove(to,special,iswhite);
            case WHITE_QUEEN:case BLACK_QUEEN:
                return queenmove(to,special,iswhite);
            case WHITE_PAWN:case BLACK_PAWN:
                return pawnmove(to,special,iswhite,isattacking);
            default:
                throw new IllegalArgumentException("can't read this PGN :(");
        }
    }

    private Square rookmove(Square to, String special,boolean iswhite){
        char p = 'r';
        if(iswhite)
            p='R';
        int[] index = getIndexBySquare(to.toString());
        int x=index[0]; int y = index[1];
        if(!(special.equals(""))){
            y=(int)special.charAt(0)-97;
            for(int i=0;i<8;i++){
                if(finalPositions[i][y]==p)
                    return getSquareByIndex(i,y);
            }
        }
        else {
            //ofoghi
            for(int i=y+1;i<8;i++){
                if(finalPositions[x][i]!=' ' && finalPositions[x][i]!=p)
                    break;
                if(finalPositions[x][i]==p)
                    return getSquareByIndex(x,i);
            }
            for(int i=y-1;i>=0;i--){
                if(finalPositions[x][i]!=' ' && finalPositions[x][i]!=p)
                    break;
                if(finalPositions[x][i]==p)
                    return getSquareByIndex(x,i);
            }
            //amodi
            for(int i=x+1;i<8;i++){
                if(i<8 && finalPositions[i][y]!=' ' && finalPositions[i][y]!=p)
                    break;
                if(i<8 && finalPositions[i][y]==p)
                    return getSquareByIndex(i,y);
            }
            for(int i=x-1;i>=0;i--){
                if(i>=0 && finalPositions[i][y]!=' ' && finalPositions[i][y]!=p)
                    break;
                if(i>=0 && finalPositions[i][y]==p)
                    return getSquareByIndex(i,y);
            }

        }
        throw new IllegalArgumentException("rookmove");

    }
    private Square knightmove(Square to, String special,boolean iswhite){
        char p = 'n';
        if(iswhite)
            p='N';
        int[] index = getIndexBySquare(to.toString());
        int x=index[0]; int y = index[1];
        if(!(special.equals(""))){
            y=(int)special.charAt(0)-97;
            for(int i=0;i<8;i++){
                if(finalPositions[i][y]==p)
                    return getSquareByIndex(i,y);
            }
        }
        else {
            //amod>ofogh
            for(int i:new int[]{2,-2})
                for(int j:new int[]{1,-1})
                    if((x+i>=0 && y+j>=0)&&(x+i<8 && y+j<8)&&(finalPositions[x+i][y+j]==p))
                        return getSquareByIndex(i+x,y+j);
            //ofogh>amod
            for(int i:new int[]{1,-1})
                for(int j:new int[]{2,-2})
                    if((x+i>=0 && y+j>=0)&&(x+i<8 && y+j<8)&&(finalPositions[x+i][y+j]==p))
                        return getSquareByIndex(i+x,y+j);

        }throw new IllegalArgumentException("knight move");

    }
    private Square bishopmove(Square to, String special,boolean iswhite){

        char p = 'b';
        if(iswhite)
            p='B';
        int[] index = getIndexBySquare(to.toString());
        int x=index[0]; int y = index[1];
        if(!(special.equals(""))){
            y=(int)special.charAt(0)-97;
            for(int i=0;i<8;i++){
                if(finalPositions[i][y]==p)
                    return getSquareByIndex(i,y);
            }
        }
        else {
            //shib manfi
            for(int i=x-Math.min(x,y),j=y-Math.min(x,y);i<8 && j<8;i++,j++)
                if(finalPositions[i][j]==p)
                    return getSquareByIndex(i,j);
            //shib mosbat
            for(int i=x,j=y;i>=0 && j<8;i--,j++)
                if(finalPositions[i][j]==p)
                    return getSquareByIndex(i,j);
            for(int i=x,j=y;y>=0 && x<8;i++,j--)
                if(finalPositions[i][j]==p)
                    return getSquareByIndex(i,j);

        }throw new IllegalArgumentException("bishop move");
    }
    private Square kingmove(Square to, String special,boolean iswhite){
        char p = 'k';
        if(iswhite)
            p='K';
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(finalPositions[i][j]==p)
                    return getSquareByIndex(i,j);
        throw new IllegalArgumentException("king move");
    }
    private Square queenmove(Square to, String special,boolean iswhite){
        char p = 'q';
        if(iswhite)
            p='Q';
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(finalPositions[i][j]==p)
                    return getSquareByIndex(i,j);
        throw new IllegalArgumentException("king move");
    }
    private Square pawnmove(Square to, String special,boolean iswhite,boolean isattacking){

        char p = 'p';
        int adding=+1;
        if(iswhite) {
            p = 'P';
            adding=-1;
        }
        int[] index = getIndexBySquare(to.toString());
        int x=index[0];int y = index[1];
        if(!(special.equals(""))){
            y=(int)special.charAt(0)-97;
            return getSquareByIndex(x+adding,y);
        }
        else {
            //attacking move
            if(isattacking){
                for(int i:new int[]{-1,+1})
                    if(finalPositions[x+adding][y+i]==p)
                        return getSquareByIndex(x+adding,y+i);
            }
            else {
                if(finalPositions[x+adding][y]==p)
                    return getSquareByIndex(x+adding,y);
                else return getSquareByIndex(x+((int)adding*2),y);
            }
        }
        throw new IllegalArgumentException("pawnmove!");

    }
    private boolean isEnPassent(int[] indexes,Piece moving){
        if(moving==Piece.BLACK_PAWN || moving==Piece.WHITE_PAWN)
            if(finalPositions[indexes[0]][indexes[1]]==' ')
                return true;
        return false;
    }
    private char[][] doEnPassent(char[][] fboard,Square from,Square to,Piece moving){
        char[][] newboard = fboard;
        char c = 'p';
        if(moving==Piece.WHITE_PAWN)
            c='P';
        int[] indexoffrom = getIndexBySquare(from.toString());
        int[] indexofto = getIndexBySquare(to.toString());
        newboard[indexoffrom[0]][indexoffrom[1]]=' ';
        newboard[indexofto[0]][indexofto[1]]=c;
        capturedPieces.add(Piece.getPiecebystr(""+fboard[indexoffrom[0]][indexofto[1]]));
        newboard[indexoffrom[0]][indexofto[1]]=' ';
        return newboard;

    }
    private int[] getIndexBySquare(String sq){

        int[] index = new int[2];
        switch (sq.charAt(0)){
            case 'A':
                index[1]=0;break;
            case 'B':
                index[1]=1;break;
            case 'C':
                index[1]=2;break;
            case 'D':
                index[1]=3;break;
            case 'E':
                index[1]=4;break;
            case 'F':
                index[1]=5;break;
            case 'G':
                index[1]=6;break;
            case 'H':
                index[1]=7;break;
            default:
                break;
        }
        index[0] = Character.getNumericValue(sq.charAt(1))-1;
        return index;
    }
    private Square getSquareByIndex(int i,int j){
        char c = (char) (j+97);
        String val = String.valueOf(Character.toUpperCase(c));
        val+=(i+1);
        return Square.valueOf(val);
    }
    //    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************
//    ******************************************************************************************************************
    public char[][] copyboard(char[][] lb){
        char[][] b = new char[8][8];
        for(int i=0;i<8;i++)
            for (int j=0;j<8;j++)
                b[i][j]=lb[i][j];
        return b;
    }
    public char[][] domovep(Move move,char[][] lastBoard){
        char[][] newboard = copyboard(lastBoard);
        if(move.getCaptured()!=Piece.NULL){
            capturedPieces.add(move.getCaptured());
        }

        int[] indexoffrom = getIndexBySquare(move.getFrom().toString());
        int[] indexofto = getIndexBySquare(move.getTo().toString());

        Piece moving = move.getMovingPiece();
        newboard[indexoffrom[0]][indexoffrom[1]] = ' ';
        newboard[indexofto[0]][indexofto[1]] = Piece.toStr(moving);

        if(move.getPromotion()!=Piece.NULL)
            newboard[indexofto[0]][indexofto[1]] = Piece.toStr(move.getPromotion());
        if(move.getCapturedBCpromotion()!=Piece.NULL)
            capturedPieces.add(move.getCapturedBCpromotion());
//        print(newboard);
//        System.out.println("==================================================="+movenum);
//        movenum++;

        return newboard;
    }
    private void rearrangeBoard() {

        finalPositions = new char[][]{
                {'R','N','B','Q','K','B','N','R'}
                ,{'P','P','P','P','P','P','P','P'}
                ,{' ',' ',' ',' ',' ',' ',' ',' '}
                ,{' ',' ',' ',' ',' ',' ',' ',' '}
                ,{' ',' ',' ',' ',' ',' ',' ',' '}
                ,{' ',' ',' ',' ',' ',' ',' ',' '}
                ,{'p','p','p','p','p','p','p','p'}
                ,{'r','n','b','q','k','b','n','r'}
        };
        boards.clear();
        boards.add(finalPositions);
    }
}
