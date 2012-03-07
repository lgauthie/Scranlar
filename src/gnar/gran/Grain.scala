package gnar.gran

import gnar.gran.source._
import gnar.gran.env._

/** Grain
 *
 * Generates grains from a source, and an envelope.
 *
 * @constructor construcs a grain with a source and envelope
 * @param source the source data for the Grain
 * @param envelope the envelope for each Grain
 *
 */
class Grain(val source:Source, val envelope:Envelope) {

  /** Synthesizes a new grain from the given source
   *
   * @return returns Array[Byte] that contains the current grain
   *
   */
  def synthesize(length:Int) = {
    source synthesize(1,1,1)
  }
}
