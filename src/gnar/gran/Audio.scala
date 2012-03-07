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

import collection.mutable.ArrayBuffer

object Audio {

  val AUDIO_FORMAT = new AudioFormat(
    44100f, 
    16, 
    1,
    true,
    true
  )

  val EXTERNAL_BUFFER_SIZE = 128000 

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
      case e: Exception => e.printStackTrace()
    }
    audioInputStream
  }

  /** audioStreamToByteArry
   *
   * Converts and audio stream into an Array[Byte]
   * 
   * @param audioInputStream the input to be converted into a Array[Byte]
   * @return a copy of audioInputStream in Array[Byte] form
   *
   */
  def audioStreamToByteArray(audioInputStream:AudioInputStream) = {
    var nBytesRead = 0
    val baos = new ByteArrayOutputStream()
    val abBuffer = new Array[Byte](EXTERNAL_BUFFER_SIZE)
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

  /** Converts a byte array into a float array
   *
   * Currently assumes that everything is big endian
   *
   * @param bArray the input byte array
   * @param af the audio format of the input bArray
   * 
   * @return a float version of the input byte array
   */
  def byteArrayToFloat(bArray:Array[Byte], af:AudioFormat) = {
    val floatBuffer = new ArrayBuffer[Float]
    af.getFrameSize match {
      // 1 byte a frame to float
      case 1 => {
        for(elem <- bArray){
          val float = elem / 128.0f
          floatBuffer += float
        }
      }
      // 2 byte a frame to float
      case 2 => {
        for(sList <- bArray.sliding(2,2)){
          val float = (((sList(0) << 8) | (sList(1) & 0xFF)) / 32768.0f)
          floatBuffer += float
        }
      }
      case x:Int => println("Conversion not yet implemented for framesize: " + x)
    }
    floatBuffer.toArray
  }

  /** Converts a byte array into a float array
   * 
   * Currently assumes that everything is big endian
   *
   * @param fArray the input float array
   * @param af the audio format of the input bArray
   * 
   * @return a byte version of the input float array
   */
  def floatArrayToByte(fArray:Array[Float], af:AudioFormat) = {
    val byteBuffer = new ArrayBuffer[Byte]
    af.getFrameSize match {
      // 1 byte a frame conversion from float
      case 1 => {
        for(elem <- fArray){
          val byte = (elem * 128.0f).asInstanceOf[Byte]
          byteBuffer += byte 
        }
      }
      // 2 bytes a frame conversion from float
      case 2 => {
        for(elem <- fArray){
          val num = Math.round(elem * 32768) 
          val bHigh = ((num >> 8) & 0xFF).asInstanceOf[Byte]
          val bLow = (num & 0xFF).asInstanceOf[Byte]
          byteBuffer += bHigh += bLow
        }
      }
      case x:Int => println("Conversion not yet implemented for framesize: " + x)
    }
    byteBuffer.toArray
  }

  /** plays a byte array 
   *
   * @param audioByteArray the byte array to be played
   * @param audioFormat the format of the output
   *
   */
  def play(audioByteArray:Array[Byte], audioFormat:AudioFormat) = {

    val info = new DataLine.Info(classOf[SourceDataLine], audioFormat)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    // The line is there, but it is not yet ready to
    // receive audio data. We have to open the line.
    line.open(audioFormat)
    line.start()
    line.write(audioByteArray, 0, audioByteArray.length)
    
    // Wait until all the data is played.
    line.drain()

    
    // All data are played. Time to close 
    line.close()
  }
}
