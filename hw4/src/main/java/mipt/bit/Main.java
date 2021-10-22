package mipt.bit;

import java.net.URL;

public class Main {
    public static void main(String[] args) throws Exception {
        URL[] urls = {
                new URL("https://www.googleapis.com/drive/v3/files/1Hda8Kn9m96LBiNDUvBP7gTmrLustg70H?alt=media&key=AIzaSyCnyt2lgtvTEVvITi-mD7v0s49OaxLcEog"),
                new URL("https://www.googleapis.com/drive/v3/files/1wG0bcva7AcA2v2TUEADYJFWCgSyL7KzN?alt=media&key=AIzaSyCnyt2lgtvTEVvITi-mD7v0s49OaxLcEog")
        };

        String secretName = "ru.sbt.java.tasks.Secret";
        String veryStrangeSecretName = "ru.sbt.java.tasks.VeryStrangeSecret";

        int[] nameLengths = {secretName.length(), veryStrangeSecretName.length()};

        URLCustomClassLoader classLoader = new URLCustomClassLoader(urls, nameLengths);

        Runnable secret = (Runnable) classLoader.loadClass(secretName).getDeclaredConstructor().newInstance();
        Runnable veryStrangeSecret = (Runnable) classLoader.loadClass(veryStrangeSecretName).getDeclaredConstructor().newInstance();

        secret.run();
        // output C:\Users\19648488 is smart student
        veryStrangeSecret.run();
        // output C:\Users\19648488 is Hacker
    }
}
