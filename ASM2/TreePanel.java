package ASM2;

import java.awt.*;
import javax.swing.*;

public class TreePanel extends JPanel {

    private AVLTree tree;

    public TreePanel(AVLTree tree) {
        this.tree = tree;
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (tree.root != null) {
            drawTree(g2d, tree.root, getWidth() / 2, 50, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int hGap) {
        if (node == null) {
            return;
        }

        g.setColor(Color.WHITE);
        g.fillOval(x - 20, y - 20, 40, 40);
        g.setColor(Color.BLACK);
        g.drawOval(x - 20, y - 20, 40, 40);
        g.drawString(String.valueOf(node.sinhVien.id), x - 5, y + 5);

        if (node.left != null) {
            int childX = x - hGap;
            int childY = y + 100;
            g.drawLine(x, y + 20, childX, childY - 20);
            drawTree(g, node.left, childX, childY, hGap / 2);
        }

        if (node.right != null) {
            int childX = x + hGap;
            int childY = y + 100;
            g.drawLine(x, y + 20, childX, childY - 20);
            drawTree(g, node.right, childX, childY, hGap / 2);
        }
    }
}
