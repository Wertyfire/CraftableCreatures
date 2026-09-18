/**
 * File created on 21:49 11.09.2026 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures;

import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonNodeFactories;
import argo.jdom.JsonRootNode;
import net.minecraft.src.mod_CraftableCreatures;
import ru.wertyfiregames.craftablecreatures.lib.SimpleVersion;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CCVersionChecker {
    private static final String updateUrl = "https://raw.githubusercontent.com/Wertyfire/CraftableCreatures/refs/heads/1.7.10/updates.json";
    private static final String mcVersion = "1.2.5";

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
        if (mod_CraftableCreatures.CraftableCreaturesIDs.checkForUpdates) new Thread("CC Version Check") {
            @Override
            public void run() {
                try {
                    mod_CraftableCreatures.info("Version Check");
                    mod_CraftableCreatures.info("Checking Craftable Creatures version...");
                    mod_CraftableCreatures.info("Current version: " + currentVersion);

                    URL url = new URL(updateUrl);
                    InputStream stream = url.openStream();
                    InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                    StringBuilder content = new StringBuilder();
                    char [] buf = new char[1024];
                    for (int n = reader.read(buf); n > -1; n = reader.read(buf))
                        content.append(buf, 0, n);

                    JsonRootNode json = new JdomParser().parse(content.toString());
                    reader.close();
                    stream.close();

                    homepage = json.getStringValue("homepage");
                    JsonNode promos = (JsonNode) json.getFields().get(JsonNodeFactories.aJsonString("promos"));
                    JsonNode changes = (JsonNode) json.getFields().get(JsonNodeFactories.aJsonString(mcVersion));

                    String rec = promos.getStringValue(mcVersion + "-recommended");
                    String lat = promos.getStringValue(mcVersion + "-latest");
                    SimpleVersion current = new SimpleVersion(currentVersion);

                    if (rec != null) {
                        SimpleVersion recommended = new SimpleVersion(rec);
                        int diff = recommended.compareTo(current);

                        if (diff == 0) {
                            updateResult = UpdateResult.UP_TO_DATE;
                            mod_CraftableCreatures.info("Version up to date");
                        }
                        else if (diff < 0) {
                            updateResult = UpdateResult.AHEAD;
                            if (lat != null) {
                                if (current.compareTo(new SimpleVersion(lat)) < 0) {
                                    updateResult = UpdateResult.OUTDATED;
                                    downloadLink = homepage + "/versions/" + lat;
                                    target = lat;
                                    changelog = changes.getStringValue(lat);
                                    mod_CraftableCreatures.info("Found new version: " + lat);
                                }
                            }
                        } else {
                            updateResult = UpdateResult.OUTDATED;
                            downloadLink = homepage + "/versions/" + rec;
                            target = rec;
                            changelog = changes.getStringValue(rec);
                            mod_CraftableCreatures.info("Found new version: " + rec);
                        }
                    } else if (lat != null) {
                        if (current.compareTo(new SimpleVersion(lat)) < 0) {
                            updateResult = UpdateResult.BETA_OUTDATED;
                            downloadLink = homepage + "/versions/" + lat;
                            target = lat;
                            changelog = changes.getStringValue(lat);
                            mod_CraftableCreatures.info("Found new version: " + lat);
                        } else
                            updateResult = UpdateResult.BETA;
                    } else
                        updateResult = UpdateResult.BETA;
                } catch (Exception e) {
                    mod_CraftableCreatures.err("Failed to check for updates: " + e.getMessage());
                    updateResult = UpdateResult.FAILED;
                }
            }
        }.start();
    }
}