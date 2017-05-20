package io.github.robfletcher.time

import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.should.shouldMatch
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.api.lifecycle.CachingMode.SCOPE
import org.jetbrains.spek.subject.SubjectSpek
import java.time.Clock
import java.time.Clock.fixed
import java.time.Duration
import java.time.Instant.EPOCH
import java.time.ZoneOffset.MAX
import java.time.ZoneOffset.MIN

object MutableClockSpec : SubjectSpek<MutableClock>({

  var fixedClock: Clock? = null

  beforeGroup {
    fixedClock = Clock.fixed(subject.instant(), subject.zone)
  }

  describe("mutable clock") {
    subject(SCOPE) {
      mutableClock()
    }

    it("is equal to a fixed clock with the same instant and zone") {
      subject shouldMatch equalTo(fixedClock)
    }

    it("is not equal to a fixed clock with a different instant") {
      subject shouldMatch !equalTo(fixed(subject.instant.plusMillis(1), subject.zone))
    }

    it("is not equal to a fixed clock with a different time zone") {
      subject.withZone(MIN) shouldMatch !equalTo(fixedClock!!.withZone(MAX))
    }

    describe("advancing time") {
      on("advancing the clock") {
        subject += Duration.ofHours(1)
      }

      it("changes the instant reported by the clock") {
        subject.instant shouldMatch equalTo(fixedClock!!.instant() + Duration.ofHours(1))
      }

      given("and advanced again") {
        on("advancing the clock again") {
          subject += Duration.ofHours(1)
        }

        it("changes the instant reported by the clock") {
          subject.instant shouldMatch equalTo(fixedClock!!.instant() + Duration.ofHours(2))
        }
      }
    }

    describe("changing the instant") {
      on("changing the instant") {
        subject.instant = EPOCH
      }

      it("returns the new instant") {
        subject.instant() shouldMatch equalTo(EPOCH)
      }
    }
  }
})