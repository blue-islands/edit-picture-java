import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static void main(final String[] args) {

        try {
            // フォントファイルを読み込む
            var font = Font.createFont(Font.TRUETYPE_FONT, new File("myfont.ttf"));

            // フォントサイズを設定する
            font = font.deriveFont(20f);

            // 画像ファイルを読み込む
            final var originalImg = ImageIO.read(new File("input.jpg"));

            // 画像サイズを調整する
            final var width = 1024;
            final var height = (int) (((double) width / (double) originalImg.getWidth()) * originalImg.getHeight());
            final var img = new BufferedImage(width, height, originalImg.getType());
            final var g2dResized = img.createGraphics();
            g2dResized.drawImage(originalImg, 0, 0, width, height, null);
            g2dResized.dispose();

            // Graphics2D オブジェクトを取得する
            final var g2d = img.createGraphics();

            // フォントを設定する
            g2d.setFont(font);

            // 画像上にテキストを描画する
            final var text = "こんにちは、世界！";
            final var fm = g2d.getFontMetrics();
            final var x = img.getWidth() / 2 - fm.stringWidth(text) / 2;
            final var y = (img.getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.drawString(text, x, y);

            // 変更を適用し、Graphics2D オブジェクトを破棄する
            g2d.dispose();

            // 画像を出力する
            ImageIO.write(img, "jpg", new File("output.jpg"));

        } catch (IOException | FontFormatException ex) {
            ex.printStackTrace();
        }
    }
}
