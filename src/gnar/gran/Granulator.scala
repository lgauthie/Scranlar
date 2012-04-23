package gnar.gran

import seq._
import source._
import env._

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

import swing._
import event._

/** Granulator
 *
 *
 **/
object Granulator extends SimpleSwingApplication{

  val audio = new Audio
  var sched: Scheduler = null

  /**
   * The main window for the swing GUI
   **/
  def top = new MainFrame {
    title = "Gnarulator"

    val forward = new Button {
      text = "forward"
    }

    val reverse = new Button {
      text = "reverse"
    }
    
    val slow = new Button {
      text = "slow"
    }

    val fast = new Button {
      text = "fast"
    }
    
    contents = new BoxPanel(Orientation.Vertical) {
      contents += forward
      contents += reverse
      contents += slow
      contents += fast
    }
    listenTo(forward, reverse, slow, fast)
    reactions += {
      case ButtonClicked(`forward`) => {
        sched.sequence.pmode = "n"
        sched synthesize
      }
      case ButtonClicked(`reverse`) => {
        sched.sequence.pmode = "r"
        sched synthesize
      }
      case ButtonClicked(`slow`) => {
        sched.sequence.pmode = "s"
        sched synthesize
      }
      case ButtonClicked(`fast`) => {
        sched.sequence.pmode = "f"
        sched synthesize
      }
    }
  }

  /** main
   *
   * @param args: Array[String]
   *
   **/
  override def startup(args: Array[String]) {
    var fileName:String = null
    var mode:String = "n"
    var grainSize:Int = 70

    // Parse them arguments
    args.foreach { arg =>
      fileName = arg
      //arg match {
      //  case "-gs" => 
      //}
    }

    // Starts and displays the frame
    val t = top
    if (t.size == new Dimension(0,0)) t.pack()
    t.visible = true

    // Sets up the inputs and outputs for the synthesizer
    val audioInputStream = audio openAudioFile(fileName)
    val audioWorkingStream = AudioSystem getAudioInputStream(audio.AUDIO_FORMAT,audioInputStream)
    val audioByteArray = audio audioStreamToByteArray(audioWorkingStream)
    val floatArray = audio byteArrayToFloat(audioByteArray)
    val table = new TableSource(floatArray)

    val sampleRate = audio.AUDIO_FORMAT.getSampleRate
    val sequence = new SingleTableSequence(table, sampleRate, grainSize, mode)
    sched = new Scheduler(sequence, audio)
  }
}
