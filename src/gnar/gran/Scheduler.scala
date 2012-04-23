package gnar.gran

import collection.mutable.ListBuffer

import seq._
import source._
import env._

class Scheduler(var sequence:GrainSequence, audio:Audio, density:Int = 0) {

  var grainPool = new ListBuffer[Grain]
  
  def synthesize() = {
    audio.openAudio
    generateGrainPool

    sequence.playBackOrder(grainPool).foreach { grain =>
      audio.play(audio.floatArrayToByte(grain.synthesize))
      audio.play(audio.floatArrayToByte(silence(density)))
    }

    audio.closeAudio()
  }

  /** Generates a list of grains from the input table **/
  def generateGrainPool() = {
    grainPool = new ListBuffer[Grain]
    println("generating grain pool")
    println("Mode " + sequence.poolMode)
    for(i <- sequence.poolOrder){
      grainPool += new Grain(sequence.source, sequence.envelope, i, sequence.grainLength)
    }
  }

  /** Creates an array of 0's to fill n ms of time
   * 
   * @param time the time in ms to fill
   *
   * @return an array filled with 0's
   *
   **/
  def silence(time:Int) = {
    val end = ((audio.AUDIO_FORMAT.getSampleRate()) * (time / 1000.0f)).asInstanceOf[Int]
    val emptySamples = new ListBuffer[Float]
    for(i <- 0 to end){
      emptySamples += 0.0f
    }
    emptySamples toArray
  }
}
