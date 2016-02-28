package com.img.streaming

import scala.io.Source

/**
 * Created by pradeep on 26/02/2016.
 */

/**
 * Trait produces Stream of input
 */
trait SportsDataStreamer {

  def getData : Stream[String]

}

/**
 * Stream sports data from file
 * @param path
 */
class SportsDataFileStreamer(path :String) extends SportsDataStreamer{
  override def getData: Stream[String] = Source.fromFile(path)
    .getLines()
    .map(_.trim)
    .toStream
}
