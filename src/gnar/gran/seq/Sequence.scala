package gnar.gran.seq

import collection.mutable.ListBuffer

import gnar.gran.source.Source
import gnar.gran.env.Envelope
import gnar.gran.Grain


trait GrainSequence {
      //grainPool += new Grain(sequence.source,sequence.env,i,sequence.grainLength)
  def source():Source
  def env():Envelope
  def grainLength():Int
  def playBackOrder(list:ListBuffer[Grain]):Iterable[Grain]
  def poolOrder():Iterable[Int]
}
