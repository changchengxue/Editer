package changcheng;

/**
 * Created by changcheng on 2017/4/16.
 */
public interface TextDAO {
    void create(String file);
    String read(String file);
    void save(String file, String text);
}
