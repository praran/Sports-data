package com.img.matchstate

import com.img.parser.SportEvent
import org.scalatest.FunSpec


/**
 * Created by pradeep on 21/02/2016.
 */
class MatchStateTest extends FunSpec {

  import com.img.matchstate.MatchStateImplicits._

  describe("Match state") {


    it("is initialized") {
      val matchState = new MatchState()
      assert(matchState != null)
    }

    it("adds valid sport events to its state") {
      val matchState = new MatchState()
      val sportEvent = SportEvent("f1243", true, 0, 0, 0, 0, 0)
      assert(matchState.addEvent(sportEvent) == true)
    }


    it("does not add invalid sport events to its state"){
      val matchState = new MatchState()
      val sportEvent = SportEvent("f1243", false, 0, 0, 0, 0, 0)
      assert(matchState.addEvent(sportEvent) == false)
      assert(matchState.getFailedEvents.size == 1)
    }

    it("returns events from its state "){
      val matchState = new MatchState()
      val sportEvent = SportEvent("f1243", true, 0, 0, 0, 0, 0)
      matchState.addEvent(sportEvent)
      val events =matchState.getLastEvents(1)
      assert(events.size == 1)
      assert(events.head == sportEvent)
    }

    it("returns no event when queried for latest event and state is empty"){
      val matchState = new MatchState()
      val event =matchState.getLastEvent
      assert(event.equals(None))
    }

    it("returns latest event when queried for latest event"){
      val matchState = new MatchState()
      val sportEvent1 = SportEvent("f1243", true, 30, 0, 0, 0, 0)
      val sportEvent2 = SportEvent("f1243", true, 10, 0, 0, 0, 0)
      matchState.addEvent(sportEvent1)
      matchState.addEvent(sportEvent2)
      val event =matchState.getLastEvent
      assert(event.get.equals(sportEvent1))
    }


    it("returns empty list when queried for  n event and state is empty"){
      val matchState = new MatchState()
      val actualEvents =matchState.getLastEvents(2)
      assert(actualEvents.size == 0)
    }

    it("returns 2 events when queried for last 3 event when state contains only 2 events"){
      val matchState = new MatchState()
      val sportEvent1 = SportEvent("f1243", true, 10, 0, 0, 0, 0)
      val sportEvent2 = SportEvent("f2345", true, 15, 0, 0, 0, 0)
      matchState.addEvent(sportEvent1)
      matchState.addEvent(sportEvent2)
      val actualEvents =matchState.getLastEvents(3)
      val expectedEvents = sportEvent1 :: sportEvent2 :: Nil
      assert(actualEvents.size == 2)
      assert(areEqual(actualEvents, expectedEvents) == true)
    }

    it("returns latest n event when queried"){
      val matchState = new MatchState()
      val sportEvent1 = SportEvent("f1243", true, 10, 0, 0, 0, 0)
      val sportEvent2 = SportEvent("f2345", true, 15, 0, 0, 0, 0)
      val sportEvent3 = SportEvent("f5432", true, 20, 0, 0, 0, 0)
      matchState.addEvent(sportEvent1)
      matchState.addEvent(sportEvent2)
      matchState.addEvent(sportEvent3)
      val actualEvents =matchState.getLastEvents(2)
      val expectedEvents = sportEvent1 :: sportEvent3 :: Nil
      assert(areEqual(actualEvents, expectedEvents) == true)
    }

    it("returns all events when queried for"){
      val matchState = new MatchState()
      val sportEvent1 = SportEvent("f1243", true, 10, 0, 0, 0, 0)
      val sportEvent2 = SportEvent("f2345", true, 15, 0, 0, 0, 0)
      val sportEvent3 = SportEvent("f5432", true, 20, 0, 0, 0, 0)
      matchState.addEvent(sportEvent1)
      matchState.addEvent(sportEvent2)
      matchState.addEvent(sportEvent3)
      val actualEvents =matchState.getAllEvents
      val expectedEvents = sportEvent1 ::sportEvent2 :: sportEvent3 :: Nil
      assert(areEqual(actualEvents, expectedEvents) == true)
    }


  }

  private def areEqual(actualEvents:List[SportEvent], expectedEvents:List[SportEvent]):Boolean = {
      actualEvents.foldLeft(true)(_ && expectedEvents.contains(_))
  }

}
