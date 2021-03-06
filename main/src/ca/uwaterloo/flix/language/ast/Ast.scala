/*
 * Copyright 2015-2016 Magnus Madsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.uwaterloo.flix.language.ast

import java.nio.file.Path

import ca.uwaterloo.flix.api.Flix

/**
  * A collection of AST nodes that are shared across multiple ASTs.
  */
object Ast {

  /**
    * A common super-type for inputs.
    */
  sealed trait Input

  object Input {

    /**
      * A source that is backed by an internal resource.
      */
    case class Internal(name: String, text: String) extends Input

    /**
      * A source that is backed by a regular string.
      */
    case class Str(text: String) extends Input

    /**
      * A source that is backed by a regular file.
      */
    case class TxtFile(path: Path) extends Input

    /**
      * A source that is backed by flix package file.
      */
    case class PkgFile(path: Path) extends Input

  }

  /**
    * A source is a name and an array of character data.
    */
  case class Source(name: String, data: Array[Char]) {
    def format: String = name

    override def equals(o: scala.Any): Boolean = o match {
      case that: Source => this.name == that.name
    }

    override def hashCode(): Int = name.hashCode
  }

  /**
    * A common super type for AST nodes that represent annotations.
    */
  trait Annotation

  object Annotation {

    /**
      * An AST node that represents a `@benchmark` annotation.
      *
      * A function marked with `benchmark` is evaluated as part of the benchmark framework.
      *
      * @param loc the source location of the annotation.
      */
    case class Benchmark(loc: SourceLocation) extends Annotation {
      override def toString: String = "@benchmark"
    }

    /**
      * An AST node that represents a `@law` annotation.
      *
      * A `law` function is a property (theorem) about the behaviour of one or more functions.
      *
      * @param loc the source location of the annotation.
      */
    case class Law(loc: SourceLocation) extends Annotation {
      override def toString: String = "@law"
    }

    /**
      * An AST node that represents a `@test` annotation.
      *
      * A function marked with `test` is evaluated as part of the test framework.
      *
      * @param loc the source location of the annotation.
      */
    case class Test(loc: SourceLocation) extends Annotation {
      override def toString: String = "@test"
    }

    /**
      * An AST node that represents an `@unchecked` annotation.
      *
      * The properties of a function marked `@unchecked` are not checked by the verifier.
      *
      * E.g. if a function is marked @commutative and @unchecked then
      * no attempt is made to check that the function is actually commutative.
      * However, the compiler and run-time is still permitted to assume that the
      * function is commutative.
      *
      * @param loc the source location of the annotation.
      */
    case class Unchecked(loc: SourceLocation) extends Annotation {
      override def toString: String = "@unchecked"
    }

  }

  /**
    * Companion object of [[Annotations]].
    */
  object Annotations {
    /**
      * The empty sequence of annotations.
      */
    val Empty: Annotations = Annotations(Nil)
  }

  /**
    * A sequence of annotations.
    */
  case class Annotations(annotations: List[Annotation]) {

    /**
      * Returns `true` if `this` sequence contains the `@benchmark` annotation.
      */
    def isBenchmark: Boolean = annotations exists (_.isInstanceOf[Annotation.Benchmark])

    /**
      * Returns `true` if `this` sequence contains the `@law` annotation.
      */
    def isLaw: Boolean = annotations exists (_.isInstanceOf[Annotation.Law])

    /**
      * Returns `true` if `this` sequence contains the `@test` annotation.
      */
    def isTest: Boolean = annotations exists (_.isInstanceOf[Annotation.Test])

    /**
      * Returns `true` if `this` sequence contains the `@unchecked` annotation.
      */
    def isUnchecked: Boolean = annotations exists (_.isInstanceOf[Annotation.Unchecked])
  }

  /**
    * Documentation.
    *
    * @param lines the lines of the comments.
    * @param loc   the source location of the text.
    */
  case class Doc(lines: List[String], loc: SourceLocation) {
    def text: String = lines.mkString("\n")
  }

  /**
    * Companion object of [[Modifiers]].
    */
  object Modifiers {
    /**
      * The empty sequence of modifiers.
      */
    val Empty: Modifiers = Modifiers(Nil)
  }

  /**
    * A sequence of modifiers.
    */
  case class Modifiers(mod: List[Modifier]) {
    /**
      * Returns `true` if these modifiers contain the inline modifier.
      */
    def isInline: Boolean = mod contains Modifier.Inline

    /**
      * Returns `true` if these modifiers contain the public modifier.
      */
    def isPublic: Boolean = mod contains Modifier.Public

    /**
      * Returns `true` if these modifiers contain the synthetic modifier.
      */
    def isSynthetic: Boolean = mod contains Modifier.Synthetic
  }

  /**
    * A common super-type for modifiers.
    */
  sealed trait Modifier

  object Modifier {

    /**
      * The inline modifier.
      */
    case object Inline extends Modifier

    /**
      * The public modifier.
      */
    case object Public extends Modifier

    /**
      * The synthetic modifier.
      */
    case object Synthetic extends Modifier

  }

  /**
    * A common super-type for the polarity of an atom.
    */
  sealed trait Polarity

  object Polarity {

    /**
      * The atom is positive.
      */
    case object Positive extends Polarity

    /**
      * The atom is negative.
      */
    case object Negative extends Polarity

  }

  /**
    * Represents a dependency between two predicate symbols.
    */
  sealed trait DependencyEdge

  object DependencyEdge {

    /**
      * Represents a positive labelled edge.
      */
    case class Positive(head: Symbol.PredSym, body: Symbol.PredSym) extends DependencyEdge

    /**
      * Represents a negative labelled edge.
      */
    case class Negative(head: Symbol.PredSym, body: Symbol.PredSym) extends DependencyEdge

  }

  object DependencyGraph {
    /**
      * The empty dependency graph.
      */
    val Empty: DependencyGraph = DependencyGraph(Set.empty)

  }

  /**
    * Represents a dependency graph; a set of dependency edges.
    */
  case class DependencyGraph(xs: Set[DependencyEdge])


  object Stratification {
    /**
      * Represents the empty stratification.
      */
    val Empty: Stratification = Stratification(Map.empty)
  }

  /**
    * Represents a stratification that maps every predicate symbol to its stratum.
    */
  case class Stratification(m: Map[Symbol.PredSym, Int])

  /**
    * A hole context consists of a hole symbol and its type together with the local environment.
    */
  case class HoleContext(sym: Symbol.HoleSym, tpe: Type, env: Map[Symbol.VarSym, Type])

  /**
    * Represents that the annotated element is introduced by the class `clazz`.
    */
  case class IntroducedBy(clazz: java.lang.Class[_]) extends scala.annotation.StaticAnnotation

  /**
    * Represents that the annotated element is eliminated by the class `clazz`.
    */
  case class EliminatedBy(clazz: java.lang.Class[_]) extends scala.annotation.StaticAnnotation


  /**
    * Returns a fresh unique identifier.
    */
  def freshUId()(implicit flix: Flix): UId = {
    new UId(flix.genSym.freshId())
  }

  /**
    * A unique reference to an expression.
    *
    * @param id the globally unique name of the symbol.
    */
  final class UId(val id: Int) {
    /**
      * Returns `true` if this symbol is equal to `that` symbol.
      */
    override def equals(obj: scala.Any): Boolean = obj match {
      case that: UId => this.id == that.id
      case _ => false
    }

    /**
      * Returns the hash code of this symbol.
      */
    override val hashCode: Int = id
  }

}
