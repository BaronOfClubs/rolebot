package net.baronofclubs.Rolebot;

import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Backend.Servers;
import net.baronofclubs.Rolebot.Command.CommandManager;
import net.baronofclubs.Rolebot.Command.Commands.addSelfRole;
import net.baronofclubs.Rolebot.Command.Commands.listSelfRoles;
import net.baronofclubs.Rolebot.Command.Commands.ping;
import net.baronofclubs.Rolebot.Command.Commands.removeSelfRole;
import net.baronofclubs.Rolebot.Utility.ResourceManager;
import net.baronofclubs.debug.Debug;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RolebotMain {

    public static final String PROJECT_NAME = "Rolebot";
    public static final String VERSION = "0.2.0";
    public static final String AUTHOR = "BaronOfClubs";
    public static final String DEFAULT_PATH = "/Rolebot/";

    private static JDAConfiguration jdaConfig;
    private static JDA jda;
    private static CommandManager commandManager;

    public static void main(String[] args) {
        debug("Starting " + PROJECT_NAME + " " + VERSION + " by " + AUTHOR + "...");
        if (!isConfigured()) {
            configure();
        } else {
            jdaConfig = new JDAConfiguration();
            loadConfiguration();
        }
        startBot(jdaConfig);
        registerListeners(jdaConfig);
        refreshFilesystem();
        configureCommandManager();
    }

    public static JDA getJDA() {
        return jda;
    }

    private static boolean isConfigured() {
        // Check for configuration files on disk.
        // TODO: Return true if they exist AND are current version
        // else Return false

        File tmpDir = new File(DEFAULT_PATH + "config.properties");
        return tmpDir.exists();
    }

    private static void configure() {
        debug("Configuring Rolebot...");
        // Set debug options
        Debug.setEncoding(StandardCharsets.UTF_8);
        Debug.setLoggingLevel(Debug.Level.SPARSE);
        Debug.setLogToFile(true, "/Rolebot/logs/");
        // Create bot object and configure
        jdaConfig = new JDAConfiguration();
        jdaConfig.setAccountType();
        jdaConfig.setToken("PUT TOKEN HERE");
        jdaConfig.save();
        jdaConfig.getConfiguration().setAutoReconnect(true); // TODO: Make optional reconnect
        debug("Configured!");
    }

    private static void loadConfiguration() {
        debug("Loading Configuration...");
        // Read files from disk and create objects
        jdaConfig.load();
        jdaConfig.setAccountType(); // TODO: Make optional account type
        debug("Loaded!");
    }

    private static void startBot(JDAConfiguration apiConfiguration) {
        debug("Starting bot");
        try {
            jda = apiConfiguration.getConfiguration().buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
        debug("Started!");
    }

    private static void registerListeners(JDAConfiguration apiConfig){
        debug("Registering listeners...");
        // Get list of classes from Listeners class and register
        jda.addEventListener(new Listeners());
        debug("Registered listeners!");
    }

    private static void refreshFilesystem() {
        debug("Refreshing Filesystem...");

        int loadedFromFileSystem = 0;
        int createdNewFiles = 0;

        for (Guild guild : jda.getGuilds()) {
            Path serversPath = Paths.get(DEFAULT_PATH + "server-" + guild.getId() + ".json");
            if(ResourceManager.FileLoader.fileExists(serversPath)) {
                Servers.addServer(ResourceManager.FileLoader.loadServer(serversPath));
                loadedFromFileSystem++;
            } else {
                Server server = new Server(guild);
                Servers.addServer(server);
                server.save();
                server.createConsole();
                createdNewFiles++;
            }
        }
        debug("Refreshed Filesystem!");
        debug("Loaded " + loadedFromFileSystem + " servers from FileSystem. Created files for " + createdNewFiles + " servers.");
    }

    private static void configureCommandManager() {
        commandManager = new CommandManager();
        CommandManager.addCommand(new ping());
        CommandManager.addCommand(new addSelfRole());
        CommandManager.addCommand(new removeSelfRole());
        CommandManager.addCommand(new listSelfRoles());
    }

    private static void debug(String debugText) {
        String actor = "RolebotMain";
        Debug.print(debugText, actor, Debug.Level.NONE);
    }

    private static class JDAConfiguration {
        String token;
        AccountType type;

        Properties propertiesFile = new Properties();

        public void setToken(String token) {
            this.token = token;
        }

        public void setAccountType() {
            this.type = AccountType.BOT;
        }

        public JDABuilder getConfiguration() {
            return new JDABuilder(type).setToken(token);
        }

        public void save() {
            OutputStream output = null;
            try {
                File file = new File(DEFAULT_PATH + "config.properties");
                file.getParentFile().mkdirs();
                file.createNewFile();
                output = new FileOutputStream(DEFAULT_PATH + "config.properties");
                // set the properties value
                propertiesFile.setProperty("token", token);
                // save properties to project root folder
                propertiesFile.store(output, null);
            } catch (IOException io) {
                io.printStackTrace();
            } finally {
                if (output != null) {
                    try {
                        output.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        public JDAConfiguration load() {
            // Set debug options
            Debug.setEncoding(StandardCharsets.UTF_8);
            Debug.setLoggingLevel(Debug.Level.SPARSE);
            Debug.setLogToFile(true, "/Rolebot/logs/");

            InputStream input = null;
            try {
                input = new FileInputStream(DEFAULT_PATH + "config.properties");
                // load a properties file
                propertiesFile.load(input);
                // get the property value and print it out
                setToken(propertiesFile.getProperty("token"));
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return this;
        }
    }

}
