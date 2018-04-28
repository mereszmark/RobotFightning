package controller;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Arena;
import model.Robot;
import view.GamePlay;

public class Match {

    private final int round;
    private int count;
    private Timer timerToRound;
    private Arena arena;
    private GamePlay play;
    private Robot[] robots;

    public Match(String type,int round, int widthOfArena, int heightOfArena, Robot... robots) {
        this.round = round;
        timerToRound = new Timer();
        if(type.equals("console"))
        timerToRound.scheduleAtFixedRate(new ConsoleTask(), 1000, 1000);
        if(type.equals("terminal"))
        timerToRound.scheduleAtFixedRate(new TerminalTask(), 1000, 1000);
        arena = new Arena(widthOfArena, heightOfArena);
        play = new GamePlay(widthOfArena, heightOfArena);
        arena.initFields();
        this.robots = robots;
        for (Robot robot : robots) {
            arena.initRobots((Robot) robot);
            robot.setArena(arena);
        }
        play.setGameGrid(arena);
    }

    class ConsoleTask extends TimerTask {

        @Override
        public void run() {
            count++;
            robots[0].doAction(robots[1]);
            robots[1].doAction(robots[0]);
            if (robots[0].isMoving() == true
                    && robots[1].isMoving() == true
                    && !robots[0].getNextPosition().equals(robots[1].getNextPosition())) {
                robots[0].doStep();
                robots[1].doStep();
            }
            if (robots[0].isAttacking() == true) {
                robots[0].doDamageEnemy(robots[1]);
            }
            if (robots[1].isAttacking() == true) {
                robots[1].doDamageEnemy(robots[0]);
            }
            play.setGameGrid(arena);
            play.paint(count);
            System.out.println("");
            System.out.println(robots[0].getNameAndState());
            System.out.println(robots[1].getNameAndState());
            if (!robots[0].isALive()) {
                if (!robots[1].isALive()) {
                    System.out.println("Döntetlen");
                    timerToRound.cancel();
                }
                System.out.println("A győztes robot:" + robots[1]);
                timerToRound.cancel();
            } else if (!robots[1].isALive()) {
                System.out.println("A győztes robot:" + robots[0]);
                timerToRound.cancel();
            }

            if (count == 20) {
                if (robots[0].getArmor().getDurability() > robots[1].getArmor().getDurability()) {
                    System.out.println("A győztes robot: " + robots[0]);
                } else if (robots[0].getArmor().getDurability() == robots[1].getArmor().getDurability()) {
                    System.out.println("Döntetlen");
                } else {
                    System.out.println("A győztes robot: " + robots[1]);
                }
                timerToRound.cancel();
            }
        }
    }

    class TerminalTask extends TimerTask {

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();
        Terminal terminal;

        TerminalTask() {
            try {
                this.terminal = defaultTerminalFactory.createTerminal();
                terminal.setCursorVisible(false);
            } catch (IOException ex) {
                Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void run() {
            count++;
            try {
                robots[0].doAction(robots[1]);
                robots[1].doAction(robots[0]);
                if (robots[0].isMoving() == true
                        && robots[1].isMoving() == true
                        && !robots[0].getNextPosition().equals(robots[1].getNextPosition())) {
                    robots[0].doStep();
                    robots[1].doStep();
                }
                if (robots[0].isAttacking() == true) {
                    robots[0].doDamageEnemy(robots[1]);
                }
                if (robots[1].isAttacking() == true) {
                    robots[1].doDamageEnemy(robots[0]);
                }
                play.setGameGrid(arena);

                play.paint(count, terminal, robots);
                final TextGraphics textGraphics = terminal.newTextGraphics();
                textGraphics.setForegroundColor(TextColor.ANSI.RED);
                textGraphics.putString(terminal.getCursorPosition(), robots[0].getNameAndState().replace("\n", " "));
                TerminalPosition cursor = terminal.getCursorPosition();
                terminal.setCursorPosition(3, cursor.getRow() + 2);
                textGraphics.setForegroundColor(TextColor.ANSI.GREEN);
                textGraphics.putString(terminal.getCursorPosition(), robots[1].getNameAndState().replace("\n", " "));
                terminal.flush();

                if (!robots[0].isALive()) {
                    if (!robots[1].isALive()) {
                        terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "Döntetlen");
                        terminal.flush();
                        timerToRound.cancel();
                    }
                    terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "A győztes robot:" + robots[1]);
                    terminal.flush();
                    timerToRound.cancel();
                } else if (!robots[1].isALive()) {
                    terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "A győztes robot:" + robots[0]);
                    terminal.flush();
                    timerToRound.cancel();
                }

                if (count == 20) {
                    if (robots[0].getArmor().getDurability() > robots[1].getArmor().getDurability()) {
                        terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "A győztes robot:" + robots[0]);
                        terminal.flush();
                    } else if (robots[0].getArmor().getDurability() == robots[1].getArmor().getDurability()) {
                        terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "Döntetlen");
                        terminal.flush();
                    } else {
                        terminal.newTextGraphics().putString(TerminalPosition.OFFSET_1x1, "A győztes robot:" + robots[1]);
                        terminal.flush();
                    }
                    timerToRound.cancel();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
