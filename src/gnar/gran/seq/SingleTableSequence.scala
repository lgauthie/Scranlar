package gnar.gran.seq

import collection.mutable.ListBuffer

import gnar.gran.Audio
import gnar.gran.Grain
import gnar.gran.source._
import gnar.gran.env._

class SingleTableSequence(newSource:Source, newEnv:Envelope,
                          sampleRate:Float, grainLengthMs:Int = 60,
                          mode:String = "n") extends GrainSequence {
  def source = newSource
  def env = {
    newEnv.lenSamples(grainLength)
    newEnv
  }
  def grainLength = (sampleRate * grainLengthMs/1000).asInstanceOf[Int]
  
  /** Plays all of the grains in the list of grains
    *
    * @todo should a message that says when and where the next grain appears
    * @todo the order the grains appear
    *
    */
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
