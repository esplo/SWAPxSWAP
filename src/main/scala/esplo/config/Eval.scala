package esplo.config

// ref: http://d.hatena.ne.jp/xuwei/20140607/1402128646

import java.io.File

import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox


object Eval {

  def apply[A](string: String): A = {
    val toolbox = currentMirror.mkToolBox()
    val tree = toolbox.parse(string)
    toolbox.eval(tree).asInstanceOf[A]
  }

  def fromFile[A](file: File): A =
    apply(scala.io.Source.fromFile(file).mkString(""))

  def fromFileName[A](file: String): A =
    fromFile(new File(file))

}
