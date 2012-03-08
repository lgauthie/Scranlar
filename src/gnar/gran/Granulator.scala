package gnar.gran

import seq._
import source._
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

/** Granulator
 *
 *
 */
object Granulator {


  /** main
   *
   *  @param args: Array[String]
   *
   */
  def main(args: Array[String]) {
    var fileName:String = null

    args.foreach { arg =>
      fileName = arg
    }

    val audio = new Audio

    val audioInputStream = audio openAudioFile(fileName)
    val audioWorkingStream = AudioSystem getAudioInputStream(audio.AUDIO_FORMAT,audioInputStream)
    val audioByteArray = audio audioStreamToByteArray(audioWorkingStream)
    val floatArray = audio byteArrayToFloat(audioByteArray)
    val table = new TableSource(floatArray)

    val sequence = new SingleTableSequence(table, audio)

    val sched = new Scheduler(sequence, audio)

    sched synthesize
  }
}
