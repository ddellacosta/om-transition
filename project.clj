(defproject animation-proto "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [om "0.6.2"]
                 [sablono "0.2.16"]
                 [org.clojure/core.async "0.1.301.0-deb34a-alpha"]
                 [ominate "0.1.0"]]

  :plugins [[lein-cljsbuild "1.0.3"]]

  :source-paths ["src"]

  :cljsbuild { 
    :builds [{:id "animation-proto"
              :source-paths ["src"]
              :compiler {
                :output-to "animation_proto.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
