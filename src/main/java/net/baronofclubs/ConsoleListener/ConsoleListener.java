package net.baronofclubs.ConsoleListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleListener {

    private static RunLoop runLoop = new RunLoop();

    public static void main(String[] args) {
        Thread consoleListenerThread = new Thread(runLoop);
        consoleListenerThread.start();
    }

    public static void start() {
        Thread consoleListenerThread = new Thread(runLoop);
        consoleListenerThread.start();
    }

    public static void stop() {
        runLoop.stop();
    }

    private static class RunLoop implements Runnable {

        private final String STOP_COMMAND = "quit";

        private boolean run;

        @Override
        public void run() {

            run = true;

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Console reader running. Type '" + STOP_COMMAND + "' to stop.");

            while (run) {
                String input = null;
                try {
                    input = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!input.isEmpty()) {
                    parseLine(input);
                }
            }
        }

        public void stop() {
            run = false;
        }

        private void parseLine(String input) {
            if (input.equalsIgnoreCase(STOP_COMMAND)) {
                System.out.println("Exiting.");
                run = false;
            } else {
                ConsoleCommandManager.checkLine(input);
            }
        }

    }

}
