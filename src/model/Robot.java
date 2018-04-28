package model;

public class Robot implements Ability {

    private char name;
    private String className;
    private int directionofAttacking, directionofDefending;
    private boolean isAttacking, isMoving;
    private Position myPosition, nextPosition, oldPosition;

    protected Arena arena;
    protected int damage;
    protected Armor armor;

    public Robot(Integer durability, Character name, String className) {
        armor = new Armor(durability);
        this.name = name;
        this.className = className;
        damage = 1;
    }

    @Override
    public Position getPosition() {
        return myPosition;
    }

    public void setPosition(Position myPosition) {
        this.myPosition = myPosition;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    @Override
    public Armor getArmor() {
        return armor;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    private double distanceToEnemy(Position from, Position to) {
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private int direcionToEnemy(Robot enemy) {
        int direction;
        if (myPosition.getX() == enemy.getPosition().getX()) {
            if (myPosition.getY() < enemy.getPosition().getY()) {
                direction = 2;
            } else {
                direction = 4;
            }
        } else if (myPosition.getX() < enemy.getPosition().getX()) {
            direction = 3;
        } else {
            direction = 1;
        }
        return direction;
    }

    private int minValueOfArray(double[] values) {
        double minValue = values[0];
        int minIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] < minValue) {
                minValue = values[i];
                minIndex = i;
            }

        }
        return minIndex;
    }

    public final void doAction(Robot enemy) {
        String doing = Math.random() < 0.5 ? "defense" : "attack";

        if (distanceToEnemy(myPosition, enemy.getPosition()) > 1.0) {
            int direction = stepToEnemyDirection(enemy);
            nextPosition = stepToDirection(direction);
        } else {
            if (doing.equals("defense")) {
                defendToDirection(direcionToEnemy(enemy));
            } else {
                attackToDirection(direcionToEnemy(enemy));
            }
        }
    }

    public int stepToEnemyDirection(Robot robot) {
        double[] distances = new double[4];
        distances[0] = distanceToEnemy(new Position(myPosition.getX(), myPosition.getY() - 1), robot.getPosition());
        distances[1] = distanceToEnemy(new Position(myPosition.getX() + 1, myPosition.getY()), robot.getPosition());
        distances[2] = distanceToEnemy(new Position(myPosition.getX(), myPosition.getY() + 1), robot.getPosition());
        distances[3] = distanceToEnemy(new Position(myPosition.getX() - 1, myPosition.getY()), robot.getPosition());
        return minValueOfArray(distances);
    }

    private boolean checkStep(Position position) {
        return ((position.getX() < arena.getRowCount()) && (position.getX() >= 0)
                && (position.getY() < arena.getColumnCount()) && (position.getY() >= 0));
    }

    @Override
    public final Position stepToDirection(int direction) {
        isAttacking = false;
        switch (direction) {
            case 0:
                if (checkStep(new Position(myPosition.getX(), myPosition.getY() - 1))) {
                    nextPosition = new Position(myPosition.getX(), myPosition.getY() - 1);
                    isMoving = true;
                } else {
                    stop();
                }
                break;
            case 1:
                if (checkStep(new Position(myPosition.getX() + 1, myPosition.getY()))) {
                    nextPosition = new Position(myPosition.getX() + 1, myPosition.getY());
                    isMoving = true;
                } else {
                    stop();
                }
                break;
            case 2:
                if (checkStep(new Position(myPosition.getX(), myPosition.getY() + 1))) {
                    nextPosition = new Position(myPosition.getX(), myPosition.getY() + 1);
                    isMoving = true;
                } else {
                    stop();
                }
                break;
            case 3:
                if (checkStep(new Position(myPosition.getX() - 1, myPosition.getY()))) {
                    nextPosition = new Position(myPosition.getX() - 1, myPosition.getY());

                    isMoving = true;
                } else {
                    stop();
                }
                break;
        }
        if (oldPosition == null) {
            oldPosition = new Position(nextPosition.getX(), nextPosition.getY());
        } else if (!oldPosition.equals(nextPosition)) {
            oldPosition = new Position(nextPosition.getX(), nextPosition.getY());
        } else {
            int random = (Math.random() < 0.3 ? -1 : (Math.random() < 0.5 ? 0 : 1));
            if (Math.random() < 0.5) {
                if (checkStep(new Position(myPosition.getX() + random, myPosition.getY()))) {
                    nextPosition = new Position(myPosition.getX() + random, myPosition.getY());
                } else {
                    nextPosition = new Position(myPosition.getX() - random, myPosition.getY());
                }
            } else if (checkStep(new Position(myPosition.getX(), myPosition.getY() + random))) {
                nextPosition = new Position(myPosition.getX(), myPosition.getY() + random);
            } else {
                nextPosition = new Position(myPosition.getX(), myPosition.getY() - random);
            }
        }
        return nextPosition;
    }

