# Maze_Creator-Solver
Overview: Uses a modified version of Kruskal's algorithm to generate and solve mazes.

Design: This concept was assigned by Dr. Arvind Bhursnurmath at the University of Pennsylvania. Skeleton files were provided. All implementation by Ryan Smith.

Use: This program implements the Disjoint Set data structure and then uses it with a modified version of Kruskal's algorithm (modified in that it attempts to make a spanning tree instead of MST) in order to generate mazes. It then uses either BFS, DFS or randomization to solve the maze. 

The executable jar is run from the command line and takes two arguments; choice of algorithm to solve (bfs/dfs/random) and an integer for the number of cells wide the maze should be made.

Other info: Javadocs and executable included. 