package io.github.robfletcher.time;

import java.time.Clock;
import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.time.Clock.fixed;
import static java.time.Instant.EPOCH;
import static java.time.ZoneOffset.MAX;
import static java.time.ZoneOffset.MIN;
import static org.junit.jupiter.api.Assertions.*;

class MutableClockTests {

  private MutableClock clock;
  private Clock fixedClock;

  @BeforeEach
  void createClock() {
    clock = new MutableClock();
    fixedClock = Clock.fixed(clock.instant(), clock.getZone());
  }

  @Test
  void canCompareWithOtherClocks() {
    assertAll("fixed",
        () -> assertEquals(clock, fixedClock),
        () -> assertNotEquals(clock, fixed(clock.instant().plusMillis(1), clock.getZone())),
        () -> assertNotEquals(clock.withZone(MIN), fixedClock.withZone(MAX))
    );
  }

  @Test
  void canAdvanceTime() {
    clock.advanceBy(Duration.ofHours(1));
    assertEquals(clock.instant(), fixedClock.instant().plus(Duration.ofHours(1)));
    clock.advanceBy(Duration.ofHours(1));
    assertEquals(clock.instant(), fixedClock.instant().plus(Duration.ofHours(2)));
  }

  @Test
  void canChangeInstant() {
    clock.instant(EPOCH);
    assertEquals(clock.instant(), EPOCH);
    clock.instant(fixedClock.instant());
    assertEquals(clock.instant(), fixedClock.instant());
  }
}