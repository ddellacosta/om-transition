(defproject om-transition "0.0.1"
  :description "Transition Component for Om"
  :url "http://github.com/ddellacosta/om-transition"
  :license {:name "Eclipse"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [om "0.6.2"]
                 [org.clojure/core.async "0.1.301.0-deb34a-alpha"]
                 [sablono "0.2.16"]
                 [ominate "0.1.0"]]

  :plugins [[lein-cljsbuild "1.0.3"]]

  :source-paths ["src"]

  ;; :cljsbuild {
  ;;   :builds [{:id "test"
  ;;             :source-paths ["src" "test"]
  ;;             :compiler {
  ;;               :preamble ["react/react.min.js"]
  ;;               :output-to "script/tests.simple.js"
  ;;               :output-dir "script/out"
  ;;               :source-map "script/tests.simple.js.map"
  ;;               :output-wrapper false
  ;;               :optimizations :simple}}
  ;;            ;; examples

  :cljsbuild {
    :builds [{:id "hello"
              :source-paths ["src" "examples/hello/src"]
              :compiler {
                :output-to "examples/hello/main.js"
                :output-dir "examples/hello/out"
                :source-map true
                :optimizations :none}}
             {:id "basic-easing"
              :source-paths ["src" "examples/basic_easing/src"]
              :compiler {
                :output-to "examples/basic_easing/main.js"
                :output-dir "examples/basic_easing/out"
                :optimizations :none
                :source-map true}}]})
