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

import java.io.InputStream;
import java.net.URL;
import java.util.Map;

public class CCVersion {
    public static final int majorVersion = 0;
    public static final int minorVersion = 2;
    public static final int revisionVersion = 0;

    private static UpdateResult updateResult = UpdateResult.PENDING;
    private static String target = null;

    public static int getMajorVersion() {
        return majorVersion;
    }

    public static int getMinorVersion() {
        return minorVersion;
    }

    public static int getRevisionVersion() {
        return revisionVersion;
    }

    public static UpdateResult getStatus() {
        return updateResult;
    }

    public static String getTarget() {
        return target;
    }

    public static String getVersion() {
        return String.format("%d.%d.%d", majorVersion, minorVersion, revisionVersion);
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

    public static void startVersionCheck() {
        new Thread("CC Version Check") {
            @SuppressWarnings("unchecked")
            @Override
            public void run() {
                try {
                    URL url = new URL("https://raw.githubusercontent.com/Wertyfire/CraftableCreatures/1.7.10/updates.json");
                    InputStream con = url.openStream();
                    String data = new String(ByteStreams.toByteArray(con));
                    con.close();

                    Map<String, Object> json = new Gson().fromJson(data, Map.class);
                    Map<String, String> promos = (Map<String, String>) json.get("promos");

                    String rec = promos.get(MinecraftForge.MC_VERSION + "-recommended");
                    String lat = promos.get(MinecraftForge.MC_VERSION + "-latest");
                    ArtifactVersion current = new DefaultArtifactVersion(getVersion());

                    if (rec != null) {
                        ArtifactVersion recommended = new DefaultArtifactVersion(rec);
                        int diff = recommended.compareTo(current);

                        if (diff == 0)
                            updateResult = UpdateResult.UP_TO_DATE;
                        else if (diff < 0) {
                            updateResult = UpdateResult.AHEAD;
                            if (lat != null) {
                                if (current.compareTo(new DefaultArtifactVersion(lat)) < 0) {
                                    updateResult = UpdateResult.OUTDATED;
                                    target = lat;
                                }
                            }
                        } else {
                            updateResult = UpdateResult.OUTDATED;
                            target = rec;
                        }
                    } else if (lat != null) {
                        if (current.compareTo(new DefaultArtifactVersion(lat)) < 0) {
                            updateResult = UpdateResult.BETA_OUTDATED;
                            target = lat;
                        } else
                            updateResult = UpdateResult.BETA;
                    } else
                        updateResult = UpdateResult.BETA;
                } catch (Exception e) {
                    e.printStackTrace();
                    updateResult = UpdateResult.FAILED;
                }
            }
        }.start();
    }
}