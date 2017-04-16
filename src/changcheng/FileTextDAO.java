package changcheng;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by changcheng on 2017/4/16.
 */
public class FileTextDAO implements TextDAO {
    @Override
    public void create(String file) {
        try {
            Files.createFile(Paths.get(file));
        } catch (IOException ex) {
            Logger.getLogger(
                    FileTextDAO.class.getName()
            ).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String read(String file) {
        byte[] datas = null;
        try {
            datas = Files.readAllBytes(Paths.get(file));
        } catch (IOException ex) {
            Logger.getLogger(
                    FileTextDAO.class.getName()
            ).log(Level.SEVERE, null, ex);
        }
        return new String(datas);
    }

    @Override
    public void save(String file, String text) {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(file),
                Charset.forName(System.getProperty("file.encoding"))
        )) {
            writer.write(text);
        } catch (IOException ex) {
            Logger.getLogger(
                    FileTextDAO.class.getName()
            ).log(Level.SEVERE, null, ex);
        }
    }
}
