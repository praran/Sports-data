package com.img.reactive

import akka.actor.ActorSystem
import akka.stream.{OverflowStrategy, ActorFlowMaterializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.img.matchstate.MatchState
import com.img.parser.{SportEvent, SportsDataParser}
import com.img.streaming.SportsDataFileStreamer
import com.img.validation.SportEventValidator

import scala.concurrent.Future

/**
 * Created by pradeep on 26/02/2016.
 */


trait SportDataGameActorSystem {
  implicit val actorSystem = ActorSystem()
  implicit val flowMaterializer = ActorFlowMaterializer()

  /**
   * Sports data file source
   */
  val sportsDataFileSource = (path: String) => Source(new SportsDataFileStreamer(path).getData)

  /**
   * Parser
   */
  val parser = Flow[String].map(str => SportsDataParser.parse(str))

  /**
   * Match state
   */
  val matchState = (state: MatchState) => Sink.foreach[SportEvent]{
    eve => if(SportEventValidator.validate(eve, state.getLastEvent.get)){
           state.addEvent(eve)
    }else{
          state.addFailedEvents(eve)
    }
  }
}

object SportDataGame extends SportDataGameActorSystem {

  import actorSystem.dispatcher
  def main(args: Array[String]) {

    import com.img.matchstate.MatchStateImplicits.sportEventValidator
    val path = getClass.getResource("/sample1.txt").getPath
    val state = new MatchState()

    val sys: Future[Unit] = sportsDataFileSource(path)
      .buffer(10, OverflowStrategy.dropHead)
      .via(parser)
      .runWith(matchState(state))


    sys.onSuccess {
      case _ => state.getLastEvents(5).foreach(println)
                actorSystem.shutdown()
                actorSystem.awaitTermination()
    }

  }
}
