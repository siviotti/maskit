package br.net.maskit.maskservice

import br.net.maskit.*
import org.springframework.stereotype.Controller
import java.util.logging.Logger

const val SEQ = "122"
const val TABLE = "122"

@Controller
class MaskService {

    // Intenal Maskits
    private val maskit: NumericMaskit
    private val prefixMaskit: PrefixMaskit
    private val logger: Logger = Logger.getLogger(javaClass.canonicalName)

    init {
        logger.info("Constructor MaskService")
        // Enviroment Variables: 1. tableChars, 2. Simple Indexes, 3. Prefix Indexes
        val tableChars = randomChars()
        val simpleIndexes = indexesToString(randomIndexes(11))
        val prefixIndexes = indexesToString(randomIndexes(25))
        // Internal maskits
        maskit = numericMaskitOf(simpleIndexes, tableChars)
        prefixMaskit = prefixMaskitOf(prefixIndexes, tableChars)
        // Info
        logger.info("Mask Table Chars      : ${maskit.table.toPlainString()}")
        logger.info("Simple Swapper Indexes: ${maskit.swapper.indexes}")
        logger.info("Prefix Swapper Indexes: ${prefixMaskit.maskit.swapper.indexes}")
    }

    fun mask(id: NumericId) = maskit.mask(id)

    fun randomMask(id: NumericId) = maskit.randomMask(id)

    fun unmask(masked: Masked) = maskit.unmask(masked)

    fun dateMask(id: NumericId) = prefixMaskit.mask(id)

    fun dateRandomMask(id: NumericId) = prefixMaskit.randomMask(id)

    fun dateUnmask(masked: Masked) = prefixMaskit.unmask(masked)

}
