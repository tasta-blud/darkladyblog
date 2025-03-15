package darkladyblog.darkladyblog.common.util

import kotlin.test.Test
import kotlin.test.assertEquals

class RangedTest {

    @Test
    fun testToRange() {
        assertEquals(5, 5.toRanged(0..10))
        assertEquals(10, 11.toRanged(0..10))
        assertEquals(0, 0.toRanged(0..10))
        assertEquals(0, (-1).toRanged(0..10))
    }

}