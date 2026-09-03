/**
 * File created on 21:34 02.09.2026 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.src.Achievement;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public class AchievementT extends Achievement {
    protected String achievementDesc;

    public AchievementT(int id, String name, int row, int col, Item icon, Achievement parent) {
        this(id, name, row, col, new ItemStack(icon), parent);
    }

    public AchievementT(int id, String name, int col, int row, Block icon, Achievement parent) {
        this(id, name, col, row, new ItemStack(icon), parent);
    }

    public AchievementT(int id, String name, int col, int row, ItemStack icon, Achievement parent) {
        super(id, name, col, row, icon, parent);
        achievementDesc = "achievement." + name + ".desc";
    }

    @Override
    public String toString() {
        return TranslateUtils.translate(func_44020_i());
    }

    @Override
    public String func_44020_i() {
        return TranslateUtils.translate(super.func_44020_i());
    }

    @Override
    public String getDescription() {
        return TranslateUtils.translate(achievementDesc);
    }
}