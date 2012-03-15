package gnar.gran

import gnar.gran.source._
import gnar.gran.env._

import collection.mutable.ArrayBuffer

/** Grains to be used as the synthesis base
  *
  * Generates grains from a source, and an envelope.
  *
  * @constructor construcs a grain with a source and envelope
  * @param source the source data for the Grain
  * @param envelope the envelope for each Grain
  * @param offset some kind of offset to be passed into the sources
  *        sythesize method. It could be a time offset in the case of
  *        a table method, or a frequency offset in the case of a 
  *        synthetic method.
  * @param len the length of the grain in samples
  *
  */
class Grain(val source:Source, val envelope:Envelope, val offset:Int = 0, val len:Int) {

  /** Synthesizes a new grain from the given source
    *
    * @return returns Array[Byte] that contains the current grain
    *
    */
  def synthesize = {
    val fArrayBuf = new ArrayBuffer[Float]
    for(sample <- 0 until len){
      val envVal = envelope.generateAmplitude(sample)
      fArrayBuf += source synthesize(sample,envVal,offset)
    }
    fArrayBuf toArray
  }
}
