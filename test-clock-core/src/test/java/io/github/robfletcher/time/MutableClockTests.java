package io.github.robfletcher.time;

import java.time.Clock;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import static java.time.Clock.fixed;
import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.MAX;
import static java.time.ZoneOffset.MIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MutableClockTests {

  @Test
  void canCompareWithOtherClocks() {
    MutableClock testClock = new MutableClock();
    Clock fixedClock = testClock.toFixed();

    assertEquals(testClock, fixedClock);

    assertNotEquals(testClock, fixed(testClock.instant().plusMillis(1), testClock.getZone()));
    assertNotEquals(testClock.withZone(MIN), fixedClock.withZone(MAX));
  }

  @Test
  void canAdvanceTime() {
    // tag::create[]
    MutableClock testClock = new MutableClock();
    // end::create[]
    // tag::convert-to-fixed[]
    Clock fixedClock = testClock.toFixed();
    assertEquals(testClock.instant(), fixedClock.instant());
    // end::convert-to-fixed[]

    // tag::advance-time[]
    testClock.advanceBy(Duration.ofHours(1));
    assertEquals(testClock.instant(), fixedClock.instant().plus(Duration.ofHours(1)));
    // end::advance-time[]

    testClock.advanceBy(Duration.ofHours(1));
    assertEquals(testClock.instant(), fixedClock.instant().plus(Duration.ofHours(2)));
  }

  @Test
  void canChangeInstant() {
    MutableClock testClock = new MutableClock();
    Clock fixedClock = testClock.toFixed();

    // tag::assign-instant[]
    testClock.instant(EPOCH);
    assertEquals(testClock.instant(), EPOCH);
    // end::assign-instant[]

    testClock.instant(fixedClock.instant());
    assertEquals(testClock.instant(), fixedClock.instant());
  }
}
