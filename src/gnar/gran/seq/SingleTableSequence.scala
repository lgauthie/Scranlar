package gnar.gran.seq

import collection.mutable.ListBuffer

import gnar.gran.Audio
import gnar.gran.Grain
import gnar.gran.source._
import gnar.gran.env._

class SingleTableSequence(val table:TableSource, val audio:Audio, grainLengthMs:Int = 30, mode:String = "n") extends Sequence {

  val grainLength = ((audio.AUDIO_FORMAT.getSampleRate()) * (grainLengthMs / 1000.0f)).asInstanceOf[Int]
  val env = new SinEreversenvelope(grainLength)
  val grainList = new ListBuffer[Grain]

  def synthesize = {
    grainList.reverse.foreach { grain =>
      audio.play(audio.floatArrayToByte(grain.synthesize))
    }
  }

  def generateGrainList = {
    val end = table.table.length - grainLength
    val step = grainLength
    for(i <- 0 until end by step){
      grainList += new Grain(table,env,i,grainLength)
    }
  }

  def silence(time:Int) = {
    val end = ((audio.AUDIO_FORMAT.getSampleRate()) * (time / 1000.0f)).asInstanceOf[Int]
    val emptySamples = new ListBuffer[Float]
    for(i <- 0 to time){
      emptySamples += 0.0f
    }
    emptySamples toArray
  }
}