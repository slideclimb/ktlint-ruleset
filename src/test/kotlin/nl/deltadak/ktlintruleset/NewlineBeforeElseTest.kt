package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.test.lint
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe


class NewlineBeforeElseTest : StringSpec() {
    init {
        "no newline before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    "hi"
                } else {
                    "nothing"
                }
            """.trimIndent())
            lintErrors.forExactly(1) {
                it.shouldBe(LintError(3, 3, NewlineBeforeElse().id, "Missing newline before else"))
            }
        }

        "curly brace before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    "hi"
                }else {
                    "nothing"
                }
            """.trimIndent())
            lintErrors.forExactly(1) {
                it.shouldBe(LintError(3, 2, NewlineBeforeElse().id, "Missing newline before else"))
            }
        }

        "newline before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    "hi"
                }
                else {
                    "nothing"
                }
            """.trimIndent())
            lintErrors.shouldBeEmpty()
        }

        "newline between comment and else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    "hi"
                }
                // This is a comment.
                else {
                    "nothing"
                }
            """.trimIndent())
        }

        "expected no newline before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) "hi" else "nothing"
            """.trimIndent())
            lintErrors.shouldBeEmpty()
        }
    }
}