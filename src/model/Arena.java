package model;


public class Arena {
    
    private int row;
    private int column;
    private Object[][] arenaFields;

    public Arena(int row, int column) {
        this.row = row;
        this.column = column;
        arenaFields=new Object[row][column];
    }
    
    public void initFields(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                arenaFields[i][j]=new EmptyField();
            }
        }
    }
    
    public void initRobots(Robot robot){
        int rowPosition=(int)(Math.random()*this.row);
        int columnPosition=(int)(Math.random()*this.column);
        if(arenaFields[rowPosition][columnPosition] instanceof EmptyField)
        arenaFields[rowPosition][columnPosition]=robot;
        robot.setPosition(new Position(rowPosition, columnPosition));
    }
    
    public boolean isEmptyField(Position position){
        return arenaFields[position.getX()][position.getY()] instanceof EmptyField;      
    }
    
    public void setRobotPosition(Robot robot,Position from,Position to){
        arenaFields[from.getX()][from.getY()]=null;
        arenaFields[from.getX()][from.getY()]=new EmptyField();
        arenaFields[to.getX()][to.getY()]=robot; 
    }
    
    public char getObjectViewFromPosition(int x,int y){
        return arenaFields[x][y].toString().charAt(0);
    }  

    public int getColumnCount() {
        return column;
    }

    public int getRowCount() {
        return row;
    }
    
    
}
