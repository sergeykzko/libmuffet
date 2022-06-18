package name.seajay.libmuffet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MuffetConf {
    public String input;
    @Getter
    public static boolean debug = false;
    int workersCount = Runtime.getRuntime().availableProcessors();
}
