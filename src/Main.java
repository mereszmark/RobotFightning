
import controller.Match;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Robot;

public class Main {

    public static int readFromConsole() {
        boolean ok = false;
        int szam = 0;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        do {
            try {
                System.out.println("1-Konzolos vagy 2-Lanterna terminál megjelenítést választja (Kérem nyomja le a megfelelő számot)?\n");
                szam = Integer.parseInt(br.readLine().trim());
                ok = true;
            } catch (Exception ex) {
                System.out.println("Nem számot adtál meg.");
            }
        } while (!ok || (szam != 1 && szam != 2));
        return szam;
    }

    public static void main(String[] args) {
        try {
            Class classofWolf = model.ClassLoader.loadClass(args[0]);
            Class classofFox = model.ClassLoader.loadClass(args[1]);
            Robot robot1 = (Robot) classofWolf.getConstructor(Integer.class, Character.class, String.class, int.class).newInstance(12, 'A', classofWolf.getName(), 3);
            Robot robot2 = (Robot) classofFox.getConstructor(Integer.class, Character.class, String.class).newInstance(10, 'B', classofFox.getName());
            if (readFromConsole() == 1) {
                new Match("console",5, 6, 7, new Robot[]{robot1, robot2});
            } else {
                new Match("terminal",5, 6, 7, new Robot[]{robot1, robot2});
            }
        } catch (SecurityException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(Match.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
