
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DirectDrawDemo extends JPanel {

    private BufferedImage canvas;

    public DirectDrawDemo(int width, int height) {
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        fillCanvas(Color.WHITE);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }


    public void drawLine(Color c, int x1, int y1, int x2, int y2) {
        // Implement line drawing
    	Graphics g = canvas.getGraphics();
    	g.setColor(c);
    	g.drawLine(x1, y1, x2, y2);
        repaint();
    }

    public void drawPoint(Color c, int x, int y) {
        int color = c.getRGB();
        // Implement rectangle drawing
        canvas.setRGB(x, y, color);
        repaint();
    }

    public void drawOval(Color c, int x1, int y1, int width, int height) {
        // Implement oval drawing
        repaint();
    }



    public static void main(String[] args) {
        int width = 640;
        int height = 480;
        JFrame frame = new JFrame("Direct draw demo");

        DirectDrawDemo panel = new DirectDrawDemo(width, height);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
