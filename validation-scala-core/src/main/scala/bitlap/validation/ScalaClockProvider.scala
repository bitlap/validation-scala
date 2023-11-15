package bitlap.validation

import java.time.Clock

import jakarta.validation.ClockProvider

final class ScalaClockProvider(clock: Clock = Clock.systemDefaultZone()) extends ClockProvider {
  override def getClock: Clock = clock
}
