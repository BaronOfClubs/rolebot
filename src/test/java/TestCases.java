import net.baronofclubs.Rolebot.Backend.Server;
import net.baronofclubs.Rolebot.Utility.ResourceManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCases {

    ResourceManager resourceManager = new ResourceManager();

    public static void main(String[] args) {
        /*
        Test test = new Test();
        test.save();

        Server server = new Server(null);
        server.save();
        */

        Path path = Paths.get("tests/server-343241996447514626.json");
        Server server = ResourceManager.FileLoader.loadServer(path);
        System.out.println(server.getLastSave());
    }


}
