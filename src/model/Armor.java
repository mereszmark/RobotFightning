
package model;

public class Armor {
    private int durability;
    private final int initialDurability;

    public Armor(int dur) {
        this.durability = dur;
        initialDurability=dur;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public String toString() {
       return "Páncél: "+durability+"/"+initialDurability;
    }  
    
}
