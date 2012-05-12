package fsart.maze.common.model

/**
 * Represents a generic node with 4 child.
 *
 * It's used in search algo.
 *
 * @auther:   Fabien Sartor
 * @version:  2.0
 *
 */
trait CardinalPointNode[N] {

  /**
   * Get the north neighbor nodes
   */
  def northNode: Option[N]

  /**
   * Get the west neighbor nodes
   */
  def westNode: Option[N]

  /**
   * Get the south neighbor nodes
   */
  def southNode: Option[N]

  /**
   * Get the east neighbor nodes
   */
  def eastNode: Option[N]

}
