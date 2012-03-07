package gnar.gran

import gnar.gran.source._
import gnar.gran.env._

import collection.mutable.ArrayBuffer
/** Grain
 *
 * Generates grains from a source, and an envelope.
 *
 * @constructor construcs a grain with a source and envelope
 * @param source the source data for the Grain
 * @param envelope the envelope for each Grain
 *
 */
class Grain(val source:Source, val envelope:Envelope, val offset:Int, val len:Int) {

  /** Synthesizes a new grain from the given source
   *
   * @param length length of grain
   *
   * @return returns Array[Byte] that contains the current grain
   *
   */
  def synthesize = {
    val fArrayBuf = new ArrayBuffer[Float]
    for(i <- 0 until len){
      fArrayBuf += source synthesize(i,1,offset)
    }
    fArrayBuf toArray
  }
}
