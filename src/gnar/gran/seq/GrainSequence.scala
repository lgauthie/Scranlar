package gnar.gran.seq

import collection.mutable.ListBuffer

import gnar.gran.source.Source
import gnar.gran.env.Envelope
import gnar.gran.Grain


abstract class GrainSequence(val source:Source) {
  def envelope():Envelope
  def grainLength():Int
  def playBackOrder(list:ListBuffer[Grain]):Iterable[Grain]
  def poolOrder():Iterable[Int]
}
