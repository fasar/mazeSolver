

package fsart.maze.common

import fsart.maze.common.model.Maze


/**
 * Maze factory interface
 *
 * This interface gives functions to get mazes available.
 *
 * @author      Fabien Sartor
 * @version     1.0
 */
abstract class MazeFactory {

  /**
   * Get a list of name of maze you can get with getMaze
   *
   * @return A list of name of maze
   */
  def getMazeNames(): List[String]


  /**
   * Get the maze identified with a name
   *
   * @return Some(Maze) if maze named name is found
   *         None       otherwise
   */
  def getMaze(name: String): Option[Maze]

  //def getMazeFromFile(file: File): Option[Maze]
}
