/**
 * File created on 17:19 31.08.2024 by Wertyfire
 */

package craftablecreatures;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.*;

public class GuiScreenGuideBook extends GuiScreen {
    private final String playerUsername;

    private static int
            guideBookPage1,
            guideBookPage2,
            guideBookBack,
            guideBookCover;
    private static int
            guideBookIllustrationSheet1;

    private GuiButton buttonBack;
    private GuiButton buttonNext;
    private CloseButton buttonCloseIllustration;

    private final List<Link> links = new ArrayList<>();
    private final Map<Short, Illustration> illustrations = new HashMap<>();
    private final List<DrawingStack> stacks = new ArrayList<>();

    private static final int xSize = 146, ySize = 180, fontHeight = 9;

    private static final int maxWidthPerLine = 111;
    private static final int maxLinesPerPage = 16;

    private static boolean wasUnicode;

    private static final RenderItem itemRender = new RenderItem();

    private boolean leftPage = true;
    private boolean pageEnded = false;
    private short line = 1;
    private int lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
    private int lineYPos = (height - ySize) / 2 + 10;

    private final short totalPages = 15;
    private short
            currentPage = 1,
            currentIllustration = 0;

    public GuiScreenGuideBook(EntityPlayer player) {
        super();

        playerUsername = player.username;
        if (ItemGuideBook.lastPageForPlayers.containsKey(playerUsername))
            currentPage = ItemGuideBook.getLastPageForPlayer(playerUsername);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        guideBookPage1 = mc.renderEngine.getTexture("/craftablecreatures/gui/guide_book/guide_book_page1.png");
        guideBookPage2 = mc.renderEngine.getTexture("/craftablecreatures/gui/guide_book/guide_book_page2.png");
        guideBookBack = mc.renderEngine.getTexture("/craftablecreatures/gui/guide_book/guide_book_back.png");
        guideBookCover = mc.renderEngine.getTexture("/craftablecreatures/gui/guide_book/guide_book_cover.png");

        guideBookIllustrationSheet1 = mc.renderEngine.getTexture("/craftablecreatures/gui/guide_book/illustrations/guide_book_illustration_sheet_1.png");

        wasUnicode = StringTranslate.getInstance().func_46110_d();

        buttonBack = new ChangePageButton(0, width / 2 - xSize - 25, (height + ySize) / 2 - 25, false);
        buttonNext = new ChangePageButton(1, width / 2 + xSize + 5, (height + ySize) / 2 - 25, true);
        buttonCloseIllustration = new CloseButton(2, (width - xSize) / 2 + xSize / 2 + 111, (height - ySize) / 2 + 9);

        controlList.clear();
        controlList.add(buttonBack);
        controlList.add(buttonNext);
        controlList.add(buttonCloseIllustration);
        checkButtons();
        newFrame();
    }

    @Override
    public void onGuiClosed() {
        ItemGuideBook.setLastPageForPlayer(playerUsername, currentPage);
        stacks.clear();
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        fontRenderer.func_44032_a(true);
        drawDefaultBackground();
        super.drawScreen(x, y, partialTicks);

        drawPage(x, y);

        newFrame();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == buttonBack.id) {
            minusPage();
            checkButtons();
        } else if (button.id == buttonNext.id) {
            plusPage();
            checkButtons();
        } else if (button.id == buttonCloseIllustration.id) {
            currentIllustration = 0;
            checkButtons();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (mouseButton == 0) {
            for (Link link : links) {
                if (link.canOpen(currentPage) && link.isMouseOver(mouseX, mouseY)) {
                    currentPage = link.page;
                    break;
                }
            }

            for (Illustration illustration : illustrations.values()) {
                if (illustration.canOpen(currentPage, currentIllustration) && illustration.isMouseOver(mouseX, mouseY)) {
                    currentIllustration = illustration.id;
                    break;
                }
            }
        }
    }

    private void newFrame() {
        line = 1;
        lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
        lineYPos = (height - ySize) / 2 + 10;
        leftPage = true;
        pageEnded = false;
        fontRenderer.func_44032_a(wasUnicode);

        stacks.removeIf(stack -> stack.shouldDraw(currentPage) && stack.x < width - xSize);
    }

    private void drawPage(int x, int y) {
        if (currentPage == 1) {
            drawBookTexture(2);
            drawTitle();
        } else if (currentPage == totalPages) {
            drawBookTexture(0);
            drawBack();
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        } else if (currentPage < 1) {
            currentPage = 1;
        } else {
            drawBookTexture(1);
            drawPageText(x, y);
            drawPageItemStacks();
            drawItemStackHovering(x, y);
            drawIllustrationHovering(x, y);
            drawCurrentIllustration(x, y);
        }
    }

    private void minusPage() {
        if (currentPage > 1) currentPage--;
    }
    private void plusPage() {
        if (currentPage < totalPages) currentPage++;
    }

    private void plusLine() {
        line++;
        lineYPos += fontHeight;
        checkLines();
    }

    private void leftPage() {
        if (leftPage) return;
        line = 1;
        lineXPos -= xSize;
        lineYPos = (height - ySize) / 2 + 10;
        leftPage = true;
        checkLines();
    }
    private void rightPage() {
        if (!leftPage) return;
        line = 1;
        lineXPos += xSize;
        lineYPos = (height - ySize) / 2 + 10;
        leftPage = false;
        checkLines();
    }

