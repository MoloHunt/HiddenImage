import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Marcello on 15/07/2015.
 */
public class Smoother {

    private BufferedImage image, altered;

    public Smoother(BufferedImage source) {
        image = source;
        altered = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    }

    public void smooth(){
        int choice = 0;
        while(choice <= 0 || choice > 10){
            try{
                String inp = JOptionPane.showInputDialog("Number of iterations between 1 and 10:");
                choice = Integer.parseInt(inp);
            }catch (Exception e){
                choice = 0;
            }
        }
        for (int i = 0; i < 10; i++) {
            smoothie();
            image = getAltered();
        }
    }

    public void smoothie(){
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                int red = 0, green = 0, blue = 0;
                for (int neighbourX = x - 1; neighbourX <= x + 1; neighbourX ++) {
                    for (int neighbourY = y - 1; neighbourY <= y + 1; neighbourY ++) {
                        if (isInRange(neighbourX, neighbourY)) {
                            if (neighbourX != x || neighbourY != y) {
                                Color pixel = new Color(image.getRGB(neighbourX, neighbourY));
                                red += pixel.getRed();
                                green += pixel.getGreen();
                                blue += pixel.getBlue();
                            }
                        }else{
                            red += 255;
                            green += 255;
                            blue += 255;
                        }
                    }
                }
                altered.setRGB(x, y, new Color(red / 9, green / 9, blue / 9).getRGB());
            }
        }
    }

    private boolean isInRange(int x, int y) {
        return (x < image.getWidth() && x > 0 && y < image.getHeight() && y > 0);
    }

    public BufferedImage getAltered(){
        return altered;
    }
}
