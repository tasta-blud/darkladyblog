package darkladyblog.darkladyblog.client.util

import org.w3c.dom.Node
import org.w3c.dom.NodeList

@Suppress("UNCHECKED_CAST")
fun <N : Node> NodeList.forEach(action: (N) -> Unit) {
    (0..length).forEach { i -> (item(i) as? N)?.let { action(it) } }
}
