package view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import model.Arena;
import model.Robot;

public class GamePlay {
    private char [][] gameGrid; 

    public GamePlay(int x, int y) {
        gameGrid = new char[x][y];
    }
    
    public void setGameGrid(Arena arena){
         for (int i = 0; i < gameGrid.length; i++) {
            for (int j = 0; j < gameGrid[0].length; j++) {
                gameGrid[i][j]=arena.getObjectViewFromPosition(i, j);                  
            }
        }
    }
    
    public void paint(int round){
        
        System.out.println(round+".kör\n");
        for (int j = 0; j < gameGrid[0].length+2; j++) {
            System.out.print("#");
        }
        System.out.println("");
        for (int i = 0; i < gameGrid.length; i++) {
            System.out.print("#");
            for (int j = 0; j < gameGrid[0].length; j++) {
                System.out.print(gameGrid[i][j]); 
            }
            System.out.println("#");
        }
        for (int j = 0; j < gameGrid[0].length+2; j++) {
            System.out.print("#");
        }
        System.out.println("");
    }
    
    public void paint(int round,Terminal terminal,Robot... robots) throws IOException{
        
        terminal.clearScreen();
        final TextGraphics textGraphics = terminal.newTextGraphics();
         textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
            textGraphics.putString(2, 1, round+".kör\n", SGR.BOLD);
            textGraphics.drawLine(3, 4,   gameGrid[0].length+4,4, '#');
            
        for (int i = 0; i < gameGrid.length; i++) {
            terminal.setCursorPosition(3, 5+i);
            terminal.putCharacter('#');
            for (int j = 0; j < gameGrid[0].length; j++) {
                if(Character.toString(gameGrid[i][j]).equals(robots[0].toString()))
                 terminal.setForegroundColor(TextColor.ANSI.RED);
                else if(Character.toString(gameGrid[i][j]).equals(robots[1].toString()))
                 terminal.setForegroundColor(TextColor.ANSI.GREEN);
                else
                    terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
                    terminal.putCharacter(gameGrid[i][j]); 
            }
            terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.putCharacter('#');
        }
        textGraphics.drawLine(3, 5+gameGrid.length,   gameGrid[0].length+4,5+gameGrid.length, '#');
        terminal.setCursorPosition(3, 5+gameGrid.length+3);
    }  
}
