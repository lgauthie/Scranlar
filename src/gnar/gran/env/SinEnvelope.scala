package gnar.gran.env

class SinEnvelope(len:Int = 1)
  extends Envelope{

  def generateAmplitude(time:Int):Float = {
    scala.math.sin((2 * scala.math.Pi) / len * time).asInstanceOf[Float]
  }
}
