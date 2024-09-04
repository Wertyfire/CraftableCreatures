/**
 * File created on 17:19 31.08.2024 by Wertyfire
 */

package ru.wertyfiregames.craftablecreatures.inventory.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;
import ru.wertyfiregames.craftablecreatures.CraftableCreatures;

import java.util.ArrayList;
import java.util.List;

public class GuiScreenGuideBook extends GuiScreen {

    private static final ResourceLocation
            guideBookPage1 = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/guide_book_page1.png"),
            guideBookPage2 = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/guide_book_page2.png"),
            guideBookBack = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/guide_book_back.png"),
            guideBookCover = new ResourceLocation(CraftableCreatures.getModId(), "textures/gui/guide_book_cover.png");

    private final GuiButton buttonBack = new ChangePageButton(0, 100, 0, 19, 13, false);
    private final GuiButton buttonNext = new ChangePageButton(1, 130, 0, 19, 13, true);

    private final List<Link> links = new ArrayList<>();

    private static final int xSize = 146, ySize = 180;

    private static final int maxSymbolsPerLine = 27;
    private static final int maxLinesPerPage = 16;

    private static boolean wasUnicode;

    private boolean leftPage = true;
    private boolean pageEnded = false;
    private short line = 1;
    private int lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
    private int lineYPos = (height - ySize) / 2 + 10;

    private final short totalPages = 6;
    private short currentPage = 1;

