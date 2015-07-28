import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Marcello on 15/07/2015.
 */
public class ImageViewer extends JFrame{

    private BufferedImage image;

    public ImageViewer(BufferedImage _image){
        super("Image Viewer");

        image = _image;

        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if(image.getWidth() > image.getHeight()){
                    g.drawImage(image, 0, 0, 800, makeHeight(), null);
                }else{
                    g.drawImage(image, 0, 0, makeWidth(), 600, null);
                }
            }
        };

        add(panel);

        pack();
        setResizable(false);
        setVisible(true);
    }

    private int makeHeight() {
        int width, height;

        width = image.getWidth();
        height = image.getHeight();

        height = (height * 800) / width;

        setSize(new Dimension(800, height));
        return height;
    }

    private int makeWidth() {
        int width, height;

        width = image.getWidth();
        height = image.getHeight();

        width = (width * 600) / height;

        setSize(new Dimension(width, 600));
        return width;
    }
}
