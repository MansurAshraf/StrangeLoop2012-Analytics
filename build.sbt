
scalaVersion :="2.9.2"

seq(webSettings :_*)

libraryDependencies ++= Seq("org.mortbay.jetty" % "jetty" % "6.1.26" % "container",
"ch.qos.logback" % "logback-classic" % "1.0.6" % "runtime"
)
