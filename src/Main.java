import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static void main(final String[] args) {

        try {
            var font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/IBMPlexSansJP-Text.ttf"));
            font = font.deriveFont(50f);

            final var originalImg = ImageIO.read(Main.class.getResourceAsStream("/background.png"));

            final var width = 1024;
            final var height = (int) (((double) width / (double) originalImg.getWidth()) * originalImg.getHeight());
            final var img = new BufferedImage(width, height, originalImg.getType());
            final var g2dResized = img.createGraphics();
            g2dResized.drawImage(originalImg, 0, 0, width, height, null);
            g2dResized.dispose();

            final var g2d = img.createGraphics();
            g2d.setFont(font);
            g2d.setColor(Color.BLACK);

            final var originalText = "こんにちは、世界！あいうえおかきくけこさしすせそたちつてと";
            final var text = Main.insertNewlines(originalText, 12); // 12 characters per line
            final var fm = g2d.getFontMetrics();
            final var lineHeight = fm.getHeight();
            final var x = img.getWidth() / 2;

            // Calculate the number of lines and adjust the initial y position
            final var numLines = text.split("\n").length;
            var y = img.getHeight() / 2 - (numLines * lineHeight) / 2 + fm.getAscent();

            for (final String line : text.split("\n")) {
                final var textWidth = fm.stringWidth(line);
                g2d.drawString(line, x - textWidth / 2, y);
                y += lineHeight;
            }

            g2d.dispose();

            ImageIO.write(img, "jpg", new File("output.jpg"));

        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
    }


    public static String insertNewlines(final String original, final int charsPerLine) {

        final var sb = new StringBuilder();
        var start = 0;
        while (start < original.length()) {
            final var end = Math.min(start + charsPerLine, original.length());
            sb.append(original, start, end);
            if (end < original.length()) {
                sb.append("\n");
            }
            start = end;
        }
        return sb.toString();
    }
}
