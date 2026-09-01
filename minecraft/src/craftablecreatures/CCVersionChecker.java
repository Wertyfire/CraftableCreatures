/**
 * File created on 21:40 01.02.2024 by Wertyfire
 * using Forge class ForgeVersion
 * and then backported for mc 1.1 on 13:23 31.08.2026
 */

package craftablecreatures;

import craftablecreatures.lib.Json;
import craftablecreatures.lib.SimpleVersion;
import net.minecraft.src.mod_CraftableCreatures;

import java.net.URL;


public class CCVersionChecker {
    private static final String updateUrl = "https://raw.githubusercontent.com/Wertyfire/CraftableCreatures/refs/heads/1.7.10/updates.json";
    private static final String mcVersion = "1.1";

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
                    Json json = Json.read(url);

                    homepage = json.at("homepage").asString();
                    Json promos = json.at("promos");
                    Json changes = json.at(mcVersion);

                    String rec = promos.at(mcVersion + "-recommended").asString();
                    String lat = promos.at(mcVersion + "-latest").asString();
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
                                    changelog = changes.at(lat).asString();
                                    mod_CraftableCreatures.info("Found new version: " + lat);
                                }
                            }
                        } else {
                            updateResult = UpdateResult.OUTDATED;
                            downloadLink = homepage + "/versions/" + rec;
                            target = rec;
                            changelog = changes.at(rec).asString();
                            mod_CraftableCreatures.info("Found new version: " + rec);
                        }
                    } else if (lat != null) {
                        if (current.compareTo(new SimpleVersion(lat)) < 0) {
                            updateResult = UpdateResult.BETA_OUTDATED;
                            downloadLink = homepage + "/versions/" + lat;
                            target = lat;
                            changelog = changes.at(lat).asString();
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