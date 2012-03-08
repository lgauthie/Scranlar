package gnar.gran.env

class SinEnvelope(var lenSample:Int = 1) extends Envelope{

  def lenSamples(x:Int) = {lenSample = x}
  def generateAmplitude(time:Int) = {
    scala.math.sin((2 * scala.math.Pi) / lenSample * time).asInstanceOf[Float]
  }
}
