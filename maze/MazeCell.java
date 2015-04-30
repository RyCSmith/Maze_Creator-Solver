package maze;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *  The <code>MazeCell</code> class stores information about each individual cell
 *  in the maze.  The boolean values <code>north</code>, <code>east</code>,
 *  <code>west</code>, and <code>south</code> store which walls are up; e.g., if
 *  <code>north</code> is <code>true</code>, then the north wall is up.
 *  
 *  Each cell also has pointer to its four <code>MazeCell</code> neighbors,
 *  <code>neighborN</code>, <code>neighborE</code>, <code>neighborS</code>, and
 *  <code>neighborW</code>.  If any of these values are null, it means this cell
 *  is on the border of the maze.  
 *  
 *  Each cell also has a pointer to its <code>parent</code> and a <code>rank</code>
 *  for use in the <code>DisjointSet</code> class.
 *	@author Ryan C Smith
 *	@author Arvind Bhusnurmath
 *
 */
public class MazeCell {
    
    private boolean north, east, south, west;
    private boolean visited, examined;
    private MazeCell neighborN, neighborE, neighborS, neighborW;
    private Random generator;
    private MazeCell parent;
    private int rank;


    /** 
     *  Sets all the walls to <code>true</code> and initializes
     *  the random number generator.
     */
    public MazeCell() {
        north = true;
        east  = true;
        south = true;
        west  = true;
        generator = new Random();
        visited = false;
        examined = false;
        parent = null;
        rank = 0;
    }
    
    /**
     *  Sets the visited flag to <code>true</code>.
     */
    public void visit() {
        visited = true;
    }

    /**
     *  Returns whether or not this cell has been visited.
     *  @return <code>true</code> if the cell has been visited.
     */
    public boolean visited() {
        return visited;
    }

    /**
     *  Sets the examined flag to <code>true</code>.
     */
    public void examine() {
        examined = true;
    }

    /**
     *  Returns whether or not this cell has been examined.
     *  @return <code>true</code> if the cell has been examined.
     */
    public boolean examined() {
        return examined;
    }
    
    /**
     *  Sets the pointers to the neighbors of this cell.  If a pointer 
     *  is null, that means this cell is along the border of the maze.
     *  @param n  The north neighbor of this cell.
     *  @param e  The east neighbor of this cell.
     *  @param s  The south neighbor of this cell.
     *  @param w  The west neighbor of this cell.
     */
    public void setNeighbors(MazeCell n, MazeCell e, MazeCell s, MazeCell w) {
        neighborN = n;
        neighborE = e;
        neighborS = s;
        neighborW = w;
    }

    /**
     *  Sets whether or not there are walls up to the north, east, south, and 
     *  west of this cell.
     *  @param north <code>true</code> if there's a wall on the north side of the cell.
     *  @param east <code>true</code> if there's a wall on the east side of the cell.
     *  @param south <code>true</code> if there's a wall on the south side of the cell.     
     *  @param west <code>true</code> if there's a wall on the west side of the cell.
     */
    public void setWalls(boolean north, boolean east, boolean south, boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }
    
    /**
     *  Returns whether or not this cell has all its walls up.
     *  @return <code>true</code> if all walls are up.
     */
    public boolean hasAllWalls() {
        if (!north) return false;
        if (!south) return false;
        if (!east) return false;
        if (!west) return false;
        return true;
    }

    /**
     *  Returns whether or not this cell has its north wall up.
     *  @return <code>true</code> if the cell's north wall is up.
     */
    public boolean north() {
	    if (north) return true;
        return false;
    }

    /**
     *  Returns whether or not this cell has its south wall up.
     *  @return <code>true</code> if the cell's south wall is up.
     */
    public boolean south() {
        if (south) return true;
        return false;
    }

    /**
     *  Returns whether or not this cell has its east wall up.
     *  @return <code>true</code> if the cell's east wall is up.
     */
    public boolean east() {
        if (east) return true;
        return false;
    }

    /**
     *  Returns whether or not this cell has its west wall up.
     *  @return <code>true</code> if the cell's west wall is up.
     */
    public boolean west() {
	    if (west) return true;
        return false;
    }

    /**
     *  Gets all neighbors of this cell that are non-null and do not have 
     *  a wall between it and this cell. In other words, returns all
     *  legal moves for dfs/bfs solve.
     *  Returns an array of those neighbors.  The length of the array
     *  is the number of neighbors this cell has.
     *  @return  An array with the neighbors contained within it.
     */
    public MazeCell[] getNeighbors() {
        ArrayList<MazeCell> al = new ArrayList<MazeCell>();
        if (neighborN != null && !north())
        	al.add(neighborN);
        if (neighborE != null && !east())
        	al.add(neighborE);
        if (neighborS != null && !south())
        	al.add(neighborS);
        if (neighborW != null && !west())
        	al.add(neighborW);
        MazeCell[] array = new MazeCell[al.size()];
        for (int i = 0; i < al.size(); i++){
        	array[i] = al.get(i);
        }
        return array;
    }

    /**
     *  Knocks down the wall between this cell and the neighbor cell.
     *  The neighbor cell must actually be a neighbor of this cell.
     *  This method is used in conjunction with <code>neighborWithWalls</code>.
     *  @param neighbor  The neighboring cell; must be one of the neighbors
     *                set in <code>setNeighbors</code>.
     */
    public void knockDownWall(MazeCell neighbor) {
        if (neighbor == neighborE){
        	east = false;
        	neighbor.setWalls(neighbor.north(), neighbor.east(), neighbor.south(), false);
        }
        else if (neighbor == neighborN){
        	north = false;
        	neighbor.setWalls(neighbor.north(), neighbor.east(), false, neighbor.west());
        }
        else if (neighbor == neighborS){
        	south = false;
        	neighbor.setWalls(false, neighbor.east(), neighbor.south(), neighbor.west());
        }
        else if (neighbor == neighborW){
        	west = false;
        	neighbor.setWalls(neighbor.north(), false, neighbor.south(), neighbor.west());
        }
    }
    
    /**
     * This function returns a random neighbor that also currently has a wall up with this MazeCell.
     * This avoids checking for walls in Kruskal.
     * @return random choice of one of the legal neighbors (for Kruskal == wall up).
     */
    public MazeCell getRandomNeighbor(){
    	ArrayList<MazeCell> al = new ArrayList<MazeCell>();
    	if (neighborN != null && neighborN.south())
    		if(north())
    			al.add(neighborN);
    	if (neighborS != null && neighborS.north())
    		if (south())
    			al.add(neighborS);
    	if (neighborE != null && neighborE.west())
    		if (east())
    			al.add(neighborE);
    	if (neighborW != null && neighborW.east())
    		if (west())
    			al.add(neighborW);
    	if (al.size() == 0)
    		return null;
    	return al.get(generator.nextInt(al.size()));
    }
    
    /**
     * Returns the parent of this <code>MazeCell</code>.
     * @return parent
     */
	public MazeCell getParent() {
		return parent;
	}
	
	/**
	 * Sets the parent of this <code>MazeCell</code> to the argument <code>parent</code>.
	 * @param parent
	 */
	public void setParent(MazeCell parent) {
		this.parent = parent;
	}
	
	/**
     * Returns the rank of this <code>MazeCell</code>.
     * @return rank
     */
	public int getRank() {
		return rank;
	}
	
	/**
	 * Sets the rank of this <code>MazeCell</code> to the argument <code>rank</code>.
	 * @param rank
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}  
   
}
