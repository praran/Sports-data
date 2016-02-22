package com.img.matchstate

import com.img.parser.SportEvent
import com.img.validation.SportEventValidator

import scala.collection.mutable
import scala.util.Try


/**
 * Created by pradeep on 21/02/2016.
 */
class MatchState {

  /**
   * Priority queue maintaining the state of valid events
   * order by time elapsed descending
   */
  private val state = mutable.PriorityQueue[SportEvent]()(EventOrdering)

  /**
   * Holder to store failed events for auditing and review
   */
  private val failedEvents = mutable.MutableList[SportEvent]()


  /**
   * Returns the latest event
   * @return
   */
  def getLastEvent = {
    Try(Some(state.head)).getOrElse(None)
  }

  /** returns the latest n events
    *
    * @param n
    * @return
    */
  def getLastEvents(n: Int): List[SportEvent] = {
    state.take(n).toList
  }


  /**
   * Adds events to Match
   * @param event
   * @return
   */
  def addEvent(event: SportEvent): Boolean = {
    if (state.headOption.isEmpty && event.valid) {
      state.enqueue(event)
      return true
    } else if (!state.headOption.isEmpty && SportEventValidator.validate(event, state.head)) {
      state.enqueue(event)
      return true
    } else {
      failedEvents.+=(event)
      false
    }

  }

  /**
   * gets the list of failed events
   * @return
   */
  def getFailedEvents = failedEvents.toList


  /**
   * Returns a list of events
   * @return
   */
  def getAllEvents = {
    state.toList
  }

  /**
   * Order events based on time elapsed descending
   */
  private object EventOrdering extends Ordering[SportEvent] {
    override def compare(x: SportEvent, y: SportEvent): Int = {
      if (x.timeElapsed > y.timeElapsed)
        1
      else
        -1
    }
  }


   def size() = state.size

}
