package com.img.input

import scala.io.Source

/**
 * Created by pradeep on 21/02/2016.
 */
trait SportDataIn {

    def getData : Iterator[String]
}


class SportDataFileIn(path :String) extends SportDataIn{

    def getData: Iterator[String] = Source.fromFile(path).getLines().map(_.trim)

}