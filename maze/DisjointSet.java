package maze;

import java.util.HashMap;
import java.util.HashSet;
/**
 * Provides methods to construct and manipulate a disjoint set data structure.
 * @author Ryan Smith
 *
 */
public class DisjointSet {
        
    /**
     * Makes a set out of the cells in the maze. Basically, establishes rank and parent pointers (to self)
     * for every MazeCell object.
     * @param maze
     */
    public void makeSet(MazeCell[][] maze) {
    	for (int i = 0; i < maze.length; i++){
    		for (int j = 0; j < maze[i].length; j++){
    			MazeCell current = maze[i][j];
    			current.setParent(current);
    			current.setRank(0);
    		}
    	}
        
    }

    /**
     * Create the union of the sets that contain cell1 and cell2.
     * If the two cells are in the same set, nothing changes,
     * else create the new set as a union. 
     * Please see the union-find algorithm.
     * @param cell1
     * @param cell2
     */
    public void union(MazeCell cell1, MazeCell cell2){
        MazeCell cell1Leader = find(cell1);
        MazeCell cell2Leader = find(cell2);
        //if they are the same, cells in same component, return to exit without union'ing
        if (cell1Leader == cell2Leader)
        	return;
        //if ranks are different, assign lower rank to larger rank, ranks not affected
        if (cell1Leader.getRank() > cell2Leader.getRank())
        	cell2Leader.setParent(cell1Leader);
        else if (cell1Leader.getRank() < cell2Leader.getRank())
        	cell1Leader.setParent(cell2Leader);
        else{
        	//ranks equal, assign parent, update rank
        	cell2Leader.setParent(cell1Leader);
        	cell1Leader.setRank(cell1Leader.getRank() + 1);
        }
        	
    }

    /**
     * Find the set that the cell is a part of.
     * While finding the set, do the path compression as well.
     * @param cell
     * @return leader of the MazeCell's set
     */
    public MazeCell find(MazeCell cell){
    	//if cell is not it's own parent, continue searching for parent
    	//use path compression, assigns leader as parent of all MazeCells in the path using recursion
    	if (cell != cell.getParent()){
    		cell.setParent(find(cell.getParent()));
    	}
    	return cell.getParent();
    }

}
