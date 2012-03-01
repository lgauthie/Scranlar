import javax.sound.sampled.AudioInputStream
import java.io.InputStream
import java.io.IOException
import java.io.FileNotFoundException
import java.io.File

import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.SourceDataLine

/** Granulator
 *
 *
 */
object Granulator {
  val EXTERNAL_BUFFER_SIZE = 128000 

  /** main
   *
   *
   */
  def main(args: Array[String]) {

    val audioInputStream = openAudioFile("cat_meow_x.wav")
    val audioFormat = audioInputStream.getFormat()
    //TODO: Copy input into an Array[Byte]
    //TODO: Make play take in an Array[Byte] and play it.
    play(audioInputStream, audioFormat)
  }

  /** openAudioFile
   *
   *
   */
  def openAudioFile(strSource:String) = {
    val file = new File(strSource)
    var audioInputStream: AudioInputStream = null 
    try {
      audioInputStream = AudioSystem.getAudioInputStream(file)
      println("File Opened")
    }catch{
      case e: Exception => e.printStackTrace();
    }
    audioInputStream
  }

  /** play 
   *
   */
  def play(audioInputStream:AudioInputStream, audioFormat:AudioFormat) = {
    var info = new DataLine.Info(classOf[SourceDataLine], audioFormat)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]
    /*
      The line is there, but it is not yet ready to
      receive audio data. We have to open the line.
    */
    line.open(audioFormat)
    line.start()
    var nBytesRead = 0
    val abData = new Array[Byte](EXTERNAL_BUFFER_SIZE) 
    while (nBytesRead != -1) {
      try{
        nBytesRead = audioInputStream.read(abData, 0, abData.length)
      }catch{
        case ioe: IOException => ioe.printStackTrace()
      }
      if (nBytesRead >= 0){
        line.write(abData, 0, nBytesRead)
      }
    }
    /*
      Wait until all the data is played.
    */
    line.drain();

    /*
      All data are played. Time to close 
    */
    line.close();
  }
}
