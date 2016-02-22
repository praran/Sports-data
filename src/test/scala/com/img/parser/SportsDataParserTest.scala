package com.img.parser

import org.scalatest.FunSpec

/**
 * Created by pradeep on 21/02/2016.
 */
class SportsDataParserTest extends FunSpec{

   describe("Sports data parser"){

     val sportsDataParser =  SportsDataParser;

     it(" is initialized"){
         assert(sportsDataParser != null)
     }

     it(" for invalid data creates invalid sport event"){
       val sportEvent = sportsDataParser.parse("Invalid")
       assert(sportEvent.valid == false)
     }

     it(" parses valid sports data "){
       val data = "0xf0101f"
       val sportsEvent = sportsDataParser.parse(data)
       assert(sportsEvent.valid == true)
       assert(sportsEvent.pointScored == 3)
       assert(sportsEvent.teamScored == 1)
       assert(sportsEvent.team1Point == 2)
       assert(sportsEvent.team2Point == 3)
       assert(sportsEvent.timeElapsed == 30)
     }
   }
}
