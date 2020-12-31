package io.github.robfletcher.time

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Clock
import java.time.Duration
import java.time.Instant.EPOCH
import java.time.ZoneOffset.MAX
import java.time.ZoneOffset.MIN

class MutableClockSpec : DescribeSpec({

    var fixedClock: Clock? = null

    describe("mutable clock") {
        lateinit var subject: MutableClock
        beforeEach {
            subject = mutableClock()
            fixedClock = Clock.fixed(subject.instant(), subject.zone)
        }

        it("is equal to a fixed clock with the same instant and zone") {
            subject shouldBe fixedClock
        }

        it("is not equal to a fixed clock with a different instant") {
            subject shouldNotBe Clock.fixed(subject.instant.plusMillis(1), subject.zone)
        }

        it("is not equal to a fixed clock with a different time zone") {
            subject.withZone(MIN) shouldNotBe fixedClock!!.withZone(MAX)
        }

        describe("advancing time") {
            it("changes the instant reported by the clock") {
                subject += Duration.ofHours(1)
                subject.instant shouldBe fixedClock!!.instant() + Duration.ofHours(1)
            }

            describe("changing the instant") {
                beforeEach {
                    subject.instant = EPOCH

                }
                it("returns the new instant") {
                    subject.instant() shouldBe EPOCH
                }
            }
        }
    }
})
