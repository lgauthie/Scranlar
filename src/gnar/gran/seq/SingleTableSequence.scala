package gnar.gran.seq

import collection.mutable.ListBuffer

import gnar.gran.Audio
import gnar.gran.Grain
import gnar.gran.source._
import gnar.gran.env._

class SingleTableSequence(source:Source, sampleRate:Float, grainLengthMs:Int = 60, mode:String = "n")
  extends GrainSequence(source) {

  def grainLength =  (sampleRate * grainLengthMs/1000).asInstanceOf[Int]
  val env = new SinEnvelope(grainLength)
  def envelope() = env
  
  /** Plays all of the grains in the list of grains
    *
    **/
  def playBackOrder(list:ListBuffer[Grain]) = {
    mode match{
      case "n" => list
      case "r" => list reverse
    }
  }

  def poolOrder() = {
    val end = source.length - grainLength
    val step = grainLength
    0 until end by step
  }
}
