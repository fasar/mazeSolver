
//libraryDependencies <+= (sbtVersion) { sv => "com.eed3si9n" %% "sbt-assembly" % ("sbt" + sv + "_0.5") }


addSbtPlugin("com.eed3si9n" %% "sbt-assembly" % "0.6")

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"


addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "0.11.0")
