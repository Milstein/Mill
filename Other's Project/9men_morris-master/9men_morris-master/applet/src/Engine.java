
/**
 *
 * @author kai
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.applet.Applet;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import netscape.javascript.JSObject; 
import netscape.javascript.JSException; 

public class Engine extends Applet {
	
	private static final long serialVersionUID = 1L;
    
	Font f;
	String version = "Nine Men Morris Engine 1.0";
    Color white = Color.YELLOW;
    Color black = Color.BLACK;
    int radius = 30;
    int[][] coord = {
        {0,0}, {3,0}, {6,0}, {6,3}, {6,6}, {3,6}, {0,6}, {0,3},
        {1,1}, {3,1}, {5,1}, {5,3}, {5,5}, {3,5}, {1,5}, {1,3},
        {2,2}, {3,2}, {4,2}, {4,3}, {4,4}, {3,4}, {2,4}, {2,3}};
    
    Position pos = new Position();

	public void init() {
		System.out.println("init");
		f = new Font("Helvetica", Font.BOLD, 16);
	}
	
	public void start() {
		System.out.println("start");
        doTurn(null, "0", null);
        doTurn("", "3", "");
        try {
        	String jsCallbackName = getParameter("applet_ready_callback");
            if(jsCallbackName != null) {
                JSObject.getWindow(this).eval(jsCallbackName + "()");
            }
        } catch(JSException ex) {
        	ex.printStackTrace();
        }
	}
    
    private int[] translate(int p1) {
        return new int[] {100 + coord[p1][0]*100, 100 + coord[p1][1]*100};
    }

    private void drawLine(Graphics g, int from, int to) {
        int[] f = translate(from);
        int[] t = translate(to);
        g.drawLine(f[0], f[1], t[0], t[1]);
    }
    
    private void drawPiece(Graphics g, int pos, boolean isWhite) {
        int[] p = translate(pos);
        g.setColor(isWhite ? white : black);
        g.fillOval(p[0] - radius, p[1] - radius, 2*radius, 2*radius);
    }

	public void paint ( Graphics g ) {
        System.out.println("paint");
        g.setColor(Color.BLACK);
    	g.setFont(f);
        g.drawString ( version, 20, 20);
        
        drawLine(g, 0, 2);
        drawLine(g, 2, 4);
        drawLine(g, 4, 6);
        drawLine(g, 6, 0);

        drawLine(g, 8, 10);
        drawLine(g, 10, 12);
        drawLine(g, 12, 14);
        drawLine(g, 14, 8);
        
        drawLine(g, 16, 18);
        drawLine(g, 18, 20);
        drawLine(g, 20, 22);
        drawLine(g, 22, 16);
        
        drawLine(g, 1, 17);
        drawLine(g, 3, 19);
        drawLine(g, 5, 21);
        drawLine(g, 7, 23);
        
        for(int i = 0; i < pos.board.length; i++) {
            if(pos.board[i] != 0) {
                drawPiece(g, i, pos.board[i] == 1);
            }
        }
    }
    
    public void stop() {
    	System.out.println("stop");
    }
    
    public void destroy() {
    	System.out.println("destroy");
    }
    
    public String echo(String str) {
    	return version + " " + str;
    }
    
    public String doTurn(String from, String to, String remove) {
        try {
            Turn t = new Turn(pos.whitesTurn(), Integer.parseInt(to));
            if(from != null && !from.equals(""))
                t.from = Integer.parseInt(from);
            if(remove != null && !remove.equals(""))
                t.remove = Integer.parseInt(remove);
            pos.doTurn(t);
            repaint();
            return t.toString();
        } catch(RuntimeException ex) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintWriter(baos));
            return ex.toString() + baos.toString();
        }
    }
}
