/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package perpective;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Insets;

/**
 *
 * @author helmuthtrefftz
 */
public class Perpective extends JPanel implements KeyListener {

    double dist = -900;
    int a = 0, d= 0, w = 0, s = 0;
    int points[][] = 
            {{100, 100, -1100},{100, -100, -1100},{-100, -100, -1100},{-100, 100, -1100},
             {100, 100, -1200},{100, -100, -1200},{-100, -100, -1200},{-100, 100, -1200},
             {0, -150, -1150}};
    int[][] aux = new int[points.length][3];
    
    Point edges[]={new Point(0, 1), 
                new Point(1, 2), 
                new Point(2, 3), 
                new Point(3, 0), 
                new Point(4, 5), 
                new Point(5, 6), 
                new Point(6, 7), 
                new Point(7, 4), 
                new Point(0, 4), 
                new Point(1, 5), 
                new Point(2, 6), 
                new Point(3, 7), 
                new Point(1, 8), 
                new Point(2, 8), 
                new Point(5, 8), 
                new Point(6, 8)};
        
    double mat[][] = new double[4][4];    
    
    public Perpective() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);        
    }

        @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Dimension size = getSize();
        Insets insets = getInsets();
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        g.setColor(Color.blue);
        Point aux_pnt[] = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            aux_pnt[i] = new Point((int)(dist*points[i][0])/points[i][2], (int)(dist*points[i][1])/points[i][2]);
        }
        
        for (Point edge : edges) {
            g.drawLine(w/2+aux_pnt[edge.x].x, h/2+aux_pnt[edge.x].y, w/2+aux_pnt[edge.y].x, h/2+aux_pnt[edge.y].y);
        }
    }

    
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int tecla = e.getKeyCode();
        switch (tecla) {
        // TRANSLATION
            case KeyEvent.VK_D:
                mini(20, 0);
                move();
                break;
            case KeyEvent.VK_A:
                mini(-20, 0);
                move();
                break;
            case KeyEvent.VK_W:
                mini(-20, 1);
                move();
                break;
            case KeyEvent.VK_S:
                mini(20, 1);
                move();
                break;
            case KeyEvent.VK_L:
                mini(20, 2);
                move();
                break;
            case KeyEvent.VK_O:
                mini(-20, 2);
                move();
                break;
        // SCALING
//            case KeyEvent.VK_Z: // Reduce image size.
//                fill();
//                double sx = 0.8, sy = 0.8, sz=0.8;
//                mat[0][0] = sx;
//                mat[1][1] = sy;
//                mat[2][2] = sz;
//                mat[3][3] = 1;
//                break;
//            case KeyEvent.VK_X: // Increase image size.
//                fill();
//                sx = 1/0.8; sy = 1/0.8; sz=1/0.8;
//                mat[0][0] = sx;
//                mat[1][1] = sy;
//                mat[2][2] = sz;
//                mat[3][3] = 1;
//                break;
            default:
                break;
        }
        
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    /**
     * In the main method, the frame and panels are created.
     * And the KeyListeners are added.
     * @param args 
     */
    public static void main(String[] args) {
        Perpective kle = new Perpective();

        // Create a new Frame
        JFrame frame = new JFrame("Ejemplo KeyListener");
        // Upon closing the frame, the application ends
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Add a panel called DibujarCasita3D
        frame.add(kle);

        // Asignarle tamaño
        frame.setSize(472, 500);
        // Put the frame in the middle of the window
        frame.setLocationRelativeTo(null);
        // Show the frame
        frame.setVisible(true);
    }
    
    private void mini(int delta, int pos) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                mat[i][j] = 0;
            }
        }
        for (int i = 0; i < 4; i++) {
            mat[i][i] = 1;
        }
        mat[pos][3] = delta;
    }
    
    private void move(){
        int pt[][] = new int[4][1];
        for (int[] point : points) {
            pt[0][0] = point[0];
            pt[1][0] = point[1];
            pt[2][0] = point[2];
            pt[3][0] = 1;
            pt = multiply(mat,pt);
            point[0] = pt[0][0];
            point[1] = pt[1][0];
            point[2] = pt[2][0];
        }
    }
    
    private int[][] multiply(double matrixA[][], int matrixB[][]){
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;
        
        int[][] matrixC = new int [rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for(int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
        return matrixC;
    }
}