    public GuiScreenGuideBook() {
        super();
        wasUnicode = Minecraft.getMinecraft().fontRenderer.getUnicodeFlag();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        buttonList.clear();
        checkButtons();
        buttonList.add(buttonBack);
        buttonList.add(buttonNext);
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        fontRendererObj.setUnicodeFlag(true);
        drawDefaultBackground();
        super.drawScreen(x, y, partialTicks);

        drawPage(x, y);

        newFrame();
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            minusPage();
            checkButtons();
        } else if (button.id == 1) {
            plusPage();
            checkButtons();
        }
    }

    @Override
    public void onGuiClosed() {
        fontRendererObj.setUnicodeFlag(wasUnicode);
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
        }
    }

    private void newFrame() {
        line = 1;
        lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
        lineYPos = (height - ySize) / 2 + 10;
        leftPage = true;
        pageEnded = false;
        fontRendererObj.setUnicodeFlag(wasUnicode);
    }

    private void drawPage(int x, int y) {
        if (currentPage == 1) {
            drawBookTexture(2);
            drawTitle();
        } else if (currentPage == totalPages) {
            drawBookTexture(0);
        } else if (currentPage > totalPages) {
            currentPage = totalPages;
        } else if (currentPage < 1) {
            currentPage = 1;
        } else {
            drawBookTexture(1);
            drawPageText(x, y);
        }
    }

    private void minusPage() {
        if (currentPage > 1) currentPage--;
    }
    private void plusPage() {
        if (currentPage < totalPages) currentPage++;
    }

    private void checkButtons() {
        if (currentPage == 1) {
            buttonBack.enabled = false;
            buttonBack.visible = false;
            if (!buttonNext.enabled) buttonNext.enabled = true;
            if (!buttonNext.visible) buttonNext.visible = true;
        } else if (currentPage == totalPages) {
            buttonNext.enabled = false;
            buttonNext.visible = false;
            if (!buttonBack.enabled) buttonBack.enabled = true;
            if (!buttonBack.visible) buttonBack.visible = true;
        } else {
            if (!buttonBack.enabled) buttonBack.enabled = true;
            if (!buttonBack.visible) buttonBack.visible = true;
            if (!buttonNext.enabled) buttonNext.enabled = true;
            if (!buttonNext.visible) buttonNext.visible = true;
        }
    }

    // part: 0 - back, 1 - default (middle), 2 - cover
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
            mc.getTextureManager().bindTexture(guideBookPage2);
            drawTexturedModalRect(x, y, u, v, xSize, ySize);

            mc.getTextureManager().bindTexture(guideBookPage1);
            drawTexturedModalRect(xLeft, y, u, v, xSize, ySize);
        } else if (part == 0) {
            mc.getTextureManager().bindTexture(guideBookBack);
            drawTexturedModalRect(xLeft, y, u, v, xSize, ySize);
        } else {
            mc.getTextureManager().bindTexture(guideBookCover);
            drawTexturedModalRect(x, y, u, v, xSize, ySize);
        }
    }

    private void drawTitle() {
        String s = I18n.format("craftableCreatures.guide.cover.title");
        drawNonTextString(s, ((width - xSize) / 2 + xSize) - fontRendererObj.getStringWidth(s) / 2, (height - ySize) / 2 + 45, 10339058);
    }

    private void drawPageText(int x, int y) {
        drawPageNumber();

        if (currentPage == 2) {
            drawString(I18n.format("item.guideBook.name"));
            drawEmptyString(5);
            drawLinkString("Test Link #1", x, y, (short) 3, currentPage);
            drawString("Строка");
            drawEmptyString();
            drawString("Ещё строка");
        }
    }

    private void drawPageNumber() {
        drawNonTextString("" + ((currentPage - 1) * 2 - 1), (width - xSize) / 2 + xSize / 2 - 20 - fontRendererObj.getStringWidth("" + ((currentPage - 1) * 2 - 1)), (height - ySize) / 2 + ySize - 20, 0);
        drawNonTextString("" + ((currentPage - 1) * 2), (width - xSize) / 2 + xSize / 2 + 20, (height - ySize) / 2 + ySize - 20, 0);
    }

    private void drawString(String s) {
        drawString(s, 0);
    }
    private void drawString(String s, int color) {
        drawString(s, lineXPos, lineYPos, color);
    }
    private void drawString(String s, int x, int y, int color) {
        if (StringUtils.isNullOrEmpty(s)) {
            drawEmptyString();
            return;
        }
        if (pageEnded) return;
        fontRendererObj.drawString(s, x, y, color);
        line++;
        lineYPos += fontRendererObj.FONT_HEIGHT;
        checkLines();
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
//                lineYPos += fontRendererObj.FONT_HEIGHT * 3;
//                GL11.glPopMatrix();
//                break;
//            case 2:
//                GL11.glPushMatrix();
//                GL11.glScalef(2f, 2f, 2f);
//                drawNonTextString(s, (int) (x / 2), (int) (y / 2), color);
//                GL11.glPopMatrix();
//                line += 2;
//                lineYPos += fontRendererObj.FONT_HEIGHT * 2;
//                break;
//            case 3:
//                drawString(s, x, y, color);
//        }
//    }

    private void drawNonTextString(String s, int x, int y, int color) {
        if (StringUtils.isNullOrEmpty(s)) return;
        fontRendererObj.drawString(s, x, y, color);
    }

    private void drawEmptyString(int times) {
        line += (short) times;
        lineYPos += fontRendererObj.FONT_HEIGHT * times;
        checkLines();
    }
    private void drawEmptyString() {
        line++;
        lineYPos += fontRendererObj.FONT_HEIGHT;
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
        int width = fontRendererObj.getStringWidth(s);
        int height = fontRendererObj.FONT_HEIGHT;

        Link link = new Link(s, lineXPos, lineYPos, openPage, width, height, from);
        if (!links.contains(link)) links.add(link);

        boolean hovered = isMouseOver(mouseX, mouseY, lineXPos, lineYPos, width, height);
        drawString(s, hovered ? hoveredColor : color);
    }

    private void drawTexture(int u, int v) {
        drawTexture(guideBookPage1, u, v);
    }
    private void drawTexture(ResourceLocation rl, int u, int v) {
        if (pageEnded) return;
        mc.getTextureManager().bindTexture(rl);
        drawTexturedModalRect(lineXPos, lineYPos, u, v, 100, 80);
    }

    private void checkLines() {
        int line = leftPage ? this.line : this.line / 2 - 1;

        if (line > maxLinesPerPage) {
            if (leftPage) {
                lineYPos = (height - ySize) / 2 + 10;
                lineXPos += xSize;
                leftPage = false;
                pageEnded = true;
            } else {
                lineYPos = (height - ySize) / 2 + 10;
                lineXPos = (width - xSize) / 2 - xSize / 2 + 18;
                leftPage = true;
                pageEnded = false;
            }
        }
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    static class Link {
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

    static class ChangePageButton extends GuiButton {
        private final boolean reverse;

        public ChangePageButton(int id, int x, int y, int width, int height, boolean shouldReverse) {
            super(id, x, y, width, height, "");
            reverse = shouldReverse;
        }

        public void drawButton(Minecraft mc, int x, int y) {
            if (visible) {
                boolean hovered = isMouseOver(x, y);
                GL11.glColor4f(1f, 1f,1f, 1f);
                mc.getTextureManager().bindTexture(guideBookPage1);

                int u = 2;
                int v = 192;

                if (hovered) u += 23;
                if (!reverse) v += 13;

                drawTexturedModalRect(xPosition, yPosition, u, v, 23, 13);
            }
        }

        private boolean isMouseOver(int mouseX, int mouseY) {
            return mouseX >= xPosition && mouseX < xPosition + width && mouseY >= yPosition && mouseY < yPosition + height;
        }
    }
}