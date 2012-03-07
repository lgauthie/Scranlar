package gnar.gran.seq

import collection.mutable.ListBuffer
import gnar.gran.Grain

class DefaultSequence extends Sequence {
  val grainList = new ListBuffer[Grain]
  def synthesize() = {
    //grainList foreach (_.synthesize(80))
    1
  }
  def addSource(grain:Grain) = {
    grainList += grain
  }
}
