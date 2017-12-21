package net.baronofclubs.Rolebot.Utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.baronofclubs.Debug.Debug;
import net.baronofclubs.Rolebot.Backend.Server;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Stream;

public class ResourceManager {

    private static Gson gson = new GsonBuilder()
            .registerTypeAdapter(UUID.class, new GsonSerialization.UUIDSerialization())
            .registerTypeAdapter(ZonedDateTime.class, new GsonSerialization.ZonedDateTimeSerialization())
            .registerTypeAdapter(Charset.class, new GsonSerialization.CharsetSerialization())
            .registerTypeAdapter(Role.class, new GsonSerialization.RoleSerialization())
            .registerTypeAdapter(Guild.class, new GsonSerialization.GuildSerialization())
            .registerTypeAdapter(TextChannel.class, new GsonSerialization.TextChannelSerialization())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static abstract class SaveFile implements Savable {

        private transient String filename;
        private transient FileType extension;
        private transient Path directory;
        private transient Charset encoding;

        private byte[] data;

        private ZonedDateTime lastSave;

        public SaveFile() {

        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public void setExtension(FileType extension) {
            this.extension = extension;
        }

        public void setDirectory(Path directory) {
            this.directory = directory;
        }

        public void setEncoding(Charset encoding) {
            this.encoding = encoding;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getFilename() {
            return filename;
        }

        public FileType getExtension() {
            return extension;
        }

        public Path getDirectory() {
            return directory;
        }

        public Charset getEncoding() {
            return encoding;
        }

        public Path getFilePath() {
            String fullPath = new StringBuilder()
                    .append(directory.toString())
                    .append("/")
                    .append(filename)
                    .append(".")
                    .append(extension.getFileExtension())
                    .toString();
            return Paths.get(fullPath);
        }

        public ZonedDateTime getLastSave() {
            return lastSave;
        }

        public void updateLastSave() {
            lastSave = ZonedDateTime.now();
        }

        public byte[] getData() {
            return data;
        }
    }

    public interface Savable {
        void save();

        String getFilename();
        FileType getExtension();
        Path getDirectory();
        Charset getEncoding();
        Path getFilePath();

        ZonedDateTime getLastSave();
        void updateLastSave();
    }

    //////////////////////////////////////

    public enum FileType {
        JSON ("json", true),
        XML ("xml", false),
        TXT ("txt", false);

        String fileExtension;
        boolean savable;

        FileType(String extension, boolean savable) {
            this.fileExtension = extension;
            this.savable = savable;
        }

        public String getFileExtension() {
            return fileExtension;
        }

        public boolean isSavable() {
            return savable;
        }
    }

    //////////////////////////////////////

    public static class FileLoader {

        public static boolean fileExists(Path path) {
            return Files.exists(path);
        }

        private static byte[] loadByteArray(Path path) {
            byte[] encoded = new byte[0];
            try {
                return Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private static String loadString(Path path) {
            return new String(loadByteArray(path), StandardCharsets.UTF_8);
        }

        public static Savable loadSavable(Path path) {
            System.out.println(loadString(path));
            return gson.fromJson(loadString(path), SaveFile.class);
        }

        private static void loadAllFiles(Path path) {
            try {
                Stream<Path> list = Files.list(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // TODO: load as Savable and cast to Server.
        public static Server loadServer(Path path) {
            return gson.fromJson(loadString(path), Server.class);
        }

    }

    public static class FileSaver {

        private static void saveByteArray(byte[] byteArray, Path path) {
            if (!Files.exists(path.getParent())) {
                try {
                    Files.createDirectories(path.getParent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                Files.write(path, byteArray);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void saveString(String string, Path path) {
            saveByteArray(string.getBytes(), path);
        }

        public static void saveFile(Savable savable) {
            debug("Saving file: " + savable.getFilename());
            savable.updateLastSave();
            if (savable.getExtension().equals(FileType.JSON)) {
                String jsonString = gson.toJson(savable);
                saveString(jsonString, savable.getFilePath());
            }
            debug("Saved!");
        }

    }

    //////////////////////////////////////

    private static void debug(String debugText) {
        Debug.print(debugText, "ResourceManager", Debug.Level.MEDIUM);
    }

}
