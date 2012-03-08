package gnar.gran.env

class SinEnvelope(val lenSamples:Int) extends Envelope{
  def generateAmplitude(time:Int) = {
    scala.math.sin((2 * scala.math.Pi) / lenSamples * time).asInstanceOf[Float]
  }
}
