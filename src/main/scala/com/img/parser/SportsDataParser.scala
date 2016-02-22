package com.img.parser

import scala.util.Try


trait SportDataPattern {

  /**
   * Constants specifying the bit position of sports data
   */
  val pointsScoredBitStartPos = 0
  val teamScoredBitStartPos = 2
  val team2PointsBitStartPos = 3
  val team1PointsBitStartPos = 11
  val timeElapsedBitStartPos = 19

  val pointsScoredBitLength = 2
  val teamScoredBitLength = 1
  val team2pointsBitLength = 8
  val team1pointsBitLength = 8
  val timeElapsedBitLength = 12


  /**
   * Pre-calculating the values for bit manipulation
   */
  val pointsScoredMaxVal = bitsMaxValue(pointsScoredBitLength)
  val teamScoreMaxVal = bitsMaxValue(teamScoredBitLength)
  val team2PointsMaxVal = bitsMaxValue(team2pointsBitLength)
  val team1PointsMaxVal = bitsMaxValue(team1pointsBitLength)
  val timeElapsedMaxVal = bitsMaxValue(timeElapsedBitLength)

  protected def bitsMaxValue(length: Int): Int = {
    Math.pow(2, length) - 1 toInt
  }

}

object SportsDataParser extends SportDataPattern {


  /**
   * Parses the raw sport event data to sport event object
   * @param rawSportEventData
   * @return
   */
  def parse(rawSportEventData: String): SportEvent = {
    Try(getSportingEvent(rawSportEventData))
      .getOrElse(SportEvent(rawSportEventData, false, 0, 0, 0, 0, 0))

  }

  /**
   * Helper method to get generate SportEvent object from raw string
   * @param rawSportEventData
   * @return
   */
  private def getSportingEvent(rawSportEventData: String): SportEvent = {
    val hexData = Integer.parseInt(rawSportEventData.substring(2), 16)
    val pointsScored = getDataValue(hexData, pointsScoredBitStartPos, pointsScoredMaxVal)
    val teamScored = getDataValue(hexData, teamScoredBitStartPos, teamScoreMaxVal)
    val team2Points = getDataValue(hexData, team2PointsBitStartPos, team2PointsMaxVal)
    val team1points = getDataValue(hexData, team1PointsBitStartPos, team1PointsMaxVal)
    val timeElapsed = getDataValue(hexData, timeElapsedBitStartPos, timeElapsedMaxVal)
    SportEvent(rawSportEventData, true, timeElapsed, team1points, team2Points, teamScored, pointsScored)

  }

  /**
   * Helper method to get int value based on bit positions
   * @param data
   * @param bitStartPos
   * @param maxVal
   * @return
   */
  private def getDataValue(data: Int, bitStartPos: Int, maxVal: Int) = {
    (data >> bitStartPos) & maxVal
  }


}
