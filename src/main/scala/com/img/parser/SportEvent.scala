package com.img.parser

/**
 * Created by pradeep on 21/02/2016.
 */

/**
 * Value object storing the sport event
 * @param rawData
 * @param valid
 * @param timeElapsed
 * @param team1Point
 * @param team2Point
 * @param teamScored
 * @param pointScored
 */
case class SportEvent(rawData: String, valid: Boolean, timeElapsed: Int, team1Point: Int, team2Point: Int, teamScored: Int, pointScored: Int)
