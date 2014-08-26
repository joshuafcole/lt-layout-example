(defproject layout-example "0.1.0-SNAPSHOT"
  :description "A testing ground for the future of LT's layout."
  :url "http://github.com/joshuafcole/lt-layout-example"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2156"]
                 [ring "1.2.1"]
                 [hiccup "1.0.5"]
                 [crate "0.2.5"]
                 [jayq "2.5.2"]]
  :plugins [[lein-cljsbuild "1.0.2"]
            [lein-ring "0.8.10"]]
  :hooks [leiningen.cljsbuild]
  :source-paths ["src/clj"]
  :cljsbuild {
    :builds {
      :main {
        :source-paths ["src/cljs"]
        :compiler {:output-to "resources/public/js/cljs.js"
                   :optimizations :simple
                   :pretty-print true}
        :jar true}}}
  :main layout-example.server
  :ring {:handler layout-example.server/app})

