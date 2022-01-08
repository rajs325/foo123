import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import java.util.*;

public class DrawTest {
    static DrawPanel panel;
    static DrawControls controls;

    public static void main(String args[]) {
        Frame f = new Frame("DrawTest");
        DrawPanel panel = new DrawPanel();
        DrawControls controls = new DrawControls(panel);
        f.add("Center", panel);
        f.add("South", controls);
        f.setSize(300, 300);
        f.setVisible(true);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println(panel.getShapes());
                System.exit(0);
            }
        });
    }
}

abstract class Shape {
    protected int x1, y1, x2, y2;
    Color c;
    boolean fill;
    abstract void draw(Graphics g);

    void setColor(Color c) {
        this.c = c;
    }
    void update(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    Color getColor() {
        return c;
    }
    void setFill(boolean fill) {
        this.fill = fill;
    }
    boolean getFill() {
        return fill;
    }
}
class Line extends Shape {
    Line(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    void draw(Graphics g) {
        g.setColor(getColor());
        g.drawLine(x1, y1, x2, y2);
    }
    public String toString() {
        return "[Line:" + x1 + "," + y1 + "," + x2 + "," + y2 + "];";
    }
}
class Rectangle extends Shape {
    Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    void draw(Graphics g) {
        g.setColor(getColor());
        int width = x2 - x1;
        int height = y2 - y1;
        if (getFill() == true) {
            g.fillRect(x1, y1, width, height);
        } else {
            g.drawRect(x1, y1, width, height);
        }
    }
    public String toString() {
        return "[Rectangle:" + x1 + "," + y1 + "," + x2 + "," + y2 + "," + getFill() + "];";
    }
}
class Oval extends Shape {
    Oval(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    void draw(Graphics g) {
        g.setColor(getColor());
        int width = x2 - x1;
        int height = y2 - y1;
        if (getFill() == true) {
            g.fillOval(x1, y1, width, height);
        } else {
            g.drawOval(x1, y1, width, height);
        }
    }
    public String toString() {
        return "[Oval:" + x1 + "," + y1 + "," + x2 + "," + y2 + "," + getFill() + "];";
    }
}

class DrawPanel extends Panel implements MouseListener, MouseMotionListener {
    public static final int LINES = 0;
    public static final int RECTS = 1;
    public static final int OVALS = 2;
    int mode = LINES;
    boolean fill = false;
    ArrayList < Shape > shapes = new ArrayList < Shape > ();

    //Vector colors = new Vector();
    //Vector shapes = new Vector();

    int x1, y1;
    int x2, y2;

    Shape shape;

    public DrawPanel() {
        setBackground(Color.white);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setDrawMode(int mode) {
        this.mode = mode;
    }

    public void setFiller(boolean fill) {
        this.fill = fill;
    }

    public void mouseDragged(MouseEvent e) {
        e.consume();
        x2 = e.getX();
        y2 = e.getY();
        shape.update(x1, y1, x2, y2);
        //System.out.println("mouseDragged:" + e.toString());
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouseMoved:" + e.toString());
    }

    public void mousePressed(MouseEvent e) {
        e.consume();
        x1 = e.getX();
        y1 = e.getY();
        System.out.println("mousePressed:" + e.toString());
        System.out.println("" + x1 + "," + y1);
        shape = null;
        if (mode == LINES) {
            shape = new Line(x1, y1, x2, y2);
        } else if (mode == RECTS) {
            shape = new Rectangle(x1, y1, x2, y2);
        } else if (mode == OVALS) {
            shape = new Oval(x1, y1, x2, y2);
        }

        shape.setColor(getForeground());
        shape.setFill(fill);
    }

    public void mouseReleased(MouseEvent e) {
        e.consume();
        x2 = e.getX();
        y2 = e.getY();
        System.out.println("mouseReleased:" + e.toString());
        System.out.println("" + x2 + "," + y2);

        shapes.add(shape);
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
        //System.out.println("mouseEntered:" + e.toString());
    }

    public void mouseExited(MouseEvent e) {
        //System.out.println("mouseExited:" + e.toString());
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked:" + e.toString());
    }

    public void paint(Graphics g) {
        //current shape
        if (shape != null) {
            shape.draw(g);
        }

        //previously finalized shapes
        for (Shape s: shapes) {
            s.draw(g);
        }
    }
    public java.util.List < Shape > getShapes() {
        return shapes;
    }
}


class DrawControls extends Panel implements ItemListener {
    DrawPanel target;
    Choice colors;
    Choice shapes;
    Choice filler;

    public DrawControls(DrawPanel target) {
        this.target = target;
        setLayout(new FlowLayout());
        setBackground(Color.lightGray);
        target.setForeground(Color.red);

        colors = new Choice();
        colors.addItemListener(this);
        colors.addItem("Red");
        colors.addItem("Blue");
        colors.addItem("Green");
        colors.addItem("Black");
        colors.addItem("Orange");
        colors.setBackground(Color.lightGray);
        add(colors);

        shapes = new Choice();
        shapes.addItemListener(this);
        shapes.addItem("Line");
        shapes.addItem("Rectangle");
        shapes.addItem("Oval");
        shapes.setBackground(Color.lightGray);
        add(shapes);

        filler = new Choice();
        filler.addItemListener(this);
        filler.addItem("Outline");
        filler.addItem("Fill");
        filler.setBackground(Color.lightGray);
        add(filler);
    }

    public void itemStateChanged(ItemEvent e) {
        Component source = (Component) e.getSource();

        if (source == colors) {
            String color = (String) e.getItem();
            if (color.equals("Red")) {
                target.setForeground(Color.red);
            } else if (color.equals("Blue")) {
                target.setForeground(Color.blue);
            } else if (color.equals("Green")) {
                target.setForeground(Color.green);
            } else if (color.equals("Black")) {
                target.setForeground(Color.black);
            } else if (color.equals("Orange")) {
                target.setForeground(Color.orange);
            }
        } else if (source == shapes) {
            String choice = (String) e.getItem();
            if (choice.equals("Line")) {
                target.setDrawMode(DrawPanel.LINES);
            } else if (choice.equals("Rectangle")) {
                target.setDrawMode(DrawPanel.RECTS);
            } else if (choice.equals("Oval")) {
                target.setDrawMode(DrawPanel.OVALS);
            }
        } else if (source == filler) {
            String filler = (String) e.getItem();
            if (filler.equals("Fill")) {
                target.setFiller(true);
            } else {
                target.setFiller(false);
            }
        }
    }
}
