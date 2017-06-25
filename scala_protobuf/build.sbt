name := "scalapb"
version := "1.0"
scalaVersion := "2.12.1"
import com.trueaccord.scalapb.compiler.Version.scalapbVersion
version := "1.0"
scalaVersion := "2.12.1"
PB.pythonExe := "C:\\Python27\\Python.exe"

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies ++= Seq(
  "com.trueaccord.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"

)
