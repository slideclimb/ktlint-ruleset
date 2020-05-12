package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.core.ast.prevLeaf
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * Default Hello class.
 */
class NewlineBeforeElse : Rule("newline-before-else") {
    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node.elementType == ELSE_KEYWORD) {
            val prevLeaf = node.prevLeaf()
        }
    }

}
