package gnar.gran

import seq._
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

/** Granulator
 *
 *
 */
object Granulator {

  val AUDIO_FORMAT = new AudioFormat(
    44100f, 
    16, 
    1,
    true,
    true
  )

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

    val audioInputStream = Audio.openAudioFile(fileName)
    val audioFormat = audioInputStream.getFormat()
    val audioWorkingStream = AudioSystem.getAudioInputStream(AUDIO_FORMAT,audioInputStream)
    val audioByteArray = Audio.audioStreamToByteArray(audioWorkingStream)
    val floatArray = Audio.byteArrayToFloat(audioByteArray,AUDIO_FORMAT)
    val byteArray = Audio.floatArrayToByte(floatArray,AUDIO_FORMAT)

    val sequence = new DefaultSequence()

    Audio.play(byteArray, AUDIO_FORMAT)
  }
}
