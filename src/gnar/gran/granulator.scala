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

object Granulator {
  val strSource = "cat_meow_x.wav"
  val EXTERNAL_BUFFER_SIZE = 128000 

  def main(args: Array[String]) {

    val file = new File(strSource)
    var audioInputStream: AudioInputStream = null 
    try {
      audioInputStream = AudioSystem.getAudioInputStream(file)
    }catch{
      case e: Exception => e.printStackTrace();
    }
    println("File Opened")
    val audioFormat = audioInputStream.getFormat()
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
        println("Tried Something")
        nBytesRead = audioInputStream.read(abData, 0, abData.length)
      }catch{
        case ioe: IOException => ioe.printStackTrace()
      }
      if (nBytesRead >= 0){
        val nBytesWritten = line.write(abData, 0, nBytesRead)
      }
    }
    /*
      Wait until all data are played.
      This is only necessary because of the bug noted below.
      (If we do not wait, we would interrupt the playback by
      prematurely closing the line and exiting the VM.)
     
      Thanks to Margie Fitch for bringing me on the right
      path to this solution.
    */
    line.drain();

    /*
      All data are played. We can close the shop.
    */
    line.close();
  }
}
