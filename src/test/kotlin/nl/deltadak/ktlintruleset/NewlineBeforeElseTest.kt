package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.test.lint
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import org.intellij.lang.annotations.Language


class NewlineBeforeElseTest : StringSpec() {
    init {
        "no newline before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    "hi"
                }
                else {
                    "nothing"
                }
            """.trimIndent())
            lintErrors.forExactly(1) {
                it.shouldBe(LintError(3, 1, NewlineBeforeElse().id, "Expected newline before else"))
            }
        }

        "newline before else" {
            val lintErrors = NewlineBeforeElse().lint("""
                val temp = if (true) {
                    println("hi")
                }
                else {
                    println("nothing")
                }
            """.trimIndent())
            lintErrors.shouldBeEmpty()
        }
    }
}