package main.Home;

import java.util.HashMap;

public enum Square {
    A1,A2,A3,A4,A5,A6,A7,A8,B1,B2,B3,B4,B5,B6,B7,B8,C1,C2,C3,C4,C5,C6,C7,C8,D1,D2,D3,D4,D5,D6,D7,D8,E1,E2,E3,E4,E5,E6,E7,E8,F1,F2,F3,F4,F5,F6,F7,F8,G1,G2,G3,G4,G5,G6,G7,G8,H1,H2,H3,H4,H5,H6,H7,H8,NULL;
    public static HashMap<Square, String> notation = new HashMap<>();
    static {
        notation.put(Square.NULL,"NULL");
        notation.put(Square.A1,"A1");
        notation.put(Square.A2,"A2");
        notation.put(Square.A3,"A3");
        notation.put(Square.A4,"A4");
        notation.put(Square.A5,"A5");
        notation.put(Square.A6,"A6");
        notation.put(Square.A7,"A7");
        notation.put(Square.A8,"A8");
        notation.put(Square.B1,"B1");
        notation.put(Square.B2,"B2");
        notation.put(Square.B3,"B3");
        notation.put(Square.B4,"B4");
        notation.put(Square.B5,"B5");
        notation.put(Square.B6,"B6");
        notation.put(Square.B7,"B7");
        notation.put(Square.B8,"B8");
        notation.put(Square.C1,"C1");
        notation.put(Square.C2,"C2");
        notation.put(Square.C3,"C3");
        notation.put(Square.C4,"C4");
        notation.put(Square.C5,"C5");
        notation.put(Square.C6,"C6");
        notation.put(Square.C7,"C7");
        notation.put(Square.C8,"C8");
        notation.put(Square.D1,"D1");
        notation.put(Square.D2,"D2");
        notation.put(Square.D3,"D3");
        notation.put(Square.D4,"D4");
        notation.put(Square.D5,"D5");
        notation.put(Square.D6,"D6");
        notation.put(Square.D7,"D7");
        notation.put(Square.D8,"D8");
        notation.put(Square.E1,"E1");
        notation.put(Square.E2,"E2");
        notation.put(Square.E3,"E3");
        notation.put(Square.E4,"E4");
        notation.put(Square.E5,"E5");
        notation.put(Square.E6,"E6");
        notation.put(Square.E7,"E7");
        notation.put(Square.E8,"E8");
        notation.put(Square.F1,"F1");
        notation.put(Square.F2,"F2");
        notation.put(Square.F3,"F3");
        notation.put(Square.F4,"F4");
        notation.put(Square.F5,"F5");
        notation.put(Square.F6,"F6");
        notation.put(Square.F7,"F7");
        notation.put(Square.F8,"F8");
        notation.put(Square.G1,"G1");
        notation.put(Square.G2,"G2");
        notation.put(Square.G3,"G3");
        notation.put(Square.G4,"G4");
        notation.put(Square.G5,"G5");
        notation.put(Square.G6,"G6");
        notation.put(Square.G7,"G7");
        notation.put(Square.G8,"G8");
        notation.put(Square.H1,"H1");
        notation.put(Square.H2,"H2");
        notation.put(Square.H3,"H3");
        notation.put(Square.H4,"H4");
        notation.put(Square.H5,"H5");
        notation.put(Square.H6,"H6");
        notation.put(Square.H7,"H7");
        notation.put(Square.H8,"H8");
    }
}

