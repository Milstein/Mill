/**
 *
 * @author kai
 */
public class Turn {

    public Turn(boolean white, int to) {
        this.white = white;
        this.to = to;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(white ? "White" : "Black");
        sb.append(" moves");
        if(from >= 0) {
            sb.append(" from");
            sb.append(from);
        }
        sb.append(" to ");
        sb.append(to);
        if(remove >= 0) {
            sb.append(" removing ");
            sb.append(remove);
        }
        sb.append(".");
        return sb.toString();
    }

    public boolean white = true;
    public int from = -1;
    public int to = 0;
    public int remove = -1;

}
	