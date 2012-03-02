package gnar.gran

import javax.sound.sampled.AudioInputStream
import java.io.InputStream
import java.io.IOException
import java.io.FileNotFoundException
import java.io.File
import java.io.ByteArrayOutputStream

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
    var fileName:String = null

    args.foreach{ arg =>
      fileName = arg
    }

    val audioInputStream = openAudioFile(fileName)
    val audioFormat = audioInputStream.getFormat()
    val audioByteArray = audioStreamToByteArray(audioInputStream)

    play(audioByteArray, audioFormat)
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

  /** audioStreamToByteArry
   *
   *  Converts and audio stream into an Array[Byte]
   *
   */
  def audioStreamToByteArray(audioInputStream:AudioInputStream) = {
    var baos = new ByteArrayOutputStream()
    var nBytesRead = 0
    var abBuffer = new Array[Byte](EXTERNAL_BUFFER_SIZE)
    while(nBytesRead != -1){
      try{
        nBytesRead = audioInputStream.read(abBuffer)
      }catch{
        case ioe: IOException => ioe.printStackTrace()
      }
      if(nBytesRead >= 0){
        baos.write(abBuffer, 0, nBytesRead)
      }
    }
    baos.toByteArray()
  }

  /** play 
   *
   */
  def play(audioByteArray:Array[Byte], audioFormat:AudioFormat) = {
    var info = new DataLine.Info(classOf[SourceDataLine], audioFormat)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]
    /*
      The line is there, but it is not yet ready to
      receive audio data. We have to open the line.
    */
    line.open(audioFormat)
    line.start()
    line.write(audioByteArray, 0, audioByteArray.length)
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