    private void checkButtons() {
        if (currentIllustration <= 0) {
            currentIllustration = 0;
            if (buttonCloseIllustration.enabled) buttonCloseIllustration.enabled = false;
            if (buttonCloseIllustration.drawButton) buttonCloseIllustration.drawButton = false;
        }

        if (currentPage == 1 && currentIllustration == 0) {
            buttonBack.enabled = false;
            buttonBack.drawButton = false;
            if (!buttonNext.enabled) buttonNext.enabled = true;
            if (!buttonNext.drawButton) buttonNext.drawButton = true;
        } else if (currentPage == totalPages && currentIllustration == 0) {
            buttonNext.enabled = false;
            buttonNext.drawButton = false;
            if (!buttonBack.enabled) buttonBack.enabled = true;
            if (!buttonBack.drawButton) buttonBack.drawButton = true;
        } else if (currentIllustration == 0) {
            if (!buttonBack.enabled) buttonBack.enabled = true;
            if (!buttonBack.drawButton) buttonBack.drawButton = true;
            if (!buttonNext.enabled) buttonNext.enabled = true;
            if (!buttonNext.drawButton) buttonNext.drawButton = true;
        }
    }

    // part: 0 - back, 1 - middle (default), 2 - cover
    private void drawBookTexture(int part) {
        if (part < 0 || part > 2)
            throw new IllegalArgumentException("Argument part must be 0 to 2 but current is: " + part);

        GL11.glColor4f(1f, 1f, 1f, 1f);

        int x = (width - xSize) / 2 + xSize / 2;
        int xLeft = (width - xSize) / 2 - xSize / 2;
        int y = (height - ySize) / 2;

        int u = 0;
        int v = 0;

        if (part == 1) {
            mc.renderEngine.bindTexture(guideBookPage2);
            drawTexturedModalRect(x, y, u, v, xSize, ySize);

            mc.renderEngine.bindTexture(guideBookPage1);
            drawTexturedModalRect(xLeft, y, u, v, xSize, ySize);
        } else if (part == 0) {
            mc.renderEngine.bindTexture(guideBookBack);
            drawTexturedModalRect(xLeft, y, u, v, xSize, ySize);
        } else {
            mc.renderEngine.bindTexture(guideBookCover);
            drawTexturedModalRect(x, y, u, v, xSize, ySize);
        }
    }

    private void drawTitle() {
        String s = TranslateUtils.translate("craftableCreatures.guide.cover.title");
        drawNonTextString(s, ((width - xSize) / 2 + xSize) - w(s) / 2, (height - ySize) / 2 + 45, 10339058);
    }

    private void drawBack() {
        int y = (height - ySize) / 2;
        drawAlignedString("Wertyfire, 2025", y + ySize / 2 + fontHeight * 6, 10339058, Alignment.CENTER);
    }

