package com.img.validation

import com.img.parser.SportEvent

/**
 * Created by pradeep on 21/02/2016.
 */

/**
  * Trait representing the validation rule
  */
trait ValidationRule {
  def validate(newEvent: SportEvent)( lastEvent: SportEvent): Boolean
}

/**
 * Rule checks if data is corrupted
 */
object DataFormatValidationRule extends ValidationRule {
  override def validate(newEvent: SportEvent)( lastEvent: SportEvent): Boolean = newEvent.valid
}

/**
 * Rule checks if the score in the new event is less than the score in last event
 */
object DataInconsistencyRule1 extends ValidationRule {
  override def validate(newEvent: SportEvent)( lastEvent: SportEvent) = (newEvent, lastEvent) match {
      case (ne, le) if(ne.timeElapsed > le.timeElapsed) => le.team1Point <= ne.team1Point && le.team2Point <= ne.team2Point
      case _                                            => true
    }
}

/**
 * Rule checks if the team has scored, the teams last score plus the points scored is equals
 * to the current score in the event
 */
object DataInconsistencyRule2 extends ValidationRule {
  override def validate(newEvent: SportEvent)( lastEvent: SportEvent) = (newEvent, lastEvent) match{
    case (ne, le) if(ne.teamScored == 0 && ne.timeElapsed > le.timeElapsed) =>
                                        le.team1Point + ne.pointScored == ne.team1Point
    case (ne, le) if(ne.teamScored == 1 && ne.timeElapsed > le.timeElapsed) =>
                                        le.team2Point + ne.pointScored == ne.team2Point
    case  _                                                                  => false
  }
}

/**
  * trait representing the EventValidator
  */
trait EventValidator{

   def validate(newEvent: SportEvent, lastEvent: SportEvent):Boolean
}

/**
  * Class representing the SportEvent validator
  * @param validationRules
  */
class SportEventValidator(validationRules : List[ValidationRule]) extends EventValidator{

  def validate(newEvent: SportEvent, lastEvent: SportEvent) = {
    validationRules.map(rule => rule.validate(newEvent) (lastEvent)).reduce(_ && _)
  }

  def getRules = validationRules
}

/**
 * Validator for sports events
 */
object SportEventValidator  extends EventValidator{
  val validationRules = List(DataFormatValidationRule, DataInconsistencyRule1, DataInconsistencyRule2)

  def apply(): Unit = {
     new SportEventValidator(validationRules)
  }

  /**
   * Returns list of validation rules
   * @return
    **/
  def getRules = validationRules

  /**
   * Validate the new event with last rule in the system
   * @param newEvent
   * @param lastEvent
   * @return
    */
  def validate(newEvent: SportEvent, lastEvent: SportEvent) = {
    validationRules.map(rule => rule.validate(newEvent)( lastEvent)).reduce(_ && _)
  }

}
