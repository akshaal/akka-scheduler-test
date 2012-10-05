import akka.actor._
import scala.concurrent.util.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Test extends App {
    var ticks = 0
    val system = ActorSystem("Test")

    val freq = 300.milliseconds
    val interval = 60.seconds
    val expected = (interval / freq).toInt

    val counter = system.actorOf(Props[Counter])
    Thread.sleep (interval.toMillis)
    system.shutdown()

    println("Ticks: " + ticks)
    println("Expected: " + expected)
    println("Actual frequency was " + (interval / ticks).toMillis + " instead of expected " + freq.toMillis + " milliseconds")
    println("Accuracy: " + (ticks.toDouble / expected * 100.0).toInt + "%")
}

class Counter extends Actor {
    object Tick

    override def preStart() = {
        context.system.scheduler.schedule(0.milliseconds, Test.freq, self, Tick)
    }

    override def receive = {
        case Tick =>
            println("...tick...")
            Test.ticks += 1
    }
}

class Counter2 extends Actor {
    object Tick

    override def preStart() = {
        context.system.scheduler.schedule(0.milliseconds, Test.freq) {
            self ! Tick
        }
    }

    override def receive = {
        case Tick =>
            println("...tick2...")
            Test.ticks += 1
    }
}
