package io.github.robfletcher.time

import java.time.Instant
import java.time.ZoneId
import java.time.temporal.TemporalAmount

fun mutableClock(
  instant: Instant = Instant.now(),
  zone: ZoneId = ZoneId.systemDefault()
) = MutableClock(instant, zone)

operator fun MutableClock.plusAssign(amount: TemporalAmount) = advanceBy(amount)

var MutableClock.instant: Instant
  get() = instant()
  set(value) = instant(value)