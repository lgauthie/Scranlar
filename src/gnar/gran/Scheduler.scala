package gnar.gran

import seq._
import source._
import env._

import collection.mutable.ListBuffer
import java.lang.System.currentTimeMillis

class Scheduler(sequence:Sequence, table:TableSource) {
  val grainList = new ListBuffer[Grain]
  val grainLengthMs = 40 
  val grainLength = ((Audio.AUDIO_FORMAT.getSampleRate()) * (grainLengthMs / 1000.0f)).asInstanceOf[Int]
  val env = new SinEnvelope(grainLength)

  def synthesize = {
    val end = table.table.length - grainLength
    val step = grainLength
    for(i <- 0 until end by step){
      println("Grain: " + i)
      grainList += new Grain(table,env,i,grainLength)
    }
  }

  def start = {
    synthesize
    println("len: " + grainList.length)
    grainList foreach { grain =>
      Audio.play(Audio.floatArrayToByte(grain.synthesize))
      Audio.play(Audio.floatArrayToByte(silence(5)))
    }
  }

  def silence(time:Int) = {
    val end = ((Audio.AUDIO_FORMAT.getSampleRate()) * (time / 1000.0f)).asInstanceOf[Int]
    val emptySamples = new ListBuffer[Float]
    for(i <- 0 to time){
      emptySamples += 0.0f
    }
    emptySamples toArray
  }
}
