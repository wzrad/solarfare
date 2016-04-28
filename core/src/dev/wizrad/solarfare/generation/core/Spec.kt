package dev.wizrad.solarfare.generation.core

import dev.wizrad.solarfare.support.extensions.findMapped
import dev.wizrad.solarfare.support.extensions.rand
import dev.wizrad.solarfare.support.extensions.upto
import dev.wizrad.solarfare.support.Tag
import dev.wizrad.solarfare.support.debug
import java.util.*

class Spec(
  val id: Identifier,
  val elements: List<SpecElement<Node>>) {

  /** A pseudo-element that tracks when to stop node generation */
  private val stop = Stop()

  // MARK: Generation
  val hasNext: Boolean
    get() = !stop.reached

  fun next(): Node? {
    val element = sampleElement()
    var node = if(element !== stop) {
      generateNode(element)
    } else {
      stopGenerating()
      null
    }

    return node
  }

  private fun generateNode(element: SpecElement<Node>): Node? {
    val result = element.generate()

    // if we generated a node, increase the stop weight appropriately
    if(result != null) {
      stop.weight += element.stopGrowth
    }

    if(result != null) {
      debug(Tag.GENERATION, "$this generated child -> $result, weight -> ${element.weight}")
    } else {
      debug(Tag.GENERATION, "$this failed to generate a child")
    }

    return result
  }

  private fun stopGenerating() {
    stop.reached = true
    debug(Tag.GENERATION, "$this did stop generating")
  }

  // MARK: Sampling
  private fun sampleElement(): SpecElement<Node> {
    // return first valid element searching by descending priority; the `stop` fallback shouldn't be
    // hit, as it's returned by the priority-based override
    return priorities.findMapped { sampleElement(it) } ?: stop
  }

  private fun sampleElement(priority: SpecElement.Priority): SpecElement<Node>? {
    var weight = totalWeight(priority)

    // if no element has any weight at this priority, short-circuit
    if(weight <= 0) {
      return null
    }

    debug(Tag.GENERATION, "$this sampling priority -> $priority")

    // fallback to `stop` instead of `null` for non-required priorities, and include its weight
    // when sampling
    var fallback: SpecElement<Node>? = null
    if(priority != SpecElement.Priority.Required) {
      fallback = stop
      weight += stop.weight;
    }

    // sample a value in the total weight
    var sample = rand().upto(weight)
    val result = elements
      .filter { it.priority == priority }
      .find {
        sample -= it.weight
        sample <= 0
      }

    return result ?: fallback
  }

  private fun totalWeight(priority: SpecElement.Priority): Int {
    return elements.filter { it.priority == priority }.sumBy { it.weight }
  }

  private val priorities: Array<SpecElement.Priority>
    get() = SpecElement.Priority.values()

  // MARK: Debugging
  override fun toString(): String {
    return "[spec: $id]"
  }

  // MARK: Building
  companion object {
    fun start(tag: String): Builder {
      return Builder(tag)
    }
  }

  class Builder(tag: String) {
    private val id: Identifier = Identifier.next(tag)
    private val elements = ArrayList<SpecElement<Node>>()

    // MARK: Elements
    fun <N: Node> child(factory: () -> N): SpecElement<N> {
      val element = SpecElement(factory)
      elements.add(element)
      return element
    }

    fun end(): Spec {
      return Spec(id, elements)
    }
  }

  private class Stop: SpecElement<Node>({ Node("stop") }) {
    var reached: Boolean = false
  }
}