package misc

/*
An awesome trick to sort list of lists (not necessarily of a homogeneous lengths)
From https://stackoverflow.com/questions/1467641/groovy-list-sort-by-first-second-then-third-elements/30443821#30443821
 */

class AwesomeListOfListsSort {
    static sortListOfLists = { lol ->
        lol.sort { l1, l2 ->
            def (e1, e2) = [l1, l2].transpose().find { e1, e2 ->
                e1 != e2
            }
            e1 <=> e2
        }
    }
}
