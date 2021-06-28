package main.chess;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import main.Home.pgnreader;

public class GUI extends JFrame {
    private pgnreader pgn = null;
    private int gameIndex;
    private int moveIndex;

    public GUI() {
        this.setTitle("PGN Viewer");
        setLayout(null);

        add(p1());
        add(p2(""));
        add(p3("Import PGN to continue..."));
        add(p4());
        add(p5());

        initializeGui();
    }

    public JPanel p1() {
        JPanel p1 = new JPanel(new GridLayout(1,1));
        p1.setBounds(0, 0, 635, 635);
        p1.setBackground(Color.black);
        p1.add(getGui());
        return p1;
    }

    private JTextArea tp;

    public JPanel p2(String textField){
        JPanel p2 = new JPanel(new GridLayout(1,1));
        p2.setBounds(634, 0, 276, 500);
        tp = new JTextArea();
        tp.setText(textField);
        tp.setLineWrap(true);
        tp.setEditable(false);
        tp.setBorder(new LineBorder(Color.BLACK));
        tp.setPreferredSize(new Dimension(300,400));
        p2.add(tp);
        return p2;
    }

    private JTextArea td;

    public JPanel p3(String textDeTails){
        JPanel p3 = new JPanel(new GridLayout(1,1));
        p3.setBounds(909, 0, 275, 500);
        td = new JTextArea();
        td.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        td.setText(textDeTails);
        td.setLineWrap(true);
        td.setEditable(false);
        td.setBorder(new LineBorder(Color.BLACK));
        td.setPreferredSize(new Dimension(300,400));
        p3.add(td);
        return p3;
    }

    public JPanel p4() {
        JPanel p4 = new JPanel(new GridLayout(1,1));
        p4.setBounds(1034,500,150,135);
        JButton pgnButton = new JButton("import PGN");
        pgnButton.setBackground(Color.black);
        pgnButton.setForeground(Color.WHITE);
        pgnButton.setBorderPainted(false);

        pgnButton.setMargin(new Insets(0, 0, 0, 0));
        pgnButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("PGN Annotated File (.pgn)", "pgn"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getPath();
                try {
                    pgn = new pgnreader(filePath);
                    gameIndex = 0;
                    moveIndex = 0;
                    configuration();
                    tp.setText(pgn.getGames().get(gameIndex).getBoard().moveString);
                    td.setText(pgn.getGames().get(gameIndex).makeDetails());
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        p4.add(pgnButton);
        return p4;
    }

    public JPanel p5() {
        JPanel p5 = new JPanel(new GridLayout(3,2));
        p5.setBounds(635, 500, 400, 135);

        JButton btn1 = new JButton("previous move");
        btn1.setBackground(Color.darkGray);
        btn1.setForeground(Color.WHITE);
        btn1.setBorder(new LineBorder(Color.BLACK));

        btn1.addActionListener(e -> {
            if(pgn != null && moveIndex >= 0){
                moveIndex--;
                configuration();
            }
        });


        JButton btn2 = new JButton("next move");
        btn2.setBackground(Color.darkGray);
        btn2.setForeground(Color.WHITE);
        btn2.setBorder(new LineBorder(Color.BLACK));

        btn2.addActionListener(e -> {
            if(pgn != null && moveIndex < pgn.getGames().get(gameIndex).getBoard().numberofmoves){
                moveIndex++;
                configuration();
            }
        });


        JButton btn3 = new JButton("previous game");
        btn3.setBackground(Color.darkGray);
        btn3.setForeground(Color.WHITE);
        btn3.setBorder(new LineBorder(Color.BLACK));

        btn3.addActionListener(e -> {
            if(pgn != null && gameIndex >= 0){
                moveIndex = 0;
                gameIndex--;
                configuration();
                tp.setText(pgn.getGames().get(gameIndex).getBoard().moveString);
                td.setText(pgn.getGames().get(gameIndex).makeDetails());
            }
        });


        JButton btn4 = new JButton("next game");
        btn4.setBackground(Color.darkGray);
        btn4.setForeground(Color.WHITE);
        btn4.setBorder(new LineBorder(Color.BLACK));

        btn4.addActionListener(e -> {
            if(pgn != null && gameIndex < pgn.getGames().size()){
                gameIndex++;
                moveIndex = 0;
                configuration();
                tp.setText(pgn.getGames().get(gameIndex).getBoard().moveString);
                td.setText(pgn.getGames().get(gameIndex).makeDetails());
            }
        });


        JButton btn5 = new JButton("first move");
        btn5.setBackground(Color.darkGray);
        btn5.setForeground(Color.WHITE);
        btn5.setBorder(new LineBorder(Color.BLACK));

        btn5.addActionListener(e -> {
            if(pgn != null && moveIndex >= 0){
                moveIndex = 0;
                configuration();
            }
        });


        JButton btn6 = new JButton("last move");
        btn6.setBackground(Color.darkGray);
        btn6.setForeground(Color.WHITE);
        btn6.setBorder(new LineBorder(Color.BLACK));

        btn6.addActionListener(e -> {
            if(pgn != null && moveIndex >= 0){
                moveIndex = pgn.getGames().get(gameIndex).getBoard().numberofmoves;
                configuration();
            }
        });

        p5.add(btn1);
        p5.add(btn2);
        p5.add(btn3);
        p5.add(btn4);
        p5.add(btn5);
        p5.add(btn6);

        return p5;
    }

    public JPanel gui = new JPanel(new BorderLayout(0, 0));
    public JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    private final String cols = "ABCDEFGH";

    public void initializeGui() {
        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        // create the main.chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int i = 0; i < chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton btn = new JButton();
                btn.setBorderPainted(false);
                btn.setModel(new FixedStateButtonModel());
                btn.setMargin(buttonMargin);

                ImageIcon icn = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                btn.setIcon(icn);
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    btn.setBackground(Color.WHITE);
                } else {
                    btn.setBackground(Color.BLACK);
                }
                chessBoardSquares[i][j] = btn;
            }
        }


        // fill the main.chess board
        chessBoard.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            chessBoard.add(
                    new JLabel(cols.substring(i, i + 1),
                            SwingConstants.CENTER));
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (j) {
                    case 0:
                        chessBoard.add(new JLabel("" + (i + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[i][j]);
                }
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public void configuration() {
        char[][] chars = pgn.getGames().get(gameIndex).getBoard().boards.get(moveIndex);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                switch (chars[i][j]) {
                    case 'R':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wr.png"));
                        break;
                    case 'r':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\br.png"));
                        break;
                    case 'N':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wn.png"));
                        break;
                    case 'n':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\bn.png"));
                        break;
                    case 'B':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wb.png"));
                        break;
                    case 'b':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\bb.png"));
                        break;
                    case 'Q':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wq.png"));
                        break;
                    case 'q':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\bq.png"));
                        break;
                    case 'K':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wk.png"));
                        break;
                    case 'k':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\bk.png"));
                        break;
                    case 'P':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\wp.png"));
                        break;
                    case 'p':
                        chessBoardSquares[i][j].setIcon(new ImageIcon("src\\main\\Images\\bp.png"));
                        break;
                    default:
                        chessBoardSquares[i][j].setIcon(null);

                }
            }
        }
    }

    public class FixedStateButtonModel extends DefaultButtonModel {
        @Override
        public boolean isPressed() {
            return false;
        }

        @Override
        public boolean isRollover() {
            return false;
        }

        @Override
        public void setRollover(boolean b) {
            //LOL
        }
    }

}
