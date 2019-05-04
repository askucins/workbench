package misc.traversing

import groovy.transform.Canonical

@Canonical
class TreeNode {
    def value = null
    List<TreeNode> children = []

    Boolean hasChildren() {
        this.children ? this.children.size() > 0 : false
    }

    Boolean isEmpty() {
        this.value == null & !hasChildren()
    }
}
