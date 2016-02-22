package com.img.streaming

import scala.io.Source

/**
 * Created by pradeep on 21/02/2016.
 */
trait SportDataStreamer {

    def getData : Iterator[String]
}


class SportDataFileStream(path :String) extends SportDataStreamer{

    def getData: Iterator[String] = Source.fromFile(path).getLines().map(_.trim)

}