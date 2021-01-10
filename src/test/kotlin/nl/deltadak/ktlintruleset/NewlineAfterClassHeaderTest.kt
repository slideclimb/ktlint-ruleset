package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.LintError
import com.pinterest.ktlint.test.format
import com.pinterest.ktlint.test.lint
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.forExactly
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class NewlineAfterClassHeaderTest : StringSpec() {

    init {
        "no newline after class header" {
            val lintErrors = NewlineAfterClassHeader().lint("""
                class Hi {
                    override fun toString() = "bye"
                }
            """.trimIndent())

            lintErrors.forExactly(1) {
                it.shouldBe(LintError(1, 10, NewlineAfterClassHeader().id, "Missing newline after class header"))
            }
        }

        "no newline at start of companion object" {
            val lintErrors = NewlineAfterClassHeader().lint("""
                class Hi {
                
                    companion object {
                        const val WHAT = "bye"
                    }
                }
            """.trimIndent())

            lintErrors.forExactly(1) {
                it.shouldBe(LintError(3, 22, NewlineAfterClassHeader().id, "Missing newline after class header"))
            }
        }

        "with newline" {
            val lintErrors = NewlineAfterClassHeader().lint("""
                class Hi {
                
                    companion object {
                    
                        const val WHAT = "bye"
                    }
                }
            """.trimIndent())

            lintErrors.shouldBeEmpty()
        }

        "formatting" {
            NewlineAfterClassHeader().format("""
                class Hi {
                    companion object {
                        const val WHAT = "bye"
                    }
                }
            """.trimIndent()) shouldBe """
                class Hi {
                
                    companion object {
                
                        const val WHAT = "bye"
                    }
                }
            """.trimIndent()
        }
    }
}