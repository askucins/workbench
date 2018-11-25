package misc

import spock.lang.Specification

import static misc.AwesomeListOfListsSort.sortListOfLists

class AwesomeListOfListsSortSpec extends Specification {

    def "should sort list of lists"() {
        expect:
        verifyAll {
            sortListOfLists([]) == []
            sortListOfLists([[]]) == [[]]
            sortListOfLists([[2, 1, 3], [2, 0, 5], [3, 1]]) == [[2, 0, 5], [2, 1, 3], [3, 1]]
            sortListOfLists([[2, 1, 3], [2, 0, 5], [1, 5]]) == [[1, 5], [2, 0, 5], [2, 1, 3]]
            sortListOfLists([[3], [2, 1], [1, 2, 3]]) == [[1, 2, 3], [2, 1], [3]]
            sortListOfLists([[2, 2], [2, 1, 3], [2, 1, 2]]) == [[2, 1, 2], [2, 1, 3], [2, 2]]
            sortListOfLists([[1, 1], [2, 1, 3], [2, 1, 2]]) == [[1, 1], [2, 1, 2], [2, 1, 3]]
            sortListOfLists([[1, 1], [1, 1]]) == [[1, 1], [1, 1]]
        }
    }
}

