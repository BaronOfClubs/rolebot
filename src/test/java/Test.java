import net.baronofclubs.Rolebot.Utility.ResourceManager;

import java.nio.file.Paths;
import java.util.UUID;

public class Test extends ResourceManager.SaveFile implements ResourceManager.Savable {
    String name;
    UUID id;

    public Test() {
        this.name = "TestOne";
        this.id = UUID.randomUUID();
    }

    @Override
    public void save() {
        setFilename("test");
        setDirectory(Paths.get("tests/"));
        setExtension(ResourceManager.FileType.JSON);

        ResourceManager.FileSaver.saveFile(this);
    }
}
