package local.fox.fenestra.dto;

public class AppDataDTO {
    private String name;
    private String version;

    public AppDataDTO(
        String name,
        String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}