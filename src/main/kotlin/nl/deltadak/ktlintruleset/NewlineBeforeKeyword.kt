package nl.deltadak.ktlintruleset

import com.pinterest.ktlint.core.Rule
import com.pinterest.ktlint.core.ast.ElementType
import com.pinterest.ktlint.core.ast.ElementType.WHITE_SPACE
import com.pinterest.ktlint.core.ast.prevLeaf
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafElement
import org.jetbrains.kotlin.com.intellij.psi.tree.IElementType

/**
 * Rule that states that the keyword should be on a new line.
 */
open class NewlineBeforeKeyword(private val keywordName: String, private val keywordElement: IElementType) : Rule("newline-before-$keywordName") {
    override fun visit(node: ASTNode, autoCorrect: Boolean, emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit) {
        if (node.elementType == keywordElement) {
            val prevLeaf = node.prevLeaf()

            // If the previous element is a white space but not a newline
            if (prevLeaf?.elementType == WHITE_SPACE && !prevLeaf.textContains('\n')) {
                val presumableCurly = prevLeaf.prevLeaf()
                // Check if the element before this white space is a right brace,
                // to prevent causing a warning on "if (true) 1 else 0"
                if (presumableCurly != null && presumableCurly.elementType == ElementType.RBRACE) {
                    emit(node.startOffset, "Missing newline before $keywordName", true)
                    // Auto correct by replacing the white space with a newline
                    if (autoCorrect) {
                        (prevLeaf as LeafElement).rawReplaceWithText(getIndent(presumableCurly) ?: "")
                    }
                }
            }
            // If the previous element is a rightbrace (there is no white space
            // between the brace and the keyword)
            else if (prevLeaf?.elementType == ElementType.RBRACE) {
                emit(node.startOffset, "Missing newline before $keywordName", true)

                // Auto correct by inserting the newline
                if (autoCorrect) {
                    (prevLeaf as LeafElement).rawReplaceWithText("}${getIndent(prevLeaf) ?: ""}")
                }
            }
        }
    }

    /**
     * Get the indent of the curly right brace.
     *
     * Returns null if the psi element before the curly right brace is not a white space.
     */
    private fun getIndent(rbrace: ASTNode): String? {
        val presumablyIndent = rbrace.prevLeaf() ?: return null
        if (presumablyIndent.elementType == WHITE_SPACE) {
            return presumablyIndent.text
        }
        return null
    }
}
