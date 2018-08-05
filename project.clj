(defproject chip-8-disassembler "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.7"]]
  :main ^:skip-aot chip-8-disassembler.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