    public Position getNextPosition() {
        return nextPosition;
    }

    @Override
    public final void stop() {
        isMoving = false;
    }

    @Override
    public final void defendToDirection(int direction) {
        isAttacking = false;
        isMoving = false;
        switch (direction) {
            case 1:
                directionofDefending = 1;
                break;
            case 2:
                directionofDefending = 2;
                break;
            case 3:
                directionofDefending = 3;
                break;
            case 4:
                directionofDefending = 4;
                break;
        }
    }

    @Override
    public final void attackToDirection(int direction) {
        isAttacking = true;
        isMoving = false;
        directionofDefending = 0;
        switch (direction) {
            case 1:
                directionofAttacking = 1;
                break;
            case 2:
                directionofAttacking = 2;
                break;
            case 3:
                directionofAttacking = 3;
                break;

            case 4:
                directionofAttacking = 4;
                break;
        }
    }

    public int getDirectionofAttacking() {
        return directionofAttacking;
    }

    public int getDirectionofDefending() {
        return directionofDefending;
    }

    public boolean isALive() {
        return getArmor().getDurability() > 0;
    }

    public void doStep() {
        arena.setRobotPosition(this, myPosition, nextPosition);
        myPosition.setX(nextPosition.getX());
        myPosition.setY(nextPosition.getY());

    }

    public void doDamageEnemy(Robot enemy) {
        if (directionofAttacking == 1
                && enemy.getPosition().getX() == myPosition.getX() - 1
                && enemy.getPosition().getY() == myPosition.getY()
                && enemy.getDirectionofDefending() != 3 && enemy.isMoving == false) {
            enemy.getArmor().setDurability(enemy.getArmor().getDurability()-damage);
        }

        if (directionofAttacking == 2
                && enemy.getPosition().getX() == myPosition.getX()
                && enemy.getPosition().getY() == myPosition.getY() + 1
                && enemy.getDirectionofDefending() != 4
                && enemy.isMoving == false) {
            enemy.getArmor().setDurability(enemy.getArmor().getDurability()-damage);
        }

        if (directionofAttacking == 3
                && enemy.getPosition().getX() == myPosition.getX() + 1
                && enemy.getPosition().getY() == myPosition.getY()
                && enemy.getDirectionofDefending() != 1
                && enemy.isMoving == false) {
            enemy.getArmor().setDurability(enemy.getArmor().getDurability()-damage);
        }

        if (directionofAttacking == 4
                && enemy.getPosition().getX() == myPosition.getX()
                && enemy.getPosition().getY() == myPosition.getY() - 1
                && enemy.getDirectionofDefending() != 2
                && enemy.isMoving == false) {
            enemy.getArmor().setDurability(enemy.getArmor().getDurability()-damage);
        }
    }

    @Override
    public String toString() {
        return "" + name;
    }
    
    public String getNameAndState(){
        return "\"" + name+"\" robot:"+className+"\n"+getArmor()+"\n";
    }

}
