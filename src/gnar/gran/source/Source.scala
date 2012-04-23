package gnar.gran.source

trait Source {
  
  val length:Int
  /** 
   * Uses time to calculate amplitude at a given time.
   *
   * @param time the current time in ms
   * @param offset the time or frequency offset
   *        depending on the type of source.
   *
   * @return amplitude at current time
   **/
  def synthesize(time:Int, amplitude:Float, offset:Int):Float
}
