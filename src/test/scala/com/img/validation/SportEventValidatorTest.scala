package com.img.validation

import com.img.parser.SportEvent
import org.scalatest.FunSpec

/**
 * Created by pradeep on 21/02/2016.
 */
class SportEventValidatorTest extends FunSpec{


   describe("SportEVentValidator is initialized and contains rules"){

     it("is initialized and contains rules"){
         assert((SportEventValidator.getRules.size > 0) == true)
     }

     it("Contains rule which checks if the data format is invalid"){
       val newEvent = SportEvent("f1234",false,15,2,2,1,1)
       val lastEvent = SportEvent("f1234",true,10,3,2,1,1)
       assert(SportEventValidator.validate(newEvent, lastEvent) == false)
     }


     it("Contains rule which checks new event score is less than last event"){
          val newEvent = SportEvent("f1234",true,15,2,2,1,1)
          val lastEvent = SportEvent("f1234",true,10,3,2,1,1)
          assert(SportEventValidator.validate(newEvent, lastEvent) == false)
     }

     it("Contains rule which checks new event score is consistent with last event score"){
       val newEvent = SportEvent("f1234",true,15,2,2,1,1)
       val lastEvent = SportEvent("f1234",true,10,2,2,1,1)
       assert(SportEventValidator.validate(newEvent, lastEvent) == false)
     }

   }
}
