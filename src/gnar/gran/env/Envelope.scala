package gnar.gran.env

trait Envelope {
  def lenSamples(x:Int):Unit
  def generateAmplitude(time:Int):Float
}
