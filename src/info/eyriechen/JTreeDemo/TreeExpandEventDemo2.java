package info.eyriechen.JTreeDemo;

/* From http://java.sun.com/docs/books/tutorial/index.html */
/*
 * Copyright (c) 2006 Sun Microsystems, Inc. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MIDROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

/*
 * TreeExpandEventDemo2.java is a 1.4 example that requires no other files.
 */

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeNode;

public class TreeExpandEventDemo2 extends JPanel {
  DemoArea demoArea;

  JTextArea textArea;

  final static String newline = "\n";

  public TreeExpandEventDemo2() {
    super(new GridBagLayout());
    GridBagLayout gridbag = (GridBagLayout) getLayout();
    GridBagConstraints c = new GridBagConstraints();

    c.fill = GridBagConstraints.BOTH;
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.weightx = 1.0;
    c.weighty = 1.0;

    c.insets = new Insets(1, 1, 1, 1);
    demoArea = new DemoArea();
    gridbag.setConstraints(demoArea, c);
    add(demoArea);

    c.insets = new Insets(0, 0, 0, 0);
    textArea = new JTextArea();
    textArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane
        .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(200, 75));
    gridbag.setConstraints(scrollPane, c);
    add(scrollPane);

    setPreferredSize(new Dimension(450, 450));
    setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
  }

  void saySomething(String eventDescription, TreeExpansionEvent e) {
    textArea.append(eventDescription + "; " + "path = " + e.getPath()
        + newline);
  }

  class DemoArea extends JScrollPane implements TreeExpansionListener,
      TreeWillExpandListener {
    Dimension minSize = new Dimension(100, 100);

    JTree tree;

    Object[] willExpandOptions = { "Cancel Expansion", "Expand" };

    String willExpandText = "A branch node is about to be expanded.\n"
        + "Click \"Cancel Expansion\" to prevent it.";

    String willExpandTitle = "Tree Will Expand";

    public DemoArea() {
      TreeNode rootNode = createNodes();
      tree = new JTree(rootNode);
      tree.addTreeExpansionListener(this);
      tree.addTreeWillExpandListener(this);

      setViewportView(tree);
    }

    private TreeNode createNodes() {
      DefaultMutableTreeNode root;
      DefaultMutableTreeNode grandparent;
      DefaultMutableTreeNode parent;
      DefaultMutableTreeNode child;

      root = new DefaultMutableTreeNode("San Francisco");

      grandparent = new DefaultMutableTreeNode("Potrero Hill");
      root.add(grandparent);
      //
      parent = new DefaultMutableTreeNode("Restaurants");
      grandparent.add(parent);
      child = new DefaultMutableTreeNode("Thai Barbeque");
      parent.add(child);
      child = new DefaultMutableTreeNode("Goat Hill Pizza");
      parent.add(child);
      //
      parent = new DefaultMutableTreeNode("Grocery Stores");
      grandparent.add(parent);
      child = new DefaultMutableTreeNode("Good Life Grocery");
      parent.add(child);
      child = new DefaultMutableTreeNode("Safeway");
      parent.add(child);

      grandparent = new DefaultMutableTreeNode("Noe Valley");
      root.add(grandparent);
      //
      parent = new DefaultMutableTreeNode("Restaurants");
      grandparent.add(parent);
      child = new DefaultMutableTreeNode("Hamano Sushi");
      parent.add(child);
      child = new DefaultMutableTreeNode("Hahn's Hibachi");
      parent.add(child);
      //
      parent = new DefaultMutableTreeNode("Grocery Stores");
      grandparent.add(parent);
      child = new DefaultMutableTreeNode("Real Foods");
      parent.add(child);
      child = new DefaultMutableTreeNode("Bell Market");
      parent.add(child);

      return root;
    }

    public Dimension getMinimumSize() {
      return minSize;
    }

    public Dimension getPreferredSize() {
      return minSize;
    }

    //Required by TreeWillExpandListener interface.
    public void treeWillExpand(TreeExpansionEvent e)
        throws ExpandVetoException {
      saySomething("Tree-will-expand event detected", e);
      int n = JOptionPane.showOptionDialog(this, willExpandText,
          willExpandTitle, JOptionPane.YES_NO_OPTION,
          JOptionPane.QUESTION_MESSAGE, null, willExpandOptions,
          willExpandOptions[1]);
      if (n == 0) {
        //User said cancel expansion.
        saySomething("Tree expansion cancelled", e);
        throw new ExpandVetoException(e);
      }
    }

    //Required by TreeWillExpandListener interface.
    public void treeWillCollapse(TreeExpansionEvent e) {
      saySomething("Tree-will-collapse event detected", e);
    }

    // Required by TreeExpansionListener interface.
    public void treeExpanded(TreeExpansionEvent e) {
      saySomething("Tree-expanded event detected", e);
    }

    // Required by TreeExpansionListener interface.
    public void treeCollapsed(TreeExpansionEvent e) {
      saySomething("Tree-collapsed event detected", e);
    }
  }

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowGUI() {
    //Make sure we have nice window decorations.
    JFrame.setDefaultLookAndFeelDecorated(true);

    //Create and set up the window.
    JFrame frame = new JFrame("TreeExpandEventDemo2");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    JComponent newContentPane = new TreeExpandEventDemo2();
    newContentPane.setOpaque(true); //content panes must be opaque
    frame.setContentPane(newContentPane);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
}           
         
    
    