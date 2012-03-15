package gnar.gran

import seq._
import source._
import env._

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem


/** Granulator
  *
  *
  */
object Granulator extends SimpleSwingApplication{


  /** main
    *
    * @param args: Array[String]
    *
    */
  def main(args: Array[String]) {
    var fileName:String = null
    var mode:String = "r"
    var grainSize:Int = 70

    args.foreach { arg =>
      fileName = arg
      //arg match {
      //  case "-gs" => 
      //}
    }

    val audio = new Audio

    val audioInputStream = audio openAudioFile(fileName)
    val audioWorkingStream = AudioSystem getAudioInputStream(audio.AUDIO_FORMAT,audioInputStream)
    val audioByteArray = audio audioStreamToByteArray(audioWorkingStream)
    val floatArray = audio byteArrayToFloat(audioByteArray)
    val table = new TableSource(floatArray)

    val sampleRate = audio.AUDIO_FORMAT.getSampleRate
    val sequence = new SingleTableSequence(table, sampleRate, 70, mode)

    val sched = new Scheduler(sequence, audio)

    sched synthesize
  }
}
