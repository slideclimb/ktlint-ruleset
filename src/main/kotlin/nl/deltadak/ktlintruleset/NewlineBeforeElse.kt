package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType.ELSE_KEYWORD
import com.pinterest.ktlint.core.ast.ElementType.RBRACE
import com.pinterest.ktlint.core.ast.ElementType.WHITE_SPACE
import com.pinterest.ktlint.core.ast.prevLeaf
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement

/**
 * Rule that states that the else keyword should be on a new line.
 */
class NewlineBeforeElse : Rule("newline-before-else") {
    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node.elementType == ELSE_KEYWORD) {
            val prevLeaf = node.prevLeaf()

            // If the previous element is a white space but not a newline
            if (prevLeaf?.elementType == WHITE_SPACE && !prevLeaf.textContains('\n')) {
                val presumableCurly = prevLeaf.prevLeaf()
                // Check if the element before this white space is a right brace,
                // to prevent causing a warning on "if (true) 1 else 0"
                if (presumableCurly != null && presumableCurly.elementType == RBRACE) {
                    emit(node.startOffset, "Missing newline before else", true)
                    // Auto correct by replacing the white space with a newline
                    if (autoCorrect) {
                        (prevLeaf as LeafElement).rawReplaceWithText("\n")
                    }
                }
            }
            // If the previous element is a rightbrace (there is no white space
            // between the brace and the keyword)
            else if (prevLeaf?.elementType == RBRACE) {
                emit(node.startOffset, "Missing newline before else", true)

                // Auto correct by inserting the newline
                if (autoCorrect) {
                    (prevLeaf as LeafElement).rawReplaceWithText("}\n")
                }
            }
        }
    }
}