    private void drawPageText(int mouseX, int mouseY) {
        drawPageNumber();
        if (currentPage == 2) {
            int xLeft = (width - xSize) / 2;
            int x = (width - xSize) / 2 + xSize;
            int y = (height - ySize) / 2;
            String
                    s1 = f("craftableCreatures.guide.cover.title"),
                    s2 = "Craftable Creatures",
                    s3 = f("craftableCreatures.guide.page1.author"),
                    s4 = "Wertyfire",
                    s5 = f("craftableCreatures.guide.page1.edition"),
                    s6 = "2026";
            drawNonTextAlignedString(s1, xLeft, y + ySize / 2 - fontHeight, 0, Alignment.CENTER);
            drawNonTextAlignedString(s2, xLeft, y + ySize / 2, 0, Alignment.CENTER);
            rightPage();
            drawNonTextAlignedString(s3, x, y + ySize / 2 - fontHeight * 6, 0, Alignment.CENTER);
            drawNonTextAlignedString(s4, x, y + ySize / 2 - fontHeight * 5, 0, Alignment.CENTER);
            drawNonTextAlignedString(s5, x, y + ySize / 2 + fontHeight * 4, 0, Alignment.CENTER);
            drawNonTextAlignedString(s6, x, y + ySize / 2 + fontHeight * 6, 0, Alignment.CENTER);
        } else if (currentPage == 3) {
            drawAlignedString(f("craftableCreatures.guide.page2.tableOfContents"), Alignment.CENTER);
            drawLinkString(f("craftableCreatures.guide.page2.chapter1"), mouseX, mouseY, (short) 4, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter2"), mouseX, mouseY, (short) 5, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter3"), mouseX, mouseY, (short) 6, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter4"), mouseX, mouseY, (short) 8, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter5"), mouseX, mouseY, (short) 10, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter6"), mouseX, mouseY, (short) 11, (short) 3);
            drawLinkString(f("craftableCreatures.guide.page2.chapter7"), mouseX, mouseY, (short) 12, (short) 3);
        } else if (currentPage == 4) {
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page3.chapter"), Alignment.CENTER);
            drawEmptyString();
            drawSplitString(f("craftableCreatures.guide.page3.mainText.1"));
        } else if (currentPage == 5) {
            drawSplitString(f("craftableCreatures.guide.page3.mainText.2"));
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page4.chapter"), Alignment.CENTER);
            drawEmptyString();
            drawSplitString(f("craftableCreatures.guide.page4.mainText.1", new Object[]{ItemSoulElement.soulNames.length, ItemSoulElement.soulNames.length - 1}));
        } else if (currentPage == 6) {
            drawSplitString(f("craftableCreatures.guide.page4.mainText.2"));
            int stackX = lineXPos;
            int stackY = lineYPos;
            int stacks = 0;
            for (int i = 0; i < ItemSoulElement.soulTextures.length - 1; i++) {
                drawItemStack(s(mod_CraftableCreatures.soulElement, 1, i), stackX, stackY);
                stackX += 18;
                stacks++;
                if (stacks >= 6) {
                    stackX = lineXPos;
                    stackY = lineYPos + 18;
                    lineYPos += 18;
                    stacks = 0;
                }
            }
            drawEmptyString(2);
            drawSplitString(f("craftableCreatures.guide.page4.mainText.3"));
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page5.chapter"), Alignment.CENTER);
            drawEmptyString();
            drawSplitString(f("craftableCreatures.guide.page5.mainText.1"));
            drawEmptyString();
            drawAlignedString(f("item.bluestone.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.bluestone), lineXPos + xSize - 18 - 32, lineYPos - fontHeight * 2);
            drawSplitString(f("craftableCreatures.guide.page5.mainText.2"));
        } else if (currentPage == 7) {
            drawAlignedString(f("item.template.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.template), lineXPos + xSize - 18 - 32, lineYPos - fontHeight);
            drawSplitString(f("craftableCreatures.guide.page5.mainText.3"));
            drawEmptyString();
            drawAlignedString(f("item.spawnEggTemplate.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.spawnEggTemplate), lineXPos + xSize - 18 - 32, lineYPos - fontHeight * 2);
            drawSplitString(f("craftableCreatures.guide.page5.mainText.4"));
        } else if (currentPage == 8) {
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page6.chapter"), Alignment.CENTER);
            drawEmptyString();
            drawSplitString(f("craftableCreatures.guide.page6.mainText.1"));
            drawEmptyString();
            drawAlignedString(f("craftableCreatures.guide.page6.mainText.2"), Alignment.CENTER);
            drawEmptyString();
            drawAlignedString(f("tile.bluestoneOre.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.bluestoneOre), lineXPos + xSize - 18 - 32, lineYPos - fontHeight * 2);
            drawSplitString(f("craftableCreatures.guide.page6.mainText.3"));
        } else if (currentPage == 9) {
            drawAlignedString(f("tile.bluestoneBlock.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.bluestoneBlock), lineXPos + xSize - 18 - 32, lineYPos - fontHeight);
            drawSplitString(f("craftableCreatures.guide.page6.mainText.4"));
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page6.mainText.7"), Alignment.CENTER);
            drawEmptyString();
            drawAlignedString(f("tile.soulExtractor.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.soulExtractorLit), lineXPos + xSize - 18 - 32, lineYPos - fontHeight);
            drawSplitString(f("craftableCreatures.guide.page6.mainText.8"));
            drawIllustration((short) 1, r("craftablecreatures/gui/guide_book/illustrations/soul_extractor_interface_scaled.png"), f("craftableCreatures.guide.illustration.1.comment"), 0, 0);
        } else if (currentPage == 10) {
            drawAlignedString(f("tile.spawnEggCombiner.name"), Alignment.CENTER);
            drawItemStack(s(mod_CraftableCreatures.combinerLit), lineXPos + xSize - 18 - 32, lineYPos - fontHeight);
            drawSplitString(f("craftableCreatures.guide.page6.mainText.9"));
            drawIllustration((short) 2, r("craftablecreatures/gui/guide_book/illustrations/combiner_interface_scaled.png"), f("craftableCreatures.guide.illustration.2.comment"), 111, 0);
            drawSplitString(f("craftableCreatures.guide.page6.mainText.10"));
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page7.chapter"), Alignment.CENTER);
            drawSplitString(f("craftableCreatures.guide.page7.mainText.1"));
            drawEmptyString();
            drawSplitString(f("craftableCreatures.guide.page7.mainText.2"));
        } else if (currentPage == 11) {
            drawSplitString(f("craftableCreatures.guide.page7.mainText.3"));
            drawCraftingRecipe(new ItemStack[]{s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(mod_CraftableCreatures.bluestoneBlock), s(Block.cobblestone), s(Block.cobblestone), s(Block.stoneOvenIdle), s(Block.cobblestone)}, s(mod_CraftableCreatures.soulExtractor), lineXPos - 6, lineYPos);
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page8.chapter"), Alignment.CENTER);
            drawSplitString(f("craftableCreatures.guide.page8.mainText.1"));
            drawSplitString(f("craftableCreatures.guide.page8.mainText.2"));
            drawSplitString(f("craftableCreatures.guide.page8.mainText.3"));
            drawCraftingRecipe(new ItemStack[]{s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(mod_CraftableCreatures.template), s(Block.cobblestone), s(Block.cobblestone), s(Block.torchRedstoneActive), s(Block.cobblestone)}, s(mod_CraftableCreatures.combiner), lineXPos - 9, lineYPos);
        } else if (currentPage == 12) {
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page9.chapter"), Alignment.CENTER);
            drawAlignedString(f("craftableCreatures.guide.page9.mainText.1"), Alignment.CENTER);
            drawAlignedString(f("tile.bluestoneBlock.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone)}, s(mod_CraftableCreatures.bluestoneBlock), lineXPos - 9, lineYPos);
            drawEmptyString();
            drawAlignedString(f("item.bluestone.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(mod_CraftableCreatures.bluestoneBlock), null, null, null}, s(mod_CraftableCreatures.bluestone, 9), lineXPos + 11, lineYPos);
        } else if (currentPage == 13) {
            drawAlignedString(f("item.template.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(Item.paper), s(mod_CraftableCreatures.bluestone), s(mod_CraftableCreatures.bluestone), s(Item.paper)}, s(mod_CraftableCreatures.template), lineXPos + 11, lineYPos);
            drawEmptyString();
            drawAlignedString(f("tile.soulExtractor.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(mod_CraftableCreatures.bluestoneBlock), s(Block.cobblestone), s(Block.cobblestone), s(Block.stoneOvenIdle), s(Block.cobblestone)}, s(mod_CraftableCreatures.soulExtractor), lineXPos - 9, lineYPos);
            rightPage();
            drawAlignedString(f("item.spawnEggTemplate.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(Item.egg), null, null, s(mod_CraftableCreatures.template)}, s(mod_CraftableCreatures.spawnEggTemplate), lineXPos + 13, lineYPos);
            drawEmptyString();
            drawAlignedString(f("tile.spawnEggCombiner.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(Block.cobblestone), s(mod_CraftableCreatures.template), s(Block.cobblestone), s(Block.cobblestone), s(Block.torchRedstoneActive), s(Block.cobblestone)}, s(mod_CraftableCreatures.combiner), lineXPos - 6, lineYPos);
        } else if (currentPage == 14) {
            drawAlignedString(StringTranslate.getInstance().translateKey("tile.mobSpawner.name"), Alignment.CENTER);
            drawCraftingRecipe(new ItemStack[]{s(Block.fenceIron), s(Block.fenceIron), s(Block.fenceIron), s(Block.fenceIron), s(mod_CraftableCreatures.soulElement), s(Block.fenceIron), s(Block.fenceIron), s(Block.fenceIron), s(Block.fenceIron)}, s(Block.mobSpawner), lineXPos - 9, lineYPos);
            drawAlignedString(f("craftableCreatures.guide.page9.mainText.2"), Alignment.CENTER);
            drawSmeltingRecipe(s(mod_CraftableCreatures.bluestoneOre), s(mod_CraftableCreatures.bluestone), lineXPos + 8, lineYPos);
            rightPage();
            drawAlignedString(f("craftableCreatures.guide.page9.mainText.3"), Alignment.CENTER);
            drawExtractingRecipe(s(Item.rottenFlesh), s(mod_CraftableCreatures.soulElement, 1, 5), lineXPos, lineYPos);
            drawAlignedString(f("craftableCreatures.guide.page9.mainText.4"), Alignment.CENTER);
            drawCombiningRecipe(s(mod_CraftableCreatures.soulElement, 1, 1), s(mod_CraftableCreatures.spawnEggTemplate), s(Item.field_44019_bC, 1, EntityList.getEntityID(new EntityCreeper(null))), lineXPos + 9, lineYPos);
        }
    }

    private void drawPageItemStacks() {
        for (DrawingStack stack : stacks) {
            if (stack.shouldDraw(currentPage)) {
                if (stack.drawSlot) {
                    boolean wasLighting = GL11.glIsEnabled(GL11.GL_LIGHTING),
                            noIllustrations = currentIllustration == 0,
                            shouldDisable = wasLighting && noIllustrations;
                    if (shouldDisable) GL11.glDisable(GL11.GL_LIGHTING);
                    drawImage(guideBookPage1, stack.x - 1, stack.y - 1, 0, 238, 18, 18);
                    if (wasLighting) GL11.glEnable(GL11.GL_LIGHTING);
                }

                if (stack.holdingStack != null) {
                    GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                    enableGUIStandardItemLighting();
                    itemRender.renderItemIntoGUI(fontRenderer, mc.renderEngine, stack.holdingStack,
                            stack.x, stack.y);
                    itemRender.renderItemOverlayIntoGUI(fontRenderer, mc.renderEngine, stack.holdingStack,
                            stack.x, stack.y);
                    RenderHelper.disableStandardItemLighting();
                    GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                }
            }
        }
    }
    private void drawItemStackHovering(int mouseX, int mouseY) {
        for (DrawingStack stack : stacks) {
            if (stack.shouldDraw(currentPage) && stack.isMouseOver(mouseX, mouseY) && currentIllustration == 0) {
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glColorMask(true, true, true, false);
                drawGradientRect(stack.x, stack.y, stack.x + 16, stack.y + 16, -2130706433, -2130706433);
                GL11.glColorMask(true, true, true, true);
                GL11.glEnable(GL11.GL_DEPTH_TEST);

                if (stack.holdingStack == null) return;
                renderToolTip(stack.holdingStack, mouseX, mouseY);
                return;
            }
        }
    }

    private void drawIllustrationHovering(int mouseX, int mouseY) {
        Illustration ill = null;
        for (Illustration ill_ : illustrations.values()) {
            if (ill_.page == currentPage) ill = ill_;
        }
        if (ill == null) return;
        if (currentIllustration == 0 && isMouseOver(mouseX, mouseY, ill.x, ill.y, ill.width, ill.height))
            drawHoveringText(Collections.singletonList(f("craftableCreatures.guide.clickToViewIllustration")), mouseX, mouseY, fontRenderer);
    }

    private void drawCurrentIllustration(int mouseX, int mouseY) {
        if (currentIllustration < 0) currentIllustration = 0;
        if (currentIllustration == 0) return;

        zLevel = 200f;
        drawDefaultBackground();

        buttonBack.enabled = false;
        buttonBack.drawButton = false;
        buttonNext.enabled = false;
        buttonNext.drawButton = false;
        buttonCloseIllustration.enabled = true;
        buttonCloseIllustration.drawButton = true;
        buttonCloseIllustration.setZLevel(200f);

        Illustration illustration = illustrations.get(currentIllustration);
        mc.renderEngine.bindTexture(illustration.scaled);
        int drawX = (width - 222) / 2;
        int drawY = (height - 136) / 2;
        drawTexturedModalRect(drawX, drawY, 0, 0, 222, 136);
        buttonCloseIllustration.drawButton(mc, mouseX, mouseY);
        if (isMouseOver(mouseX, mouseY, drawX, drawY, 222, 136))
            drawHoveringText(Collections.singletonList(illustration.comment), mouseX, mouseY, fontRenderer);

        buttonCloseIllustration.setZLevel(0f);
        zLevel = 0f;
    }

    private void drawPageNumber() {
        drawNonTextString("" + ((currentPage - 1) * 2 - 1), (width - xSize) / 2 + xSize / 2 - 20 - w("" + ((currentPage - 1) * 2 - 1)), (height - ySize) / 2 + ySize - 20);
        drawNonTextString("" + ((currentPage - 1) * 2), (width - xSize) / 2 + xSize / 2 + 20, (height - ySize) / 2 + ySize - 20);
    }

    private void drawString(String s) {
        drawString(s, 0);
    }
    private void drawString(String s, int color) {
        drawString(s, lineXPos, lineYPos, color);
    }
    private void drawString(String s, int x, int y, int color) {
        if (s == null || s.isEmpty()) return;
        if (pageEnded) return;
        drawNonTextString(s, x, y, color);
        plusLine();
    }

    private void drawSplitString(String s) {
        drawSplitString(s, 0);
    }
    @SuppressWarnings("unchecked")
    private void drawSplitString(String s, int color) {
        if (s == null || s.isEmpty()) return;
        if (pageEnded) return;

        List<String> lines = listFormattedStringToWidth(s, maxWidthPerLine);
        for (String line : lines)
            drawString(line, color);
    }

    private void drawAlignedString(String s, Alignment horizontalAlignment) {
        drawAlignedString(s, 0, horizontalAlignment);
    }
    private void drawAlignedString(String s, int color, Alignment horizontalAlignment) {
        drawAlignedString(s, lineYPos, color, horizontalAlignment);
    }
    private void drawAlignedString(String s, int y, int color, Alignment horizontalAlignment) {
        int bookX = lineXPos + xSize / 2 - 18;
        switch (horizontalAlignment) {
            case LEFT:
                drawString(s, lineXPos, y, color);
                break;
            case CENTER:
                drawString(s, bookX - w(s) / 2, y, color);
                break;
            case RIGHT:
                drawString(s, lineXPos + xSize - 18 - w(s) - 18, y, color);
                break;
        }
    }

//    private void drawHeaderString(String s, int size) {
//        drawHeaderString(s, 0, size);
//    }
//    private void drawHeaderString(String s, int color, int size) {
//        drawHeaderString(s, lineXPos, lineYPos, color, size);
//    }
//    private void drawHeaderString(String s, int x, int y, int color, int size) {
//        switch (size) {
//            case 1:
//                GL11.glPushMatrix();
//                GL11.glScalef(3f, 3f, 3f);
//                drawNonTextString(s, x / 3, y / 3, color);
//                line += 3;
//                lineYPos += fontHeight * 3;
//                GL11.glPopMatrix();
//                break;
//            case 2:
//                GL11.glPushMatrix();
//                GL11.glScalef(2f, 2f, 2f);
//                drawNonTextString(s, (int) (x / 2), (int) (y / 2), color);
//                GL11.glPopMatrix();
//                line += 2;
//                lineYPos += fontHeight * 2;
//                break;
//            case 3:
//                drawString(s, x, y, color);
//        }
//    }

    private void drawNonTextString(String s, int x, int y) {
        drawNonTextString(s, x, y, 0);
    }
    private void drawNonTextString(String s, int x, int y, int color) {
        if (s == null || s.isEmpty() || s.equals("<e>")) return;
        fontRenderer.drawString(s, x, y, color);
    }

    private void drawNonTextAlignedString(String s, Alignment position) {
        drawNonTextAlignedString(s, 0, position);
    }
    private void drawNonTextAlignedString(String s, int color, Alignment position) {
        drawNonTextAlignedString(s, lineXPos + xSize / 2 - 18, lineYPos, color, position);
    }
    private void drawNonTextAlignedString(String s, int x, int y, int color, Alignment position) {
        if (s == null || s.isEmpty()) return;
        switch (position) {
            case LEFT:
                drawNonTextString(s, x, y, color);
                break;
            case LEFT_WITH_TAB:
                drawNonTextString("    " + s, x, y, color);
                break;
            case CENTER:
                drawNonTextString(s, x - w(s) / 2, y, color);
                break;
            case RIGHT:
                drawNonTextString(s, x + xSize - w(s), y, color);
                break;
        }
    }

    private void drawEmptyString(int times) {
        line += (short) times;
        lineYPos += fontHeight * times;
        checkLines();
    }
    private void drawEmptyString() {
        line++;
        lineYPos += fontHeight;
        checkLines();
    }
    private void drawLinkString(String s, int mouseX, int mouseY, short openPage, short from) {
        drawLinkString(s, mouseX, mouseY, openPage, from, 5636095);
    }
    private void drawLinkString(String s, int mouseX, int mouseY, short openPage, short from, int hoveredColor) {
        drawLinkString(s, mouseX, mouseY, openPage, from, 0, hoveredColor);
    }
    private void drawLinkString(String s, int mouseX, int mouseY, short openPage, short from, int color, int hoveredColor) {
        if (pageEnded) return;
        int width = w(s);
        int height = fontHeight;

        Link link = new Link(s, lineXPos, lineYPos, openPage, width, height, from);
        if (!links.contains(link)) links.add(link);

        boolean hovered = isMouseOver(mouseX, mouseY, lineXPos, lineYPos, width, height);
        drawString(s, hovered ? hoveredColor : color);
    }

    private void drawCraftingRecipe(ItemStack[] stacks, ItemStack output, int x, int y) {
        if (stacks.length == 4) {
            drawImage(guideBookPage1, x, y, 163, 197, 84, 46);
            drawItemStack(stacks[0], x + 6, y + 6, false);
            drawItemStack(stacks[1], x + 24, y + 6, false);
            drawItemStack(stacks[2], x + 6, y + 24, false);
            drawItemStack(stacks[3], x + 24, y + 24, false);
            drawItemStack(output, x + 62, y + 16, false);
            drawEmptyString(5);
        } else if (stacks.length == 9) {
            drawImage(guideBookPage1, x, y, 146, 0, 80, 64);
            drawImage(guideBookPage1, x + 80, y, 146, 64, 45, 32);
            drawImage(guideBookPage1, x + 80, y + 32, 191, 64, 45, 32);
            drawItemStack(stacks[0], x + 6, y + 6, false);
            drawItemStack(stacks[1], x + 24, y + 6, false);
            drawItemStack(stacks[2], x + 42, y + 6, false);
            drawItemStack(stacks[3], x + 6, y + 24, false);
            drawItemStack(stacks[4], x + 24, y + 24, false);
            drawItemStack(stacks[5], x + 42, y + 24, false);
            drawItemStack(stacks[6], x + 6, y + 42, false);
            drawItemStack(stacks[7], x + 24, y + 42, false);
            drawItemStack(stacks[8], x + 42, y + 42, false);
            drawItemStack(output, x + 99, y + 24, false);
            drawEmptyString(7);
        } else
            throw new IllegalArgumentException("stack.length must be 4 or 9. If you need empty slot write 'null'");
    }
    private void drawSmeltingRecipe(ItemStack input, ItemStack output, int x, int y) {
        drawImage(guideBookPage1, x, y, 63, 186, 92, 64);
        drawItemStack(s(Item.coal), x + 6, y + 42, false);
        drawItemStack(input, x + 6, y + 6, false);
        drawItemStack(output, x + 66, y + 24, false);
        drawEmptyString(7);
    }
    private void drawExtractingRecipe(ItemStack input, ItemStack output, int x, int y) {
        drawImage(guideBookPage1, x, y, 147, 118, 107, 64);
        drawItemStack(s(mod_CraftableCreatures.bluestone), x + 6, y + 42, false);
        drawItemStack(s(mod_CraftableCreatures.soulElement, 1, 0), x + 43, y + 6, false);
        drawItemStack(input, x + 6, y + 6, false);
        drawItemStack(output, x + 81, y + 24, false);
        drawEmptyString(7);
    }
    private void drawCombiningRecipe(ItemStack firstInput, ItemStack secondInput, ItemStack output, int x, int y) {
        drawImage(guideBookPage2, x, y, 0, 182, 90, 72);
        drawItemStack(firstInput, x + 6, y + 6, false);
        drawItemStack(secondInput, x + 68, y + 6, false);
        drawItemStack(output, x + 37, y + 46, false);
    }

    private void drawItemStack(ItemStack stackToDraw, int x, int y) {
        drawItemStack(stackToDraw, x, y, true);
    }
    private void drawItemStack(ItemStack stackToDraw, int x, int y, boolean drawSlot) {
        stacks.add(new DrawingStack(stackToDraw, x, y, currentPage, drawSlot));
    }

    private void drawIllustration(short id, int scaledImage, String comment, int u, int v) {
        drawIllustration(id, guideBookIllustrationSheet1, scaledImage, comment, u, v);
    }
    private void drawIllustration(short id, int rl, int scaledImage, String comment, int u, int v) {
        drawIllustration(id, rl, scaledImage, comment, lineXPos, lineYPos, u, v, maxWidthPerLine, fontHeight * 8);
    }
    private void drawIllustration(short id, int rl, int scaledImage, String comment, int x, int y, int u, int v, int width, int height) {
//        if (pageEnded) return;
//        if ((line + height / fontHeight) > maxLinesPerPage) return;
        illustrations.put(id, new Illustration(rl, scaledImage, comment, id, x, y, currentPage, u, v, width, height));

        drawImage(rl, x, y, u, v, width, height);

        for (int i = 0; i < height / fontHeight; i++)
            plusLine();
    }

    private void drawImage(int rl, int x, int y, int u, int v, int width, int height) {
        GL11.glColor4f(1f, 1f, 1f, 1f);
        mc.renderEngine.bindTexture(rl);
        drawTexturedModalRect(x, y, u, v, width, height);
    }

    private void checkLines() {
        int line = leftPage ? this.line : this.line / 2 - 1;

        if (line > maxLinesPerPage) {
            if (leftPage) {
                lineYPos = (height - ySize) / 2 + 10;
                lineXPos += xSize;
                leftPage = false;
            } else {
                lineYPos = (height - ySize) / 2 + 10;
                lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
            }
        }
    }

    private String f(String key) {
        String output = TranslateUtils.translate(key);
        output = output.replace("\\n", "\n").replace("<empty>", "");
        return output;
    }
    private String f(String key, Object[] replacements) {
        String output = TranslateUtils.translateFormatted(key, replacements);
        output = output.replace("\\n", "\n").replace("<empty>", "");
        return output;
    }
    private int w(String s) {
        return fontRenderer.getStringWidth(s);
    }
    private int r(String path) {
        return mc.renderEngine.getTexture("/" + path);
    }

    private ItemStack s(Block block) {
        return new ItemStack(block);
    }
    private ItemStack s(Block block, int amount) {
        return new ItemStack(block, amount);
    }
    private ItemStack s(Block block, int amount, int meta) {
        return new ItemStack(block, amount, meta);
    }
    private ItemStack s(Item item) {
        return new ItemStack(item);
    }
    private ItemStack s(Item item, int amount) {
        return new ItemStack(item, amount);
    }
    private ItemStack s(Item item, int amount, int meta) {
        return new ItemStack(item, amount, meta);
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    private enum Alignment {
        LEFT,
        LEFT_WITH_TAB,
        CENTER,
        RIGHT
    }

    private static class Link {
        String text;
        int x, y;
        short page;
        int width, height;
        short fromPage;

        public Link(String text, int x, int y, short page, int width, int height, short fromPage) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.page = page;
            this.width = width;
            this.height = height;
            this.fromPage = fromPage;
        }

        public boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
        }

        public boolean canOpen(short page) {
            return page == fromPage;
        }
    }

    private static class Illustration {
        int sheet, scaled;
        String comment;
        short id;
        int x, y;
        int page;
        int width, height;
        int u, v;

        public Illustration(int sheet, int scaled, String comment, short id, int x, int y, short page, int u, int v, int width, int height) {
            this.sheet = sheet;
            this.scaled = scaled;
            this.comment = comment;
            this.id = id;
            this.x = x;
            this.y = y;
            this.page = page;
            this.width = width;
            this.height = height;
            this.u = u;
            this.v = v;
        }

        public boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
        }

        public boolean canOpen(short page, short illustration) {
            return page == this.page && illustration == 0;
        }
    }

    private static class DrawingStack {
        ItemStack holdingStack;
        int x, y;
        short fromPage;
        boolean drawSlot;

        public DrawingStack(ItemStack holdingStack, int x, int y, short fromPage, boolean drawSlot) {
            this.holdingStack = holdingStack;
            this.x = x;
            this.y = y;
            this.fromPage = fromPage;
            this.drawSlot = drawSlot;
        }

        public boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16;
        }

        public boolean shouldDraw(short page) {
            return page == this.fromPage;
        }
    }

    private static class ChangePageButton extends GuiButton {
        private final boolean reverse;

        public ChangePageButton(int id, int x, int y, boolean shouldReverse) {
            super(id, x, y, 23, 13, "");
            reverse = shouldReverse;
        }

        public void drawButton(Minecraft mc, int x, int y) {
            if (drawButton) {
                boolean hovered = isMouseOver(x, y);
                GL11.glColor4f(1f, 1f,1f, 1f);
                mc.renderEngine.bindTexture(guideBookPage1);

                int u = 2;
                int v = 192;

                if (hovered) u += 23;
                if (!reverse) v += 13;

                drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
            }
        }

        private boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= xPosition && mouseX < xPosition + width && mouseY >= yPosition && mouseY < yPosition + height;
        }
    }

    private static class CloseButton extends GuiButton {
        public CloseButton(int id, int x, int y) {
            super(id, x, y, 13, 13, "");
            enabled = false;
            drawButton = false;
        }

        public void drawButton(Minecraft mc, int x, int y) {
            if (drawButton) {
                boolean hovered = isMouseOver(x, y);
                GL11.glColor4f(1f, 1f, 1f, 1f);
                mc.renderEngine.bindTexture(guideBookPage1);

                int u = 5;
                int v = 220;

                if (hovered) u += 24;

                drawTexturedModalRect(xPosition, yPosition, u, v, width, height);
            }
        }

        public void setZLevel(float newValue) {
            zLevel = newValue;
        }

        private boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= xPosition && mouseX < xPosition + width && mouseY >= yPosition && mouseY < yPosition + height;
        }
    }

    /* Copied from 1.7.10 - GuiScreen with small corrections */
    private void drawHoveringText(List p_146283_1_, int p_146283_2_, int p_146283_3_, FontRenderer font)
    {
        if (!p_146283_1_.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = p_146283_1_.iterator();

            while (iterator.hasNext())
            {
                String s = (String)iterator.next();
                int l = font.getStringWidth(s);

                if (l > k)
                {
                    k = l;
                }
            }

            int j2 = p_146283_2_ + 12;
            int k2 = p_146283_3_ - 12;
            int i1 = 8;

            if (p_146283_1_.size() > 1)
            {
                i1 += 2 + (p_146283_1_.size() - 1) * 10;
            }

            if (j2 + k > this.width)
            {
                j2 -= 28 + k;
            }

            if (k2 + i1 + 6 > this.height)
            {
                k2 = this.height - i1 - 6;
            }

            this.zLevel = 300.0F;
            itemRender.zLevel = 300.0F;
            int j1 = -267386864;
            this.drawGradientRect(j2 - 3, k2 - 4, j2 + k + 3, k2 - 3, j1, j1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 3, j2 + k + 3, k2 + i1 + 4, j1, j1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 - 4, k2 - 3, j2 - 3, k2 + i1 + 3, j1, j1);
            this.drawGradientRect(j2 + k + 3, k2 - 3, j2 + k + 4, k2 + i1 + 3, j1, j1);
            int k1 = 1347420415;
            int l1 = (k1 & 16711422) >> 1 | k1 & -16777216;
            this.drawGradientRect(j2 - 3, k2 - 3 + 1, j2 - 3 + 1, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 + k + 2, k2 - 3 + 1, j2 + k + 3, k2 + i1 + 3 - 1, k1, l1);
            this.drawGradientRect(j2 - 3, k2 - 3, j2 + k + 3, k2 - 3 + 1, k1, k1);
            this.drawGradientRect(j2 - 3, k2 + i1 + 2, j2 + k + 3, k2 + i1 + 3, l1, l1);

            for (int i2 = 0; i2 < p_146283_1_.size(); ++i2)
            {
                String s1 = (String)p_146283_1_.get(i2);
                font.drawStringWithShadow(s1, j2, k2, -1);

                if (i2 == 0)
                {
                    k2 += 2;
                }

                k2 += 10;
            }

            this.zLevel = 0.0F;
            itemRender.zLevel = 0.0F;
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            RenderHelper.enableStandardItemLighting();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        }
    }

    private void renderToolTip(ItemStack p_146285_1_, int p_146285_2_, int p_146285_3_)
    {
        List list = new ArrayList();
        String string = p_146285_1_.getItem().getItemDisplayName(p_146285_1_);
        list.add("\u00A7f" + string);
        drawHoveringText(list, p_146285_2_, p_146285_3_, fontRenderer);
    }

    /* Copied from 1.7.10 - RenderHelper */
    public static void enableGUIStandardItemLighting()
    {
        GL11.glPushMatrix();
        GL11.glRotatef(-30.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(165.0F, 1.0F, 0.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
    }

    /* Backported from FontRenderer - 1.7.10 */

    public List listFormattedStringToWidth(String p_78271_1_, int p_78271_2_) {
        return Arrays.asList(wrapFormattedStringToWidth(p_78271_1_, p_78271_2_).split("\n"));
    }

    String wrapFormattedStringToWidth(String p_78280_1_, int p_78280_2_) {
        int j = sizeStringToWidth(p_78280_1_, p_78280_2_);

        if (p_78280_1_.length() <= j) return p_78280_1_;
        else {
            String s1 = p_78280_1_.substring(0, j);
            char c0 = p_78280_1_.charAt(j);
            boolean flag = c0 == 32 || c0 == 10;
            String s2 = getFormatFromString(s1) + p_78280_1_.substring(j + (flag ? 1 : 0));
            return s1 + "\n" + wrapFormattedStringToWidth(s2, p_78280_2_);
        }
    }

    private int sizeStringToWidth(String p_78259_1_, int p_78259_2_) {
        int j = p_78259_1_.length();
        int k = 0;
        int l = 0;
        int i1 = -1;

        for (boolean flag = false; l < j; ++l) {
            char c0 = p_78259_1_.charAt(l);

            switch (c0) {
                case 10:
                    --l;
                    break;
                case 167:
                    if (l < j - 1) {
                        ++l;
                        char c1 = p_78259_1_.charAt(l);

                        if (c1 != 108 && c1 != 76) {
                            if (c1 == 114 || c1 == 82 || isFormatColor(c1)) flag = false;
                        } else flag = true;
                    }
                    break;
                case 32:
                    i1 = l;
                default:
                    int charW = (c0 == 167) ? -1 : (c0 == 32 ? 4 : fontRenderer.getStringWidth(Character.toString(c0)));
                    k += charW;
                    if (flag) ++k;
            }

            if (c0 == 10) {
                ++l;
                i1 = l;
                break;
            }

            if (k > p_78259_2_) break;
        }

        return l != j && i1 != -1 && i1 < l ? i1 : l;
    }

    private static boolean isFormatColor(char p_78272_0_) {
        return p_78272_0_ >= 48 && p_78272_0_ <= 57 || p_78272_0_ >= 97 && p_78272_0_ <= 102 || p_78272_0_ >= 65 && p_78272_0_ <= 70;
    }

    private static boolean isFormatSpecial(char p_78270_0_) {
        return p_78270_0_ >= 107 && p_78270_0_ <= 111 || p_78270_0_ >= 75 && p_78270_0_ <= 79 || p_78270_0_ == 114 || p_78270_0_ == 82;
    }

    private static String getFormatFromString(String p_78282_0_) {
        String s1 = "";
        int i = -1;
        int j = p_78282_0_.length();

        while ((i = p_78282_0_.indexOf(167, i + 1)) != -1) {
            if (i < j - 1) {
                char c0 = p_78282_0_.charAt(i + 1);

                if (isFormatColor(c0)) s1 = "\u00a7" + c0;
                else if (isFormatSpecial(c0)) s1 = s1 + "\u00a7" + c0;
            }
        }

        return s1;
    }
}