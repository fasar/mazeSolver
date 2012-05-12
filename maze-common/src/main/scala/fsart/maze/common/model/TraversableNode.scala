package fsart.maze.common.model

import annotation.tailrec
import collection.mutable.HashSet

/**
 * Represents a generic traversable node.
 *
 * It's used in search algo.
 *
 * @auther:   Fabien Sartor
 * @version:  2.0
 *
 */
trait TraversableNode[N] {

  /**
   * Get the list of neighbor nodes
   */
  def aroundNodes: List[N]

  /**
   * get the weight to go to the node nodeDst
   */
  def  weightToGoTo(nodeDst:N): Double

  /**
   * get the estimate Weight to go to the node nodeDst
   */
  def estimateWeightToGoTo(nodeDst:N): Double

}
