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

    val audioInputStream = Audio openAudioFile(fileName)
    val audioFormat = audioInputStream getFormat()
    val audioWorkingStream = AudioSystem getAudioInputStream(Audio AUDIO_FORMAT,audioInputStream)
    val audioByteArray = Audio audioStreamToByteArray(audioWorkingStream)
    val floatArray = Audio byteArrayToFloat(audioByteArray)
    val table = new TableSource(floatArray)
    val byteArray = Audio floatArrayToByte(floatArray)

    val sequence = new DefaultSequence()

    val sched = new Scheduler(sequence, table)
    
    Audio openAudio

    sched start

    Audio closeAudio

    //Audio play(byteArray, AUDIO_FORMAT)
  }
}
