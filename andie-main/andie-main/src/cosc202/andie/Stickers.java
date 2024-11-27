package cosc202.andie;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <p>
 * Stickers
 * </p>
 * 
 * <p>
 * This class provides a sticker implementation to be used on the main image.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Ivan Chevtchenko
 * @version 1.0
 */

public class Stickers extends JFrame { 

    protected static boolean stickerMode;
    protected static boolean outOfCanvas;
    protected static JLabel stickerSelection;
    protected static Icon currentIcon;
    
    public Stickers() { 
        // Enables sticker mode to avoid unintented interaction with other features.
        stickerMode = true;
        outOfCanvas = false;

        File directoryPath = new File("src/cosc202/andie/resources/stickers");
        String contents[] = directoryPath.list();

        int stickerCount = 0;
        for(int i=0; i<contents.length; i++) {
            stickerCount++;
        }

        JFrame frame = new JFrame(Andie.bundle.getString("stickers_title"));

        //positions jframe in center of screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getSize().width) / 2;
        int y = (screenSize.height - frame.getSize().height) / 2;
        frame.setLocation(x, y);
        
        try {
            Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton attribution = new JButton("All emojis designed by OpenMoji");
        attribution.setContentAreaFilled(false);
        attribution.setBorderPainted(false);
        attribution.setFocusPainted(false);
        attribution.setOpaque(true);
        attribution.setToolTipText("the open-source emoji and icon project. License: CC BY-SA 4.0");
        attribution.setFont(new Font("", Font.BOLD, 8));
        frame.add(attribution, BorderLayout.SOUTH);

        JButton but[] = new JButton[stickerCount];

        frame.addWindowListener(new WindowAdapter() {    
            @Override
            public void windowClosing(WindowEvent e) {
                for (MouseListener m : Andie.imagePanel.getMouseListeners()) {
                    Andie.imagePanel.removeMouseListener(m);
                }
                for (MouseMotionListener m : Andie.imagePanel.getMouseMotionListeners()) {
                    Andie.imagePanel.removeMouseMotionListener(m);
                }
                for (Component c : Andie.imagePanel.getComponents()) {
                    Andie.imagePanel.remove(c);
                }
                Andie.imagePanel.repaint();

                stickerMode = false;

                Andie.imagePanel.addMouseListener(ImagePanel.mouseHandler);
                Andie.imagePanel.addMouseMotionListener(ImagePanel.mouseHandler);
                Andie.imagePanel.setSelection(new Rectangle());    
            }
        });
        frame.setSize(300,400);

        GridLayout layout = new GridLayout(23, 3);
        layout.setHgap(10);
        layout.setVgap(10);

        JPanel panel = new JPanel();

        panel.setLayout(layout);
        frame.add(new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));

        for(int i = 0; i < stickerCount; i++)
        {
            try {
                BufferedImage stickerImage = ImageIO.read(new File("src/cosc202/andie/resources/stickers/" + contents[i]));
                ImageIcon icon = new ImageIcon(stickerImage);
                but[i] = new JButton(icon);
                but[i].setPreferredSize(new Dimension(64, 64));
                panel.add(but[i]);
                but[i].setContentAreaFilled(false);  
                but[i].setOpaque(false);
                but[i].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {

                        for (Component c : Andie.imagePanel.getComponents()) {
                            Andie.imagePanel.remove(c);
                        }

                        Andie.imagePanel.repaint();

                        stickerSelection = new JLabel();
                        Andie.imagePanel.add(stickerSelection);

                        Andie.imagePanel.addMouseMotionListener(new MouseAdapter() {
                            @Override
                            public void mouseMoved(MouseEvent e) {
                                if (!outOfCanvas) {
                                    stickerSelection.setIcon(icon);
                                    stickerSelection.setBounds(e.getX() - (icon.getIconWidth() / 2), e.getY() - (icon.getIconHeight() / 2), 
                                    icon.getIconWidth(), icon.getIconHeight());
                                    stickerSelection.repaint();
                                }
                            }
                         });

                        for (MouseListener m : Andie.imagePanel.getMouseListeners()) {
                            Andie.imagePanel.removeMouseListener(m);
                        }

                        Andie.imagePanel.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                StickerAction stickerAction = new StickerAction("", null, "", null);
                                stickerAction.setStickerImage(stickerImage);
                                stickerAction.setStickerPosition(new Point((int)(e.getX()/Andie.imagePanel.getScale()), (int)(e.getY()/Andie.imagePanel.getScale())));
                                stickerAction.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                                Andie.imagePanel.repaint();
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                outOfCanvas = true;
                                stickerSelection.setIcon(null);
                                stickerSelection.repaint();
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                outOfCanvas = false;
                                stickerSelection.setIcon(icon);
                                stickerSelection.repaint();
                            }

                        });
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        frame.setVisible(true);  
    }


    /**
     * <p>
     * Sub class that implements the {@link ImageOperation} class so StickerAction can apply 
     * to the buffered image.
     * </p>
     */
    public class StickerOperation implements ImageOperation, java.io.Serializable {

        private Point stickerPosition;
        private transient BufferedImage stickerImage;

        public StickerOperation(BufferedImage stickerImage, Point stickerPosition) {
            this.stickerImage = stickerImage;
            this.stickerPosition = stickerPosition;
        }

        @Override
        public BufferedImage apply(BufferedImage input) {
            Graphics2D g = input.createGraphics();
            g.drawImage(stickerImage, stickerPosition.x - (stickerImage.getWidth() / 2), stickerPosition.y - (stickerImage.getHeight() / 2), null);
            g.dispose();
            return input;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            ImageIO.write(stickerImage, "png", out);
        }
    
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            stickerImage = ImageIO.read(in);
        }

    }

    /**
     * <p>
     * Action to apply sticker to buffered image.
     * </p>
     */
    public class StickerAction extends ImageAction {

        private Point stickerPosition;
        private BufferedImage stickerImage;

        /**
         * <p>
         * Applies sticker to the buffered image.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */

        StickerAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void setStickerImage(BufferedImage stickerImage) {
            this.stickerImage = stickerImage;
        }

        public void setStickerPosition(Point stickerPosition) {
            this.stickerPosition = stickerPosition;
        }
        
        public void actionPerformed(ActionEvent e) {
            if (ImagePanel.image.hasImage()) {
                target.getImage().apply(new StickerOperation(stickerImage, stickerPosition));
                target.repaint();
                target.getParent().revalidate();
            }
        }
    }
}