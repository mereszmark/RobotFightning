package model;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassLoader {

    public static Class loadClass(String args) {
        Class cls=null;
        try {
            File file = new File("");
            URL url = file.toURI().toURL();
            java.lang.ClassLoader cl = new URLClassLoader(new URL[]{url});
            cls = cl.loadClass(args);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cls;
    }
}
