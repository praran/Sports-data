package com.img.validation

import com.img.parser.SportEvent

/**
 * Created by pradeep on 21/02/2016.
 */


trait ValidationRule {
  def validate(newEvent: SportEvent, lastEvent: SportEvent): Boolean
}

/**
 * Rule checks if data is corrupted
 */
object DataFormatValidationRule extends ValidationRule {
  override def validate(newEvent: SportEvent, lastEvent: SportEvent): Boolean = {
    newEvent.valid
  }
}

/**
 * Rule checks if the score in the new event is less than the score in last event
 */
object DataInconsistencyRule1 extends ValidationRule {
  override def validate(newEvent: SportEvent, lastEvent: SportEvent): Boolean = {
    if (newEvent.timeElapsed > lastEvent.timeElapsed) {
      !(newEvent.team1Point < lastEvent.team1Point || newEvent.team2Point < lastEvent.team2Point)
    } else {
      true
    }
  }
}

/**
 * Rule checks if the team has scored, the teams last score plus the points scored is equals
 * to the current score in the event
 */
object DataInconsistencyRule2 extends ValidationRule {
  override def validate(newEvent: SportEvent, lastEvent: SportEvent): Boolean = {
    if (newEvent.timeElapsed > lastEvent.timeElapsed) {
      if (newEvent.teamScored == 0) {
        ((lastEvent.team1Point + newEvent.pointScored) == newEvent.team1Point)
      } else {
        ((lastEvent.team2Point + newEvent.pointScored) == newEvent.team2Point)
      }
    } else {
      true
    }
  }
}


/**
 * Validator for sports events
 */
object SportEventValidator {

  /**
   * List of rules for validating sports events
   */
  private val validationRules = List(DataFormatValidationRule
    , DataInconsistencyRule1
    , DataInconsistencyRule2)

  /**
   * Returns list of validation rules
   * @return
   */
  def getRules = validationRules

  /**
   * Validate the new event with last rule in the system
   * @param newEvent
   * @param lastEvent
   * @return
   */
  def validate(newEvent: SportEvent, lastEvent: SportEvent) = {
    validationRules.map(rule => rule.validate(newEvent, lastEvent)).reduce(_ && _)
  }


}