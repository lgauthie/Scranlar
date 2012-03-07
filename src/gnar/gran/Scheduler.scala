package gnar.gran

import seq._
import source._
import env._

import collection.mutable.ListBuffer
import java.lang.System.currentTimeMillis

class Scheduler(sequence:Sequence, table:TableSource) {
  val grainList = new ListBuffer[Grain]
  val grainLengthMs = 80
  val grainLength = ((Audio.AUDIO_FORMAT.getSampleRate()) * (grainLengthMs / 1000)).asInstanceOf[Int]

  def synthesize() = {
    var start = 0
    for(i <- start until table.table.lenth - grainLength){
      for(j <- 0 until grainLength){

      }
    }
  }

  def start = {
    grainList foreach { grain =>
      Audio.Play(Audio.floatArrayToByte(grain.synthesize))
      wait(grainLengthMs)
    }
  }

  def wait(time:Int) = {
    var t0:Long = currentTimeMillis
    var t1:Long = currentTimeMillis
    while(t1 - t0 < time){
      t1 = currentTimeMillis
    }
  }
}
