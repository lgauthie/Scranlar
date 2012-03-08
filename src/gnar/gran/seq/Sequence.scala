package gnar.gran.seq

trait Sequence {
  def synthesize()

  def generateGrainList()

  def silence(time:Int)
}
