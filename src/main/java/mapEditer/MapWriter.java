package mapEditer;

import entity.RiskMap;

import java.io.File;

/**
 * Interface for all map writers
 */
public interface MapWriter {
    public void writeMap(RiskMap p_map, String p_file);

}
