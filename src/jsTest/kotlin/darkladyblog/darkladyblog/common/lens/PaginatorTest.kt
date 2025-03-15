package darkladyblog.darkladyblog.common.lens

import darkladyblog.darkladyblog.common.model.Paginator
import kotlin.test.Test
import kotlin.test.assertEquals

class PaginatorTest {

    @Test
    fun test0() {
        val paginator = Paginator(10, 5L, 5)
//        paginator.offset()
        assertEquals(2, paginator.pages, "pages $paginator")
        assertEquals(2, paginator.page, "page $paginator")
    }

    @Test
    fun test1() {
        val paginator = Paginator(10, 5L, 5)
        assertEquals(2, paginator.pages, "pages $paginator")
        assertEquals(2, paginator.page, "page $paginator")
    }

    @Test
    fun test2() {
        val paginator = Paginator(10, 2, 5)
        assertEquals(2, paginator.pages, "$paginator")
        assertEquals(5, paginator.offset, "$paginator")
    }

    @Test
    fun test3() {
        val paginator = Paginator(100, 2, 10)
        assertEquals(10, paginator.pages, "$paginator")
        assertEquals(10, paginator.offset, "$paginator")
    }

    @Test
    fun test4() {
        val paginator = Paginator(99, 2, 10)
        assertEquals(10, paginator.pages, "$paginator")
        assertEquals(10, paginator.offset, "$paginator")
    }

    @Test
    fun test5() {
        val paginator = Paginator(99, 98L, 10)
        assertEquals(10, paginator.pages, "$paginator")
        assertEquals(10, paginator.page, "$paginator")
    }

}