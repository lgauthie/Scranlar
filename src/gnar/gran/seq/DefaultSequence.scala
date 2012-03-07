package gnar.gran.seq

import collection.mutable.LinkedList
import gnar.gran.Grain

class DefaultSequence extends Sequence {
  val grainList = new LinkedList[Grain]
  def synthesize() = {
    1
  }
  def addSource(grain:Grain) = {
    grainList :+ grain
  }
}
