package br.com.degiant.thirdLib;

import java.net.URL;
import java.io.File;
import java.net.URLClassLoader;

import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.lang.Class;

public final class Main extends JavaPlugin {

  @Override
  public void onLoad() {

    System.out.println("Loading Java libraries.");

    File directory = new File(".");
    File libraries = new File(directory, "third-libraries");

    if (!libraries.exists()) {
      libraries.mkdir();
    }

    File[] fileJars = libraries.listFiles();

    try {
      for (File jarFile : fileJars) {

        URL jarURL = new URL("jar:" + jarFile.toURI().toURL().toExternalForm() + "!/");
        final URLClassLoader loader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        final Method method = sysclass.getDeclaredMethod("addURL", new Class[] { URL.class });
        method.setAccessible(true);
        method.invoke(loader, new Object[] { jarURL });
        System.out.println("Loading library: " + jarFile.getName());

      }
    } catch (final Throwable t) {
      System.out.println("Failed to load libraries:");
      t.printStackTrace();
    }

  }

}