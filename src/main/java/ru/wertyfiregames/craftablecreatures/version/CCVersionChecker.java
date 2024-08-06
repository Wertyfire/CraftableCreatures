/**
 * File created on 21:40 01.02.2024 by Wertyfire
 * using Forge class ForgeVersion
 */

package ru.wertyfiregames.craftablecreatures.version;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.wertyfiregames.craftablecreatures.config.CCConfig;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

import static ru.wertyfiregames.craftablecreatures.CraftableCreatures.METADATA;

public class CCVersionChecker {
    private static UpdateResult updateResult = UpdateResult.PENDING;
    private static String target = null;
    private static String changelog = null;
    private static String homepage = null;
    private static String downloadLink = null;

    public static String getTarget() {
        return target;
    }
    public static String getChangelog() {
        return changelog;
    }
    public static String getHomepageUrl() {
        return homepage;
    }
    public static String getDownloadLink() {
        return downloadLink;
    }
    public static UpdateResult getStatus() {
        return updateResult;
    }

    public enum UpdateResult {
        PENDING,
        FAILED,
        UP_TO_DATE,
        OUTDATED,
        AHEAD,
        BETA,
        BETA_OUTDATED
    }

    public static void check(String currentVersion) {
        if (CCConfig.checkForUpdates) new Thread("CC Version Check") {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                try {
                    Logger logger = LogManager.getLogger("CC Version Check");
                    logger.info("Checking Craftable Creatures version...");
                    logger.info("Current version: {}", currentVersion);

                    URL url = new URL(METADATA.updateUrl);
                    InputStream con = url.openStream();
                    String data = new String(ByteStreams.toByteArray(con));
                    con.close();

                    Map<String, Object> json = new Gson().fromJson(data, Map.class);
                    homepage = (String) json.get("homepage");
                    Map<String, String> promos = (Map<String, String>) json.get("promos");
                    Map<String, String> changes = (Map<String, String>) json.get(MinecraftForge.MC_VERSION);

                    String rec = promos.get(MinecraftForge.MC_VERSION + "-recommended");
                    String lat = promos.get(MinecraftForge.MC_VERSION + "-latest");
                    ArtifactVersion current = new DefaultArtifactVersion(currentVersion);

                    if (rec != null) {
                        ArtifactVersion recommended = new DefaultArtifactVersion(rec);
                        int diff = recommended.compareTo(current);

                        if (diff == 0) {
                            updateResult = UpdateResult.UP_TO_DATE;
                            logger.info("Version up to date");
                        }
                        else if (diff < 0) {
                            updateResult = UpdateResult.AHEAD;
                            if (lat != null) {
                                if (current.compareTo(new DefaultArtifactVersion(lat)) < 0) {
                                    updateResult = UpdateResult.OUTDATED;
                                    downloadLink = homepage + "/versions/" + lat;
                                    target = lat;
                                    changelog = changes.get(lat);
                                    logger.info("Found new version: {}", lat);
                                }
                            }
                        } else {
                            updateResult = UpdateResult.OUTDATED;
                            downloadLink = homepage + "/versions/" + rec;
                            target = rec;
                            changelog = changes.get(rec);
                            logger.info("Found new version: {}", rec);
                        }
                    } else if (lat != null) {
                        if (current.compareTo(new DefaultArtifactVersion(lat)) < 0) {
                            updateResult = UpdateResult.BETA_OUTDATED;
                            downloadLink = homepage + "/versions/" + lat;
                            target = lat;
                            changelog = changes.get(lat);
                            logger.info("Found new version: {}", lat);
                        } else
                            updateResult = UpdateResult.BETA;
                    } else
                        updateResult = UpdateResult.BETA;
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                    updateResult = UpdateResult.FAILED;
                }
            }
        }.start();
    }
}