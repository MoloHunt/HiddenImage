import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Marcello on 15/07/2015.
 */
public class Window extends JFrame {

    private BufferedImage original, altered;

    private Encrypter eC;
    private Smoother sC;
    private ImageViewer iV;

    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        super("Hidden Image");

        add(makePanel());

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);
        setVisible(true);
    }

    private JPanel makePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton loadButton = new JButton(" Load  ");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                original = loadImage();
                iV = new ImageViewer(original);
            }
        });

        JButton saveButton = new JButton(" Save  ");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        final JTextField keyField = new JTextField(6);

        JButton encryptButton = new JButton("Encrypt");
        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encryptImage(keyField.getText());
            }
        });

        JButton decryptButton = new JButton("Decrypt");
        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decryptImage(keyField.getText());
            }
        });

        JButton smoothButton = new JButton("Smooth");
        smoothButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                smoothImage();
            }
        });

        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(new JLabel("Key Code"));
        panel.add(keyField);
        panel.add(encryptButton);
        panel.add(decryptButton);
        panel.add(smoothButton);

        return panel;
    }

    private void smoothImage() {
        if(original == null){
            JOptionPane.showMessageDialog(this, "You need to load an image", "WARNING", JOptionPane.ERROR_MESSAGE);
        }else{
            sC = new Smoother(original);
            sC.smooth();
            altered = sC.getAltered();
            iV.setVisible(false);
            iV = new ImageViewer(altered);
        }
    }

    private BufferedImage loadImage() {
        String userDir = System.getProperty("user.home");
        JFileChooser fileChoose = new JFileChooser(userDir + "/Desktop");

        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);

        boolean fileChosen = false;
        while (!fileChosen) {
            if (fileChoose.showOpenDialog(this) == 0)
            {
                File selectedFile = fileChoose.getSelectedFile();
                try
                {
                    img = ImageIO.read(selectedFile);
                    fileChosen = true;
                }
                catch (IOException e)
                {
                    fileChosen = false;
                }
            }
        }

        return img;
    }

    private void saveImage() {
        String userDir = System.getProperty("user.home");
        JFileChooser fc = new JFileChooser(userDir + "/Desktop");
        if (fc.showSaveDialog(fc) == 0)
        {
            File file = fc.getSelectedFile();
            try
            {
                ImageIO.write(eC.getAltered(), "PNG", file);
                System.exit(0);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void encryptImage(String text) {
        if(original == null){
            JOptionPane.showMessageDialog(this, "You need to load an image", "WARNING", JOptionPane.ERROR_MESSAGE);
        }else {
            eC = new Encrypter(original, text);
            eC.encrypt();
            altered = eC.getAltered();
            iV.setVisible(false);
            iV = new ImageViewer(altered);
        }
    }

    private void decryptImage(String text) {
        if(original == null){
            JOptionPane.showMessageDialog(this, "You need to load an image", "WARNING", JOptionPane.ERROR_MESSAGE);
        }else {
            eC = new Encrypter(original, text);
            eC.decrypt();
            altered = eC.getAltered();
            iV.setVisible(false);
            iV = new ImageViewer(altered);
        }
    }
}
