
package model;


public interface Ability {
    public Position getPosition();
    public Armor getArmor();
    public Position stepToDirection(int direction);
    public void stop();
    public void defendToDirection(int direction);
    public void attackToDirection(int direction);
}
