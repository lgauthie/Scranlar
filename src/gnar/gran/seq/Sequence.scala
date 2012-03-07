package gnar.gran.seq

import gnar.gran.Grain

trait Sequence {
  def synthesize(): Int
  def addSource(grain:Grain)
}

