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

     it("is data format valid"){
       val newEvent = SportEvent("f1234",false,15,2,2,1,1)
       val lastEvent = SportEvent("f1234",true,10,3,2,1,1)
       assert(DataFormatValidationRule.validate(newEvent)(lastEvent) == false)
       assert(DataFormatValidationRule.validate(lastEvent)(newEvent) == true)
     }

     it("is data validation rule 1"){
       val newEvent = SportEvent("f1234",false,15,1,2,1,1)
       val newEvent2=  SportEvent("f1234",false,15,3,2,1,1)
       val lastEvent = SportEvent("f1234",true,10,3,2,1,1)
       assert(DataInconsistencyRule1.validate(newEvent)(lastEvent) == false)
       assert(DataInconsistencyRule1.validate(newEvent2)(lastEvent) == true)
     }

     it("is data validation rule 2"){
       val newEvent = SportEvent("f1234",false,15,3,2,1,1)
       val newEvent2=  SportEvent("f1234",false,15,3,3,1,1)
       val lastEvent = SportEvent("f1234",true,10,3,2,1,1)
       assert(DataInconsistencyRule2.validate(newEvent)(lastEvent) == false)
       assert(DataInconsistencyRule2.validate(newEvent2)(lastEvent) == true)
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
