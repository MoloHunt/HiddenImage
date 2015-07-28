import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by Marcello on 15/07/2015.
 */
public class Encrypter {
    
    private BufferedImage image, altered;
    private BufferedImage[][] blocks;
    
    private int slices, iterations;
    private int width, height;

    private Random random;
    
    public Encrypter(BufferedImage _image, String _key){
        random = new Random(_key.hashCode());
        
        slices = (random.nextInt(50) + 50) * 2;
        iterations = random.nextInt(4) + 1;

        image = _image;

        image = cropOut(image);

        blocks = new BufferedImage[slices][slices];

        width = image.getWidth() / slices;
        height = image.getHeight() / slices;

        populateBlocks();
    }

    private BufferedImage cropOut(BufferedImage _image) {
        int cropX = _image.getWidth() % slices;
        int cropY = _image.getHeight() % slices;

        BufferedImage im = _image.getSubimage(0, 0, _image.getWidth() - cropX, _image.getHeight() - cropY);

        return im;
    }

    private void populateBlocks() {
        for (int x = 0; x < slices; x++) {
            for (int y = 0; y < slices; y++) {
                blocks[x][y] = image.getSubimage(x * width, y * height, width, height);
            }
        }
    }

    public void encrypt(){
        for (int i = 0; i < iterations; i++) {
            blocks = forwards(blocks);
        }

        altered = populateImage(blocks);
    }

    private BufferedImage[][] forwards(BufferedImage[][] source){
        BufferedImage[][] temp1 = new BufferedImage[slices][slices];
        BufferedImage[][] temp3 = new BufferedImage[slices][slices];

        for (int x = 0; x < slices; x++) {
            BufferedImage[] temp = new BufferedImage[slices];
            for (int y = 0; y < slices; y++) {
                //Flip every other column
                if(x % 2 == 0){
                    temp[y] = source[x][y];
                }
            }
            for (int y = 0; y < slices; y++) {
                //Flip every other column
                if(x % 2 == 0){
                    temp1[x][y] = temp[temp.length - y - 1];
                }else{
                    temp1[x][y] = source[x][y];
                }
            }
        }

        for (int x = 0; x < slices; x++) {
            for (int y = 0; y < slices; y++) {
                temp3[y][x] = temp1[x][y]; //Flips on the y = x axis
            }
        }

        return temp3;
    }

    public void decrypt(){
        for (int i = 0; i < iterations; i++) {
            blocks = backwards(blocks);
        }

        altered = populateImage(blocks);
    }

    private BufferedImage[][] backwards(BufferedImage[][] source) {
        BufferedImage[][] temp1 = new BufferedImage[slices][slices];
        BufferedImage[][] temp3 = new BufferedImage[slices][slices];

        for (int x = 0; x < slices; x++) {
            for (int y = 0; y < slices; y++) {
                temp1[y][x] = source[x][y]; //Flips on the y = x axis
            }
        }

        for (int x = 0; x < slices; x++) {
            BufferedImage[] temp = new BufferedImage[slices];
            for (int y = 0; y < slices; y++) {
                //Flip every other column
                if(x % 2 == 0){
                    temp[y] = temp1[x][y];
                }
            }
            for (int y = 0; y < slices; y++) {
                //Flip every other column
                if(x % 2 == 0){
                    temp3[x][y] = temp[temp.length - y - 1];
                }else{
                    temp3[x][y] = temp1[x][y];
                }
            }
        }

        return temp3;
    }

    private BufferedImage populateImage(BufferedImage[][] _blocks) {
        BufferedImage temp = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < slices; x++) {
            for (int y = 0; y < slices; y++) {
                for (int x1 = 0; x1 < width; x1++) {
                    for (int y1 = 0; y1 < height; y1++) {
                        temp.setRGB((x * width) + x1, (y * height) + y1, blocks[x][y].getRGB(x1, y1));
                    }
                }
            }
        }

        return temp;
    }



    public BufferedImage getAltered(){
        return altered;
    }
}
