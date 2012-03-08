package gnar.gran

import seq._

class Scheduler(sequence:Sequence, audio:Audio) {
  def synthesize = {
    audio.openAudio()
    sequence.generateGrainList()
    sequence.synthesize()
    audio.closeAudio()
  }
}
