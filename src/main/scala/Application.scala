import com.img.matchstate.MatchState
import com.img.parser.SportsDataParser
import com.img.input.SportDataFileIn
import com.img.validation.SportEventValidator

/**
 * Created by pradeep on 21/02/2016.
 */
object Application extends App{

     import com.img.matchstate.MatchStateImplicits._

     val sportMatch = new MatchState()
     handleSportEvent("/sample1.txt", sportMatch)
     println("======== Reading Sample1.txt file =======")
     println("Latest event : "+sportMatch.getLastEvent.get)
     println("Last 5 event")
     sportMatch.getLastEvents(5).foreach(println)
     println("============ failed events ===============")
     sportMatch.getFailedEvents.foreach(println)

     val sportMatch2 = new MatchState()
     handleSportEvent("/sample2.txt", sportMatch2)
     println("======== Reading Sample2.txt file =======")
     println("Latest event : "+sportMatch2.getLastEvent.get)
     println("Last 5 event")
     sportMatch2.getLastEvents(5).foreach(println)
     println("============ failed events ===============")
     sportMatch2.getFailedEvents.foreach(println)



     private def handleSportEvent(fileName:String, matchState:MatchState): Unit ={
          val path = getClass.getResource(fileName).getPath
          val streamer = new SportDataFileIn(path)
          streamer.getData.map(SportsDataParser.parse).foreach(matchState.addEvent)
     }

}
