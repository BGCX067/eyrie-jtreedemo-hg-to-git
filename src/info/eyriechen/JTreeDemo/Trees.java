package info.eyriechen.JTreeDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

// Takes an array of Strings and makes the first
// element a node and the rest leaves:
class Branch {
  private DefaultMutableTreeNode r;

  public Branch(String[] data) {
    r = new DefaultMutableTreeNode(data[0]);
    for (int i = 1; i < data.length; i++)
      r.add(new DefaultMutableTreeNode(data[i]));
  }

  public DefaultMutableTreeNode node() {
    return r;
  }
}

public class Trees extends JApplet {
  private String[][] data = { { "Colors", "Red", "Blue", "Green" },
      { "Flavors", "Tart", "Sweet", "Bland" },
      { "Length", "Short", "Medium", "Long" },
      { "Volume", "High", "Medium", "Low" },
      { "Temperature", "High", "Medium", "Low" },
      { "Intensity", "High", "Medium", "Low" }, };

  private static int i = 0;

  private DefaultMutableTreeNode root, child, chosen;

  private JTree tree;

  private DefaultTreeModel model;

  public void init() {
    Container cp = getContentPane();
    root = new DefaultMutableTreeNode("root");
    tree = new JTree(root);
    // Add it and make it take care of scrolling:
    cp.add(new JScrollPane(tree), BorderLayout.CENTER);
    // Capture the tree's model:
    model = (DefaultTreeModel) tree.getModel();
    JButton test = new JButton("Press me");
    test.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (i < data.length) {
          child = new Branch(data[i++]).node();
          // What's the last one you clicked?
          chosen = (DefaultMutableTreeNode) tree
              .getLastSelectedPathComponent();
          if (chosen == null)
            chosen = root;
          // The model will create the appropriate event.
          // In response, the tree will update itself:
          model.insertNodeInto(child, chosen, 0);
          // Puts the new node on the chosen node.
        }
      }
    });
    // Change the button's colors:
    test.setBackground(Color.BLUE);
    test.setForeground(Color.WHITE);
    JPanel p = new JPanel();
    p.add(test);
    cp.add(p, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    run(new Trees(), 250, 250);
  }

  public static void run(JApplet applet, int width, int height) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(applet);
    frame.setSize(width, height);
    applet.init();
    applet.start();
    frame.setVisible(true);
  }
} ///:~